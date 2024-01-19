package view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class RecommendationPanel extends JPanel {
	
	private String image = "images/widget.png";

    public RecommendationPanel() {
        setLayout(new GridLayout(1, 3)); // Use GridLayout with 1 row and 3 columns
        

        // Create sub panels for each section with background image
        JPanel westPanel = createSectionPanel();
        JPanel centerPanel = createSectionPanel();
        JPanel eastPanel = createSectionPanel();

        // Add sub panel to main panel with GridLayout
        add(westPanel);
        add(centerPanel);
        add(eastPanel);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Recommendation Frame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1440, 900);
            RecommendationPanel recommendationPanel = new RecommendationPanel();
            frame.add(recommendationPanel);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
