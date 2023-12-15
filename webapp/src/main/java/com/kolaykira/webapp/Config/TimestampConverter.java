package com.kolaykira.webapp.Config;

import com.google.cloud.Timestamp;

public class TimestampConverter {

    public static Timestamp convertJavaSqlTimestamp(java.sql.Timestamp sqlTimestamp) {
        long milliseconds = sqlTimestamp.getTime();
        long seconds = milliseconds / 1000;
        int nanos = (int) ((milliseconds % 1000) * 1_000_000);
        return Timestamp.ofTimeSecondsAndNanos(seconds, nanos);
    }
}

