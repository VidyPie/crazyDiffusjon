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
    private int z;

    public Location(int x, int y, int z)
    {
        this.x = x;
        this.z = z;
        this.y = y;
    }
    
    /**
     * Return a string of the form row,column
     * @return A string representation of the location.
     */
    public String toString()
    {
        return x + "," + y + "," + z;
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
    
    public int getZ()
    {
        return z;
    }
    
    public void setX(int newX) {
        x = newX;
    }
    
    public void setY(int newY) {
        y = newY;
    }
    
    public void setZ(int newZ) {
        z = newZ;
    }
}
