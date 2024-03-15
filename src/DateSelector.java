import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import javafx.event.Event;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DateCell;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;

/**
 * Write a description of JavaFX class CovidViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DateSelector
{
    private Label toLabel; // Label of "To" date picker
    private Label fromLabel; // Label of "From" date picker
    private String stringDate;
    private DatePicker fromDatePicker;
    private DatePicker toDatePicker;
    private LocalDate firstDate; // Earliest date with available data
    private LocalDate lastDate; // Latest date with available data
    private LocalDate fromDate; // Earliest date picked by user for
    private LocalDate toDate; // Last date picked by user
    private PanelSelector ps; // Object that controls current panel movement

    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */

    public DateSelector(PanelSelector ps) {
        toLabel = new Label("To:");
        fromLabel = new Label("From");
        firstDate = LocalDate.of(2020, 1, 1);
        lastDate  = LocalDate.of(2023, 10, 15);
        this.ps = ps;
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
                setDisable(date.isBefore(firstDate) || date.isAfter(lastDate));
                
            } 
        });  
        
        fromDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // disable dates outside of the specified range
                setDisable(date.isBefore(firstDate) || date.isAfter(lastDate));
                
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
        System.out.println(date.toString() + " is the current date");
        
        if (date.isBefore(firstDate) || date.isAfter(lastDate)) {
            displayErrorMessage("No data from this date");
            ps.disableButtons();
            fromDatePicker.setValue(null);
            fromDate = null;
            return;
        } else if (toDate != null && date.isAfter(toDate)) {
            displayErrorMessage("Start date cannot be after end date");
            ps.disableButtons();
            fromDatePicker.setValue(null);
            fromDate = null;
            return;
        }
        
        fromDate = date;
        if (isDateRangeValid()) {
            ps.updateDateMap(getFirstLastDate()); 
            ps.enableButtons();
        }
        stringDate = dateToString(date);
    }
    
    private void chooseToDate(Event event)
    {
        LocalDate date = toDatePicker.getValue();
     
        if (date.isBefore(firstDate) || date.isAfter(lastDate)) {
            displayErrorMessage("No data from this date");
            ps.disableButtons();
            toDatePicker.setValue(null);
            toDate = null;
            return;
        } else if (toDate != null && date.isBefore(fromDate)) {
            displayErrorMessage("End date cannot be before start date");
            ps.disableButtons();
            toDatePicker.setValue(null);
            toDate = null;
            return;
        }
        
        toDate = date;
        if (isDateRangeValid()) {
            ps.enableButtons();
            ps.updateDateMap(getFirstLastDate()); //returns the date interval
            ps.updateStatisticsPane(getFirstLastDate());
        }
        stringDate = dateToString(date); // convert date object to string
     
    }
    
    private void displayErrorMessage(String reason) {
        //System.out.println("Date entered is invalid. Reason: " + reason);
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Date Error");
        alert.setHeaderText(null);
        alert.setContentText(reason);
        
        alert.showAndWait();
    }
    
    /* retrieves the date range */
    public LocalDate[] getFirstLastDate(){ 
        return new LocalDate[]{fromDate,toDate};
    }
    
    
    private String dateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
    public boolean isDateRangeValid() {
        
        return fromDate != null && toDate != null;
    }
}
