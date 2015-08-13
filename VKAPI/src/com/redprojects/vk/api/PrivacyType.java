package com.redprojects.vk.api;


import com.redprojects.utils.Utils;

public enum PrivacyType {
    all, friends, friends_of_friends,
    friends_of_friends_only, nobody, only_me;

    public static String list(Integer[] list_ids) {
        return "list{" + Utils.concat(list_ids) + "}";
    }

    public static String user_ids(Integer[] user_ids) {
        return "{" + Utils.concat(user_ids) + "}";
    }

    public static String no_list(Integer[] list_ids) {
        return "-list{" + Utils.concat(list_ids) + "}";
    }

    public static String no_user_ids(Integer[] user_ids) {
        return "-{" + Utils.concat(user_ids) + "}";
    }
}
