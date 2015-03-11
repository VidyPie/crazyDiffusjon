
package diffusjon;

import java.util.List;
import java.util.Random;

/**
 *
 * @author vidar
 * @version 2015.03.04
 */
public class Particle {
    private Location location;
    private ParticleSystem system;
    private int oldX, oldY, oldZ;
    int getOldX;
    int getOldY;
    int getOldZ;
    public Particle(ParticleSystem system, Location location) {
        this.system = system;
        setLocation(location);           
    }
    
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            system.clear(location);
        }   
         system.place(this, newLocation);
         location = newLocation;
    }
    
    public void act(List<Particle> newParticles) {
        Location newLocation = system.randomAdjacentLocation(location);
        if (newLocation == null) {
            System.out.println("ohno");
        }
        oldX = location.getX();
        oldY = location.getY();
        
        setLocation(newLocation);
    }
    
    public void act2(List<Particle> newParticles) { 
        int x = location.getX();
        int y = location.getY();
        Random r = new Random();
        int i = r.nextInt(4);
        if(i == 0 || i == 2) {
            x = x - 1;
        } else {
            x = x + 1;
        }
        if(x > 239) {
            x = 239;
        }
        if (x < 0) {
            x = 0;
        }
        int u = r.nextInt(2);
        if(u == 0){
            y = y + 1;
        }
        if (u == 1) {
            y = y - 1;
        }
        if (y <= 0) {
            y = 0;
        }
        if(y >= 239) {
            y = 239;
        }
        Location newLocation = new Location(x, y);
        setLocation(newLocation);
    
    }
    
    public int getOldX() {
       System.out.println("oldX: " + oldX);
        return oldX;
    }
    
    public int getOldY() {
        return oldY;
    }
    
    public int getOldZ() {
        return oldZ;
    }
   
}
