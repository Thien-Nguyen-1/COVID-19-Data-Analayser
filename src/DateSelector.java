

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.event.Event;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DateCell;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
/**
 * Write a description of JavaFX class CovidViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DateSelector
{
    private Label toLabel;
    private Label fromLabel;
    private String stringDate;
    private DatePicker fromDatePicker;
    private DatePicker toDatePicker;
    private LocalDate startDate = LocalDate.of(2020, 1, 1);
    private LocalDate endDate  = LocalDate.of(2023, 10, 15);
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */

    public DateSelector() {
        toLabel = new Label("To:");
        fromLabel = new Label("From");
    }
    
    public HBox createTopPart() {
        fromDatePicker = new DatePicker();
        toDatePicker = new DatePicker();
        
        fromDatePicker.setOnAction(this :: chooseFromDate);
        toDatePicker.setOnAction(this :: chooseToDate);
        
        // set the cell factory to restrict selectable dates
        toDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                
                // disable dates outside of the specified range
                setDisable(date.isBefore(startDate) || date.isAfter(endDate));
                
            } 
        });  
        
        fromDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                
                // disable dates outside of the specified range
                setDisable(date.isBefore(startDate) || date.isAfter(endDate));
                
            } 
        });
        
        // Create HBox
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_RIGHT); // allign to the right
        
        hbox.getChildren().addAll(fromLabel, fromDatePicker, toLabel, toDatePicker);

        return hbox;
    }
    
    /**
     * This will be executed when the button is clicked
     * It increments the count by 1
     */
    private void chooseFromDate(Event event)
    {
        LocalDate date = fromDatePicker.getValue();
        stringDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(stringDate);
    }
    
    private void chooseToDate(Event event)
    {
        LocalDate date = toDatePicker.getValue();
        stringDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(stringDate);
    }
}
