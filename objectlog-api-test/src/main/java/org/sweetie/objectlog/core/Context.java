package org.sweetie.objectlog.core;/*
 * Copyright (C), 2021-2024
 * FileName: Context
 * Author gouhao
 * Date: 2024/3/2 18:57
 * Description:
 */

public class Context {
    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();
    public static String getToken() { return TOKEN.get(); }
    public static void setToken(String value) { TOKEN.set(value); }
    public static void removeToken() { TOKEN.remove(); }
}
