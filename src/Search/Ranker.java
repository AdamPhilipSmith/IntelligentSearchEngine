package Search;

import java.util.*;

import static Search.Indexer.sitesToWords;
import static Search.Indexer.wordsToSites;
import static Search.SimilarWords.retrieveSimilarWords;

public class Ranker {

    //TODO Check over code
    //TODO maybe use a stop list https://stackoverflow.com/questions/30028519/information-retrieval-how-to-combine-different-word-results-when-using-tf-idf

    public static double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }

        return result / doc.size(); //normalises the frequency to avoid a bias towards larger documents
    }

    public static double idf(HashMap<String, ArrayList<String>> docs, String term) {
        double n = 0;
        Iterator it = docs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            List<String> text = (List<String>) pair.getValue();
            //System.out.println(text);

            for (String word : text) {
                //System.out.println(word);
                //System.out.println(text);
                if (term.equalsIgnoreCase(word)) {
                    //System.out.println("yes");
                    n++;
                    break;
                }
            }


        }
        //System.out.println("Documents:" + n);
        //System.out.println("Size:" + docs.size());
        //TODO maybe use math.log10. Google it
        return Math.log(docs.size() / n);
    }

    public static double tfIdf(List<String> doc, HashMap<String, ArrayList<String>> docs, String term) {
        //System.out.println(tf(doc, term) * idf(docs, term));
        return tf(doc, term) * idf(docs, term);

    }

    public static Map<Double,String> displayRankedResults(HashSet searchResults, String searchedWords) {

        Map<Double, String> sortedURLs = new HashMap<Double, String>();

        String[] splitWords = searchedWords.split(" "); // Separates the different words from the search term

        TreeMap<Double, String> rankedURLs = new TreeMap<>(); //Tree map used since it will automatically sort the results by the key

        if (searchResults == null){
            System.out.println("No results found, but we do have the following, similar words:");
            System.out.println(retrieveSimilarWords(wordsToSites, searchedWords));

        }

        if (searchResults != null) {
            for (Object url : searchResults) {
                List<String> words2 = (List<String>) sitesToWords.get(url);
                double rank = 0;
                for (int i = 0; i < splitWords.length; i++) {
                    //TODO check that adding Tf IDF together is the best way of doing this
                    rank += tfIdf(words2, sitesToWords, splitWords[i]); //Goes through each of the searched terms for each website, getting the Tf IDF rank to get a combined TF IDF score for each site


                }
                String stringURL = url.toString();
                rankedURLs.put(rank, stringURL);
                //System.out.println(words2.size());
                //System.out.println("tf:" + tf(words2, searchedWords));
                //System.out.println("idf=" + idf(sitesToWords, searchedWords));

                //System.out.print("Rank:");


                // System.out.println("");
            }
            sortedURLs = rankedURLs.descendingMap(); //Since the results are ordered lowest to highest, it needs to be reversed to get the best results at the top


            System.out.println(sortedURLs);
            Iterator it = sortedURLs.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println(pair.getValue());

                //leave below for now but it isn't used
                //List<Double> URL = (List<Double>) pair.getKey();
            }
        }

    /*public class reverseRank implements Comparator<String>{

        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }

    }*/

        return sortedURLs;
    }
}
