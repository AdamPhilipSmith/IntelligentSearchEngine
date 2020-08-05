package Search;

import java.util.HashSet;
import java.util.LinkedHashMap;

public class Search {
    //TODO might need this here: private Searcher() {}

    //TODO code fixed but needs going over


    public static HashSet<String> search(String word, LinkedHashMap hashMap) {

        // coverts word to all lower case (means when searching all words will be picked up regardless of case)
        String queryUpperCase = word.toUpperCase();

        String[] parts = queryUpperCase.split(" ");

        //Returns null if no words are found
        if (queryUpperCase.length() == 0) {
            return null;
        }

        // Checks to see if 'OR' has been used. If 'OR' is not entered, 'AND' is assumed since that is what a user would probably want
        if (parts.length == 3) {

            if (parts[1].equals("OR")) {
                //Clone made of each result and those are sent to the orSearchHandler otherwise weird stuff happens that I don't understand
                HashSet<String> firstWordResults = (HashSet) hashMap.get(parts[0]);
                HashSet<String> firstWordResultsClone = (HashSet<String>) firstWordResults.clone();

                HashSet<String> secondWordResults = (HashSet) hashMap.get(parts[2]);
                HashSet<String> secondWordResultsClone = (HashSet<String>) secondWordResults.clone();

                return orSearchHandler(firstWordResultsClone, secondWordResultsClone);
            }

        }

        return searchHandler(parts, hashMap);

    }
    //TODO 'OR' search still doesn't work properly. Breaks searches done afterwards
    //Checks the results from both words specified by 'OR' and combines the search results.
    public static HashSet<String> orSearchHandler(HashSet<String> firstWordResults, HashSet<String> secondWordResults) {

        if (firstWordResults == null && secondWordResults == null) {
            System.out.println("1");
            return null;
        }

        if (firstWordResults == null) {
            System.out.println("2");
            return secondWordResults;
        }

        if (secondWordResults == null) {
            //System.out.println(firstWordResults);
            System.out.println("3");
            return firstWordResults;
        }

        firstWordResults.addAll(secondWordResults);
        //System.out.println(firstWordResults);
        return firstWordResults;
    }
    //TODO add unfound words to an array so we can use Similar Words on all of them
    public static HashSet<String> searchHandler(String[] parts, LinkedHashMap hashMap) {


        HashSet<String> initialResults = (HashSet) hashMap.get(parts[0]);

        if (initialResults == null) {
            return null;
        }

        //Clone needed here, otherwise original hashMap is affected during search
        HashSet<String> initialResultsClone = (HashSet<String>) initialResults.clone();

        for (int i = 0; i < parts.length; i++) {

            HashSet<String> nextWordResults = (HashSet) hashMap.get(parts[i]);

            if (nextWordResults == null) {
                return null;
            }

            initialResultsClone.retainAll(nextWordResults);

        }

        return initialResultsClone;
    }

}


