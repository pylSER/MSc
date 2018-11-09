package Adaboost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

//    public static void main(String[] args) {
//        List<Integer> examples=Arrays.asList(1,1,1,1,-1,-1,-1,-1,-1);
//
//        List<Integer> h1=Arrays.asList(1,1,-1,1,-1,-1,-1,1,1);
//        List<Integer> h2=Arrays.asList(1,-1,1,1,-1,1,1,-1,-1);
//        List<Integer> h3=Arrays.asList(1,1,1,-1,1,-1,-1,-1,-1);
//        List<Integer> h4=Arrays.asList(-1,1,-1,1,-1,-1,1,-1,-1);
//
//
//        List<List<Integer>> weakClassifiers=Arrays.asList(h1,h2,h3,h4);
//
//        Adaboost adaboost=new Adaboost();
//
//        System.out.println(adaboost.runAdaboost(weakClassifiers,examples,3));
//    }

//    public static void main(String[] args) {
//        List<Integer> examples=Arrays.asList(1,1, 1, 1, -1, -1, -1, -1, -1);
//
//        List<Integer> h1=Arrays.asList(1,1,1,1,-1,-1,-1,1,-1);
//        List<Integer> h2=Arrays.asList(1,1,-1,1,1,-1,-1,1,-1);
//        List<Integer> h3=Arrays.asList(-1,1,1,1,-1,-1,1,-1,1);
//
//
//        List<List<Integer>> weakClassifiers=Arrays.asList(h1,h2,h3);
//
//        Adaboost adaboost=new Adaboost();
//
//        System.out.println(adaboost.runAdaboost(weakClassifiers,examples,2));
//    }

    public static void main(String[] args) {
        List<Integer> h1=Arrays.asList(1,1,1,1,-1,-1,-1,1,-1);
//        List<Integer> h2=Arrays.asList(1,1,-1,1,1,-1,-1,1,-1);
        List<Integer> h3=Arrays.asList(-1,1,1,1,-1,-1,1,-1,1);

        List<Double> res1=h1.stream().map(n->1.0397*n).collect(Collectors.toList());
        List<Double> res2=h1.stream().map(n->0.7332*n).collect(Collectors.toList());


        for (int i = 0; i < res1.size(); i++) {
            double x=res1.get(i)+res2.get(i);
            if(x<=0){
                System.out.println("-1");
            }else{
                System.out.println("1");
            }
        }

    }



}
