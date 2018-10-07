package pyl.searchengine;

public class SimilarityScore implements Comparable<SimilarityScore>{
    private int DID;
    private double score;


    public SimilarityScore(int did,double score){
        this.DID=did;
        this.score=score;
    }

    public int getDID() {
        return DID;
    }

    public void setDID(int DID) {
        this.DID = DID;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }




    @Override
    public String toString() {
        return DID+"/"+score;
    }

    @Override
    public int compareTo(SimilarityScore o) {
        double dif=o.getScore()-this.getScore();
        if(dif>0){
            return 1;
        }else if(dif==0){
            return 0;
        }else {
            return -1;
        }
    }
}
