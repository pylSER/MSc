package pyl.searchengine;

import pyl.query.QueryResult;
import pyl.utils.DocumentReader;
import pyl.utils.Logger;
import pyl.utils.WordFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class SearchEngine {
    private static SearchEngine searchEngine;

    static String DOCUMENTS_PATH="resources/collection-100.txt";

    private ArrayList<ArrayList<WordItem>> documentLists;  // the order is id

    private HashMap<String,LinkedList<Posting>> indexMap;

    private HashMap<Integer,ArrayList<Double>> documentVectors;


    /**
     * we init search engine only once, triggered by the first query
     */
    private SearchEngine(){
        Logger.log("Initializing Search Engine");

        //load data
        ArrayList<String> rawDocuments= loadData();

        //filter data
        WordFilter filter=new WordFilter();

        ArrayList<ArrayList<WordItem>> filteredData=filter.getFilteredData(rawDocuments);

        setDocumentLists(filteredData);

        //build index
        IndexBuilder indexBuilder=new IndexBuilder();

        setIndexMap(indexBuilder.buildIndex(filteredData));

        //build document vector
        DocumentVectorBuilder vectorBuilder=new DocumentVectorBuilder();

        setDocumentVectors(vectorBuilder.getDocumentVectors(indexMap,documentLists));

        Logger.log("Search Engine Initialized");
    }


    public static SearchEngine getSearchEngineInstance(){
        if(searchEngine==null){
            searchEngine=new SearchEngine();
            return searchEngine;
        }else {
            return searchEngine;
        }
    }





    private ArrayList<String> loadData(){
        DocumentReader reader=new DocumentReader();
        ArrayList<String> temp=(ArrayList<String>) reader.getDocuments(DOCUMENTS_PATH);

        ArrayList<String> documents=new ArrayList<>();

        for (String document:temp) {
            if(!document.equals("")){
                documents.add(document);
            }
        }

        return documents;
    }

    public ArrayList<QueryResult> search(String query,boolean enableFilter){
        QueryHandler queryHandler=new QueryHandler();
        return queryHandler.search(query,enableFilter);
    }



    private void setDocumentLists(ArrayList<ArrayList<WordItem>> documentLists) {
        this.documentLists = documentLists;
    }

    private void setIndexMap(HashMap<String, LinkedList<Posting>> indexMap) {
        this.indexMap = indexMap;
    }

    private void setDocumentVectors(HashMap<Integer, ArrayList<Double>> documentVectors) {
        this.documentVectors = documentVectors;
    }

    public HashMap<String, LinkedList<Posting>> getIndexMap() {
        return indexMap;
    }

    public HashMap<Integer, ArrayList<Double>> getDocumentVectors() {
        return documentVectors;
    }

}