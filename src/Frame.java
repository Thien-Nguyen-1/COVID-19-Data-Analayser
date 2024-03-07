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

/**
 * Write a description of class Frame here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Frame extends Application
{
 
    /**
     * Constructor for objects of class Frame
     */
    public Frame()
    {
        
    }
     public static void main(String args[]){           
        launch(args);      
    } 
    
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
   
        HBox topPart = new HBox();    // where the date selection is located
        Pane middlePart = new Pane(); // where the main map is located
        HBox lowerPart = new HBox();  // where the buttons are to change the panels
         
        //set the IDs of each part so they can be modified in CSS file
        topPart.setId("topPart");
        middlePart.setId("middlePart");
        lowerPart.setId("lowerPart");
        
        Text textLabel = new Text("Top Bar");
        Text textLabelLow = new Text("Lower Bar");
        Text textLabelMid = new Text("Middle Bar");

        Scene scene = new Scene(root, 600,600, Color.WHITE);
        scene.getStylesheets().add("design.css");

        topPart.getChildren().addAll(textLabel);
        middlePart.getChildren().addAll(textLabelMid); // Add content to middlePart
        lowerPart.getChildren().addAll(textLabelLow);

        topPart.setPrefHeight(50);
        middlePart.setPrefHeight((double)(scene.getHeight()/(2)));
        lowerPart.setPrefHeight(50);
      
        root.setTop(topPart);
        root.setCenter(middlePart);
        root.setBottom(lowerPart);

        stage.setTitle("Template Structure");
        stage.setScene(scene);
        stage.show();
        
    }

    }
    
    
    

    

