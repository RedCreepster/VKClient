package com.redprojects;

import com.redprojects.utils.user.activity.Listener;
import com.redprojects.utils.user.activity.Mouse;

public class tmp {

    public static void main(String[] args) throws InterruptedException {
        Mouse.getInstance().setDuration(15 * 60 * 1000);
        Mouse.getInstance().addListener(new Listener() {
            @Override
            public void change(Type type) {
                System.out.println(type.name());
            }
        });
    }
}
