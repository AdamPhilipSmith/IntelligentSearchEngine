package Search;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class ReadFile {

    public static LinkedHashMap wordsToSites = new LinkedHashMap<String, HashSet>();//Used Hashset since I don't want duplicates here
    public static HashMap sitesToWords = new HashMap<String, ArrayList<String>>(); //Used ArrayList since I do want duplicates here

        //TODO need to deal with punctioation at the end of words. For example, 'chapter.' will not be picked up when search 'chapter'

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

                    String[] _arr = Url.split("\\s"); // Removes the occasional time a website still has extra text after its URL for some reason. //TODO Maybe mention this in write up maybe (had problems and fixed them.)
                    Url = _arr[0];


                    if (Url.substring(Url.length() - 1).equals("/")) { // Gets rid of the "/" at the end of some URL's preventing duplicate results.//TODO same as above
                        Url = Url.substring(0, Url.length() - 1);
                    }


                    continue;
                }

                if (Url == null) { // keeps going until we find the first Url so we don't add words without a corresponding Url.

                    continue;
                }
               //ArrayList<String> words = (ArrayList<String>) sitesAndWords.get(Url);

                //if (words == null) {

                //}
                //System.out.println(words);
                addEntryToHashMap(wordsToSites, word, Url); //adds the word to the Hashmap with the corresponding Url.
                addToSiteAndWords(Url, word);
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    //TODO sort the code below

    /**
     * @param hashMap
     * @param word
     * @param url     Adds the word as the key and the url as the value of the hashmap. Checks for duplicates inside each UrlList and only appends
     *                new urls to the end.
     */
    protected static void addEntryToHashMap(LinkedHashMap hashMap, String word, String url) { // Adds searched word as key and url as a value of the hashmap. Checks for duplicates only adding new Url if previous doesn't exist.
        //System.out.println(word);
        HashSet<String> urlHashMap = (HashSet) hashMap.get(word);
        //System.out.println(hashMap.get(word));
        //System.out.println(urlHashMap);

        if (urlHashMap == null) { //Checks to see if this Url has already been added for this word.
            HashSet<String> hashSet = new HashSet<String>();
            hashSet.add(url);
            hashMap.put(word, hashSet);
        } else {
            //System.out.println(url);
            //System.out.println(urlHashMap);
            //TODO might cause problems
            if (urlHashMap.contains(url)) {
                urlHashMap.add(url);
            }
            urlHashMap.add(url);

            //System.out.println(urlHashMap);
        }
    }

    protected static void addToSiteAndWords(String url, String word) { // Adds searched word as key and url as a value of the hashmap. Checks for duplicates only adding new Url if previous doesn't exist.
        //System.out.println(word);
        //HashSet<String> wordHashMap = (HashSet) hashMap.get(url);
        //System.out.println(hashMap.get(word));
        //System.out.println(urlHashMap);


        ArrayList temp = new ArrayList();
        temp = (ArrayList) sitesToWords.get(url);

        if (temp!=null) {
            temp.add(word);
            //System.out.println(1);
        }
        //TODO Below shouldn't be necessary and yet it breaks if it isn't here??? System never prints '2' so looks like it isn't used.
        else{
            //System.out.println(2);
            ArrayList temp2 = new ArrayList();
            temp2.add(word);
            temp = temp2;
            sitesToWords.put(url, temp2);

        }

        sitesToWords.put(url, temp);
        //System.out.println(sitesAndWords);
        //System.out.println("/////////////////////////////////////////");

       /* if (wordHashMap == null) { //Checks to see if this word has already been added for this word.
            HashSet<String> hashSet2 = new HashSet<String>();
            hashSet2.add(word);
            hashMap.put(url, hashSet);
        } else {*/

           // wordHashMap.add(word);


            //System.out.println(urlHashMap);
        }
    }


