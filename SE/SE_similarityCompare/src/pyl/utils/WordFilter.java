package pyl.utils;

import pyl.searchengine.WordItem;

import java.util.ArrayList;

public class WordFilter {

    private String deletePunctuations(String word){
        String noPunctuation=word.replaceAll("\\p{Punct}", "");

        return noPunctuation;
    }

    private String stemWord(String word){
        PorterStemmer stemmer=new PorterStemmer();

        char[] temp;

        temp=word.toCharArray();

        stemmer.add(temp,word.length());

        stemmer.stem();

        return stemmer.toString();
    }





    private ArrayList<ArrayList<WordItem>> filter(ArrayList<String> rawDocuments){
        ArrayList<ArrayList<WordItem>> preparedDoc=new ArrayList<>();

        // for each document, we trim it
        for (String rawDoc:rawDocuments) {
            ArrayList<WordItem> temp=new ArrayList<>();

            String[] wordarr=rawDoc.split(" ");

            for (int i = 0; i < wordarr.length; i++) {
                String word=wordarr[i];
                word=word.trim();  //space discarded
                word=deletePunctuations(word);//punctuation discarded

                if(word.length()>=4){  //length consideration

                    //removing end "s" using porter stemmer

                    word=stemWord(word);

                    WordItem wordItem=new WordItem(word,i);

                    temp.add(wordItem);
                }
            }

            preparedDoc.add(temp);

        }

        return preparedDoc;
    }

    public ArrayList<ArrayList<WordItem>> getFilteredData(ArrayList<String> rawDocuments) {
        Logger.log("Filtering Data");

        ArrayList<String> lowerCases=new ArrayList<>();

        for (String raw:rawDocuments) {
            lowerCases.add(raw.toLowerCase());
        }

        ArrayList<ArrayList<WordItem>> res=filter(lowerCases);

        Logger.log("Filtering Data Done");

        return res;
    }
}
