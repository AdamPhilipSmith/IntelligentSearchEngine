package Search;

import java.io.IOException;
import java.util.HashSet;

import static Search.ReadFile.results;
import static Search.Search.Search;

public class Main {
    public static void main(String[] args) throws IOException {


        //Crawler crawler = new Crawler("https://www.mmu.ac.uk/", 1000);
        //crawler.crawl();
        ReadFile test = new ReadFile("mmuSiteTest1000.txt");
        HashSet results2 = Search("Adam", results);

        System.out.println(results2);

      /* for (Object key: results2.keySet()) {
           if (key.equals("research")) {
               System.out.println(get(key));
           }
       }
*/


        }
        //System.out.println);
    }





