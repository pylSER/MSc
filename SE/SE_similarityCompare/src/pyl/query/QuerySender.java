package pyl.query;

import pyl.searchengine.SearchEngine;
import pyl.utils.DocumentReader;
import pyl.utils.Logger;

import java.util.ArrayList;

public class QuerySender {
    static String QUERY_PATH="resources/query-10.txt";

    private ArrayList<String> queryLists;

    static boolean enableFilter=false;

    SearchEngine searchEngine;

    public QuerySender(){
        loadQueryData();
        searchEngine= SearchEngine.getSearchEngineInstance();
    }


    private void loadQueryData(){
        Logger.log("Loading Query Data...");
        DocumentReader reader=new DocumentReader();
        queryLists=(ArrayList<String>) reader.getDocuments(QUERY_PATH);
        Logger.log("Query Data Loaded.");
    }

    public ArrayList<ArrayList<QueryResult>> searchFromFile(){

        ArrayList<ArrayList<QueryResult>> results=new ArrayList<>();

        for (String keyword:queryLists) {
            ArrayList<QueryResult> q=searchEngine.search(keyword,enableFilter);

            for (QueryResult r:q) {
                r.setQueryWords(keyword);
            }
            results.add(q);
        }
        return results;
    }

    public void showResult(ArrayList<ArrayList<QueryResult>> res){
        for (int i = 0; i < res.size(); i++) {
            System.out.println("---------------------------------------------");
            System.out.println("Search results for  "+queryLists.get(i)+":");

            ArrayList<QueryResult> queryResults=res.get(i);

            if(queryResults.size()==0){
                System.out.println("No Results.");
            }

            for (int j = 0; j < queryResults.size() ; j++) {
                System.out.println(queryResults.get(j));
            }

            System.out.println("---------------------------------------------");
        }
    }


    public static void main(String[] args) {

        if(args.length>0){
            if("enableFilter".equals(args[0])){
                enableFilter=true;
            }
        }

        QuerySender querySender =new QuerySender();
        ArrayList<ArrayList<QueryResult>> results=querySender.searchFromFile();
        querySender.showResult(results);
    }
}
