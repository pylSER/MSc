package pyl.searchengine;

import java.util.ArrayList;

public class Posting {
    private int DID;

    private ArrayList<Integer> positionList;


    public int getDID() {
        return DID;
    }

    public void setDID(int DID) {
        this.DID = DID;
    }

    public ArrayList<Integer> getPositionList() {
        return positionList;
    }

    public void setPositionList(ArrayList<Integer> positionList) {
        this.positionList = positionList;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj instanceof Posting){
            Posting other=(Posting) obj;
            if(this.DID==other.DID){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return ("x"+DID).hashCode();
    }

    @Override
    public String toString() {
        String temp="D"+DID+":";

        for (int i = 0; i < positionList.size(); i++) {

            temp+=positionList.get(i);
            if(i!=positionList.size()-1){
                temp+=",";
            }
        }
        return temp;
    }
}
