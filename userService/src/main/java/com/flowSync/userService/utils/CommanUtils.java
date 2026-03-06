package com.flowSync.userService.utils;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.ObjectUtils;

public final class CommanUtils {
     private static final Calendar CALENDAR = Calendar.getInstance();

    /**
     * Gets current time stamp.
     *
     * @return the current time stamp
     */
    public static long getCurrentTimeStamp() {
        return CALENDAR.getTimeInMillis();
    }


    /**
     * Is not empty boolean.
     *
     * @param input the input
     * @return the boolean
     */
    public static boolean isNotEmpty(final Object input){
         return !ObjectUtils.isEmpty(input);
    }

    /**
     * Is empty boolean.
     *
     * @param input the input
     * @return the boolean
     */
    public  static boolean isEmpty(final Object input){
        return ObjectUtils.isEmpty(input);
    }


    /**
     * Generate uuid uuid.
     *
     * @return the uuid
     */
    public static UUID generateUUID() {
        return UUID.randomUUID();
    }
}
