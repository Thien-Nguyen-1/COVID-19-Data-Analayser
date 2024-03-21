import java.time.LocalDate;

/**
 * A comparator class used to compare the date attributes of 2 covid data classes.
 *
 * @author Tawyeeb Soetan
 * @version 19/03/2023
 */
public class CDDateComparator implements java.util.Comparator<CovidData>
{
    
    // Compares the dates of the CovidData objects
    @Override
    public int compare(CovidData cd1, CovidData cd2)
    {
        LocalDate date1 = LocalDate.parse(cd1.getDate());
        LocalDate date2 = LocalDate.parse(cd2.getDate());
        return date1.compareTo(date2);
    }
}
