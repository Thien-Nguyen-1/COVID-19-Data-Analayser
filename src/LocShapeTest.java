
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color; 

/* 
 UNIT TESTING for coursework criteria
 Normal & Extreme cases tested 
*/
public class LocShapeTest
{
    LocShape boroughShape;
    
    public LocShapeTest()
    {
        
    }

    @BeforeEach
    public void setUp(){
        boroughShape = new LocShape("Bromley", 5 , 6);   //test case, point of origin at coordinate (5,6)
    }
    
    @Test
    public void checkName(){
        assertEquals("Bromley", boroughShape.getName()); //check if name of borough is equal to the one specified when instantiating a LocShape
    }
    
    @Test
    public void checkGetShape(){
        assertNotNull(boroughShape.getShape()); //check if polygon shape returned isn't null
    }
    
    @Test
    public void checkAddData(){
        CovidData data1 = new CovidData("1-1-2022", "Bromley", 0, 0, 
                        0, 0, 0, 0, 0, 0, 0, 0);
        try{
            
            boroughShape.addData(data1); //adding a single piece of data to the borough
            
            assertEquals(1, boroughShape.getData().size());  //check if there is only 1 CovidData object in the borough
            assertEquals(data1, boroughShape.getData().get(0)); //check if the the CovidData object stored in borough is referring to the same one as the CovidData in this method
        }
        catch(Exception e){
            fail("Unable to add data test case");
        }
    }
    
    @Test 
    public void checkClearData() {
        boroughShape.resetData(); //check to see if borough clears all of its CovidData objects
        
        assertEquals(0, boroughShape.getData().size()); //check if array list storing CoviData is empty
    }
    
    @Test
    public void checkGetOrigin() {
        assertEquals(5, (int)boroughShape.getOrigin().x ); //check if x-value of borough corresponds to the value passed in when instantiating a borough object (in setUp() method)
        assertEquals(6, (int)boroughShape.getOrigin().y); //check if y-value ... ...
    }
   
    
    @Test
    public void checkInBounds(){
        Point testPoint = new Point(5,6.5); // check if a normal point lies within the bounds of the hexagon shape button of the borough
        assertEquals(true, boroughShape.checkInBounds(testPoint)); 
        
        Point testPoint2 = new Point(100000000,1000000000); //check if a extreme point lies within bounds ... ...
        assertEquals(false, boroughShape.checkInBounds(testPoint2)); 
    }
  

    @Test
    public void checkResetShape(){
        boroughShape.resetShape(15,16); //setting a new origin point
        assertEquals(15,(int)boroughShape.getOrigin().x); //check if origin of a borough is updated
        assertEquals(16,(int)boroughShape.getOrigin().y);
        
    }
    
    @Test
    public void checkDetermineColour(){
        
        assertEquals(Color.GREY, boroughShape.getColour());  //check if colour of borough shape is grey when no data is added    
         
        CovidData data1 = new CovidData("2022-10-15", "Bromley", 0, 0, 
                        0, 0, 0, 0, 0, 0, 0, 1);
        
        boroughShape.addData(data1);
        boroughShape.determineColour();
        assertEquals(Color.GREEN, boroughShape.getColour()); // check if the colour of borough shape is green when total number of deaths is less than 2500
        
        CovidData data2 = new CovidData("2022-10-15", "Bromley", 0, 0, 0, 0, 0, 0, 0, 0, 0, 18000); 
        boroughShape.resetData();
        boroughShape.addData(data2);
        boroughShape.determineColour();
        assertEquals(Color.YELLOW, boroughShape.getColour()); //check if the colour of borough shape is yellow when total number of deaths is less than 20000
        
        CovidData data3 = new CovidData("2022-10-15", "Bromley", 0, 0, 0, 0, 0, 0, 0, 0, 0, 30000); 
        boroughShape.resetData();
        boroughShape.addData(data3);
        boroughShape.determineColour();
        assertEquals(Color.RED, boroughShape.getColour()); //check if the colour of borough shape is yellow when total number of deaths is greater than 20000
        
        
    }

}
