package Algorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class selectPage extends JFrame {

    public selectPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 2));

        JButton selectButton1 = new JButton("Weight");
        JButton selectButton2 = new JButton("Shortest Path");
        
     // Set size for buttons using setSize
        selectButton1.setSize(new Dimension(100, 50));
        selectButton2.setSize(new Dimension(100, 50));

        panel1.add(selectButton1);
        panel1.add(selectButton2);

        selectButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TravelRecommendationApp().setVisible(true);
            }
        });

        selectButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new algorithm2().setVisible(true);
            }
        });

        add(panel1);

        // Ensure proper initialization
        initialize();
    }

    private void initialize() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        // Call the constructor instead of the selectPage method
        new selectPage();
    }
}