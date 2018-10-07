package pyl.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DocumentReader {


    /**
     *
     * @param filepath
     * @return documents as a string in a list
     * each item is a document
     */
    public List<String> getDocuments(String filepath){
        Logger.log("Loading Data From "+filepath);

        ArrayList<ArrayList<String>> documents=new ArrayList<>();

        ArrayList<String> lines=new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line=null;
            while ((line=reader.readLine())!=null){
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Logger.log("Data Loaded");
        return lines;
    }


}
