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
 * Write a description of JavaFX class PanelSelector here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PanelSelector
{
    private Pane[] paneList;
    private int pointer;
    private Pane currentPane;
    private Button nextButton;
    private Button prevButton;
    private BorderPane root;
    
    public PanelSelector(BorderPane root) {
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
    
    public HBox createBottomPart()
    {
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
    
    public Pane getCurrentPane() {
        return currentPane;
    }
    
    private Region createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        return spacer;
    }
    
    public void disableButtons() {
        nextButton.setDisable(true);
        prevButton.setDisable(true);
        currentPane = paneList[0];
        pointer = 0;
        displayPane();
    }
    
    public void enableButtons() {
        nextButton.setDisable(false);
        prevButton.setDisable(false);
    }
    
    public void updateDateMap(LocalDate[] startEndDates){   //update the map to show colour + stats indications
        for(int i=0; i<paneList.length;i++){
            if(paneList[i] instanceof MapHandler){
                 MapHandler refMap = (MapHandler)paneList[i];
                 refMap.addDates(startEndDates);  
                 break;
            }
        }
    }
    
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
    
    /*resizes any UI elements in different panes based on stage's width and height (parameters)*/
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
    
    public void displayPane() {
        root.setCenter(currentPane);
    }
}
