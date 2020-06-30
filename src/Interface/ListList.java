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
import java.net.MalformedURLException;
import java.util.*;

import static Search.Indexer.wordsToSites;
import static Search.Search.search;

public class ListList extends Application {

    final ListView listView = new ListView();
    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

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
        final TextField urlField = new TextField();
        Button b = new Button("Search");




        hBox.getChildren().addAll(b, urlField);

        b.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                links.clear();
                listView.getItems().clear();
                String searchQuery = urlField.getText();

                HashSet searchResults = search(searchQuery, wordsToSites);

                Map<Double,String> sortedURLs = Ranker.displayRankedResults(searchResults,searchQuery); // ranks the results

                String result3 = "";

                Iterator it = sortedURLs.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    //String result2 = pair.getValue().toString();
                    String result2 = (pair.getValue().toString());

                    Hyperlink link = new Hyperlink(result2);
                    //links.add(link);
                    addLink(result2);

                    it.remove(); // avoids a ConcurrentModificationException
                }
                listView.getItems().addAll(links);
                urlField.clear();
            }
        });
        vBox.getChildren().add(hBox);
        listView.setPrefWidth(1200);
        listView.setPrefHeight(1180);//TODO maybe make this scale dynamically with the size of the links
        vBox.getChildren().add(listView);

        pane.getChildren().add(vBox);
        Scene scene = new Scene(pane, 1200, 800);
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