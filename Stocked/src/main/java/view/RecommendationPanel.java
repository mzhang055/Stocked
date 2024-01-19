package view;

import javax.swing.*;

import org.jfree.chart.ChartPanel;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

import controller.ChartController;

public class RecommendationPanel extends JPanel {

  
    public RecommendationPanel() {
        setLayout(new BorderLayout());

        // Get the ChartPanel from ChartController
        ChartPanel chartPanel = ChartController.getChartPanel();

        // Check if the chartPanel is not null before adding it
        if (chartPanel != null) {
            add(chartPanel, BorderLayout.CENTER);
        }

        System.out.println("got the rec panel");
    }
}
