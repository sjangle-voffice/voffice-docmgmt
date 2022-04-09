package com.voffice.rearch.utils.DateFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CustomDateFormatter {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DEFAULT_ZONE_CHAR = "Z";
    private static final String DEFAULT_TIME_ZONE="UTC";

    public static String getDateWithTimeZone(Date date, String timeZone) {
        String dateWithTimeZone = "";
        try {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            String formatDate = dateFormat.format(date);
            Instant instantTime = Instant.parse(formatDate + DEFAULT_ZONE_CHAR);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instantTime, ZoneId.of(timeZone!=null && !"".equals(timeZone)?timeZone:DEFAULT_TIME_ZONE, ZoneId.SHORT_IDS));
            dateWithTimeZone = dateFormat.format(java.sql.Timestamp.valueOf(localDateTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateWithTimeZone;
    }
}
