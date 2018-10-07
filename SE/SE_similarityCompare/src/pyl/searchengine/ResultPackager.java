package pyl.searchengine;

import pyl.query.ItemVO;
import pyl.query.QueryResult;
import pyl.utils.TopFiveWord;
import pyl.utils.TopK;

import java.util.*;

public class ResultPackager {
    static int TOP_K=5;

    /**
     * 除了queryword 都要set
     * @param scores (ranked)
     * @return
     */
    public ArrayList<QueryResult> packageResult(ArrayList<SimilarityScore> scores){
        ArrayList<QueryResult> list=new ArrayList<>();

        HashMap<Integer,ArrayList<Double>> documentVectors=SearchEngine.getSearchEngineInstance().getDocumentVectors();
        HashMap<String,LinkedList<Posting>> indexMap=SearchEngine.getSearchEngineInstance().getIndexMap();



        for (int i = 0; i <scores.size() ; i++) {
            SimilarityScore similarityScore=scores.get(i);

            QueryResult queryResult=new QueryResult();


            queryResult.setDID(similarityScore.getDID());
            queryResult.setSimilarityScore(similarityScore.getScore());

            ArrayList<Double> documentV=documentVectors.get(similarityScore.getDID());
            CosineComparator cosineComparator=new CosineComparator();

            double mag=cosineComparator.getMagnitude(documentV);

            queryResult.setDocumentVectorLength(mag);

            queryResult.setUniqueKeywordNum(getUniqueKeywords(documentV));


            //1. get five highest weighted keywords of the document
            ArrayList<ItemVO> topFiveItemVO =new ArrayList<>();

            ArrayList<TopFiveWord> rankWeightList=new ArrayList<>();


            int mapCounter=0;
            for (Map.Entry<String, LinkedList<Posting>> entry : indexMap.entrySet()) {
                double weight=documentV.get(mapCounter);
                if(weight!=0){
                    TopFiveWord topFiveWord=new TopFiveWord(entry.getKey(),weight);
                    rankWeightList.add(topFiveWord);
                }

                mapCounter++;
            }

            Collections.sort(rankWeightList);

            TopK topK=new TopK();

            ArrayList<TopFiveWord> topRankWeightList= (ArrayList<TopFiveWord>) topK.getTopK(rankWeightList,TOP_K);

            //2. get word linkedlist
            for (int j = 0; j <topRankWeightList.size(); j++) {
                ItemVO itemVO=new ItemVO();

                String keyword=topRankWeightList.get(j).getKeyword();

                itemVO.setKeyword(keyword);

                itemVO.setPostings(indexMap.get(keyword));

                topFiveItemVO.add(itemVO);
            }

            queryResult.setTopFive(topFiveItemVO);
            list.add(queryResult);
        }

        return list;
    }


    private int getUniqueKeywords(ArrayList<Double> vector){
        int sum=0;

        for (int i = 0; i < vector.size(); i++) {
            if(vector.get(i)!=0){
                sum++;
            }
        }

        return sum;
    }




}
