package com.redprojects.bot.actions.player;

import com.redprojects.utils.Log;
import com.redprojects.utils.Utils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTube {

    class Meta {
        public String num;
        public String type;
        public String ext;

        public Meta(String num, String ext, String type) {
            this.num = num;
            this.ext = ext;
            this.type = type;
        }
    }

    class Video {
        public String ext = "";
        public String type = "";
        public String url = "";

        public Video(String ext, String type, String url) {
            this.ext = ext;
            this.type = type;
            this.url = url;
        }
    }

    public ArrayList<Video> getStreamingUrisFromYouTubePage(String ytUrl) throws IOException {
        if (ytUrl == null)
            return null;

        // Remove any query params in query string after the watch?v=<vid> in
        // e.g.
        // http://www.youtube.com/watch?v=0RUPACpf8Vs&feature=youtube_gdata_player
        int andIdx = ytUrl.indexOf('&');
        if (andIdx >= 0)
            ytUrl = ytUrl.substring(0, andIdx);

        String html = Utils.executeGet(ytUrl);

        Log.console(html);

        // Parse the HTML response and extract the streaming URIs
        if (html.contains("verify-age-thumb")) {
            Log.console(this, "YouTube is asking for age verification. We can't handle that sorry.");
            return null;
        }

        if (html.contains("das_captcha")) {
            Log.console(this, "Captcha found, please try with different IP address.");
            return null;
        }

        Pattern p = Pattern.compile("url_encoded_fmt_stream_map\": \"(.*?)?\"");
        // Pattern p = Pattern.compile("/stream_map=(.[^&]*?)\"/");
        Matcher m = p.matcher(html);
        List<String> matches = new ArrayList<>();
        while (m.find())
            matches.add(m.group());
        System.out.println(matches.size());

        if (matches.size() != 1) {
            Log.console(this, "Found zero or too many stream maps.");
            return null;
        }

        String urls[] = matches.get(0).split(",");
        HashMap<String, String> foundArray = new HashMap<>();
        for (String ppUrl : urls) {
            String url = URLDecoder.decode(ppUrl, "UTF-8");

            Pattern p1 = Pattern.compile("itag=([0-9]+?)[&]");
            Matcher m1 = p1.matcher(url);
            String itag = null;
            if (m1.find())
                itag = m1.group(1);

            Pattern p2 = Pattern.compile("sig=(.*?)[&]");
            Matcher m2 = p2.matcher(url);
            String sig = null;
            if (m2.find())
                sig = m2.group(1);

            Pattern p3 = Pattern.compile("url=(.*?)[&]");
            Matcher m3 = p3.matcher(ppUrl);
            String um = null;
            if (m3.find())
                um = m3.group(1);

            if (itag != null && sig != null && um != null)
                foundArray.put(itag, URLDecoder.decode(um, "UTF-8") + "&" + "signature=" + sig);
        }

        if (foundArray.size() == 0) {
            Log.console(this, "Couldn't find any URLs and corresponding signatures");
            return null;
        }

        HashMap<String, Meta> typeMap = new HashMap<>();
        typeMap.put("13", new Meta("13", "3GP", "Low Quality - 176x144"));
        typeMap.put("17", new Meta("17", "3GP", "Medium Quality - 176x144"));
        typeMap.put("36", new Meta("36", "3GP", "High Quality - 320x240"));
        typeMap.put("5", new Meta("5", "FLV", "Low Quality - 400x226"));
        typeMap.put("6", new Meta("6", "FLV", "Medium Quality - 640x360"));
        typeMap.put("34", new Meta("34", "FLV", "Medium Quality - 640x360"));
        typeMap.put("35", new Meta("35", "FLV", "High Quality - 854x480"));
        typeMap.put("43", new Meta("43", "WEBM", "Low Quality - 640x360"));
        typeMap.put("44", new Meta("44", "WEBM", "Medium Quality - 854x480"));
        typeMap.put("45", new Meta("45", "WEBM", "High Quality - 1280x720"));
        typeMap.put("18", new Meta("18", "MP4", "Medium Quality - 480x360"));
        typeMap.put("22", new Meta("22", "MP4", "High Quality - 1280x720"));
        typeMap.put("37", new Meta("37", "MP4", "High Quality - 1920x1080"));
        typeMap.put("33", new Meta("38", "MP4", "High Quality - 4096x230"));

        ArrayList<Video> videos = new ArrayList<>();

        for (String format : typeMap.keySet()) {
            Meta meta = typeMap.get(format);

            if (foundArray.containsKey(format)) {
                Video newVideo = new Video(meta.ext, meta.type,
                        foundArray.get(format));
                videos.add(newVideo);
                Log.console(this, "YouTube Video streaming details: ext:" + newVideo.ext
                        + ", type:" + newVideo.type + ", url:" + newVideo.url);
            }
        }

        return videos;
    }

    /*private class YouTubePageStreamUriGetter extends YouTube {
        ProgressDialog progressDialog;

        protected String doInBackground(String... params) {
            String url = params[0];
            try {
                ArrayList<Video> videos = getStreamingUrisFromYouTubePage(url);
                if (videos != null && !videos.isEmpty()) {
                    String retVidUrl = null;
                    for (Video video : videos) {
                        if (video.ext.toLowerCase().contains("mp4")
                                && video.type.toLowerCase().contains("medium")) {
                            retVidUrl = video.url;
                            break;
                        }
                    }
                    if (retVidUrl == null) {
                        for (Video video : videos) {
                            if (video.ext.toLowerCase().contains("3gp")
                                    && video.type.toLowerCase().contains(
                                    "medium")) {
                                retVidUrl = video.url;
                                break;

                            }
                        }
                    }
                    if (retVidUrl == null) {

                        for (Video video : videos) {
                            if (video.ext.toLowerCase().contains("mp4")
                                    && video.type.toLowerCase().contains("low")) {
                                retVidUrl = video.url;
                                break;

                            }
                        }
                    }
                    if (retVidUrl == null) {
                        for (Video video : videos) {
                            if (video.ext.toLowerCase().contains("3gp")
                                    && video.type.toLowerCase().contains("low")) {
                                retVidUrl = video.url;
                                break;
                            }
                        }
                    }

                    return retVidUrl;
                }
            } catch (Exception e) {
                Log.console(this, "Couldn't get YouTube streaming URL");
                e.printStackTrace();
            }
            Log.console(this, "Couldn't get stream URI for " + url);
            return null;
        }
    }*/
}
