
package diffusjon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author vidar
 * @version 2015.03.04
 */
public class Simulator {
    
    private ArrayList<Particle> particles;
    private SimulatorView view;
    private ParticleSystem system;
    private int step;
    public static enum SimState {
        READY, RUNNING, PAUSE, 
    }
    
    private SimState simState;
    
    public Simulator() {
        particles = new ArrayList<Particle>();
        view = new SimulatorView();
        view.setSize(900, 440);
        system = new ParticleSystem(100, 100, 100);
        reset();
        layoutMenu();
    }
    
    private void layoutMenu() {
        //menu.setLayout(null);
        JButton startButton = new JButton("START");
        JButton pauseButton = new JButton("PAUSE");
        JButton resumeButton = new JButton("RESUME");
        JButton resetButton = new JButton("RESET");
        JButton exitButton = new JButton("EXIT");

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("You clicked START");
                if (simState != SimState.PAUSE) {
                    simState = SimState.RUNNING;
                } else {
                    int result = JOptionPane.showConfirmDialog(null, "A simulation is already running", "CRITICAL ERROR", JOptionPane.CLOSED_OPTION);
                }
            }
        });
        pauseButton.addActionListener((ActionEvent e) -> {
            System.out.println("You clicked PAUSE");
            if (simState != SimState.READY) {
                view.removeButton(pauseButton);
                view.addToMenu(resumeButton);
                simState = SimState.PAUSE;
            }
        });
        resumeButton.addActionListener((ActionEvent e) -> {
            System.out.println("You clicked RESUME");
            view.removeButton(resumeButton);
            view.addToMenu(pauseButton);
            simState = SimState.RUNNING;
        });
        resetButton.addActionListener((ActionEvent e) -> {
            System.out.println("You clicked RESET");
            if (simState != SimState.RUNNING) {
                view.removeButton(resumeButton);
                view.addToMenu(pauseButton);
                reset();
                simState = simState.READY;
            } else {
                int result = JOptionPane.showConfirmDialog(null, "Pause the simulation first", "CRITICAL ERROR", JOptionPane.CLOSED_OPTION);
            }
        });
        exitButton.addActionListener((ActionEvent e) -> {
            System.out.println("You clicked EXIT");
            exit();
        });
        startButton.setBounds(10, 10, 90, 30);
        pauseButton.setBounds(10, 45, 90, 30);
        resumeButton.setBounds(10, 45, 90, 30);
        resetButton.setBounds(10, 80, 90, 30);
        exitButton.setBounds(10, 115, 90, 30);
        view.addToMenu(startButton);
        view.addToMenu(pauseButton);
        view.addToMenu(resetButton);
        view.addToMenu(exitButton);
        view.repaint();
    }
    
    public void reset() {
        step = 0;
        particles.clear();
        populate(50, 50, 50);
        view.prepareSystem(step, system);
        view.showStatus(step, system);
    }
    
    private void exit() {
        System.exit(0);
    }
    
    private void populate(int x, int y, int z) {
        system.clear();
        Location location = new Location(x, y, z);
        Particle particle = new Particle(system, location);
        particles.add(particle);
    }

}
