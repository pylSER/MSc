package pyl.searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class NoFilter implements BooleanFilter {
    @Override
    public ArrayList<Integer> booleanMatch(ArrayList<WordItem> queryWords) {
        ArrayList<Integer> res=new ArrayList<>();

        HashMap<Integer,ArrayList<Double>> documentVectors=SearchEngine.getSearchEngineInstance().getDocumentVectors();

        for (Map.Entry<Integer,ArrayList<Double>> entry : documentVectors.entrySet()) {
            res.add(entry.getKey());
        }

        return res;
    }
}
