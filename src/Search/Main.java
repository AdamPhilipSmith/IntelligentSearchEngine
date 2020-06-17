package Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static Search.ReadFile.sitesToWords;
import static Search.ReadFile.wordsToSites;
import static Search.Search.Search;

public class Main {

    static List<List<String>> sitesAndWordsList = new ArrayList<List<String>>();

    public static void main(String[] args) throws IOException {


        //Crawler crawler = new Crawler("https://www.mmu.ac.uk/", 1000);
        //crawler.crawl();



        //HashSet allWordsOnSite = Search("https://www.mmu.ac.uk/business-school/business/sme-support/fuel-cell-innovation-centre", wordResults);

        //ArrayList tester = new ArrayList();



       /* tester = (ArrayList) sitesAndWords.get("https://www.mmu.ac.uk/international/fees-and-funding");
        double wordCount = 0;
        double searchedWordCount = 0;
        for (Object results : tester) {
            String searchedWord = results.toString();
            if (searchedWord.equalsIgnoreCase("University")) {
                //System.out.println("found");
                searchedWordCount++;
            }
            wordCount++;
        }
        double rankScore = searchedWordCount / wordCount;*/
        //System.out.println(rankScore);
        //System.out.println(wordCount);
        //System.out.println(tester);
        ReadFile test = new ReadFile("mmuSiteTest1000.txt");//Reads the mmu website, putting the info into of an Index of words to websites and another index linking websites to words

        String searchedWord = "Manchester";

        HashSet searchResults = Search(searchedWord, wordsToSites);// Gets a list of sites containing the search term, indexed by word.

        List<String> words = (List<String>) sitesToWords.get("https://www.mmu.ac.uk/international/fees-and-funding");

        Ranker.displayResults(searchResults, searchedWord);



        /*for(Object url : searchResults){

            System.out.println(url);
            List<String> words2 = (List<String>) sitesToWords.get(url);
            System.out.println(words2.size());
            System.out.println(tf(words2, searchedWord));
            System.out.println(idf(sitesToWords, searchedWord));
            System.out.print("Rank:");

            System.out.println(tfIdf(words2, sitesToWords, searchedWord));
            System.out.println("");
        }*/

        /*System.out.println(searchResults);
        System.out.println(tf(words, "Space"));
        System.out.println(idf(sitesToWords, "Space"));
        System.out.println(tfIdf(words, sitesToWords, "Space"));*/



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

    /*public static void convertToList(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            List<Integer> URLs = (List<Integer>) pair.getKey();
            List<String> text = (List<String>) pair.getValue();



            //sitesAndWordsList.add(URLs,text);
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }*/
    /*public HashMap<String,Double> calculateInverseDocFrequency(DocumentProperties [] docProperties)
    {
        HashMap<String,Double> InverseDocFreqMap = new HashMap<>();
        int size = docProperties.length;
        double wordCount ;
        for (String word : wordList) {
            wordCount = 0;
            for(int i=0;i<size;i++)
            {
                HashMap<String,Integer> tempMap = docProperties[i].getWordCountMap();
                if(tempMap.containsKey(word))
                {
                    wordCount++;
                    continue;
                }
            }
            double temp = size/ wordCount;
            double idf = 1 + Math.log(temp);
            InverseDocFreqMap.put(word,idf);
        }
        return InverseDocFreqMap;
    }
*/



        //System.out.println);
    }






