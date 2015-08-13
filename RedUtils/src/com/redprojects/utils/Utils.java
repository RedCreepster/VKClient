package com.redprojects.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Utils {

    public static String executeGet(String src) throws IOException {
        URL url = new URL(src);
        return inputStreamToString(url.openStream(), true);
    }

    public static String executePost(String targetURL, String[][] data) throws IOException {
        return executePost(targetURL, data, "", "", new byte[0], "text/plain");
    }

    public static String executePost(String targetURL, String[][] data, String postDataFieldName, String postDataName,
                                     byte[] postData, String postDataContentType) throws IOException {
        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.

        URL url = new URL(targetURL);
        URLConnection connection = url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);

        if (url.getProtocol().equals("https"))
            ((HttpsURLConnection) connection).setRequestMethod("POST");
        else
            ((java.net.HttpURLConnection) connection).setRequestMethod("POST");

        connection.setRequestProperty("Content-Type", "multipart/form-data, charset=utf-8; boundary=" + boundary);
        connection.setRequestProperty("Pragma", "no-cache");

        OutputStream os = connection.getOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");


        //==============================================================
        // Отправка POST данных
        //==============================================================
        for (int i = 0; i < data.length; i++) {
            osw.write("--" + boundary + "" +
                    "\r\n" +
                    "Content-Disposition: form-data; " +
                    "name=\"" + data[i][0] + "\"" +
                    "\r\n" +
                    "\r\n" +
                    data[i][1] +
                    ((i < data.length - 1) ? "\r\n" : ""));
        }
        /*for (String[] aData : data)
            osw.write("--" + boundary + "" +
                    "\r\n" +
                    "Content-Disposition: form-data; " +
                    "name=\"" + aData[0] + "\"" +
                    "\r\n" +
                    "\r\n" +
                    aData[1] +
                    "\r\n");
        os.write((
                "--" + boundary + "" +
                        "\r\n" +
                        "Content-Disposition: form-data; " +
                        "name=\"" + aData[0] + "\"" +
                        "\r\n" +
                        "\r\n" +
                        aData[1] +
                        "\r\n"
        ).getBytes());*/

        osw.flush();


        //==============================================================
        // Загрузка файла на сервер
        //==============================================================

        if (postData.length > 0) {
            // Отправка заголовка
            os.write((
                    "--" + boundary + "" +
                            "\r\n" +
                            "Content-Disposition: form-data; name=\"" + postDataFieldName + "\"; filename=\"" + postDataName + "\"" +
                            "\r\n" +
                            "Content-Type: " + postDataContentType +
                            "\r\n" +
                            "\r\n"
            ).getBytes());

            os.write(postData);

            // WRITE THE CLOSING BOUNDARY
            os.write((
                    "\r\n" +
                            "--" + boundary + "--"
            ).getBytes()); // another 2 new lines
        }

        os.flush();
        os.close();

        //==============================================================
        // Чтение буфера обмена
        //==============================================================

        return inputStreamToString(connection.getInputStream(), true);
    }

    public static String arrayToUrlParameters(String[][] array) {
        String buf = "?";
        for (String[] strings : array) {
            buf += strings[0] + "=" + strings[1] + "&";
        }
        return buf;
    }

    public static String concat(Object[] strings) {
        String result = "";
        for (Object object : strings)
            result += String.valueOf(object) + ",";
        return result.substring(0, result.length() - 1);
    }

    public static String inputStreamToString(InputStream inputStream, boolean close) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        StringWriter stringWriter = new StringWriter();
        char[] buffer = new char[4096];
        int length;
        while ((length = bufferedReader.read(buffer)) > 0)
            stringWriter.write(buffer, 0, length);
        if (close)
            inputStream.close();
        else
            inputStream.reset();
        return stringWriter.toString();
    }

    public static byte[] inputStreamToByteArray(InputStream inputStream, boolean close) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        copyStream(inputStream, buffer);

        if (close)
            inputStream.close();

        return buffer.toByteArray();
    }

    public static String[][] addElement(String[][] arr, String[] add) {
        String[][] buf = Arrays.copyOf(arr, arr.length + 1);
        buf[buf.length - 1] = add;
        return buf;
    }

    public static InputStream copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        if (!file.getParentFile().exists())
            if (!file.getParentFile().mkdirs())
                throw new IOException("Не удаётся создать директорию " + file.getParentFile());

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        copyStream(inputStream, bufferedOutputStream);
        bufferedOutputStream.close();

        return inputStream;
    }

    public static void copyStringToFile(String string, File file) throws IOException {
        copyInputStreamToFile(new ByteArrayInputStream(string.getBytes()), file);
    }

    public static void appendFile(File file, String string) {
        if (!file.getParentFile().exists() && file.getParentFile().mkdirs())
            return;
        try {
            FileWriter sw = new FileWriter(file, true);

            sw.write(string);

            sw.flush();
            sw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[4096];
        for (int length; (length = inputStream.read(buffer)) > 0; )
            outputStream.write(buffer, 0, length);
    }

    public static byte[] getBytes(InputStream inputStream, int length) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(length);

        byte[] buf = new byte[4096];
        for (int l; (l = inputStream.read(buf)) > 0; )
            buffer.put(buf, 0, l);

        return buffer.array();
    }

    public static Runnable getEmptyRunnable() {
        return new Runnable() {
            @Override
            public void run() {
            }
        };
    }
}
