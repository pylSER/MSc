package pyl.searchengine;

import pyl.query.QueryResult;
import pyl.utils.TopK;
import pyl.utils.WordFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class QueryHandler {

    boolean isPhraseQuery=false;


    static int TOP_K=3;

    public ArrayList<QueryResult> search(String query,boolean enableFilter){
        query=query.trim();

        if(judgeIsPhraseQuery(query)){
            isPhraseQuery=true;
            query=query.substring(1,query.length()-1);
        }else {
            isPhraseQuery=false;
        }

        ArrayList<WordItem> queryWords=getFilteredQuery(query);

        ArrayList<Double> queryVector=getQueryVector(queryWords);

        BooleanFilter booleanFilter;
        if(isPhraseQuery){
            booleanFilter=new PhraseBooleanFilter();
        }else {
            if(enableFilter){
                booleanFilter=new SingleWordBooleanFilter();
            }else {
                booleanFilter=new NoFilter();
            }
        }

        ArrayList<Integer> DIDs=booleanFilter.booleanMatch(queryWords);

        SimilarityComparator comparator=new CosineComparator();

        ArrayList<SimilarityScore> rankedDID=comparator.compareSimilarity(DIDs,queryVector);

        ResultPackager packager=new ResultPackager();

        ArrayList<QueryResult> results= packager.packageResult(rankedDID);

        TopK topK=new TopK();
        ArrayList<QueryResult> topKResults= (ArrayList<QueryResult>) topK.getTopK(results,TOP_K);

        return topKResults;
    }


    private ArrayList<WordItem> getFilteredQuery(String query){
        ArrayList<String> rawQueryList=new ArrayList<>();
        rawQueryList.add(query);

        WordFilter wordFilter=new WordFilter();
        ArrayList<ArrayList<WordItem>> filteredData=wordFilter.getFilteredData(rawQueryList);

        ArrayList<WordItem> queryWords=filteredData.get(0);

        return queryWords;
    }



    private ArrayList<Double> getQueryVector(ArrayList<WordItem> queryWords){
        HashMap<String,LinkedList<Posting>> indexMap=SearchEngine.getSearchEngineInstance().getIndexMap();

        ArrayList<Double> result=new ArrayList<>();
        for (int i = 0; i < indexMap.size(); i++) {
            result.add(0.0);
        }


        for (int i = 0; i <queryWords.size() ; i++) {
            String keyword=queryWords.get(i).getWord();

            if(indexMap.containsKey(keyword)){
                int j=0;
                for (Map.Entry<String, LinkedList<Posting>> entry : indexMap.entrySet()) {
                    String keyInHash=entry.getKey();
                    if(keyInHash.equals(keyword)){
                        result.set(j,1.0);
                        break;
                    }

                    j++;
                }
            }
        }

        return result;
    }








    private boolean judgeIsPhraseQuery(String query){
        String trimmed=query.trim();

        int lastIndex=trimmed.length()-1;

        if(trimmed.charAt(0)=='"'&&trimmed.charAt(lastIndex)=='"'){
            return true;
        }else {
            return false;
        }
    }
}
