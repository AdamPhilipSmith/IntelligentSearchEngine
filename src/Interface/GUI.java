package Interface;

import Search.Indexer;
import Search.Ranker;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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


    final ListView listView = new ListView();
    @Override
    public void start(Stage primaryStage) {


        List<Hyperlink> links = new ArrayList<>();




        AnchorPane pane = new AnchorPane();
        VBox vBox = new VBox();
        //final Hyperlink link = new Hyperlink("http://blog.professional-webworkx.de");
        //Hyperlink link2= new Hyperlink("http://www.stackoverflow.com");

        //links.add(link);
        //links.add(link2);

        for(final Hyperlink hyperlink : links) {
            hyperlink.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    getHostServices().showDocument(hyperlink.getText());
                }
            });
        }


        //listView.getItems().addAll(links);
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

            //If no words are found, prints list of similar words

            System.out.println(searchResults);

            if (searchResults != null) {




                Map<Double, String> sortedURLs = Ranker.displayRankedResults(searchResults, searchQuery); // ranks the results

                String result3 = "";
                int numberOfLinks = 0;

                Iterator it = sortedURLs.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    //String result2 = pair.getValue().toString();
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
            else {
                listView.getItems().add(0, "No results found for this word/s. Did you mean one of the following, similar word/s:,");
                listView.getItems().add(1, retrieveSimilarWords(wordsToSites, searchQuery));
            }


        });
        vBox.getChildren().add(hBox);

        listView.setPrefWidth(700);
        listView.setPrefHeight(800);//TODO maybe make this scale dynamically with the size of the links
        vBox.getChildren().add(listView);
        //vBox.getChildren().add(resultText);


        pane.getChildren().add(vBox);
        Scene scene = new Scene(pane, 700, 800);
        primaryStage.setTitle("Intelligent Search Engine:");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        LinkedHashMap hashMap = Indexer.initialise("mmuSiteTest1000.txt");
        launch(args);
    }

    private void addLink(final String url) {
        final Hyperlink link = new Hyperlink(url);
        link.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                getHostServices().showDocument(link.getText());
                //openBrowser(link.getText());
            }

        });

        listView.getItems().add(link);
    }

    private void openBrowser(final String url) {
        getHostServices().showDocument(url);
    }
}