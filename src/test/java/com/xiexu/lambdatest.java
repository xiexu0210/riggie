package com.xiexu;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class lambdatest {

    @Test
    public void lt(){

//        Comparable<Integer> c = (o1,  o2) -> o1.compare(o2);

//        Comparator<Integer> com2 = (o1, o2) -> Integer.compare(o1, o2);
//        System.out.println(com2.compare(1,2));
////        Integer.compareto()
//        Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println("A");
//            }
//        };
//        consumer.accept("a");

//
//        PrintStream ps = System.out;
//        Consumer consumer = ps::println;;
//        consumer.accept("S");

//        Comparator<Integer> comparator = Integer::compareTo;
//        System.out.println(comparator.compare(1,2));

        Function<Integer, String []> function = integer -> new String[integer];
        String[] arr = function.apply(3);
        System.out.println(arr.length);

        Function<Integer, String [] > function1 = String[]::new;
        function1.apply(1);

    }


    @Test
    public  void  labdmatest(){
//        int[] a = new int[]{1,2,3,4,5};
//        Arrays.stream(a).map(
//                b -> b+1
//        ).forEach(System.out::println);
        Stack<Integer> a = new Stack<>();
//        Object SequenceList;
//        SequenceList
    }


//    @Test
//    public  void hashTest(){
////        nums1 = [4,9,5], nums2 = [9,4,9,8,4]
//
//        int a = 33;
//        new Integer(a).toString();
//        Set<short> s = new HashSet<>()
//    }
}
