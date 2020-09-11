package Search;

import java.util.HashMap;
import java.util.HashSet;

public class Searcher {
    //TODO might need this here: private Searcher() {}

    //TODO code fixed but needs going over


    public static HashSet<String> searchHandler(String searchedTerms, HashMap hashMap) {

        // coverts searchedTerms to all lower case (means when searching all words will be
        // picked up regardless of case)
        String queryUpperCase = searchedTerms.toUpperCase();

        String[] splitTerms = queryUpperCase.split(" ");

        //Returns null if no words are found
        if (queryUpperCase.length() == 0) {
            return null;
        }

        // Checks to see if 'OR' has been used. If 'OR' is not entered, 'AND' is assumed since
        // that is what a user would probably want
        if (splitTerms.length == 3) {

            if (splitTerms[1].equals("OR")) {
                System.out.println(orSearch(splitTerms, hashMap));
                return orSearch(splitTerms, hashMap);

            }

        }

        return andSearch(splitTerms, hashMap);

    }

    //Checks the results from both words specified by 'OR' and combines the searchHandler results.
    private static HashSet<String> orSearch(String[] splitTerms, HashMap hashMap) {


        HashSet<String> firstWordResults = (HashSet) hashMap.get(splitTerms[0]);


        HashSet<String> secondWordResults = (HashSet) hashMap.get(splitTerms[2]);


        if (firstWordResults == null && secondWordResults == null) {
            return null;
        }

        if (firstWordResults == null) {
            return secondWordResults;
        }

        if (secondWordResults == null) {
            System.out.println(firstWordResults);
            return firstWordResults;
        }
        //Clone made of each result and those are then combined, otherwise problems occur
        HashSet<String> firstWordResultsClone = (HashSet<String>) firstWordResults.clone();
        //System.out.println(firstWordResultsClone);
        HashSet<String> secondWordResultsClone = (HashSet<String>) secondWordResults.clone();
       //System.out.println(secondWordResultsClone);
        firstWordResultsClone.addAll(secondWordResultsClone);

        return firstWordResultsClone;
    }

    private static HashSet<String> andSearch(String[] splitTerms, HashMap hashMap) {


        HashSet<String> results = (HashSet) hashMap.get(splitTerms[0]);

        if (results == null) {
            return null;
        }

        //Clone needed here, otherwise original hashMap is affected during searchHandler
        HashSet<String> resultsClone = (HashSet<String>) results.clone();

        for (int i = 0; i < splitTerms.length; i++) {

            HashSet<String> nextWordResults = (HashSet) hashMap.get(splitTerms[i]);

            if (nextWordResults == null) {
                return null;
            }
            //Only keeps the results that match the new results
            resultsClone.retainAll(nextWordResults);

        }

        return resultsClone;
    }

}


