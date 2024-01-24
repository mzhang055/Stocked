package view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainFrame extends JFrame {

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 900);

        // Create an instance of PredictFrame (which is a panel)
        PredictFrame predictPanel = new PredictFrame();

        // Create a main panel to hold other panels/components
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Add the predictPanel to the main panel
        mainPanel.add(predictPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
