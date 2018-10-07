package pyl.searchengine;

import java.util.ArrayList;

public interface SimilarityComparator {

    /**
     *
     * @param DIDs
     * @param queryVector
     * @return  sorted DID list. first one is the best one
     */
    ArrayList<SimilarityScore> compareSimilarity(ArrayList<Integer> DIDs, ArrayList<Double> queryVector);

}
