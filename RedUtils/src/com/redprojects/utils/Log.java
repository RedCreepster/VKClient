package com.redprojects.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private static File logFile = new File("logs/" + new Date().getTime() + ".log");

    public static ILog iLog;

    private Log() {
    }

    public static void console(Object sender, Object message) {
        message = message.toString();
        String buf = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] ";
        if (sender instanceof String)
            buf += "[" + sender.toString() + "]: " + message;
        else if (sender != null)
            buf += "[" + sender.getClass().getSimpleName() + "]: " + message;
        else
            buf += "[Anonymous]: " + message;

        System.out.println(buf);
        Utils.appendFile(logFile, buf + "\r\n");
    }

    public static void toInterface(String message) {
        console("UI", message);
        if (iLog != null) {
            iLog.getMessage(message);
        }
    }

    public static void setFilePrefix(String prefix) {
        logFile = new File("logs/" + prefix + "-" + new Date().getTime() + ".log");
    }

    public static File getLogFile() {
        return logFile;
    }

    public interface ILog {
        void getMessage(String message);
    }

}
