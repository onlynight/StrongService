package com.superlity.strongservice;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.widget.Toast;

public class Utils {


    /**
     * 判断进程是否运行
     *
     * @return
     */
    public static boolean isProessRunning(Context context, String proessName) {

        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (RunningAppProcessInfo info : lists) {
            if (info.processName.equals(proessName)) {
                isRunning = true;
            }
        }

        return isRunning;
    }

    public static void toast(String msg) {
        if (msg == null) {
            return;
        }
        Toast.makeText(TempApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
