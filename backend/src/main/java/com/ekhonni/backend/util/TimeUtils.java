package com.ekhonni.backend.util;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;
import java.util.Date;

public class TimeUtils {

    public static String timeAgo(LocalDateTime dateTime) {
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(Date.from(dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()));
    }
}
