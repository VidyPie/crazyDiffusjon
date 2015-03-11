package diffusjon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author vidar
 * @version 2015.03.04
 */
public class Simulator {

    private ArrayList<Particle> particles;
    private SimulatorView view;
    private ParticleSystem system;
    private int step = 1;
    private int nValue;
    private int l;
    private int m = 0;
    private int partCount;
    private long lastStep;
    private long speed = 20;

    public static enum SimState {

        READY, RUNNING, PAUSE, WAITING
    }

    private SimState simState;

    public Simulator() {
        particles = new ArrayList<Particle>();
        view = new SimulatorView();
        view.setSize(900, 640);
        system = new ParticleSystem(128, 128);
        simState = SimState.WAITING;
        reset();
        layoutMenu();
        simLoop();
    }

    private void simLoop() {
        while (true) {
            //System.out.println(simState);
            switch (simState) {
                case RUNNING:
                    if (System.currentTimeMillis() >= lastStep + speed && step <= nValue && m == 0) {
                        simulateOneStep();
                        //step++;
                        lastStep = System.currentTimeMillis();
                    }
                    if(step >= nValue) {
                        simState = SimState.WAITING;
                    }
                    break;
                case READY:

                    break;

                case PAUSE:

                    break;
            }
        }
    }

    private void start() {
        try {
            JTextField difField = new JTextField(4);
            JTextField parField = new JTextField(4);
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Steg:"));
            myPanel.add(difField);
            myPanel.add(Box.createHorizontalStrut(30)); // a spacer
            myPanel.add(new JLabel("Partikler:"));
            myPanel.add(parField);
            int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter nValue", JOptionPane.OK_CANCEL_OPTION);
            nValue = Integer.parseInt(difField.getText());
            partCount = Integer.parseInt(parField.getText());
            reset();
            populate();
        } catch (NumberFormatException e) {
            int result = JOptionPane.showConfirmDialog(null, "Make sure all values are numbers", "CRITICAL ERROR", JOptionPane.OK_CANCEL_OPTION);
            //customStart();
        }
        
    }

    private void simulateOneStep() {
        //System.out.println(step);
        List<Particle> newParticles = new ArrayList<Particle>();
        for (Iterator<Particle> it = particles.iterator(); it.hasNext();) {
            Particle particle = it.next();
            l = particle.act(newParticles);
            if (l == 1) {
                m = 1;
                simState = SimState.WAITING;
            }

        }
        particles.addAll(newParticles);
        view.prepareSystem(step, system);
        view.showStatus(step, system);
        step++;
    }

    private void stop() {

    }

    private void layoutMenu() {
        //menu.setLayout(null);
        JButton startButton = new JButton("START");
        JButton pauseButton = new JButton("PAUSE");
        JButton resumeButton = new JButton("RESUME");
        JButton resetButton = new JButton("RESET");
        JButton exitButton = new JButton("EXIT");
        JButton agentButton = new JButton("AGENT");
        JButton cellButton = new JButton("CELLULAR");
        JButton singleButton = new JButton("SINGLE");

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("You clicked START");
                if (simState == SimState.READY) {
                    //start();
                    simState = SimState.RUNNING;
                } else {

                    int result = JOptionPane.showConfirmDialog(null, "Select mode first", "NOMODE", JOptionPane.CLOSED_OPTION);
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
        agentButton.addActionListener((ActionEvent e) -> {
            if (simState != SimState.RUNNING) {
                start();
                simState = SimState.READY;
            }
        });
        cellButton.addActionListener((ActionEvent e) -> {

        });
        singleButton.addActionListener((ActionEvent e) -> {

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
        agentButton.setBounds(10, 165, 90, 30);
        cellButton.setBounds(10, 200, 90, 30);
        singleButton.setBounds(10, 235, 90, 30);
        view.addToMenu(startButton);
        view.addToMenu(pauseButton);
        view.addToMenu(resetButton);
        view.addToMenu(exitButton);
        view.addToMenu(agentButton);
        view.addToMenu(cellButton);
        view.addToMenu(singleButton);
        view.repaint();
    }

    public void reset() {
        step = 1;
        m = 0;
        particles.clear();
    }

    private void exit() {
        System.exit(0);
    }

    private void populate() {
        system.clear();
        int t = 0;
        int n = 0;
        Random r = new Random();
        while (t < partCount) {
            int ax = r.nextInt(59);
            int ay = r.nextInt(59);
            Location location = new Location(64, 64);
            Particle particle = new Particle(system, location);
            particles.add(particle);
            t++;
            n++;
            if (n == 4) {
                n = 0;
            }
        }
        view.prepareSystem(step, system);
        view.showStatus(step, system);
    }

}
