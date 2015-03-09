package diffusjon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import static java.awt.PageAttributes.ColorType.COLOR;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author VidyPie
 * @version 2015.03.04
 */
public class SimulatorView extends JFrame{
    
    private systemView loco;
    private sideMenu menu;
    private JSplitPane splitPane;
    
    public SimulatorView() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        setTitle("Diffusjon");
        menu = new sideMenu();
        menu.setLayout(null);
        setLocation(400, 200);
        loco = new systemView(700, 1000);
        Container contents = getContentPane();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menu, loco);
        splitPane.setDividerLocation(110);
        splitPane.setLeftComponent(menu);
        splitPane.setRightComponent(loco);
        contents.add(splitPane, BorderLayout.CENTER);
        setVisible(true);
    }
    
    public void prepareSystem(int step, ParticleSystem system) {
        if (!isVisible()) {
            setVisible(true);
        }
        loco.preparePaint();
    }
    
    public void showStatus(int step, ParticleSystem system) {
         for (int aix = 0; aix < system.getX(); aix++) {
            for (int aiy = 0; aiy < system.getY(); aiy++) {
                for (int aiz = 0; aiz < system.getZ(); aiz++) {
                    loco.drawEmpty(aix, aiy, aiz);
                }
            }
         }
        
        for (int ax = 0; ax < system.getX(); ax++) {
            for (int ay = 0; ay < system.getY(); ay++) {
                for (int az = 0; az < system.getZ(); az++) {
                    Particle particle = system.getObjectAt(ax, ay, az);
                    if (particle == null) {
                        //loco.drawEmpty(ax, ay, az);   
                    }
                    else {
                        int n = particle.getColorIdentifier();
                        loco.drawMark(ax, ay, az, n);
                    }
                }
            }
        }
        repaint();
    }
    
    public void addToMenu(JButton button) {
        menu.addButton(button);
    }
    
    public void removeButton(JButton button) {
        menu.remove(button);
        menu.repaint();
    }
    
    public class sideMenu extends JPanel {
        
        public sideMenu() {
            
        }
        
        public void addButton(JButton button) {
            menu.add(button);
        }
    }
    
    public class systemView extends JPanel {
        
        private Image underLine, yLine, zLine, systemAbove, systemFront;
        private Graphics g;
        private int xScale = 5;
        private int yScale = 5;
        private int zScale = 5;
        
        public systemView(int height, int width) {
            loadContent();
        }
        
        private void loadContent() {
            try {
                underLine = ImageIO.read(new File("graphics/line.png"));
                yLine = ImageIO.read(new File("graphics/yline.png"));
                zLine = ImageIO.read(new File("graphics/zline.png"));
            } catch (IOException ex) {
                
            }
        }
        
        public void preparePaint() {
            systemFront = loco.createImage(750, 300);
            systemAbove = loco.createImage(300, 300);
            g = systemFront.getGraphics();
        }
        
            public void drawMark(int x, int y, int z, int n) {
            Color colorA = null;
            Color colorB = null;
            if(n == 0) {    
                colorA = new Color(255 - (3*z), 0, 0);
                colorB = new Color(255 - (3*y), 0, 0);
            }
            else if (n == 1) {
                colorA = new Color(0, 255 - (3*z), 0);
                colorB = new Color(0, 255 - (3*y), 0);
            }
            else if (n == 2) {
                colorA = new Color(255 - (3*z), 0, 255 - (3*z));
                colorB = new Color(255 - (3*y), 0, 255 - (3*y));
            }
            else if (n == 3) {
                colorA = new Color(0, 255 - (3*z), 255 - (3*z));
                colorB = new Color(0, 255 - (3*y), 255 - (3*y));
            }
            g.setColor(colorA);
            g.fillRect(50 + (x * xScale), y * yScale, xScale - 0, yScale - 0);
            g.setColor(colorB);
            g.fillRect(400 + (x * xScale), z * zScale, xScale - 0, zScale - 0);
            //g.fillRect(400 + (x * xScale), y * yScale, xScale - 0, yScale - 0);
        }
            
            public void drawEmpty(int x, int y, int z) {
                Color color = new Color(0, 0, 51);    
                g.setColor(color);
                g.fillRect(50 + (x * xScale), y * yScale, xScale - 0, yScale - 0);
                g.fillRect(400 + (x * xScale), z * zScale, xScale - 0, zScale - 0);       
            }
            
         public void paintComponent(Graphics g) {
            if (systemFront != null && systemAbove != null) {
                    g.drawImage(systemFront, 0, 50, null);
                    g.drawImage(underLine, 50, 355, null);
                    g.drawImage(underLine, 400, 355, null);
                    g.drawImage(yLine, 25, 50, null);
                    g.drawImage(zLine, 375, 50, null);
                    
            }
        }
    }
}
