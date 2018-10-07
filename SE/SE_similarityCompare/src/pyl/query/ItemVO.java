package pyl.query;

import pyl.searchengine.Posting;

import java.util.ArrayList;
import java.util.LinkedList;

public class ItemVO {
    private String keyword;
    private LinkedList<Posting> postings;


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public LinkedList<Posting> getPostings() {
        return postings;
    }

    public void setPostings(LinkedList<Posting> postings) {
        this.postings = postings;
    }

    @Override
    public String toString() {
        String temp= keyword+"   -> ";

        String res="| ";
        for (int i = 0; i <postings.size(); i++) {
            res+=postings.get(i).toString();
            res+=" | ";
        }

        return temp+res;
    }
}
