import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Group;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.HashMap;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;

/**
 * Write a description of JavaFX class BoroughStatsWindow here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BoroughStatsWindow
{

    public void openWindow(String boroughName, ArrayList<CovidData> boroughData)
    {
        TableView<CovidData> table = new TableView<>();
        Stage boroughWindow = new Stage();
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox);
        
        // defiining size of window
        boroughWindow.setWidth(500);
        boroughWindow.setHeight(500);
        
        boroughWindow.setScene(scene);
        
        // Title and Label
        boroughWindow.setTitle(boroughName);
        table.setEditable(true);
        Label label = new Label("Covid Data");
        
        ObservableList<CovidData> data = FXCollections.observableArrayList(boroughData);
        
        // Creating the Date Column
        TableColumn<CovidData, String> dateCol = new TableColumn<>("Date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        
        
        // Creating Columns for Google Mobility Data
        TableColumn gmCol = new TableColumn("Google Mobility");
        
        TableColumn <CovidData, Number> retailRecCol = new TableColumn<>("Retail Recreation");
        retailRecCol.setMinWidth(150);
        retailRecCol.setCellValueFactory(cellData -> new SimpleIntegerProperty((cellData.getValue().getRetailRecreationGMR())));
        
        TableColumn <CovidData, Number> groceryPharmacyCol = new TableColumn<>("Grocery Pharmacy");
        groceryPharmacyCol.setMinWidth(150);
        groceryPharmacyCol.setCellValueFactory(cellData -> new SimpleIntegerProperty((cellData.getValue().getGroceryPharmacyGMR())));
        
        TableColumn <CovidData, Number> parksCol = new TableColumn<>("Parks");
        parksCol.setMinWidth(100);
        parksCol.setCellValueFactory(cellData -> new SimpleIntegerProperty((cellData.getValue().getParksGMR())));
        
        TableColumn <CovidData, Number> transitCol = new TableColumn<>("Transit");
        transitCol.setMinWidth(100);
        transitCol.setCellValueFactory(cellData -> new SimpleIntegerProperty((cellData.getValue().getTransitGMR())));
        
        TableColumn <CovidData, Number> workplacesCol = new TableColumn<>("Workplaces");
        workplacesCol.setMinWidth(100);
        workplacesCol.setCellValueFactory(cellData -> new SimpleIntegerProperty((cellData.getValue().getWorkplacesGMR())));
        
        TableColumn <CovidData, Number> residentialCol = new TableColumn<>("Residential");
        residentialCol.setMinWidth(100);
        residentialCol.setCellValueFactory(cellData -> new SimpleIntegerProperty((cellData.getValue().getResidentialGMR())));
        
        gmCol.getColumns().addAll(retailRecCol, groceryPharmacyCol, parksCol, transitCol, workplacesCol, residentialCol);
        
        // Creating Columns for Covid cases
        TableColumn casesCol = new TableColumn("Covid Cases");
        TableColumn<CovidData, Number> newCasesCol = new TableColumn<>("New");
        newCasesCol.setMinWidth(100);
        newCasesCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNewCases()));
        
        TableColumn<CovidData, Number> totalCasesCol = new TableColumn<>("Total");
        totalCasesCol.setMinWidth(100);
        totalCasesCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTotalCases()));
        
        casesCol.getColumns().addAll(newCasesCol,totalCasesCol);
        
        
        // Creating Columns for Deaths
        TableColumn deathsCol = new TableColumn("Deaths");
        
        TableColumn<CovidData, Number> newDeathsCol = new TableColumn<>("New");
        newDeathsCol.setMinWidth(100);
        newDeathsCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNewDeaths()));
        
        TableColumn<CovidData, Number> totalDeathsCol = new TableColumn<>("Total");
        totalDeathsCol.setMinWidth(100);
        totalDeathsCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNewDeaths()));
        
        deathsCol.getColumns().addAll(newDeathsCol, totalDeathsCol);
        
        table.setItems(data);
        table.getColumns().addAll(dateCol, gmCol, casesCol, deathsCol);
        
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));  
        vbox.setVgrow(table, Priority.ALWAYS);
        vbox.getChildren().addAll(label, table);
        

        
        boroughWindow.setScene(scene);
        // Show the Stage (window)
        boroughWindow.show();
    }
}
