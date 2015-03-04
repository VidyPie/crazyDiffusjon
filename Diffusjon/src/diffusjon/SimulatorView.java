package diffusjon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
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
                    Object particle = system.getObjectAt(ax, ay, az);
                    if (particle != null) {
                        System.out.println("found part");
                        loco.drawMark(ax, ay, az); 
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
        
        private Image systemFront;
        private Image systemAbove;
        private Graphics g;
        private int xScale = 3;
        private int yScale = 3;
        private int zScale = 3;
        
        public systemView(int height, int width) {
            
        }
        
        public void preparePaint() {
            systemFront = loco.createImage(750, 300);
            systemAbove = loco.createImage(300, 300);
            g = systemFront.getGraphics();
        }
        
            public void drawMark(int x, int y, int z) {
            Color color = new Color(25, 25, 25);    
            g.setColor(color);
            g.fillRect(50 + (x * xScale), y * yScale, xScale - 1, yScale - 1);
            g.fillRect(400 + (x * xScale), z * zScale, xScale - 1, zScale - 1);
        }
            
            public void drawEmpty(int x, int y, int z) {
                Color color = new Color(0, 255, 0);    
                g.setColor(color);
                g.fillRect(50 + (x * xScale), y * yScale, xScale - 1, yScale - 1);
                g.fillRect(400 + (x * xScale), z * zScale, xScale - 1, zScale - 1);       
            }
            
         public void paintComponent(Graphics g) {
            if (systemFront != null && systemAbove != null) {
                    g.drawImage(systemFront, 0, 50, null);
            }
        }
    }
}
