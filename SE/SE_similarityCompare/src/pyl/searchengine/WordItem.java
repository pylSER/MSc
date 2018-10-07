package pyl.searchengine;

public class WordItem {
    private String word;
    private int position;

    public WordItem(String word,int pos){
        this.word=word;
        this.position=pos;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return word+"/"+position;
    }
}
