package pyl.query;

import java.util.ArrayList;

public class QueryResult {


    private String queryWords;


    private int DID;
    private int uniqueKeywordNum;

    private double documentVectorLength;

    private double similarityScore;

    private ArrayList<ItemVO> topFive;

    public int getDID() {
        return DID;
    }

    public void setDID(int DID) {
        this.DID = DID;
    }

    public int getUniqueKeywordNum() {
        return uniqueKeywordNum;
    }

    public void setUniqueKeywordNum(int uniqueKeywordNum) {
        this.uniqueKeywordNum = uniqueKeywordNum;
    }

    public double getDocumentVectorLength() {
        return documentVectorLength;
    }

    public void setDocumentVectorLength(double documentVectorLength) {
        this.documentVectorLength = documentVectorLength;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(double similarityScore) {
        this.similarityScore = similarityScore;
    }

    public ArrayList<ItemVO> getTopFive() {
        return topFive;
    }

    public void setTopFive(ArrayList<ItemVO> topFive) {
        this.topFive = topFive;
    }

    public String getQueryWords() {
        return queryWords;
    }

    public void setQueryWords(String queryWords) {
        this.queryWords = queryWords;
    }


    @Override
    public String toString() {
        String did="DID:"+DID+"\n";

        String topFiveStr="";

        if(topFive!=null){
            for (int i = 0; i < topFive.size(); i++) {
                topFiveStr+=topFive.get(i).toString();
                topFiveStr+="\n";
            }
        }

        String unique="Number of unique keywords in document: "+uniqueKeywordNum+"\n";

        String magnitude="Magnitude:"+documentVectorLength+"\n";

        String score="Similarity score:"+similarityScore+"\n";


        return did+topFiveStr+unique+magnitude+score;
    }
}
