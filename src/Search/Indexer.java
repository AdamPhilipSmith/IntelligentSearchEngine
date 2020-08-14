package Search;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Indexer {
    //TODO code fixed but might be worth another check

    //TODO try to make these not public

    public static HashMap invertedIndex;
    static HashMap forwardIndex;

    //private Indexer(){}


    static {
        //Used Hashset since I don't want duplicates here
        invertedIndex = new HashMap<String, HashSet>();

        //Used ArrayList since I do want duplicates here
        forwardIndex = new HashMap<String, ArrayList<String>>();
    }

    public static void index(String filename) throws IOException {

        String Url = null;

        try {
            // Opens the file specified to launch in the GUI main method
            BufferedReader file = new BufferedReader(new FileReader(filename));

            while (true) {

                // Gets the next word  word and stores it as a String
                String word = file.readLine();


                //Ends the method once the end of the file has been reached
                if (word == null) {
                    break;
                }
                //TODO change page to URL below and also in crawler and re-crawl
                //checks if the word is a URL. If so, sets the URL and continues on to the next word
                if (word.startsWith("*PAGE:")) {

                    //Sets the URL as the text following "*PAGE:"
                    Url = word.substring(6);

                    // Removes the occasional time a website still has extra text after its URL for some reason. //TODO Maybe mention this in write up maybe (had problems and fixed them.)
                    String[] _arr = Url.split("\\s");
                    Url = _arr[0];

                    // Gets rid of the "/" at the end of some URL's preventing duplicate results.//TODO same as above
                    if (Url.substring(Url.length() - 1).equals("/")) {
                        Url = Url.substring(0, Url.length() - 1);
                    }


                    continue;
                }
                // keeps going until we find the first Url so we don't add words without a corresponding Url.
                if (Url == null) {
                    continue;
                }

                // Gets rid of the punctuation at the end of some words, meaning they were not being picked up in the Search //TODO same as above
                if (word.length() > 1) {
                    if (word.substring(word.length() - 1).equals(".") || word.substring(word.length() - 1).equals(",") || word.substring(word.length() - 1).equals("!") || word.substring(word.length() - 1).equals("?")) {
                        word = word.substring(0, word.length() - 1);
                    }
                }

                // coverts word to all upper case (means when searching all words will be picked up regardless of case)
                String wordUpperCase = word.toUpperCase();

                //if (!wordUpperCase.equals("THE") && !wordUpperCase.equals("IS") && !wordUpperCase.equals("AT") && !wordUpperCase.equals("ON") && !wordUpperCase.equals("WHICH")){ // checks the word is not a 'stop word'. If so, it adds it. //TODO might get rid of this, discuss why and which words to use in project. //TODO doesn't work for some reason

                addToInvertedIndex(invertedIndex, wordUpperCase, Url); //adds the word to the Hashmap with the corresponding Url.
                addToForwardIndex(Url, word);
                //}

            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //TODO if problems, put this back
    //public static HashMap getInstance() {
       // return invertedIndex;
   // }

    // Adds searched word as key and url as a value of the hashmap. Checks for duplicates only adding new Url if previous doesn't exist.
    public static void addToInvertedIndex(HashMap hashMap, String word, String url) {

        HashSet<String> urlHashSet = (HashSet) hashMap.get(word);

        //Checks to see if this Url has already been added for this word.
        if (urlHashSet == null) {

            HashSet<String> hashSet = new HashSet<String>();
            hashSet.add(url);
            hashMap.put(word, hashSet);

        } else {

            //TODO might cause problems being commented out
            //if (urlHashSet.contains(url)) {
               // urlHashSet.add(url);
           // }
            urlHashSet.add(url);

        }
    }

    // Adds url as key and searched word as a value of the hashmap.
    public static void addToForwardIndex(String url, String word) {


        ArrayList temp;
        temp = (ArrayList) forwardIndex.get(url);

        //If the URL has already been added, the new word is added as a value to the URL key
        if (temp != null) {
            temp.add(word);

        }
        //If the URL hasn't been added, a new ArrayList is created with the word and the URL is added to the map with the new ArrayList
        else {

           ArrayList temp2 = new ArrayList();
            temp2.add(word);
            temp = temp2;

            //TODO uncomment this code if any problems
            //forwardIndex.put(url, temp2);

        }

        forwardIndex.put(url, temp);

    }
}


