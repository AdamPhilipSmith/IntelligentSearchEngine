package Search;

import java.util.*;

import static Search.ReadFile.sitesToWords;

public class Ranker {

    public static double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        System.out.println(result);
        return result / doc.size();
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
        System.out.println("Documents:" + n);
        System.out.println("Size:" + docs.size());
        //TODO maybe use math.log10. Google it
        return Math.log(docs.size() / n);
    }

    public static double tfIdf(List<String> doc, HashMap<String, ArrayList<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);

    }

    public static void displayResults(HashSet searchResults, String searchedWords){

        //String[] splitWord = searchedWords.split(" "); // Separates the different words from the search term
        //String word1 = splitWord[0];
        //String word2 = splitWord[2];


        for(Object url : searchResults){

            System.out.println(url);
            List<String> words2 = (List<String>) sitesToWords.get(url);
            System.out.println(words2.size());
            System.out.println(tf(words2, searchedWords));
            System.out.println(idf(sitesToWords, searchedWords));
            System.out.print("Rank:");

            System.out.println(tfIdf(words2, sitesToWords, searchedWords));
            System.out.println("");
        }
    }
}
