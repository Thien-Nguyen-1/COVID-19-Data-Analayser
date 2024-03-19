//
import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import javafx.scene.paint.Color; 
//import java.awt.Point;





public class LocShape
{ 
    private String name;
    private Polygon shape;
    private ArrayList<Point> allPoints;
    private ArrayList<CovidData> allData; //holds list of all covid data associated with this borough in a particular date interval
    
    private double xStart, yStart;
    private final double xOffset = 40; //should be halved when reach a corner
    private final double yOffset = 60;
    
   
    private Color shapeColor;
    
    public enum DeathRateColour{
          GREEN(Color.GREEN), YELLOW(Color.YELLOW), RED(Color.RED);
          final Color color;
          DeathRateColour(Color color) {
             this.color = color;
          }
     }
    
    public LocShape(String name, double xStart, double yStart)
    {
       this.name = name;
       this.xStart = xStart;
       this.yStart = yStart;
           
       shapeColor = Color.GREY;
       
       allData = new ArrayList<CovidData>();
       shape = new Polygon();
       allPoints = new ArrayList<Point>();
       setUpShape();
    }
    
    /* method to add data on to array list, used for colour of button and statistics */
    public void addData(CovidData dataToAdd){
        allData.add(dataToAdd);
    }
    
    /*method to get all the covid data*/
    public ArrayList<CovidData> getData(){
        return allData;
    }
    
    
    public void resetData(){
        allData.clear();
    }
    
    /*method to determine the colour of the button based on list of covid data and total no. of deaths*/
    public void determineColour(){
    
      int boroughDeaths = allData.stream().mapToInt(obj -> obj.getTotalDeaths()).sum();
     
      if(boroughDeaths <= 2500) {
          shapeColor = DeathRateColour.GREEN.color;
      }
      else if(boroughDeaths <= 20000){
          shapeColor = DeathRateColour.YELLOW.color;
      }
      else {
          shapeColor = DeathRateColour.RED.color;
      }
      
     resetShape(xStart, yStart);
    }
    
   
    public void resetShape(double xStart, double yStart){
        this.xStart = xStart;
        this.yStart = yStart;
        allPoints.clear();
        shape.getPoints().clear();
        setUpShape();
    }
    
    
    private void setUpShape(){
        
        shape.setFill(shapeColor);
        
        shape.getPoints().addAll(xStart, yStart); //top left corner of the hexagon
        allPoints.add(new Point(xStart,yStart));
        
        shape.getPoints().addAll(xStart + xOffset, yStart + 0.5*yOffset);
        allPoints.add(new Point((xStart + xOffset), (yStart + 0.5 * yOffset)));
        
        shape.getPoints().addAll(xStart + xOffset, yStart + yOffset);
        allPoints.add(new Point((xStart + xOffset),(yStart + yOffset)));
        
        shape.getPoints().addAll(xStart, yStart + 1.5*yOffset);
        allPoints.add(new Point((xStart), (yStart + 1.5*yOffset)));
        
        shape.getPoints().addAll(xStart - xOffset, yStart + yOffset);
        allPoints.add(new Point((xStart - xOffset),( yStart + yOffset)));
        
        shape.getPoints().addAll(xStart - xOffset, yStart + 0.5*yOffset);
        allPoints.add(new Point((xStart- xOffset),(yStart + 0.5*yOffset)));
     
        shape.setOpacity(0.5);
        shape.setVisible(true);
    }
    
    public String getName(){
        return name;
    }
    public Point getOrigin(){
        return new Point(xStart, yStart);
    }
    
    public void printBounds(){
        for(Point point: allPoints){
            System.out.println("x:"  + point.x + " y:" + point.y);
        }
    }
    
    public boolean checkInBounds(Point mousePoint){    
        
        int noOfIntersections = 0;
        
        for(int i=0; i<=allPoints.size() - 1; i++){
            //obtain the line from the two points
            Point p1, p2;
            p1 = allPoints.get(i);
            
            if(i==allPoints.size() - 1){
              p2 = allPoints.get(0);
            }
            else {
             p2 = allPoints.get(i+1);
            }
            if(((p2.y <= mousePoint.y) && (mousePoint.y<= p1.y)) || ((p1.y <= mousePoint.y) && (mousePoint.y<= p2.y))){
                double intersect =  p1.x + (mousePoint.y-p1.y)*((p1.x-p2.x)/(p1.y-p2.y));
                if(mousePoint.x <= intersect){
                    noOfIntersections++;
                }
            }
        }
        
        return (noOfIntersections%2 == 1);
    }
    
    public Polygon getShape(){
        return shape;
    }
}
