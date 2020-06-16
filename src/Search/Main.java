package Search;

import java.io.IOException;
import java.util.*;

import static Search.ReadFile.results;
import static Search.ReadFile.sitesAndWords;
import static Search.Search.Search;

public class Main {
    public static void main(String[] args) throws IOException {


        //Crawler crawler = new Crawler("https://www.mmu.ac.uk/", 1000);
        //crawler.crawl();
        ReadFile test = new ReadFile("mmuSiteTest100.txt");
        HashSet results2 = Search("Applications", results);


        //HashSet allWordsOnSite = Search("https://www.mmu.ac.uk/business-school/business/sme-support/fuel-cell-innovation-centre", wordResults);

        ArrayList tester = new ArrayList();
        tester = (ArrayList) sitesAndWords.get("https://www.mmu.ac.uk/international/fees-and-funding");
        double wordCount = 0;
        double searchedWordCount = 0;
        for (Object results : tester) {
            String searchedWord = results.toString();
            if (searchedWord.equalsIgnoreCase("University")) {
                System.out.println("found");
                searchedWordCount++;
            }
            wordCount++;
        }
        double rankScore = searchedWordCount / wordCount;
        System.out.println(rankScore);
        System.out.println(wordCount);
        System.out.println(tester);
        List<String> words = (List<String>) sitesAndWords.get("https://www.mmu.ac.uk/international/fees-and-funding");

        System.out.println(tf(words, "University"));




        /*for( String word: allWordsOnSite){
            if (word.equals("and)")){
                System.out.println("count");
            }
        }*/


        //System.out.println(results2);
        //System.out.println(allWordsOnSite);
        //System.out.println(sitesAndWords);
      /* for (Object key: results2.keySet()) {
           if (key.equals("research")) {
               System.out.println(get(key));
           }
       }
*/


    }


        public static double tf(List<String> doc, String term) {
            double result = 0;
            for (String word : doc) {
                if (term.equalsIgnoreCase(word))
                    result++;
            }
            return result / doc.size();
        }
        //System.out.println);
    }






