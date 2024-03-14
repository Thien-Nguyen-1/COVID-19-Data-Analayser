import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.Group; 
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.scene.paint.Color; 
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import java.io.IOException;
import javafx.scene.shape.SVGPath;
import javafx.scene.layout.StackPane;
import java.awt.Point;

import javafx.scene.shape.Polygon;
import java.util.Arrays;

import javafx.geometry.Pos; // Import Pos for alignment
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;

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
        
        DateSelector dateSelector = new DateSelector(paneSelector);
        BorderPane root = new BorderPane();
        
         paneSelector = new PanelSelector(root);
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

        stage.setTitle("Template Structure");
        stage.setScene(scene);
        stage.show();
        
    }
}



