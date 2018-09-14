package org.mbmb.backpressure;

import java.util.List;

public class ComputeFunction {
    public static void compute(Integer v) {
        try {
            System.out.println("compute integer v: " + v);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void computeList(List<Integer> list) {
        try {
            list.forEach(System.out::println);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void slowComputeList(List<Integer> list) {
        try {
            list.forEach(System.out::println);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
