//Imports for GUI 
//Layout elements
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
//Components
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

//Formatting layout
import javafx.geometry.Pos;

//Imports for class functionality
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Write a description of JavaFX class TheSimpleGUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class StatisticsPane extends BorderPane
{
    private double widthPane, heightPane;
    
    // We keep track of the count, and label displaying the count:
    private int count = 0;
    private Label statMeasure = new Label();
    private Label statistic = new Label();
    
    //Get covid data into an array list
    CovidDataLoader dataLoader = new CovidDataLoader();
    private ArrayList records = dataLoader.load();
    
    String[] statisticLabels;
    int[] statistics;
    
    
    //create variable to store currently displayed stat index
    private int statIndex = 0;
    
    //Store date range variables
    LocalDate startDate;
    LocalDate endDate;
    
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */

    public StatisticsPane(double widthPane, double heightPane)

    {
        super();
        
        // Create a Button or any control item
        Button forwardButton = new Button(">");
        Button backButton = new Button("<");

        // Create a new grid pane
        VBox centerLabels = new VBox(statMeasure, statistic);
        
        // Set an action on the button using method reference
        forwardButton.setOnAction(this::forwardButtonClick);
        backButton.setOnAction(this::backButtonClick);
        

        // Add the buttons and label to the pane
        this.setLeft(backButton);
        this.setRight(forwardButton);
        this.setCenter(centerLabels);
        
        // Set alignment of the VBox to center its content
        centerLabels.setAlignment(Pos.CENTER);
        
        // Set some padding for better layout
        BorderPane.setMargin(backButton, new Insets(10));
        BorderPane.setMargin(forwardButton, new Insets(10));
        
        //setting preferred button sizes
        backButton.prefHeightProperty().bind(this.widthProperty());
        forwardButton.prefHeightProperty().bind(this.widthProperty());
        backButton.prefWidthProperty().bind(this.widthProperty());
        forwardButton.prefWidthProperty().bind(this.widthProperty());
        
        forwardButton.setMaxWidth(30);
        backButton.setMaxWidth(30);
        
        //Create the array containing stastics labels 
        this.statisticLabels = setUpLabelArray();
        
        //Preventing null pointer exceptions by creating initial statistics array full of zeros
        this.statistics = setupInitialStats();
        
    }

    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     */

    private void forwardButtonClick(ActionEvent event)
    {
        if (statIndex == 3){
            statIndex = 0;
        }
        else{
            statIndex += 1;
        }
        
        showStatistics();
        
    }
    
    private void backButtonClick(ActionEvent event)
    {
        if (statIndex == 0){
            statIndex = 3;
        }
        else{
            statIndex -= 1;
        }
        
        statMeasure.setText(statisticLabels[statIndex]);
        statistic.setText(Integer.toString(statistics[statIndex]));
    }

    private int[] calculateStatistics(ArrayList<CovidData> covidDataList){
        //initialising collection holding all stats
        int[] calculatedStats;
        calculatedStats = new int[5];

        //initialising holders for relevant statistics
        int totalRetailGMR = 0;
        int totalWorkplacesGMR = 0;
        int totalNumberOfDeaths = 0;
        int averageTotalCases = 0;
        int numberOfRecords = 0;

        //Calculating the statistics
        for (CovidData data : covidDataList) {
            System.out.println(data.getDate());
            LocalDate checkDate = LocalDate.parse(data.getDate());

            if(dateinRange(checkDate)){
                numberOfRecords += 1;

                //Incrementing values to total number of deaths
                totalNumberOfDeaths += data.getNewDeaths();
            
                //Incrementing each records data to get a total so that an average can be found after. 
                totalRetailGMR += data.getRetailRecreationGMR();
                totalWorkplacesGMR += data.getWorkplacesGMR();
                averageTotalCases += data.getTotalCases();
            }    
        }
        
        
        //dividing by number of records to obtain an average
        int averageRetailGMR = totalRetailGMR / numberOfRecords;
        int averageWorkplacesGMR = totalWorkplacesGMR / numberOfRecords;
        averageTotalCases = covidDataList.size();
        
        calculatedStats[0] = averageRetailGMR;
        calculatedStats[1] = averageWorkplacesGMR;
        calculatedStats[2] = totalNumberOfDeaths;
        calculatedStats[3] = averageTotalCases / numberOfRecords;

        return calculatedStats;
    }
    
    private String[] setUpLabelArray(){
        String[] statisticLabels;
        statisticLabels = new String[4];
        
        // Add Labels to the array to go through
        statisticLabels[0] = "Percentage Change in Retail Traffic:";
        statisticLabels[1] = "Percentage Change in Workplace Traffic:";
        statisticLabels[2] = "Total Number of Deaths:";
        statisticLabels[3] = "Average Total Cases:";
        
        return statisticLabels;
    }
    
    private int[] setupInitialStats(){
        int[] temp;
        temp = new int[4];
        //add zeros
        temp[0] = 0;
        temp[1] = 0;
        temp[2] = 0;
        temp[3] = 0;
        
        return temp;
    }
    
    //This method updates the statistics window to show values of the arrays
    private void showStatistics(){
        statMeasure.setText(statisticLabels[statIndex]);
        statistic.setText(Integer.toString(statistics[statIndex]));
    }
    
    
    //return testDate.after(startDate) && testDate.before(endDate);
    private boolean dateinRange(LocalDate checkDate){
        if (checkDate == null || this.startDate == null || this.endDate == null) {
            return false; // Handle null values gracefully
        }
        return checkDate.isAfter(this.startDate) && checkDate.isBefore(this.endDate);
    }
    
    public void updateDates(LocalDate[] startEndDates){
        this.startDate = startEndDates[0];
        this.endDate = startEndDates[1];
        this.statistics = setupInitialStats();
        this.statistics = calculateStatistics(records);
        showStatistics();
    }
} 
