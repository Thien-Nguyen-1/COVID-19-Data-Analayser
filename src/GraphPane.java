import javafx.scene.layout.Pane;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.ArrayList;
import javafx.scene.chart.CategoryAxis;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import java.time.format.DateTimeFormatter;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.Collections; 
import java.time.LocalDate;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

/**
 * The Graph Panel class displays the cumulative data of each borough
 * in a line chart over the time period selected.
 *
 * @author Tawyeeb Soetan
 * @version 21/03/2024
 */
public class GraphPane extends Pane
{
    
    private double widthPane;
    private double heightPane;
    private CovidDataLoader covidDataLoader;
    private LineChart<String,Number> lineChart;
    private ArrayList<CovidData> allCovidData;
    private LocalDate[] dateRange;
    private List<CovidData> dataWithinRange;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private ComboBox<String> dataTypeComboBox;
    private GraphType graphType;
    private VBox vbox;
    private int graphWidth;
    private int graphHeight;
    public GraphPane(double widthPane, double heightPane)
    {
       super();
       vbox = new VBox();
       vbox.setAlignment(Pos.CENTER);
       covidDataLoader = new CovidDataLoader();
       allCovidData = covidDataLoader.load();
       
       this.widthPane = widthPane;
       this.heightPane = heightPane;
       graphWidth = 1000;
       graphHeight = 580;
       
       this.getChildren().add(vbox);
       createComboBox();
       createLineChart();
    }
    
    private void createComboBox() {
       dataTypeComboBox = new ComboBox<>();
       dataTypeComboBox.setPromptText("Select Data Type");
       ObservableList<String> dataTypes = FXCollections.observableArrayList("Total Deaths", "Total Cases");
       dataTypeComboBox.setItems(dataTypes);
       
       dataTypeComboBox.setValue("Total Deaths");
       
       dataTypeComboBox.setOnAction(e -> {
        // Call a method to handle the selection change
           handleDataTypeSelection();
       });
       
       vbox.getChildren().add(dataTypeComboBox); 
    }
    
    private void createLineChart() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        xAxis.setLabel("x-axis");
        yAxis.setLabel("y-axis");
        
        xAxis.setTickLabelRotation(45);
        lineChart = new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle("Covid Data");
        lineChart.setPrefSize(graphWidth,graphHeight);
        
        vbox.getChildren().add(lineChart);
        //vbox.setVgrow(lineChart, Priority.ALWAYS);
    }
    
    private void handleDataTypeSelection() {
        // Get the selected data type from the ComboBox
        String selectedDataType = dataTypeComboBox.getValue();
        
        if (lineChart != null) {
            lineChart.getData().clear();
        }
        // Handle the selected data type
        // For example, update the graph based on the selected data type
        if (selectedDataType != null) {
            switch (selectedDataType) {
                case "Total Deaths":
                    // Update the graph to show total deaths data
                    graphType = (cd) -> cd.getTotalDeaths();
                    plotGraph(10,"Total Deaths");
                    break;
                case "Total Cases":
                    // Update the graph to show total cases data
                    graphType = (cd) -> cd.getTotalCases();
                    plotGraph(5000, "Total Cases");
                    break;
                default:
                    graphType = (cd) -> cd.getTotalDeaths();  
                    plotGraph(10,"Total Cases");
                    break;
            }
        } 
    }
    
    public void plotGraph(int tickUnit, String seriesName){
        
        List<CovidData> dataWithinRange = allCovidData.stream().filter(obj -> isDateWithinRange(LocalDate.parse(obj.getDate()), dateRange)).toList();
        Map<String, Integer> data = sumDataPoints(dataWithinRange, graphType);
        
      
        List<Integer> sortedList = new ArrayList<>();
        List<Integer> values = new ArrayList<>(data.values());
        Collections.sort(values);
        int minValue = values.get(0);
        int maxValue = values.get(values.size()-1);
        System.out.println(minValue);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(minValue - tickUnit);
        yAxis.setUpperBound(maxValue + tickUnit);
        yAxis.setTickUnit(tickUnit);
            
        xAxis.getCategories().clear();
        xAxis.getCategories().addAll(data.keySet());
        
        
        XYChart.Series series = new XYChart.Series();
        series.setName(seriesName);
        //populating the series with data
        for (String date : data.keySet()) {
            System.out.println(date);
            series.getData().add(new XYChart.Data(date,data.get(date)));
        }
        
        lineChart.getData().add(series);
    }

    public void setDateRange(LocalDate[] dateRange) {
        this.dateRange = dateRange;
        vbox.getChildren().remove(lineChart);
        createLineChart();
        handleDataTypeSelection();
    }

    private int getDataForType(CovidData dataPoint, GraphType type) {
        return type.changeGraphDataType(dataPoint);
    }
    
    
    private Map<String, Integer> sumDataPoints(List<CovidData> reducedCovidData, GraphType type) {
        List<String> categoryList = allCovidData.stream().map(obj -> obj.getDate()).distinct().toList();
        Map<String, Integer> dupesGone = new LinkedHashMap<>();
        for (CovidData cd : reducedCovidData) {
            String date = cd.getDate();
            if (dupesGone.containsKey(cd.getDate())) {
                dupesGone.put(date, dupesGone.get(date) + getDataForType(cd, type));
            } else {
                dupesGone.put(date, cd.getTotalDeaths());
            }
        }
        
        return dupesGone;
    }
    
    public void resizeGraph(int width, int height) {
        graphHeight = height - 175;
        graphWidth = width - 50;
        lineChart.setPrefSize(graphWidth, graphHeight);
    }
    
    // is date less than or equal to other date
    private boolean isDateWithinRange(LocalDate date, LocalDate[] dateRange) {
        boolean equalOrAfterStartDate = date.isAfter(dateRange[0]) || date.isEqual(dateRange[0]);
        boolean equalOrBeforeEndDate = date.isBefore(dateRange[1]) || date.isEqual(dateRange[1]);
        return equalOrAfterStartDate && equalOrBeforeEndDate;
    }

}