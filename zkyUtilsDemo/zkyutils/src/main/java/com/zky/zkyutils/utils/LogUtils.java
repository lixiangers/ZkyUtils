package com.zky.zkyutils.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LogUtils {
    public static boolean LOG_DEBUG;
    public static boolean LOG_FILE = LOG_DEBUG;

    public static void v(String tag, String msg) {
        if (LOG_DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOG_DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOG_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void printStackTrace() {
        printStackTrace(null);
    }

    public static void printStackTrace(String tag) {
        if (TextUtils.isEmpty(tag)) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        } else {
            v(tag, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }

        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackElements = new Throwable().getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                sb.append(stackElements[i]);
                if (i != stackElements.length - 1) {
                    sb.append("\n\t");
                }
            }
            if (TextUtils.isEmpty(tag)) {
                System.out.println(sb);
            } else {
                v(tag, sb.toString());
            }
        }

        if (TextUtils.isEmpty(tag)) {
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        } else {
            v(tag, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }

    }

    private static void recLifeCycle(Class<?> callingClass, String note) {
        String className = callingClass.getSimpleName();
        StackTraceElement[] s = Thread.currentThread().getStackTrace();
        String methodName = s[4].getMethodName();
    }

    private static FileWriter writer;
    private static Date date = new Date();
    private static long lastTime;
    private static final long TIME_INTERVAL = 60 * 60 * 1000;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    private static String LOG_DIR = Environment.getExternalStorageDirectory() + "/neolix/person/log/";

    public static synchronized void logFile(String... args) {
        if (!LOG_FILE) {
            return;
        }
        long time = System.currentTimeMillis();
        date.setTime(time);

        try {
            if (writer == null) {
                File file = new File(LOG_DIR);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String fileName = LOG_DIR + dateFormat.format(date) + ".txt";
                File logFile = new File(fileName);
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
                writer = new FileWriter(fileName, true);
                lastTime = time;
            } else {
                if (time - lastTime > TIME_INTERVAL) {
                    writer.close();
                    String fileName = LOG_DIR + dateFormat.format(date) + ".txt";
                    File logFile = new File(fileName);
                    if (!logFile.exists()) {
                        logFile.createNewFile();
                    }
                    writer = new FileWriter(fileName, true);
                    lastTime = time;
                }
            }
            writer.write(dateFormat.format(date) + ":>>>");
            for (int i = 0; i < args.length; i++) {
                writer.write(args[i]);
                if (i < args.length - 1) {
                    writer.write(":");
                }
            }
            writer.write('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
