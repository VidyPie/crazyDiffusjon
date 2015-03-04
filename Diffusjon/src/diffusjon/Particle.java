
package diffusjon;

/**
 *
 * @author vidar
 * @version 2015.03.04
 */
public class Particle {
    private Location location;
    private ParticleSystem system;
    
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
}
