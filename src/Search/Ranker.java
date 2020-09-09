package Search;

import java.util.*;

import static Search.Indexer.forwardIndex;

public class Ranker {

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

        return Math.log10(indexedSite.size() / n);
    }
    // Multiplies the tf and the idf to give a tf-idf rating
    public static double tfIdf(List<String> page, HashMap<String, ArrayList<String>> indexedSite, String term) {

        return tf(page, term) * idf(indexedSite, term);

    }

    //Determines the tf-idf for all webpages returned in the searchHandler results,
    // combining their scores for each word, ranking them and then returning them in descending order
    public static Map<Double, String> rankResults(HashSet searchResults, String searchedWords) {

        Map<Double, String> sortedURLs = new HashMap<Double, String>();

        // Separates the different words from the searchHandler term
        String[] splitWords = searchedWords.split(" ");

        //Tree map used since it will automatically sort the results by the key
        TreeMap<Double, String> rankedURLs = new TreeMap<>();

        if (searchResults != null) {
            for (Object url : searchResults) {
                List<String> words2 = (List<String>) forwardIndex.get(url);
                double rank = 0;

                //Goes through each of the searched terms for each website, getting the Tf IDF rank
                // to get a combined TF IDF score for each site
                for (int i = 0; i < splitWords.length; i++) {

                    //Breaks iteration of loop if the word being checked is the 'OR' operator or is a stop word so that
                    // it doesnt affect the ranking
                    if (splitWords[i].equals("OR")  || splitWords[i].equals("THE") || splitWords[i].equals("IS")
                            || splitWords[i].equals("AT") || splitWords[i].equals("ON") || splitWords[i].equals("WHICH")){
                        continue;
                    }

                    rank += tfIdf(words2, forwardIndex, splitWords[i]);

                }
                String stringURL = url.toString();

                rankedURLs.put(rank, stringURL);
            }
            //Since the results are ordered lowest to highest, the list needs to be reversed to
            // get the best results at the top
            sortedURLs = rankedURLs.descendingMap();

        }

        System.out.println(sortedURLs);
        return sortedURLs;
    }
}
