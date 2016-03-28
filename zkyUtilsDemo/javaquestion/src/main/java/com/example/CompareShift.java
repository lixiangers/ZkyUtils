package com.example;

public class CompareShift {
    public static void main(String[] args) {
        int a, b;
        long start = System.currentTimeMillis();
        System.out.println("start:" + start);
        long count = 1000000000L;
        for (long i = 0; i < count; i++) {
            a = 8 / 8;
            b = 8 * 2;
        }
        long end = System.currentTimeMillis();
        System.out.println("end:" + end + "take:" + (end - start));

        long start2 = System.currentTimeMillis();
        System.out.println("start:" + start2);
        for (long i = 0; i < count; i++) {
            a = 8 >> 3;
            b = 8 << 2;
        }
        long end2 = System.currentTimeMillis();
        System.out.println("end:" + end2 + "take:" + (end2 - start2));

    }
}
