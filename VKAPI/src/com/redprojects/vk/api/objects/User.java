package com.redprojects.vk.api.objects;

import org.json.JSONObject;

import java.net.URL;

//var xzA=document.getElementById('xzT').getElementsByTagName('tr');var xzxz=[];for(var i=0;i<xzA.length;i++)xzxz[i]=xzA[i].getElementsByTagName('td')[0].getElementsByTagName('b')[0].innerHTML;console.log(JSON.stringify(xzxz));
//TODO: Отдельныйе объекты для всех полей типа JSONObject
@SuppressWarnings("UnusedDeclaration")
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private boolean deactivated;
    private boolean hidden;

    private String photo_id;
    private boolean verified;
    private boolean blacklisted;
    private int sex;
    private String bdate;
    private JSONObject city;
    private JSONObject country;
    private String home_town;
    private URL photo_50;
    private URL photo_100;
    private URL photo_200_orig;
    private URL photo_200;
    private URL photo_400_orig;
    private URL photo_max;
    private URL photo_max_orig;
    private boolean online;
    private String lists;
    private String domain;
    private boolean has_mobile;
    private JSONObject contacts;
    private String site;
    private JSONObject education;
    private JSONObject universities;
    private JSONObject schools;
    private String status;
    private JSONObject last_seen;
    private int followers_count;
    private int common_count;
    private JSONObject counters;
    private JSONObject occupation;
    private String nickname;
    private JSONObject relatives;
    private int relation;
    private JSONObject personal;
    //Приходят в виде нескольких строк в json: acebook: '100003528591668',facebook_name: 'Онофрей Борис',twitter: 'RedCreepster'
    private JSONObject connections;
    private JSONObject exports;
    private boolean wall_comments;
    private String activities;
    private String interests;
    private String music;
    private String movies;
    private String tv;
    private String books;
    private String games;
    private String about;
    private String quotes;
    private boolean can_post;
    private boolean can_see_all_posts;
    private boolean can_see_audio;
    private boolean can_write_private_message;
    private boolean can_send_friend_request;
    private boolean is_favorite;
    private int timezone;
    private String screen_name;
    private String maiden_name;
    private JSONObject crop_photo;
    private int is_friend;
    private int friend_status;

    public User(JSONObject user) {
    }

    public User(int id, String firstName, String lastName, boolean deactivated, boolean hidden) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deactivated = deactivated;
        this.hidden = hidden;
    }

    public User(int id, String firstName, String lastName, boolean deactivated, boolean hidden,
                String photo_id, boolean verified, boolean blacklisted, int sex, String bdate,
                JSONObject city, JSONObject country, String home_town, URL photo_50, URL photo_100,
                URL photo_200_orig, URL photo_200, URL photo_400_orig, URL photo_max, URL photo_max_orig,
                boolean online, String lists, String domain, boolean has_mobile, JSONObject contacts,
                String site, JSONObject education, JSONObject universities, JSONObject schools,
                String status, JSONObject last_seen, int followers_count, int common_count,
                JSONObject counters, JSONObject occupation, String nickname, JSONObject relatives,
                int relation, JSONObject personal, JSONObject connections, JSONObject exports,
                boolean wall_comments, String activities, String interests, String music, String movies,
                String tv, String books, String games, String about, String quotes, boolean can_post,
                boolean can_see_all_posts, boolean can_see_audio, boolean can_write_private_message,
                boolean can_send_friend_request, boolean is_favorite, int timezone, String screen_name,
                String maiden_name, JSONObject crop_photo, int is_friend, int friend_status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deactivated = deactivated;
        this.hidden = hidden;
        this.photo_id = photo_id;
        this.verified = verified;
        this.blacklisted = blacklisted;
        this.sex = sex;
        this.bdate = bdate;
        this.city = city;
        this.country = country;
        this.home_town = home_town;
        this.photo_50 = photo_50;
        this.photo_100 = photo_100;
        this.photo_200_orig = photo_200_orig;
        this.photo_200 = photo_200;
        this.photo_400_orig = photo_400_orig;
        this.photo_max = photo_max;
        this.photo_max_orig = photo_max_orig;
        this.online = online;
        this.lists = lists;
        this.domain = domain;
        this.has_mobile = has_mobile;
        this.contacts = contacts;
        this.site = site;
        this.education = education;
        this.universities = universities;
        this.schools = schools;
        this.status = status;
        this.last_seen = last_seen;
        this.followers_count = followers_count;
        this.common_count = common_count;
        this.counters = counters;
        this.occupation = occupation;
        this.nickname = nickname;
        this.relatives = relatives;
        this.relation = relation;
        this.personal = personal;
        this.connections = connections;
        this.exports = exports;
        this.wall_comments = wall_comments;
        this.activities = activities;
        this.interests = interests;
        this.music = music;
        this.movies = movies;
        this.tv = tv;
        this.books = books;
        this.games = games;
        this.about = about;
        this.quotes = quotes;
        this.can_post = can_post;
        this.can_see_all_posts = can_see_all_posts;
        this.can_see_audio = can_see_audio;
        this.can_write_private_message = can_write_private_message;
        this.can_send_friend_request = can_send_friend_request;
        this.is_favorite = is_favorite;
        this.timezone = timezone;
        this.screen_name = screen_name;
        this.maiden_name = maiden_name;
        this.crop_photo = crop_photo;
        this.is_friend = is_friend;
        this.friend_status = friend_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isDeactivated() {
        return deactivated;
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public JSONObject getCity() {
        return city;
    }

    public void setCity(JSONObject city) {
        this.city = city;
    }

    public JSONObject getCountry() {
        return country;
    }

    public void setCountry(JSONObject country) {
        this.country = country;
    }

    public String getHome_town() {
        return home_town;
    }

    public void setHome_town(String home_town) {
        this.home_town = home_town;
    }

    public URL getPhoto_50() {
        return photo_50;
    }

    public void setPhoto_50(URL photo_50) {
        this.photo_50 = photo_50;
    }

    public URL getPhoto_100() {
        return photo_100;
    }

    public void setPhoto_100(URL photo_100) {
        this.photo_100 = photo_100;
    }

    public URL getPhoto_200_orig() {
        return photo_200_orig;
    }

    public void setPhoto_200_orig(URL photo_200_orig) {
        this.photo_200_orig = photo_200_orig;
    }

    public URL getPhoto_200() {
        return photo_200;
    }

    public void setPhoto_200(URL photo_200) {
        this.photo_200 = photo_200;
    }

    public URL getPhoto_400_orig() {
        return photo_400_orig;
    }

    public void setPhoto_400_orig(URL photo_400_orig) {
        this.photo_400_orig = photo_400_orig;
    }

    public URL getPhoto_max() {
        return photo_max;
    }

    public void setPhoto_max(URL photo_max) {
        this.photo_max = photo_max;
    }

    public URL getPhoto_max_orig() {
        return photo_max_orig;
    }

    public void setPhoto_max_orig(URL photo_max_orig) {
        this.photo_max_orig = photo_max_orig;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getLists() {
        return lists;
    }

    public void setLists(String lists) {
        this.lists = lists;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isHas_mobile() {
        return has_mobile;
    }

    public void setHas_mobile(boolean has_mobile) {
        this.has_mobile = has_mobile;
    }

    public JSONObject getContacts() {
        return contacts;
    }

    public void setContacts(JSONObject contacts) {
        this.contacts = contacts;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public JSONObject getEducation() {
        return education;
    }

    public void setEducation(JSONObject education) {
        this.education = education;
    }

    public JSONObject getUniversities() {
        return universities;
    }

    public void setUniversities(JSONObject universities) {
        this.universities = universities;
    }

    public JSONObject getSchools() {
        return schools;
    }

    public void setSchools(JSONObject schools) {
        this.schools = schools;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONObject getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(JSONObject last_seen) {
        this.last_seen = last_seen;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getCommon_count() {
        return common_count;
    }

    public void setCommon_count(int common_count) {
        this.common_count = common_count;
    }

    public JSONObject getCounters() {
        return counters;
    }

    public void setCounters(JSONObject counters) {
        this.counters = counters;
    }

    public JSONObject getOccupation() {
        return occupation;
    }

    public void setOccupation(JSONObject occupation) {
        this.occupation = occupation;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public JSONObject getRelatives() {
        return relatives;
    }

    public void setRelatives(JSONObject relatives) {
        this.relatives = relatives;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public JSONObject getPersonal() {
        return personal;
    }

    public void setPersonal(JSONObject personal) {
        this.personal = personal;
    }

    public JSONObject getConnections() {
        return connections;
    }

    public void setConnections(JSONObject connections) {
        this.connections = connections;
    }

    public JSONObject getExports() {
        return exports;
    }

    public void setExports(JSONObject exports) {
        this.exports = exports;
    }

    public boolean isWall_comments() {
        return wall_comments;
    }

    public void setWall_comments(boolean wall_comments) {
        this.wall_comments = wall_comments;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getMovies() {
        return movies;
    }

    public void setMovies(String movies) {
        this.movies = movies;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    public String getGames() {
        return games;
    }

    public void setGames(String games) {
        this.games = games;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    public boolean isCan_post() {
        return can_post;
    }

    public void setCan_post(boolean can_post) {
        this.can_post = can_post;
    }

    public boolean isCan_see_all_posts() {
        return can_see_all_posts;
    }

    public void setCan_see_all_posts(boolean can_see_all_posts) {
        this.can_see_all_posts = can_see_all_posts;
    }

    public boolean isCan_see_audio() {
        return can_see_audio;
    }

    public void setCan_see_audio(boolean can_see_audio) {
        this.can_see_audio = can_see_audio;
    }

    public boolean isCan_write_private_message() {
        return can_write_private_message;
    }

    public void setCan_write_private_message(boolean can_write_private_message) {
        this.can_write_private_message = can_write_private_message;
    }

    public boolean isCan_send_friend_request() {
        return can_send_friend_request;
    }

    public void setCan_send_friend_request(boolean can_send_friend_request) {
        this.can_send_friend_request = can_send_friend_request;
    }

    public boolean isIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getMaiden_name() {
        return maiden_name;
    }

    public void setMaiden_name(String maiden_name) {
        this.maiden_name = maiden_name;
    }

    public JSONObject getCrop_photo() {
        return crop_photo;
    }

    public void setCrop_photo(JSONObject crop_photo) {
        this.crop_photo = crop_photo;
    }

    public int getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(int is_friend) {
        this.is_friend = is_friend;
    }

    public int getFriend_status() {
        return friend_status;
    }

    public void setFriend_status(int friend_status) {
        this.friend_status = friend_status;
    }
}
