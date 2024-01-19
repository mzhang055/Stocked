package view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import controller.ChartController;

public class RecommendationPanel extends JPanel {
	
	private String image = "images/widget.png";

    public RecommendationPanel() {
        setLayout(new BorderLayout());

        // Create panels for the sections
        JPanel largerPanel = ChartController.getChartPanel();
        JPanel smallerPanel = new JPanel();

        // Set background colors for clarity
        largerPanel.setBackground(Color.BLUE);
        smallerPanel.setBackground(Color.RED);

        // Add panels to the frame
        add(largerPanel, BorderLayout.WEST);  // Larger panel to the west
        add(smallerPanel, BorderLayout.CENTER);  // Smaller panel to the center

        // Set the preferred size for the larger panel (adjust this based on your requirements)
        largerPanel.setPreferredSize(new Dimension(1100, 0));

        
    }

    private JPanel createSectionPanel() {
        JPanel sectionPanel = new JPanel(new BorderLayout());

        //add background image for each subpanel
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel backgroundLabel = new JLabel(imageIcon);

        // Set layout and add the background label
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.add(backgroundLabel, BorderLayout.CENTER);

        return sectionPanel;
    }
    
   
}