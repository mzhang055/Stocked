package view;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Calendar;

public class Test extends JFrame {

    private TimeSeries timeSeries;

    public Test() {
        super("Chart with DatePicker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create a time series dataset
        timeSeries = new TimeSeries("Random Data");
        TimeSeriesCollection dataset = new TimeSeriesCollection(timeSeries);

        // Create the chart
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Time Series Chart",    // chart title
                "Time",                 // x-axis label
                "Value",                // y-axis label
                dataset,                // data
                false,                  // create legend?
                true,                   // generate tooltips?
                false                   // generate URLs?
        );

        // Create the chart panel and set it to be the content pane
        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);

        // Create a JXDatePicker
        JXDatePicker datePicker = new JXDatePicker();
        datePicker.setDate(Calendar.getInstance().getTime());

        // Create a panel for date picker and add it to the frame
        JPanel datePickerPanel = new JPanel(new FlowLayout());
        datePickerPanel.add(new JLabel("Select Date: "));
        datePickerPanel.add(datePicker);

        // Create a panel for chart and add it to the frame
        JPanel chartPanelContainer = new JPanel(new BorderLayout());
        chartPanelContainer.add(chartPanel, BorderLayout.CENTER);

        // Create a panel to hold both date picker and chart panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(datePickerPanel, BorderLayout.NORTH);
        mainPanel.add(chartPanelContainer, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Set visible
        setVisible(true);

        // Generate some manual data for demonstration purposes
        generateManualData();
    }

    private void generateManualData() {
        for (int i = 0; i < 100; i++) {
            timeSeries.addOrUpdate(new Second(new Date()), Math.random() * 100);
            try {
                Thread.sleep(100); // Sleep for 100 milliseconds between data points
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Test();
            }
        });
    }
}
