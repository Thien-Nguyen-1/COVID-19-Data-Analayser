import java.time.LocalDate;

/**
 * Write a description of class DataComparator here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
