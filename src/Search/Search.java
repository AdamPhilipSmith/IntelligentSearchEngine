package Search;

import java.util.HashSet;
import java.util.LinkedHashMap;

public class Search {
    //TODO might need this here: private Searcher() {}


    public static HashSet<String> Search(String query, LinkedHashMap hashMap){

        String[] parts = query.split(" ");

        if (query.length() == 0) return null;

        if (parts.length == 1) return handleSimpleSearch(hashMap, parts[0]);

        if (parts.length == 3)
        {
            // To future proof the code, we are cloning both queries in order to match how people would intuatively use this function.
            // Cloning is needed, because retainAll modifies the HashSet in the HashMap.
            HashSet<String> resultsOne = (HashSet) hashMap.get(parts[0]);
            HashSet<String> tmpResultsOne = (HashSet<String>) resultsOne.clone();
            HashSet<String> resultsTwo = (HashSet) hashMap.get(parts[2]);
            HashSet<String> tmpResultsTwo = (HashSet<String>) resultsTwo.clone();

            // TODO if (parts[1].equals("OR")) return Searcher.handleOrSearch(tmpResultsOne, tmpResultsTwo);

            // TODO if (parts[1].equals("AND")) return Searcher.handleAndSearch(tmpResultsOne, tmpResultsTwo);
        }
        return null;
    }

    public static HashSet<String> handleSimpleSearch (LinkedHashMap hashMap, String query) {
        return (HashSet) hashMap.get(query);
    }

}
