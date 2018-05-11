package com.hao.eureka;

/**
 * Created by hao on 17-12-27.
 */
public class Test {
    public static void main(String[] args) {
        for (int i=0; i < 10; i++) {
            System.out.println(i);
            try {
                if (i == 5) {
                    throw new RuntimeException("i==5");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("----------------");
    }
}
