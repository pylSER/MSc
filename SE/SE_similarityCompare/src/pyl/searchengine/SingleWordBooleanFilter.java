package pyl.searchengine;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class SingleWordBooleanFilter implements BooleanFilter {
    @Override
    public ArrayList<Integer> booleanMatch(ArrayList<WordItem> queryWords) {
        HashMap<String,LinkedList<Posting>> indexMap=SearchEngine.getSearchEngineInstance().getIndexMap();
        ArrayList<LinkedList<Posting>> waitingIntersections=new ArrayList<>();


        for (int i = 0; i < queryWords.size(); i++) {
            String keyword=queryWords.get(i).getWord();
            if(indexMap.containsKey(keyword)){
                waitingIntersections.add(indexMap.get(keyword));
            }
        }

        LinkedList<Posting> res=waitingIntersections.stream().reduce((list1,list2)->{
            list1.retainAll(list2);
            return list1;
        }).orElse(new LinkedList<>());


        return convert2Res(res);
    }


    private ArrayList<Integer> convert2Res(LinkedList<Posting> res){
        ArrayList<Integer> list=new ArrayList<>();

        for (int i = 0; i < res.size(); i++) {
            Posting posting=res.get(i);
            list.add(posting.getDID());
        }

        return list;
    }



}
