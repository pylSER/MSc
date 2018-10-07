package pyl.searchengine;

import java.util.ArrayList;

public interface BooleanFilter {

    /**
     *
     * @param queryWords
     * @return DIDs
     */
    ArrayList<Integer> booleanMatch(ArrayList<WordItem> queryWords);


}
