package Lab1.a;

import javax.swing.*;
class CustomThread extends Thread {
    private final JSlider slider;
    private final int value;

    public CustomThread(JSlider slider, int value) {
        this.slider = slider;
        this.value = value;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            synchronized (slider) {
                slider.setValue(value);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public JSpinner GetSpinner(){
        SpinnerModel sModel = new SpinnerNumberModel(getPriority(), Thread.MIN_PRIORITY, Thread.MAX_PRIORITY, 1);
        JSpinner spinner = new JSpinner(sModel);
        spinner.addChangeListener(e -> {
            setPriority((int)(spinner.getValue()));
        });
        return spinner;
    }

}

public class TaskALab1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        JSlider slide = new JSlider(0,100);
        slide.setMajorTickSpacing(10);
        slide.setPaintTicks(true);
        slide.setPaintLabels(true);

        CustomThread Thread1 = new CustomThread(slide, 10);
        CustomThread Thread2 = new CustomThread(slide, 90);

        JButton startBTTN = new JButton("Start!");
        startBTTN.addActionListener(e -> {
            startBTTN.setEnabled(false);
            Thread1.start();
            Thread2.start();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(slide);
        panel.add(GetJPanel(Thread1, Thread2));

        JPanel jPanel = new JPanel();
        jPanel.add(startBTTN);
        panel.add(jPanel);

        frame.setContentPane(panel);
        frame.setVisible(true);

    }

    public static JPanel GetJPanel(CustomThread Thread1, CustomThread Thread2) {
        JPanel panel = new JPanel();

        panel.add(Thread1.GetSpinner());
        panel.add(Thread2.GetSpinner());
        return panel;
    }
}

