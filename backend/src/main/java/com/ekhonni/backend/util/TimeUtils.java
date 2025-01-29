package com.ekhonni.backend.util;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;


@Component
public class TimeUtils {

    public String timeAgo(LocalDateTime dateTime) {
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(Date.from(dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()));
    }
}
