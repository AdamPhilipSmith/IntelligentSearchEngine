package Interface;


import Search.Indexer;
import Search.Ranker;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static Search.Indexer.wordsToSites;
import static Search.Search.search;

public class WebViewTest extends Application {

    public static void main(String[] args) throws IOException {

        LinkedHashMap hashMap = Indexer.initialise("mmuSiteTest1000.txt");

        while(hashMap != null) {
            launch(args);
            launch(args);
        }
    }

    public void start(Stage primaryStage) throws IOException {

        Label labelExpl = new Label("Search MMU: ");
        labelExpl.setTextFill(Color.web("#0076a3"));
        TextField SearchTextField = new TextField();
        TextArea resultText = new TextArea();
        resultText.setText("Search Results: \n");
        resultText.setPrefWidth(450);
        Button btn = new Button("Search");
        btn.setPrefWidth(170);


        GridPane pane = new GridPane();
        pane.setAlignment(Pos.BOTTOM_LEFT);
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setPadding(new Insets(5, 0, 0, 45));
        pane.setBorder(Border.EMPTY);
        //Add all elements to pane (into grid)
        //GridLayout(int rows, int columns, int horizontalGap, int verticalGap)
        pane.add(labelExpl,0,0,2,1);
        pane.add(SearchTextField,0,1,2,2);

        primaryStage.setTitle("WebView");

        String searchQuery = "Manchester";

        HashSet searchResults = search(searchQuery, wordsToSites);
        WebView webView = new WebView();

        WebEngine webEngine = webView.getEngine();


        Map<Double,String> sortedURLs = Ranker.displayRankedResults(searchResults,searchQuery); // ranks the results

        String result3 = "";

        Iterator it = sortedURLs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            //String result2 = pair.getValue().toString();
            String result2 = (pair.getValue().toString() + "\n");
            result3 += result2 + "\n";

            it.remove(); // avoids a ConcurrentModificationException
        }
        //webEngine.loadContent(btn);

        webEngine.loadContent(result3, "text/html");

        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}