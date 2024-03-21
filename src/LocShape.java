import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import javafx.scene.paint.Color; 

/**
 * The LocShape class is used to represent each London Borough on 
 * the map. 
 * 
 * It creates a hexagon for each London Borough. Each Hexagon will 
 * be assigned a colour based on the death toll in each borough.
 * 
 * @Author Thien Nguyen
 * @Version 21/03/24
 */

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
    
    /**
     * Defines an enum called DeathRateColour, which represents 
     * different colors associated with death rates.
     */
    public enum DeathRateColour{
          GREEN(Color.GREEN), YELLOW(Color.YELLOW), RED(Color.RED);
          final Color color;
          DeathRateColour(Color color) {
             this.color = color;
          }
     }
    
    /**
     * The constructor establishes start x,y coordinates(origin point)
     * to draw the shape around.
     */
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
    
    /* 
     * Method to add data on to array list, used for colour of 
     * button and statistics 
     */
    public void addData(CovidData dataToAdd){
        allData.add(dataToAdd);
    }
    
    /*method to get all the covid data*/
    public ArrayList<CovidData> getData(){
        return allData;
    }
    
    /* method to get the shape colour*/
    public Color getColour(){
        return shapeColor;
    }
    
    /*Clears all data*/
    public void resetData(){
        allData.clear();
    }
    
    /**
     * Method to determine the colour of the button based on list of
     * covid data and total no. of deaths
     */
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
    
    /**
     * Resets the shape by changing the start x,y coordinates
     */
    public void resetShape(double xStart, double yStart){
        this.xStart = xStart;
        this.yStart = yStart;
        allPoints.clear();
        shape.getPoints().clear();
        setUpShape();
    }
    
    /**
     * This function draws each hexagon usng the start x and y 
     * coordinates as a base point.
     */
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
    
    //Prints all points to the terminal
    public void printBounds(){
        if(allPoints.size() > 0){
            for(Point point: allPoints){
                System.out.println("x:"  + point.x + " y:" + point.y);
            }
        }
        else {
            System.out.println("allPoints is empty, cannot print");
        }
    }
    
    //Checks if the mouse pointer is within the shape
    public boolean checkInBounds(Point mousePoint){    
        int noOfIntersections = 0;
        
        if(allPoints.size() == 0){
            return false;
        }
        
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
    
    //Get methods
    //Returns name
    public String getName(){
        return name;
    }
    
    //Returns name
    public Point getOrigin(){
        return new Point(xStart, yStart);
    }
    
    //Returns the shape
    public Polygon getShape(){
        return shape;
    }
}
