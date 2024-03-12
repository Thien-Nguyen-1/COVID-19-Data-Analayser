

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import java.util.ArrayList;


import javafx.stage.Stage;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

/**
 * Write a description of JavaFX class TheSimpleGUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CovidStatisticsWindow extends Application
{
    // We keep track of the count, and label displaying the count:
    private int count = 0;
    private Label myLabel = new Label("0");

    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage)
    {
        // Create a Button or any control item
        Button forwardButton = new Button(">");
        Button backButton = new Button("<");

        // Create a new grid pane
        BorderPane layout = new BorderPane();
        

        //set an action on the button using method reference
        forwardButton.setOnAction(this::forwardButtonClick);
        backButton.setOnAction(this::backButtonClick);
        
        // Add the buttons and label to the pane
        layout.setLeft(backButton);
        layout.setRight(forwardButton);
        layout.setCenter(myLabel);
        

        // Set some padding for better layout
        BorderPane.setMargin(backButton, new Insets(10));
        BorderPane.setMargin(forwardButton, new Insets(10));

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(layout, 300,100);
        stage.setTitle("SamplingFrame");
        stage.setScene(scene);
        
        //setting preferred button sizes
        backButton.prefHeightProperty().bind(layout.widthProperty());
        forwardButton.prefHeightProperty().bind(layout.widthProperty());
        backButton.prefWidthProperty().bind(layout.widthProperty());
        forwardButton.prefWidthProperty().bind(layout.widthProperty());
        
        forwardButton.setMaxWidth(30);
        backButton.setMaxWidth(30);

        // Show the Stage (window)
        stage.show();
    }

    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     */
    private void forwardButtonClick(ActionEvent event)
    {
        
    }
    
    private void backButtonClick(ActionEvent event)
    {
        
    }
    
    private int[] calculateStatistics(ArrayList<CovidData> covidDataList){
        //initialising collection holding all stats
        int[] calculatedStats;
        calculatedStats = new int[5];
        
        //initialising holders for relevant statistics
        double averageRetailGMR = 0;
        double averageWorkplacesGMR = 0;
        double totalNumberOfDeaths = 0;
        double averageTotalCases = 0;
        int numberOfRecords = 0;
        
        //Calculating the statistics
        for (CovidData data : covidDataList) {
            numberOfRecords += 1;
            
            //Incrementing values to total number of deaths
            totalNumberOfDeaths += data.getNewDeaths();
            
            //Incrementing each records data to get a total so that an average can be found after. 
            averageRetailGMR += data.getRetailRecreationGMR();
            averageWorkplacesGMR += data.getWorkplacesGMR();
            averageTotalCases += data.getNewCases();
        }
        
        //dividing by number of records to obtain an average
        averageRetailGMR /= covidDataList.size();
        averageWorkplacesGMR = covidDataList.size();
        averageTotalCases = covidDataList.size();
        
        return calculatedStats;
    }
    
}