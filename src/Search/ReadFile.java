package Search;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class ReadFile {

    public static LinkedHashMap results = new LinkedHashMap<String, HashSet>();




    public ReadFile(String filename) throws IOException {

        String Url = null;

        try {
            BufferedReader file = new BufferedReader(new FileReader(filename)); // Opens file

            while (true) {
                String word = file.readLine(); //reads the word
                //System.out.println(word);

                if (word == null) { //checks for the end of the file

                    break;

                }

                if (word.startsWith("*PAGE:")) { //checks if the word is a URL. If so, sets the URL and continues on to the next word

                    Url = word.substring(6);
                    continue;
                }

                if (Url == null) { // keeps going until we find the first RUL so we don't add words without a corresponding Url.

                    continue;
                }

                addEntryToHashMap(results, word, Url);

            }
            file.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

    }


    //TODO sort the code below
    /**
     * @param hashMap
     * @param word
     * @param url
     * Adds the word as the key and the url as the value of the hashmap. Checks for duplicates inside each UrlList and only appends
     * new urls to the end.
     */
    protected static void addEntryToHashMap(LinkedHashMap hashMap, String word, String url) { // Adds searched word as key and url as a value of the hashmap. Checks for duplicates only adding new Url if previous doesn't exist.
        // System.out.println(word);
        HashSet<String> urlHashMap = (HashSet) hashMap.get(word);
        //System.out.println("4");

        if (urlHashMap == null) { //Checks a
            HashSet<String> hashSet = new HashSet<String>();
            hashSet.add(url);
            hashMap.put(word, hashSet);
        } else { urlHashMap.add(url); }
    }
}
