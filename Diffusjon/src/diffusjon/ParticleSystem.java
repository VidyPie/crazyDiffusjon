package diffusjon;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represent a coordinate system
 * 
 * @author VidyPie
 * @version 2015.03.04
 */
public class ParticleSystem
{
    private int xsize, ysize, zsize;
    private Object[][][] system;

    public ParticleSystem(int x, int y, int z)
    {
        this.xsize = x;
        this.ysize = y;
        this.zsize = z;
        HashMap<Location, Particle> p;
        system = new Object[x][y][z];
    }
    
    public void clear()
    {
        for(int ax = 0; ax < xsize; ax++) {
            for(int ay = 0; ay < ysize; ay++) {
                for(int az = 0; az < zsize; az++) {
                    system[ax][ay][az] = null;
                }
            }
        }
    }

    public void clear(Location location)
    {
        system[location.getX()][location.getY()][location.getZ()] = null;
    }
    
    public void place(Object particle, int x, int y, int z)
    {
        place(particle, new Location(x, y, z));
    }
 
    public void place(Object particle, Location location) {
            system[location.getX()][location.getY()][location.getZ()] = particle;
    }

    public Object getObjectAt(Location location)
    {
        return getObjectAt(location.getX(), location.getY(), location.getZ());
    }

    public Object getObjectAt(int x, int y, int z)
    {
        return system[x][y][z];
    }

    public Location randomAdjacentLocation(Location location)
    {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }
    
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }

    public Location freeAdjacentLocation(Location location)
    {
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int x = location.getX();
            int y = location.getY();
            int z = location.getZ();
            for(int xoffset = -1; xoffset <= 1; xoffset++) {
                int nextX = x + xoffset;
                if(nextX >= 0 && nextX < xsize) {
                    for(int yoffset = -1; yoffset <= 1; yoffset++) {
                        int nextY = y + yoffset;
                        if(nextY >= 0 && nextY < ysize) {
                            for(int zoffset = -1; zoffset < zsize; zoffset++) {
                                int nextZ = z + zoffset;
                                if(nextZ >= 0 && nextZ < zsize) {
                                    locations.add(new Location(nextX, nextY, nextZ));
                                }
                            }
                        }
                    }
                }
            }
        }
        return locations;
    }

    public int getX()
    {
        return xsize;
    }
    
    public int getY()
    {
        return ysize;
    }
    
    public int getZ()
    {
        return zsize;
    }
}
