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
import java.io.File;
import javafx.beans.binding.Bindings;

/**
 * Write a description of class Frame here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javafx.geometry.Pos; // Import Pos for alignment

import javafx.geometry.Pos;
import javafx.scene.layout.Region;

import javafx.geometry.Pos;

public class Frame extends Application {

    private ImageView mapView;

    @Override
    public void start(Stage stage) throws Exception {
        DateSelector dateSelector = new DateSelector();
        BorderPane root = new BorderPane();

        HBox topPart = dateSelector.createTopPart();
        Region leftPart = new Region(); // Using a Region for flexible spacing
        Region rightPart = new Region(); // Using a Region for flexible spacing
        HBox lowerPart = new HBox();

        // Load the map image
        Image mapImage = new Image(new File("/Users/arjanbedi/Documents/GitHub/CourseWork-4-COVID-19/src/boroughs.png").toURI().toString());
        mapView = new ImageView(mapImage);

        // Set up preserving aspect ratio
        mapView.setPreserveRatio(true);

        // Create a VBox for the middle part
        VBox middlePart = new VBox();
        middlePart.setAlignment(Pos.CENTER);

        // Bind the fit width or height based on the image's aspect ratio
        mapView.fitWidthProperty().bind(Bindings.min(middlePart.widthProperty(), middlePart.heightProperty()));
        mapView.fitHeightProperty().bind(Bindings.min(middlePart.widthProperty(), middlePart.heightProperty()));

        // Add the map image to the VBox
        middlePart.getChildren().add(mapView);

        // Set the IDs of each part for styling
        topPart.setId("topPart");
        middlePart.setId("middlePart");
        lowerPart.setId("lowerPart");

        // Load CSS
        Scene scene = new Scene(root, 600, 600, Color.WHITE);
        scene.getStylesheets().add("design.css");

        // Set preferred heights for parts
        topPart.setPrefHeight(50);
        lowerPart.setPrefHeight(50);

        // Add parts to the root
        root.setTop(topPart);
        root.setLeft(leftPart);
        root.setCenter(middlePart);
        root.setRight(rightPart);
        root.setBottom(lowerPart);

        stage.setTitle("Template Structure");
        stage.setScene(scene);
        stage.show();
    }
}



