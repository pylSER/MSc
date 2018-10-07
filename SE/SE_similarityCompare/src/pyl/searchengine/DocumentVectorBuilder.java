package pyl.searchengine;

import pyl.utils.Logger;

import java.util.*;

public class DocumentVectorBuilder {
    double N;  // number of documents

    HashMap<Integer,Double> idfMap;

    double[][] tdf2dArr;


    private void calculateTDF(HashMap<String, LinkedList<Posting>> indexMap,ArrayList<ArrayList<WordItem>> documents){
        tdf2dArr=new double[indexMap.size()][documents.size()];
        int i=0;

        for (Map.Entry<String, LinkedList<Posting>> entry : indexMap.entrySet()) {
            String keyword=entry.getKey();
            LinkedList<Posting> linkedList=entry.getValue();


            for (int j = 0; j <linkedList.size() ; j++) {
                Posting item=linkedList.get(j);
                int did=item.getDID();

                int nums=item.getPositionList().size();
                int totalNum=documents.get(did).size();

                tdf2dArr[i][did]=(double) nums/totalNum;
            }

            i++;
        }


        for (int j = 0; j <tdf2dArr.length ; j++) {
            double idf=idfMap.get(j);

            for (int k = 0; k <tdf2dArr[j].length ; k++) {
                tdf2dArr[j][k]=tdf2dArr[j][k]*idf;
            }
        }

    }


    private HashMap<Integer,ArrayList<Double>> convertArr2List(){
        HashMap<Integer,ArrayList<Double>> documentVectors=new HashMap<>();
        for (int j = 0; j <tdf2dArr[0].length ; j++) {
            ArrayList<Double> vector=new ArrayList<>();

            for (int i = 0; i <tdf2dArr.length ; i++) {
                vector.add(tdf2dArr[i][j]);
            }

            documentVectors.put(j,vector);
        }

        return documentVectors;
    }



    public HashMap<Integer,ArrayList<Double>> getDocumentVectors(HashMap<String, LinkedList<Posting>> indexMap,ArrayList<ArrayList<WordItem>> documents){
        Logger.log("Building Document Vectors");
        int documentsSize=documents.size();
        init(indexMap,documentsSize);
        calculateTDF(indexMap,documents);

        HashMap<Integer,ArrayList<Double>> documentVectors=convertArr2List();

        Logger.log("Building Document Vectors Done");
        return documentVectors;
    }



    private void init(HashMap<String, LinkedList<Posting>> indexMap,int documentsSize){
        N=documentsSize;

        //idfj
        idfMap=new HashMap<>();

        int i=0;

        for (Map.Entry<String, LinkedList<Posting>> entry : indexMap.entrySet()) {
            String keyword=entry.getKey();
            double times=entry.getValue().size();


            double a=N/times;

            double idfj=(Math.log(a)/Math.log(2));

            idfMap.put(i,idfj);

            i++;
        }
        //idfj
    }
}

