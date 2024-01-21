/*
 * i used an interface here to separate concerns of adding chart panels from 
 * actual components that use these panels
 * - having multiple integrations of this makes it easier to integrate into differnet applications
 */


package view;

import org.jfree.chart.ChartPanel;

public interface ChartPanelAdder {
    void addChartPanel(String stockSymbol, ChartPanel chartPanel);
}
