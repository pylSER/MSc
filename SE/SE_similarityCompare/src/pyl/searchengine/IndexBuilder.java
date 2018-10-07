package pyl.searchengine;

import pyl.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class IndexBuilder {

    private void addPostingSorted(LinkedList<Posting> llist, Posting addPosting){
        int postingDID=addPosting.getDID();

        if (llist.size() == 0) {
            llist.add(addPosting);
        }else if (llist.get(0).getDID() > postingDID) {
            llist.add(0, addPosting);
        }else if (llist.get(llist.size() - 1).getDID() < postingDID) {
            llist.add(llist.size(), addPosting);
        }else {

            int i = 0;
            while (llist.get(i).getDID() < postingDID) {
                i++;
            }
            llist.add(i, addPosting);
        }
    }




    public HashMap<String,LinkedList<Posting>> buildIndex(ArrayList<ArrayList<WordItem>> documentLists){
        Logger.log("Building Index");

        HashMap<String,LinkedList<Posting>> indexMap=new HashMap<>();


        for (int i = 0; i < documentLists.size(); i++) {
            ArrayList<WordItem> oneDocument=documentLists.get(i);

            for (int j = 0; j <oneDocument.size() ; j++) {
                WordItem wordItem=oneDocument.get(j);

                LinkedList<Posting> postingLinkedList;

                if(indexMap.containsKey(wordItem.getWord())){
                    postingLinkedList=indexMap.get(wordItem.getWord());
                    //find did
                    boolean notFound=true;

                    for (int k = 0; k <postingLinkedList.size() ; k++) {
                        Posting posting=postingLinkedList.get(k);
                        if(posting.getDID()==i){
                            //now found
                            posting.getPositionList().add(wordItem.getPosition());
                            notFound=false;
                        }
                    }


                    if(notFound){
                        Posting addingPosting=new Posting();
                        addingPosting.setDID(i);

                        ArrayList<Integer> positions=new ArrayList<>();
                        positions.add(wordItem.getPosition());
                        addingPosting.setPositionList(positions);

                        // use custom add
                        addPostingSorted(postingLinkedList,addingPosting);
                    }
                }else{
                    postingLinkedList=new LinkedList<>();
                    //no need find did  add a new item
                    Posting addingPosting=new Posting();
                    addingPosting.setDID(i);

                    ArrayList<Integer> positions=new ArrayList<>();
                    positions.add(wordItem.getPosition());
                    addingPosting.setPositionList(positions);

                    postingLinkedList.add(addingPosting);

                    indexMap.put(wordItem.getWord(),postingLinkedList);
                }


            }

        }

        Logger.log("Building Index Done");
        return indexMap;
    }
}
