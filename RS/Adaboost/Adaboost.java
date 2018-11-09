package Adaboost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Adaboost {
    private int imageNums=0;

    private List<Double> weights;

    private ArrayList<Integer> result;

    private ArrayList<Double> alphaList;



    private void normalizeWeights(){
        if(weights.size()==0){
            for (int i = 0; i < imageNums; i++) {
                weights.add(1.0/imageNums);
            }
            return;
        }else {
            double sum=weights.stream().mapToDouble(i->i).sum();
            weights=weights.stream().map(n->n/sum).collect(Collectors.toList());
        }
    }


    private List<Double> computeErrors(List<Integer> examples,List<List<Integer>> weakClassifiers){
        List<Double> errorList=new ArrayList<>();

        for (int i = 0; i < weakClassifiers.size(); i++) {
            List<Integer> classfier=weakClassifiers.get(i);

            double errorSum=0;
            for (int j = 0; j < classfier.size(); j++) {
                double y=(double)examples.get(j);
                double hk=classfier.get(j);
                double w=weights.get(j);
                errorSum+=(((1-hk*y)/2)*w);
            }
            errorList.add(errorSum);
        }

        return errorList;
    }

    private double findTheBest(List<Double> errors){
        List<Double> sortedErrors=new ArrayList<>();
        for (int i = 0; i < errors.size(); i++) {
            if(result.contains(i)){
                continue;
            }else{
                sortedErrors.add(errors.get(i));
            }
        }

        Collections.sort(sortedErrors);

        double smallest=sortedErrors.get(0);

        for (int i = 0; i < errors.size(); i++) {
            if(smallest==errors.get(i)){
                if(result.contains(i)){
                    continue;
                }
                result.add(i);
                System.out.println("bestError= "+smallest);
                System.out.println("bestWC= "+i);
                System.out.println("weakClassifiers= "+result);
                return smallest;
            }
        }

        return 0;
    }

    private double computeAlpha(double smallest){
        double a=Math.log((1-smallest)/smallest);
        a=a/2;
        alphaList.add(a);
        System.out.println("alpha= "+alphaList);
        return a;
    }

    private void updateW(double alpha,List<Integer> examples, List<Integer> selectedClassfier){
        for (int i = 0; i < weights.size(); i++) {
            double w=weights.get(i);

            double power=-1*alpha*examples.get(i)*selectedClassfier.get(i);

            weights.set(i,Math.exp(power)*w);

        }

        System.out.println("weight updated: "+weights);
    }




    public ArrayList<Integer> runAdaboost(List<List<Integer>> weakClassifiers, List<Integer> examples, int TopK){
        imageNums=examples.size();
        weights=new ArrayList<>();
        result=new ArrayList<>();
        alphaList=new ArrayList<>();

        for (int i = 0; i <TopK ; i++) {
            normalizeWeights();
            System.out.println("w= "+weights);

            List<Double> errors=computeErrors(examples, weakClassifiers);
            System.out.println("errors= "+errors);

            double smallest=findTheBest(errors);

            int bestWC=result.get(result.size()-1);



            double alpha=computeAlpha(smallest);

            updateW(alpha,examples,weakClassifiers.get(bestWC));


            System.out.println("------------------------------------");
        }

        return result;
    }
}
