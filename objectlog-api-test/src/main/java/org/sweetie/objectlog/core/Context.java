package org.sweetie.objectlog.core;
/*
 * FileName: Context
 * Author gouhao
 */

public class Context {
    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();

    public static String getToken() {
        return TOKEN.get();
    }

    public static void setToken(String value) {
        TOKEN.set(value);
    }

    public static void removeToken() {
        TOKEN.remove();
    }
}
