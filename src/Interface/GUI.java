package Interface;

import Search.Indexer;
import Search.Ranker;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static Search.Indexer.wordsToSites;
import static Search.Search.search;
import static Search.SimilarWords.retrieveSimilarWords;

public class GUI extends Application {
//TODO sort this code

    //Indexes the specified website and launches the search window
    public static void main(String[] args) throws IOException {

        Indexer.initialise("mmuSiteTest1000.txt");
        launch(args);
    }


    final ListView listView = new ListView();
    @Override
    public void start(Stage primaryStage) {


        List<Hyperlink> links = new ArrayList<>();
        AnchorPane pane = new AnchorPane();
        VBox vBox = new VBox();

        for(final Hyperlink hyperlink : links) {
            hyperlink.setOnAction(t -> getHostServices().showDocument(hyperlink.getText()));
        }

        HBox hBox = new HBox();
        final TextField searchField = new TextField();
        Button b = new Button("Search");

        hBox.getChildren().addAll(b, searchField);

        b.setOnAction(t -> {
            // gets the current system time so we can see how long the search has taken
            long start = System.currentTimeMillis();
            links.clear();
            listView.getItems().clear();

            String searchQuery = searchField.getText();

            HashSet searchResults = search(searchQuery, wordsToSites);


            if (searchResults != null) {
                // ranks the results
                Map<Double, String> sortedURLs = Ranker.rankResults(searchResults, searchQuery);

                String result3 = "";
                int numberOfLinks = 0;

                Iterator it = sortedURLs.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    String result2 = (pair.getValue().toString());

                    Hyperlink link = new Hyperlink(result2);
                    //links.add(link);
                    addLink(result2);

                    numberOfLinks++;

                    it.remove(); // avoids a ConcurrentModificationException
                }
                // Gets the system time once the process is completed, takes the start time away from it to determine total time
                long time = ((System.currentTimeMillis() - start));

                // Adds all the links to the list view
                listView.getItems().addAll(links);

                // Appends the total time taken and number of results searched to the top of the list
                listView.getItems().add(0, numberOfLinks + " results found in " + time + " millisecond(s)."); //

                // Clears the search field for the next Search
                searchField.clear();

            }
            //If no words are found, prints list of similar words
            else {
                listView.getItems().add(0, "No results found for this word/s. Did you mean one of the following, similar word/s:,");
                listView.getItems().add(1, retrieveSimilarWords(wordsToSites, searchQuery));
            }

        });
        vBox.getChildren().add(hBox);

        listView.setPrefWidth(700);
        listView.setPrefHeight(800);
        vBox.getChildren().add(listView);

        pane.getChildren().add(vBox);
        Scene scene = new Scene(pane, 700, 800);
        primaryStage.setTitle("Intelligent Search Engine:");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void addLink(final String url) {
        final Hyperlink link = new Hyperlink(url);
        link.setOnAction(t -> {

            getHostServices().showDocument(link.getText());

        });

        listView.getItems().add(link);
    }

}