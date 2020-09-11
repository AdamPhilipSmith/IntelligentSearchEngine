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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import static Search.Indexer.invertedIndex;
import static Search.Searcher.searchHandler;

public class GUI extends Application {
//TODO sort this code

    //Indexes the specified website and launches the searchHandler window
    public static void main(String[] args) throws IOException {

        Indexer.index("mmuSiteTest1000.txt");
        launch(args);
    }


    final ListView listView = new ListView();
    @Override
    public void start(Stage primaryStage) {


        // Anchor pane used to anchor child nodes
        AnchorPane pane = new AnchorPane();

        VBox vBox = new VBox();

        HBox hBox = new HBox();

        final TextField searchField = new TextField();

        Button b = new Button("Search");

        // HBox used here since I want the Search button and input field together horizontally
        hBox.getChildren().addAll(b, searchField);
        // Vbox used to add HBox. VBox used here because I want the items aligned vertically
        vBox.getChildren().add(hBox);

        //size of the results window set
        listView.setPrefWidth(700);
        listView.setPrefHeight(800);
        vBox.getChildren().add(listView);

        pane.getChildren().add(vBox);

        //Scene is set with the AnchorPane values but I had to add a little to length
        // to accommodate the HBox
        Scene scene = new Scene(pane, 700, 825);
        primaryStage.setTitle("Intelligent Search Engine:");
        primaryStage.setScene(scene);

        //displays the windows and buttons to the user
        primaryStage.show();

        // sets all the code to be run once the Search button is pressed
        b.setOnAction(t -> {
            // gets the current system time so we can see how long the search has taken
            long start = System.currentTimeMillis();
            // clears the list view ready for new search
            listView.getItems().clear();

            String searchQuery = searchField.getText();

            // the searchHandler method to conduct the search
            HashSet searchResults = searchHandler(searchQuery, invertedIndex);

            if (searchResults != null && !searchResults.isEmpty()) {
                // ranks the results
                Map<Double, String> sortedURLs = Ranker.rankResults(searchResults, searchQuery);
                System.out.println(sortedURLs);
                int numberOfLinks = 0;

                Iterator it = sortedURLs.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    String result2 = (pair.getValue().toString());
                    addLink(result2);

                    numberOfLinks++;

                }
                // Gets the system time once the process is completed,
                // takes the start time away from it to determine total time
                long time = ((System.currentTimeMillis() - start));

                // Appends the total time taken and number of results searched to the top of the list
                listView.getItems().add(0, numberOfLinks + " results found for \"" + searchQuery
                        + "\". It took me " + time + " millisecond(s).");

                // Clears the searchHandler field for the next Search
                searchField.clear();



            }
            //If no words are found, advises the user
            else {
                String[] splitTerms = searchQuery.split(" ");
                if(splitTerms.length > 1){
                    listView.getItems().add(0, "No results found for these words.");
                }
                else {
                    listView.getItems().add(0, "No results found for this word.");
                }
            }

        });

    }

    private void addLink(final String url) {
        final Hyperlink link = new Hyperlink(url);

        link.setOnAction(t -> {
            getHostServices().showDocument(link.getText());
        });

        listView.getItems().add(link);
    }

}