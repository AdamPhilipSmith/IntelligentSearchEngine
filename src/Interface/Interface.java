package Interface;

import Search.Indexer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedHashMap;


public class Interface extends Application {


    public static void main(String[] args) {

        try {
            LinkedHashMap indexedSite = Indexer.initialise("mmuSiteTest1000.txt");

            while (indexedSite != null) {
                launch(args); //TODO figure out what this does and if it's necessary
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void start(Stage stage) {

        TextField searchWords = new TextField();
        TextArea results = new TextArea();
        //Adds search button and assigns
        Button searchButton = new Button("Search");
       // searchButton.setOnAction(new searcherInterface<ActionEvent>(searchWords, results);
        searchButton.setPrefWidth(500);

        //Sets a label to the search button
        Label searchBoxLabel = new Label("Search MMU:");
        searchBoxLabel.setTextFill(Color.web("#0076a3"));//TODO change colour and double check above comment.



    }

    class searcherInterface<A extends AWTEvent> implements EventHandler<javafx.event.ActionEvent> {

        TextField searchWords;
        TextArea results;

        public searcherInterface(TextField searchWords, TextArea results ){
            this.searchWords = searchWords;
            this.results = results;
        }
        @Override
        public void handle(javafx.event.ActionEvent event) {

        }
    }

}
