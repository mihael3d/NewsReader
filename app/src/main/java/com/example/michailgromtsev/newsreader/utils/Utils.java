package com.example.michailgromtsev.newsreader.utils;

import android.content.Context;
import android.text.format.DateUtils;

import com.example.michailgromtsev.newsreader.BuildConfig;

import java.util.Date;

import static android.text.format.DateUtils.*;

public class Utils {

    public static CharSequence formateDateTime (Context context, Date dateTime) {
        return DateUtils.getRelativeDateTimeString(
                context,
                dateTime.getTime(),
                HOUR_IN_MILLIS,
                5*DAY_IN_MILLIS,
                FORMAT_ABBREV_RELATIVE
        );
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    private Utils(){
        throw new AssertionError("No instanse");
    }
}
