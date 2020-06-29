package Search;

import java.util.HashSet;
import java.util.LinkedHashMap;

public class Search {
    //TODO might need this here: private Searcher() {}

    //TODO fix all code on this page


    public static HashSet<String> search(String query, LinkedHashMap hashMap){

        String queryUpperCase = query.toUpperCase();// coverts word to all lower case (means when searching all words will be picked up regardless of case)

        String[] parts = queryUpperCase.split(" ");

        if (queryUpperCase.length() == 0) return null;

        if (parts.length == 1) return handleSimpleSearch(hashMap, parts[0]);



        /*if (parts.length == 3)
        {
            // To future proof the code, we are cloning both queries in order to match how people would intuitively use this function.
            // Cloning is needed, because retainAll modifies the HashSet in the HashMap.
            HashSet<String> resultsOne = (HashSet) hashMap.get(parts[0]);
            HashSet<String> tmpResultsOne = (HashSet<String>) resultsOne.clone();
            HashSet<String> resultsTwo = (HashSet) hashMap.get(parts[2]);
            HashSet<String> tmpResultsTwo = (HashSet<String>) resultsTwo.clone();

            if (parts[1].equals("OR")) return Search.handleOrSearch(tmpResultsOne, tmpResultsTwo);

            if (parts[1].equals("AND")) return Search.handleAndSearch(tmpResultsOne, tmpResultsTwo);




        }*/

        if (parts.length > 1){
            //System.out.println("test");
            return Search.handleMultipleWords(parts, hashMap);
        }
        return null;
    }

    public static HashSet<String> handleSimpleSearch (LinkedHashMap hashMap, String query) {
        return (HashSet) hashMap.get(query);
    }

    private static HashSet<String> handleOrSearch (HashSet<String> resultOne, HashSet<String> resultTwo) {

        if (resultOne == null && resultTwo == null) return null;

        if (resultOne == null) return resultTwo;

        if (resultTwo == null) return resultOne;


        resultOne.addAll(resultTwo);
        return resultOne;
    }

    /**
     * Performs the AND search on two words.
     * @return
     */
    private static HashSet<String> handleAndSearch (HashSet<String> resultOne, HashSet<String> resultTwo) {

        //for (int i = 0; i>parts.length())

        if (resultOne == null || resultTwo == null) return null;

        resultOne.retainAll(resultTwo); // keeps only the matching sites

        return resultOne;
    }

    private static HashSet<String> handleMultipleWords (String[] parts, LinkedHashMap hashMap) {


        HashSet<String> initialResults = (HashSet) hashMap.get(parts[0]);
        if (initialResults == null) return null;

        for (int i = 0; i < parts.length; i++){

            //System.out.println(initialResults);
            HashSet<String> nextWordResults = (HashSet) hashMap.get(parts[i]);
            //System.out.println(i);
            //System.out.println(parts[i]+ ":");
            //System.out.println(nextWordResults);

            if (nextWordResults == null) return null;
            initialResults.retainAll(nextWordResults);
            //System.out.println(initialResults);

        }





        return initialResults;
    }
}


