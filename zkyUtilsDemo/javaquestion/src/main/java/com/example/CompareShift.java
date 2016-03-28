package com.example;

/**
 * 发现位移还是快些，但是不是特别的明显
 */
public class CompareShift {
    public static void main(String[] args) {
        long a, b;
        long start = System.currentTimeMillis();
        System.out.println("start:" + start);
        long count = 1000000000L;
        for (long i = 0; i < count; i++) {
            a = i / 8;
            b = i * 2;
        }
        long end = System.currentTimeMillis();
        System.out.println("end:" + end + "take:" + (end - start));

        long start2 = System.currentTimeMillis();
        System.out.println("start:" + start2);
        for (long i = 0; i < count; i++) {
            a = i >> 3;
            b = i << 2;
        }
        long end2 = System.currentTimeMillis();
        System.out.println("end:" + end2 + "take:" + (end2 - start2));

    }
}
