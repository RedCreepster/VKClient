package com.redprojects.utils.user.activity;

import com.sun.istack.internal.NotNull;

import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Mouse extends Thread {

    private static Mouse ourInstance = new Mouse();

    private final ArrayList<Listener> listeners = new ArrayList<>();

    private int duration = 15 * 1000 * 60;
    private static int notActiveDuration = 500;
    private Point lastLocation = MouseInfo.getPointerInfo().getLocation();
    private Listener.Type lastType = Listener.Type.ACTIVE;

    private Mouse() {
        this.setDaemon(false);
        this.setName("Thread-UserActivity");
        this.setPriority(Thread.MIN_PRIORITY);
        this.start();
    }

    public static Mouse getInstance() {
        return ourInstance;
    }

    /**
     * @return Время между проверками
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration Время между проверками
     */
    public void setDuration(@NotNull int duration) {
        this.duration = duration;
    }

    public static int getNotActiveDuration() {
        return notActiveDuration;
    }

    public static void setNotActiveDuration(int notActiveDuration) {
        Mouse.notActiveDuration = notActiveDuration;
    }

    public synchronized void addListener(@NotNull Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(@NotNull Listener listener) {
        listeners.remove(listener);
    }

    public synchronized void change(@NotNull Listener.Type type) {
        lastType = type;
        for (Listener listener : listeners)
            listener.change(type);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            Point currentLocation = MouseInfo.getPointerInfo().getLocation();
            if (lastType.equals(Listener.Type.ACTIVE) && currentLocation.equals(lastLocation))
                change(Listener.Type.NOT_ACTIVE);
            if (lastType.equals(Listener.Type.NOT_ACTIVE) && !currentLocation.equals(lastLocation))
                change(Listener.Type.ACTIVE);
            lastLocation = currentLocation;

            try {
                Thread.sleep(lastType.equals(Listener.Type.ACTIVE) ? duration : notActiveDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
