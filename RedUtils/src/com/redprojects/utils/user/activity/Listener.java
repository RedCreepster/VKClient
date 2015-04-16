package com.redprojects.utils.user.activity;

public interface Listener {
    enum Type {
        ACTIVE, NOT_ACTIVE
    }

    void change(Type type);
}
