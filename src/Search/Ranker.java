package Search;

import java.util.*;

import static Search.Indexer.sitesToWords;

public class Ranker {

    //TODO Code refactored and changed, Could do with a once over.
    //TODO maybe use a stop list https://stackoverflow.com/questions/30028519/information-retrieval-how-to-combine-different-word-results-when-using-tf-idf

    //Goes through the given web pages, calculating how many times the given word appears
    //Divides by the total size of each web page to normalise the result
    public static double tf(List<String> page, String searchedWord) {
        double result = 0;
        for (String word : page) {
            if (searchedWord.equalsIgnoreCase(word))
                result++;
        }
        return result / page.size();
    }

    //Goes through the entirety of the given website, checking how common the given searched word is
    //Stops common words being weighted as highly as less common ones
    public static double idf(HashMap<String, ArrayList<String>> indexedSite, String searchedWord) {
        double n = 0;
        Iterator it = indexedSite.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            List<String> text = (List<String>) pair.getValue();

            for (String word : text) {
                if (searchedWord.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }

        //TODO maybe use math.log10. Google it
        return Math.log10(indexedSite.size() / n);
    }
    // Multiplies the tf and the idf to give a tf-idf rating
    public static double tfIdf(List<String> page, HashMap<String, ArrayList<String>> indexedSite, String term) {

        return tf(page, term) * idf(indexedSite, term);

    }

    //Determines the tf-idf for all websites returned in the search results, combining their scores for each word, ranking them and then returning them in descending order
    public static Map<Double, String> rankResults(HashSet searchResults, String searchedWords) {

        Map<Double, String> sortedURLs = new HashMap<Double, String>();

        // Separates the different words from the search term
        String[] splitWords = searchedWords.split(" ");

        //Tree map used since it will automatically sort the results by the key
        TreeMap<Double, String> rankedURLs = new TreeMap<>();

        //TODO this breaks 'OR' search
        if (searchResults != null) {
            for (Object url : searchResults) {
                List<String> words2 = (List<String>) sitesToWords.get(url);
                double rank = 0;
                for (int i = 0; i < splitWords.length; i++) {
                    //TODO check that adding Tf IDF together is the best way of doing this
                    //Goes through each of the searched terms for each website, getting the Tf IDF rank to get a combined TF IDF score for each site
                    rank += tfIdf(words2, sitesToWords, splitWords[i]);

                }
                String stringURL = url.toString();

                rankedURLs.put(rank, stringURL);
            }
            //Since the results are ordered lowest to highest, the list needs to be reversed to get the best results at the top
            sortedURLs = rankedURLs.descendingMap();

            //TODO below no longer needed?
            Iterator it = sortedURLs.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                //System.out.println(pair.getValue());
                //System.out.println(pair.getKey());
            }
        }
        System.out.println(sortedURLs);
        return sortedURLs;
    }
}
