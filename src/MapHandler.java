import javafx.scene.layout.Pane;
import java.util.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;
import java.util.TreeSet;


public class MapHandler extends Pane
{
     
    private ArrayList<LocShape> allBoroughs;
    private ArrayList<CovidData> allData;
    private final int xSep = 80, ySep = 60, offset = 4; //units in pixel
    private Queue<String> allBoroughNames;
    private CovidDataLoader covidLoader;
    private LocalDate[] startEndDates;
    
    private double widthPane, heightPane;
    
    
    public MapHandler(double widthPane, double heightPane){
        
        super();
    
        this.widthPane = widthPane;
        this.heightPane = heightPane;
        
        covidLoader = new CovidDataLoader();
        allBoroughs = new ArrayList<LocShape>();
        setUpHandlers();
        setUpBoroughs();
        
        allData = covidLoader.load();
        drawMap();
        drawNames();
        
    }
    
    public void addDates(LocalDate[] startEndDates){
        this.startEndDates = startEndDates;
        TreeSet<Integer> orderedValues = new TreeSet<>();
        HashMap<String, ArrayList<CovidData>> extractedData = covidLoader.getDataDateRange(allData, startEndDates);
       
        int total= extractedData.values().stream().flatMap(ArrayList::stream).mapToInt(CovidData::getTotalDeaths).reduce(0, Integer::sum);   
       
         System.out.println("Changing colours");
        
           
        if(!allBoroughs.isEmpty()){
          
            for(LocShape borough: allBoroughs){
                
                 borough.resetData(); //to prevent additional appending
            
                 for(String key: extractedData.keySet()){
                
                     if(key.equals(borough.getName())){
                          ArrayList<CovidData> temp = (extractedData.get(key));
                           for(CovidData datum: temp){
                               borough.addData(datum);
                          }
                     }
                    
                 }
                 
                 
                borough.determineColour();
                 
            
            }
            
            
        }
    
}
    
    
    
    private void setUpHandlers(){
        this.setOnMouseClicked(event -> {
            double xVal =  event.getX();
            double yVal =  event.getY();
            
            boolean isHit = checkClickHit(new Point(xVal, yVal)); //do whatever you need to from here
        });
    }
    

    private void setUpBoroughNames(){
        allBoroughNames = new LinkedList<String>(){{
            offer("Enfield");
            offer("Barnet"); offer("Haringey"); offer("Waltham Forest");
            offer("Harrow");  offer("Brent"); offer("Camden"); offer("Islington"); offer("Hackney"); offer("Redbridge"); offer("Havering");
            offer("Hillingdon"); offer("Ealing");  offer("Kensington And Chelsea"); offer("Westminster"); offer("Tower Hamlets");  offer("Newham");  offer("Barking And Dagenham");
            offer("Hounslow");  offer("Hammersmith And Fulham");  offer("Wandsworth"); offer("City Of London"); offer("Greenwich"); offer("Bexley");
            offer("Richmond Upon Thames"); offer("Merton");  offer("Lambeth"); offer("Southwark"); offer("Lewisham");
            offer("Kingston Upon Thames"); offer("Sutton"); offer("Croydon"); offer("Bromley");
            
        }};
    }
    
    
    /*boroughs buttons are drawn relative to the starting position*/
    private void setUpBoroughs(){
        allBoroughs.clear();
        setUpBoroughNames();
        
        int xRef = (int)(0.5*widthPane);
        int yRef = (int)(0.125*heightPane);
    
        //1st row//
        setBoroughObj(1,xRef + 2*offset,yRef);
        xRef -= 120;
        yRef += ySep + offset;
        
        //2nd row//
        setBoroughObj(3,xRef + offset,yRef);
        xRef -= 120;
        yRef += ySep + offset;
        
        //3rd row//
        setBoroughObj(7,xRef,yRef);
        xRef -=40;
        yRef += ySep + offset;
        
        //4th row//
        setBoroughObj(7,xRef,yRef);
        xRef +=40;
        yRef += ySep + offset;
        
        //5th row//
        setBoroughObj(6,xRef,yRef);
        xRef +=40;
        yRef += ySep + offset;
        
        //6th row//
        setBoroughObj(5,xRef,yRef);
        xRef +=40;
        yRef += ySep + offset;
        
        //7th row//
        setBoroughObj(4,xRef,yRef);
    
       
    }
    
    /*method called when window is re-sized*/
     public void redrawMap(int newXSize){
        this.getChildren().clear();
        widthPane = newXSize;
        setUpBoroughs();
        drawMap();
        drawNames();
        
        addDates(startEndDates);
    }
    
    private void setBoroughObj(int noItems, int xStart, int yStart){
        for(int i=0; i<noItems;i++){
            String name = allBoroughNames.poll();
            allBoroughs.add(new LocShape(name,xStart,yStart));
            xStart+=(80 + offset);
        }
    }
    
    private void drawNames(){
        allBoroughs.forEach((e) -> {
          Text textLabel = new Text(e.getName().substring(0,4)); //gets the initials
          textLabel.setFill(Color.WHITE);
          textLabel.setFont(Font.font("Arial", FontWeight.BOLD,12));
          textLabel.setX(e.getOrigin().x - 10);
          textLabel.setY(e.getOrigin().y + 0.75*60);
          
          this.getChildren().add(textLabel);
          
        });
    }
    
    
    private void drawMap(){
        allBoroughs.forEach((e)->{this.getChildren().add(e.getShape());});
    }

    
    private boolean checkClickHit(Point mouseLoc){
        boolean hit = false;
        for(LocShape borough: allBoroughs){
            hit = borough.checkInBounds(mouseLoc);
            if(hit){
                borough.openStatsWindow();
                break;
            }
        }
        return hit;
        
    }
    

}
