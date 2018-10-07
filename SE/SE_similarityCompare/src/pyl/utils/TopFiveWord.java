package pyl.utils;

public class TopFiveWord implements Comparable<TopFiveWord>{
    private String keyword;

    private double weight;

    public TopFiveWord(String keyword, double weight) {
        this.keyword = keyword;
        this.weight = weight;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(TopFiveWord o) {
        if(o.weight>this.weight){
            return 1;
        }else if(o.weight==this.weight){
            return 0;
        }else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return keyword+"/"+weight;
    }
}
