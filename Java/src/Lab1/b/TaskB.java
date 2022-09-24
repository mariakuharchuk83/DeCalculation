package Lab1.b;

import javax.swing.*;

import javax.swing.*;

class CustomThread extends Thread {
    private final JSlider slider;
    private final int value;
    private boolean running;

    public CustomThread(JSlider slider, int value) {
        this.slider = slider;
        this.value = value;
        running = false;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            synchronized (slider) {
                slider.setValue(value);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        TaskB.SEMAPHORE = 1;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}

public class TaskB {
    public static int SEMAPHORE = 1;
    private static CustomThread Thread1;
    private static CustomThread Thread2;

    public static JLabel label;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JSlider slider = new JSlider(0, 100);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        panel.add(slider);

        label = new JLabel("Вільно");


        JPanel Thread1Panel = new JPanel();
        JButton Thread1start = new JButton("ПУСК1");
        JButton Thread1stop = new JButton("СТОП1");
        Thread1stop.setEnabled(false);
        Thread1start.addActionListener(e -> {
            if (SEMAPHORE == 0) {
                label.setText("Зайнято");
            } else {
                SEMAPHORE = 0;
                Thread1stop.setEnabled(true);
                Thread1start.setEnabled(false);
                Thread1 = new CustomThread(slider, 10);
                Thread1.setPriority(Thread.MIN_PRIORITY);
                Thread1.start();
            }
        });
        Thread1stop.addActionListener(e -> {
            Thread1.setRunning(false);
            Thread1stop.setEnabled(false);
            Thread1start.setEnabled(true);
        });
        Thread1Panel.add(Thread1start);
        Thread1Panel.add(Thread1stop);

        JPanel Thread2Panel = new JPanel();
        JButton Thread2start = new JButton("ПУСК2");
        JButton Thread2stop = new JButton("СТОП2");
        Thread2stop.setEnabled(false);
        Thread2start.addActionListener(e -> {
            if (SEMAPHORE == 0) {
                label.setText("Зайнято");
            } else {
                SEMAPHORE = 0;
                Thread2 = new CustomThread(slider, 90);
                Thread2.setPriority(Thread.MAX_PRIORITY);
                Thread2stop.setEnabled(true);
                Thread2start.setEnabled(false);
                Thread2.start();
            }
        });
        Thread2stop.addActionListener(e -> {
            Thread2.setRunning(false);
            Thread2stop.setEnabled(false);
            Thread2start.setEnabled(true);
        });
        Thread2Panel.add(Thread2start);
        Thread2Panel.add(Thread2stop);

        panel.add(Thread1Panel);
        panel.add(Thread2Panel);

        panel.add(label);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
