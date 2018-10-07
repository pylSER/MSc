package com.ust.pyl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class GeneticProgram {
    ArrayList<ArrayList<Double>> trainSet;

    int highestScore=0;

    private ArrayList<Double> weightResult=new ArrayList<>();

    static String CSV_FILE_PATH= "resources/training-set.csv";

    // w has one of the value of below
    static double[] CANDIDATES_W={-1.5,-3,-2,-1,0,1,2,3,1.5};

    static int INITIAL_PROGRAM_NUMS=5000;

    static double MUTATION_RATE=0.1;

    static double COPY_RATE=0.1;

    static int TOURNAMENT_SIZE=7;

    static int MAX_ITERATION=15;

    static int CHILDREN_NUM=9;

    /**
     * init the traing set from csv file
     */
    public void initTrainSet(){
        trainSet=new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(CSV_FILE_PATH));
            String line=null;
            while ((line=reader.readLine())!=null){
                ArrayList<Double> list=new ArrayList<>();
                String[] items=line.split(",");
                for (int i = 0; i <items.length ; i++) {
                    list.add(Double.parseDouble(items[i]));
                }
                trainSet.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param size of initial programs
     * @return weights and thresholds
     */
    public ArrayList<ArrayList<Double>> generateInitialPrograms(int size,int numW){
        ArrayList<ArrayList<Double>> programs=new ArrayList<>();

        for (int i = 0; i <size ; i++) {
            ArrayList<Double> list=new ArrayList<>();

            for (int j = 0; j <numW ; j++) {
                int index=(int)(Math.random()*(CANDIDATES_W.length));
                list.add(CANDIDATES_W[index]);
            }

            programs.add(list);
        }

        return programs;
    }


    /**
     *
     * @param program
     * @return the score
     */
    private int fitness(ArrayList<Double> program){
        int score=0;

        for (int i = 0; i <trainSet.size() ; i++) {
            ArrayList<Double> trainInstance=trainSet.get(i);

            double sum=0;
            for (int j = 0; j <trainInstance.size()-1 ; j++) {
                double x=trainInstance.get(j);
                double w=program.get(j);
                sum+=(x*w);
            }

            int res=0;
            if(sum>=program.get(program.size()-1)){
                res=1;
            }

            if(res==trainInstance.get(trainInstance.size()-1)){
                score++;
            }
        }

        return score;
    }



    private FitnessItem getHighestScoreProgram(ArrayList<ArrayList<Double>> programs){
        int max=0;

        ArrayList<Double> res=new ArrayList<>();

        for (int i = 0; i <programs.size() ; i++) {
            int score=fitness(programs.get(i));
            if(score>max){
                max=score;
                res=programs.get(i);
                if(score>highestScore){
                    highestScore=score;
                }
            }
        }

        FitnessItem fitnessItem=new FitnessItem();
        fitnessItem.setScore(max);
        fitnessItem.setProgram(res);

        return fitnessItem;
    }


    private ArrayList<ArrayList<Double>> crossOver(ArrayList<ArrayList<Double>> copyResult){

        ArrayList<ArrayList<Double>> result=new ArrayList<>();

        for (int i = 0; i <copyResult.size() ; i++) {
            ArrayList<Double> mother=copyResult.get(i);
            ArrayList<Double> father;
            if(i==(copyResult.size()-1)){
                father=copyResult.get(0);
            }else{
                father=copyResult.get(i+1);
            }

            result.addAll(doCrossOver(mother,father));
        }

        result.addAll(copyResult);

        return result;
    }


    private ArrayList<ArrayList<Double>> doCrossOver(ArrayList<Double> mother,ArrayList<Double> father){

        ArrayList<ArrayList<Double>> children=new ArrayList<>();

        for (int i = 0; i <CHILDREN_NUM ; i++) {
            // do crossOver
            int crossOverIndex=(int)(Math.random()*(father.size()-1));
            ArrayList<Double> child=new ArrayList<>();

            for (int j = 0; j <mother.size(); j++) {
                if(j<=crossOverIndex){
                    child.add(mother.get(j));
                }else{
                    child.add(father.get(j));
                }
            }

            children.add(child);
        }

        return children;
    }

    private ArrayList<ArrayList<Double>> mutate(ArrayList<ArrayList<Double>> crossOverResult){
        int mutateNum=(int)(crossOverResult.size()*MUTATION_RATE);

        for (int i = 0; i <mutateNum ; i++) {
            int programIndex=(int)(Math.random()*crossOverResult.size());
            int weightPosIndex=(int)(Math.random()*crossOverResult.get(programIndex).size());
            int weightValueIndex=(int)(Math.random()*CANDIDATES_W.length);

            ArrayList<Double> program=crossOverResult.get(programIndex);
            program.set(weightPosIndex,CANDIDATES_W[weightValueIndex]);
            crossOverResult.set(programIndex,program);
        }

        return crossOverResult;
    }

    public ArrayList<Double> getWeightResult() {
        return weightResult;
    }





    /**
     * start Gentetic reproduce
     * @param programs
     * @return final weights and thresholds
     */
    public void startGP(ArrayList<ArrayList<Double>> programs,int generationNum){

        if(generationNum==MAX_ITERATION){
            System.out.println("Max iteration exceeded");
            this.weightResult=getHighestScoreProgram(programs).getProgram();
            return;
        }

        System.out.println("Generation "+generationNum+", highest accuracy:"+highestScore+"/"+trainSet.size());


        ArrayList<Double> result=new ArrayList<>();

        ArrayList<ArrayList<Double>> copyResult=new ArrayList<>();
        ArrayList<ArrayList<Double>> copyInput=new ArrayList<>();
        //copy
        for (int i = 0; i <programs.size() ; i++) {
            if(i!=0&&i%(TOURNAMENT_SIZE)==0) {
                ArrayList<Double> winner = getHighestScoreProgram(copyInput).getProgram();
                copyResult.add(winner);
                copyInput.clear();
            }

            copyInput.add(programs.get(i));
        }


        ArrayList<ArrayList<Double>> crossOverResult=crossOver(copyResult);

        ArrayList<ArrayList<Double>> mutateResult=mutate(crossOverResult);



        //after one iteration
        FitnessItem fitnessItem=getHighestScoreProgram(mutateResult);
        int currentHighest=fitnessItem.getScore();

        if(currentHighest==trainSet.size()){
            System.out.println("Found the best match!");
            this.weightResult=fitnessItem.getProgram();
            return;
        }else {
            startGP(mutateResult,generationNum+1);
        }

    }


    public static void main(String[] args) {
        GeneticProgram test=new GeneticProgram();

        test.initTrainSet();

        ArrayList<ArrayList<Double>> list=test.generateInitialPrograms(INITIAL_PROGRAM_NUMS,test.trainSet.get(0).size());

        test.startGP(list,0);

        System.out.println("The best match is :");

        System.out.println(test.getWeightResult()+", the score is:"+test.highestScore);
    }
}
