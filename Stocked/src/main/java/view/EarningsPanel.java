package view;

import javax.swing.*;

import controller.ChartController;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EarningsPanel extends JPanel {

    private JLabel infoLabel;
    private ChartController chart;

    public EarningsPanel(ChartController chartController) {
        setLayout(new BorderLayout());

        // Assign the provided ChartController to the local variable
        this.chart = chartController;

        ImageIcon bg = new ImageIcon("images/widget.png");
        // Create a label to hold the background image
        JLabel backgroundLabel = new JLabel(bg);
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel, BorderLayout.CENTER);

        // Create and add an info label
        infoLabel = new JLabel("Click on a point");
        infoLabel.setForeground(Color.BLACK); // Set text color
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(infoLabel, BorderLayout.NORTH);

        // Add mouse listener to handle clicks
        backgroundLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });

        System.out.println("got the earnings panel");
    }


    private void handleMouseClick(MouseEvent event) {
        // Example: Display the clicked point's coordinates
        // Assuming getxValue() and getyValue() are methods in your ChartController
        //infoLabel.setText(CustomChartMouseListener.MouseClicked());
    }
}
