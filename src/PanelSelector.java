import java.util.LinkedList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import java.time.LocalDate;

/**
 * The panel selctor class adds the functionality of being able to rotate
 * the main panes of the statistics viewer:
 * WelcomePage pane, MapHandler panel, StatisticsPane pane and the GraphPane pane.
 * 
 * This is done through creating the bottom part of the window that will contains
 * navigation buttons that allow the user to rotate through panels.
 *
 * @author Tawyeeb Soetan
 * @version 21/03/2024
 */

public class PanelSelector
{
    private Pane[] paneList;
    private int pointer;
    private Pane currentPane;
    private Button nextButton;
    private Button prevButton;
    private BorderPane root;
    
    /**
     * Class constructor. Initialises root and paneList attributes as well as 
     * buttons.
     */
    public PanelSelector(BorderPane root) {
        //Initialising root, paneList and currentPane
        this.root = root;
        paneList = new Pane[]{new WelcomePage(), new MapHandler(1080,720), new StatisticsPane(1080,720), new GraphPane(1080,720)}; //change Panes for 1st and 3rd 
        currentPane = paneList[0];
 
        // creating left and right buttons to move between panels
        nextButton = new Button(">");
        prevButton = new Button("<");
        
        // changes the font size of the arrows
        Font font = Font.font("System", FontWeight.NORMAL, 20);
        nextButton.setFont(font);
        prevButton.setFont(font);
        
        // Disabling the button before date selection
        this.disableButtons();
    }
    
    /**
     * The function createBottomPart creates an HBox and returns it. 
     * 
     * This HBox contains the next and prev buttons that are used to select 
     * the panels. The method also formats the button positions within the HBox.
     */
    public HBox createBottomPart()
    {
        //
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        
        nextButton.setOnAction(this :: goToNextPanel);
        prevButton.setOnAction(this :: goToPrevPanel);
        
        prevButton.setPrefWidth(80);
        prevButton.setPrefHeight(10);
        nextButton.setPrefWidth(80);
        nextButton.setPrefHeight(10);
        
        hbox.getChildren().addAll(prevButton, createSpacer(), nextButton);
        hbox.setAlignment(Pos.CENTER);

        return hbox;
    }
    
    /**
     * createSpacer() will create a spacer region to be used to control layout
     * and spacing of children nodes in an HBox.
     */
    private Region createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        return spacer;
    }
    
    /**
     * disableButtons() will disable the next and prev buttons so the user can
     * no longer use them.
     */
    public void disableButtons() {
        nextButton.setDisable(true);
        prevButton.setDisable(true);
        currentPane = paneList[0];
        pointer = 0;
        displayPane();
    }
    
    /**
     * enableButton() will allow users to use the next and prev buttons.
     */
    public void enableButtons() {
        nextButton.setDisable(false);
        prevButton.setDisable(false);
    }
    
    /*Update methods:
     * The updateDateMap() and updateStatisticsPane methods will be called 
     * externally through the date selector class in order to update the 
     * display for changes in first and last date.
    */
   
    /**
     * This method is called through the date selector class to update the map pane
     * when there is a change in the first or last date.
     */
    public void updateDateMap(LocalDate[] startEndDates){  
        //update the map to show colour + stats indications
        for(int i=0; i<paneList.length;i++){
            if(paneList[i] instanceof MapHandler){
                 MapHandler refMap = (MapHandler)paneList[i];
                 refMap.addDates(startEndDates);  
                 break;
            }
        }
    }
    
    /**
     * This method is called through the date selector class to update panel 3
     * when there is a change in the first or last date.
     */
    public void updateStatisticsPane(LocalDate[] firstLastDates){
        for(int i=0; i<paneList.length;i++){
            //check for if current pane is map
            if(paneList[i] instanceof StatisticsPane){
                StatisticsPane temp = (StatisticsPane) paneList[i];
                temp.updateDates(firstLastDates);
                break;
            }
        }
    }
    
    /**
     * The following method allows the user to go forward through the paneList, 
     * rotating back to the index 0 after the max index.
     */
    public void goToNextPanel(Event event) {
        if(pointer== paneList.length -1){
            pointer = 0;
            currentPane = paneList[pointer];
        }
        else {
            pointer++;
            currentPane = paneList[pointer];
        }
        displayPane();
    }
    
    /**
     * The following method allows the user to go back through the paneList, 
     * rotating back to the max index after index 0.
     */
    public void goToPrevPanel(Event event) {
       if(pointer==0){
            pointer = paneList.length -1;
            currentPane = paneList[pointer];
        }
        else {
            pointer--;
            currentPane = paneList[pointer];
        }
    
        displayPane();
    }
    
    /**
     * The resizeUI method resizes any UI elements in different panes based on
     * stage's width and height (parameters).
     */
    public void resizeUI(double width, double height){
        if(paneList.length >0 ){
            for(Pane currPane: paneList){
                if(currPane instanceof MapHandler){ //re-centering UI elements in MapHandler pane
                    MapHandler tempPane = (MapHandler) currPane;
                    tempPane.redrawMap((int)width);
                }
            }
        }
    }
    
    /**
     * Updates the BorderPane(root) to display the currentPane.
     */
    public void displayPane() {
        root.setCenter(currentPane);
    }
    
    /**
     * Get Method- Returns the current pane that is to be or is being viewed
     * by the user.
     */
    public Pane getCurrentPane() {
        return currentPane;
    }
}
