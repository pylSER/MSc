package pyl.searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class PhraseBooleanFilter implements BooleanFilter {
    //TODO  binary filter for Phrase
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

        if(waitingIntersections.size()>0){
            LinkedList<Posting> res=waitingIntersections.get(0);

            for (int i = 1; i <waitingIntersections.size() ; i++) {
                //compare res and get(i)
                LinkedList<Posting> postingLinkedList=waitingIntersections.get(i);

                res=doIntersection(res,postingLinkedList);
            }

            return convert2Res(res);
        }else {
            return new ArrayList<Integer>();
        }
    }


    private LinkedList<Posting> doIntersection(LinkedList<Posting> res, LinkedList<Posting> appender){
        LinkedList<Posting> intersections=new LinkedList<>();

        for (int i = 0; i <res.size(); i++) {
            Posting resPosting=res.get(i);

            ArrayList<Integer> resPos=resPosting.getPositionList();


            for (int j = 0; j <appender.size() ; j++) {
                Posting appenderPosting =appender.get(j);
                ArrayList<Integer> appenderPos=appenderPosting.getPositionList();

                if(resPosting.equals(appenderPosting)){
                    if(isTwoPosOk(resPos,appenderPos)){
                        intersections.add(appenderPosting);
                        break;
                    }
                }
            }
        }

        return intersections;
    }


    private ArrayList<Integer> convert2Res(LinkedList<Posting> res){
        ArrayList<Integer> list=new ArrayList<>();

        for (int i = 0; i < res.size(); i++) {
            Posting posting=res.get(i);
            list.add(posting.getDID());
        }

        return list;
    }

    private boolean isTwoPosOk(ArrayList<Integer> resPos,ArrayList<Integer> appenderPos){

        for (int i = 0; i < resPos.size(); i++) {
            int pos=resPos.get(i);
            if(appenderPos.contains(pos+1)){
                return true;
            }
        }
        return false;
    }
}
