import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color; 
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import java.io.IOException;


public class Frame extends Application {

    private HBox topPart;
    private Pane middlePart;
    private HBox lowerPart;
    private BorderPane root;
    private PanelSelector paneSelector;
    
    @Override
    public void start(Stage stage) throws Exception {
        
    
        stage.setMinWidth(1080);
        stage.setMinHeight(720);
        
        
        BorderPane root = new BorderPane();
        
        paneSelector = new PanelSelector(root);
        DateSelector dateSelector = new DateSelector(paneSelector);
        // Load CSS
        Scene scene = new Scene(root, 1080, 720, Color.WHITE);
        scene.getStylesheets().add("design.css");
        
        topPart = dateSelector.createTopPart();
        middlePart = paneSelector.getCurrentPane();
        lowerPart = paneSelector.createBottomPart();

        topPart.setPrefHeight(50);
        middlePart.setPrefHeight((double)(scene.getHeight()/(2))); //keep this donnot destroy
        lowerPart.setPrefHeight(50);

        //Set the IDs of each part for styling
        topPart.setId("topPart");
        middlePart.setId("middlePart");
        lowerPart.setId("lowerPart");

        root.setTop(topPart);
        root.setCenter(middlePart);
        root.setBottom(lowerPart); 

        stage.setTitle("Covid Data Viewer");
        stage.setScene(scene);
        stage.show();
        
    }
}



