package Search;

import java.io.IOException;
import java.util.HashSet;

import static Search.ReadFile.results;
import static Search.Search.Search;
import static javax.swing.UIManager.get;

public class Main {
    public static void main(String[] args) throws IOException {

        ReadFile test = new ReadFile("itcwww-small.txt");
        HashSet results2 = Search("research", results);

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





