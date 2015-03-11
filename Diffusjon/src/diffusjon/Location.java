package diffusjon;

/**
 * Represent a location in a 3-dimensional coordinate system
 * 
 * @VidyPie
 * @version 2015.03.04
 */
public class Location
{

    private int x;
    private int y;

    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Return a string of the form row,column
     * @return A string representation of the location.
     */
    public String toString()
    {
        return x + "," + y;
    }
   
    
    /**
     * @return The row.
     */
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    
    public void setX(int newX) {
        x = newX;
    }
    
    public void setY(int newY) {
        y = newY;
    }
    
}
