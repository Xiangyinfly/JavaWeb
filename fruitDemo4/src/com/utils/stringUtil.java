package com.utils;

public class stringUtil {
    public static boolean isEmpty(String s) {
        //return s == null || "".equals(s);
        return s == null || s.isEmpty();
    }

    public static boolean isNotEmpty(String s) {
        //return s == null || "".equals(s);
        return !isEmpty(s);
    }
}
