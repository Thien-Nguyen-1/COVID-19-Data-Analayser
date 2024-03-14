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
    private double xStart, yStart;
    private final double xOffset = 40; //should be halved when reach a corner
    private final double yOffset = 60;
    Point test;
    public LocShape(String name, double xStart, double yStart)
    {
       this.name = name;
       this.xStart = xStart;
       this.yStart = yStart;
       shape = new Polygon();
       allPoints = new ArrayList<Point>();
       setUpShape();
    }
    
    public void resetShape(double xStart, double yStart){
        this.xStart = xStart;
        this.yStart = yStart;
        allPoints.clear();
        setUpShape();
    }
    
    
    private void setUpShape(){
      
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
