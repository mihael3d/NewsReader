package com.example.michailgromtsev.newsreader;

import android.content.Context;
import android.text.format.DateUtils;

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
    private Utils(){
        throw new AssertionError("No instanse");
    }
}
