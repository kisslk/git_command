package com.envision.spring.message;

public class PrintUtils {
    public static void print(String msg) {
        System.err.printf("\n[线程-%s],[消息-%s]\n", Thread.currentThread().getName(), msg);
    }
}
