package com.marko.teletrader;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;

public class AppUtil{

    public static void logMsg(String msg){
        Log.d("com.marko.teletrader_TagTest", msg);
    }

    public static void showToast(Context context, String msg){
        if(context!=null && msg != null && !msg.isEmpty())
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static Long getRandomMillis(long origin, long bound) {
        if(origin<bound)
            return ThreadLocalRandom.current().nextLong(origin, bound);
        else
            return origin;
    }

    public static double getRandomValue(double v) {
        double origin = v - (v * 0.2);
        double bound = v + (v * 0.2);

        if(origin<bound)
            return ThreadLocalRandom.current().nextDouble(origin, bound);
        else
            return v;
    }
}
