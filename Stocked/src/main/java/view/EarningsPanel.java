package view;

import javax.swing.*;
import controller.ChartController;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EarningsPanel extends JPanel {

    private JLabel infoLabel;
    private ChartController chart;

    public EarningsPanel(ChartController chart) {
        this.chart = chart; // Store the reference to ChartController

        setLayout(new BorderLayout());

        ImageIcon bg = new ImageIcon("images/widget.png");
        // Create a label to hold the background image
        JLabel backgroundLabel = new JLabel(bg);
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel, BorderLayout.CENTER);

//        
//        chart = new ChartController();
//        // Create and add an info label
//        infoLabel = new JLabel(chart.getChartInfo());
//        System.out.println("printed: "+ chart.getChartInfo());
//        infoLabel.setForeground(Color.BLACK); // Set text color
//        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        add(infoLabel, BorderLayout.NORTH);

//        // Add mouse listener to handle clicks
//        backgroundLabel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                handleMouseClick(e);
//            }
//        });

        System.out.println("got the earnings panel");
    }

    public void displayXandY(String xValue, double number) {
        // Example: Display the clicked point's coordinates
        // Assuming getxValue() and getyValue() are methods in your ChartController
        System.out.println(xValue + " and " + number);
       
        
        
        infoLabel = new JLabel(xValue + " and " + number);
        infoLabel.setForeground(Color.BLACK); // Set text color
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(infoLabel, BorderLayout.NORTH);
        
    }

}
