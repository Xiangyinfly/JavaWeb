package com.utils;

public class StringUtil {
    public static boolean isEmpty(String s) {
        //return s == null || "".equals(s);
        return s == null || s.isEmpty();
    }

    public static boolean isNotEmpty(String s) {
        //return s == null || "".equals(s);
        return !isEmpty(s);
    }
}
