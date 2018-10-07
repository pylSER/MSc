package pyl.searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CosineComparator implements SimilarityComparator {
    @Override
    public ArrayList<SimilarityScore> compareSimilarity(ArrayList<Integer> DIDs, ArrayList<Double> queryVector) {
        HashMap<Integer, ArrayList<Double>> documentVectors=SearchEngine.getSearchEngineInstance().getDocumentVectors();
        ArrayList<SimilarityScore> scores=new ArrayList<>();

        for (int i = 0; i < DIDs.size(); i++) {
            int DID=DIDs.get(i);
            ArrayList<Double> documentV=documentVectors.get(DID);
            double score=getCosValue(documentV,queryVector);
            SimilarityScore similarityScore=new SimilarityScore(DID,score);
            scores.add(similarityScore);
        }

        Collections.sort(scores);

        return scores;
    }

    private double getCosValue(ArrayList<Double> A,ArrayList<Double> B){
        double dotProduct=getDotProduct(A,B);
        double MA=getMagnitude(A);
        double MB=getMagnitude(B);

        return dotProduct/(MA*MB);
    }


    private double getDotProduct(ArrayList<Double> A,ArrayList<Double> B){
        double sum=0;
        for (int i = 0; i < A.size(); i++) {
            sum+=(A.get(i)*B.get(i));
        }

        return sum;
    }


    public double getMagnitude(ArrayList<Double> A){
        double sum=0;
        for (int i = 0; i < A.size(); i++) {
            sum+=(A.get(i)*A.get(i));
        }

        return Math.sqrt(sum);
    }
}
