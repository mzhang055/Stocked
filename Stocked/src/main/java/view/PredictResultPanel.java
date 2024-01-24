package view;

import javax.swing.*;

import org.jfree.chart.ChartPanel;

import java.awt.*;

import controller.PredictionController;

public class PredictResultPanel extends JPanel {

	//constructor and frame set up
    public PredictResultPanel(ChartPanel chartPanel) {
    	
    	System.out.println("in here");
        setLayout(new BorderLayout());

        // Get the ChartPanel from ChartController
        chartPanel = PredictionController.getChartPanelPredict();

        // check if the chartPanel is not null before adding it
        if (chartPanel != null) {
            add(chartPanel, BorderLayout.CENTER);
        }

      
    }
}