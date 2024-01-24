package view;


import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PredictFrame extends JFrame {

	//fields
	private Map<String, Double> stockMap; //store sthe stock symbols from yahoo finance
	
	//components of frame
	private NavigationBarPanel navPanel;
	private PredictPanel predictPanel;
	private JButton stockButton;
	
	
	//constructor sets up the frame 
	public PredictFrame() {

		//set up operations
		super("Stock Predictions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);

		//fills hashmap with moupdated stock symbols from yahoo finance
		//updateStockMap();

		// set the background image and add it to a label
		ImageIcon backgroundImg = new ImageIcon("images/predictBg.png");
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight()));

		JLabel imageLabel = new JLabel(backgroundImg);
		imageLabel.setBounds(0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		Dimension imageSize = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		imageLabel.setPreferredSize(imageSize);

		// creates the navigation JPanel
		navPanel = new NavigationBarPanel();
		navPanel.setBounds(0, 0, 1440, 115);
		navPanel.setVisible(true);
		imageLabel.add(navPanel);
		
		// creates the predict JPanel
		predictPanel = new PredictPanel();
		predictPanel.setBounds(0, 115, 1440, 900);
		predictPanel.setVisible(true);
		imageLabel.add(predictPanel);

//		// set iage for all buttons
//		ImageIcon icon = new ImageIcon("images/Btn.png");
//
//		JPanel panel = new JPanel();
//		panel.setLayout(new GridLayout(0, 4, 10, 10));
//
//		for (String symbol : stockMap.keySet()) {
//			JButton stockButton = createStockButton(symbol, icon);
//			panel.add(stockButton);
//		}

		JScrollPane jsp = new JScrollPane(layeredPane);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.getVerticalScrollBar().setUnitIncrement(12);

		// add background image to the last layer
		layeredPane.add(imageLabel, Integer.valueOf(0));
		layeredPane.add(navPanel, Integer.valueOf(1));
		
		//add scroll pane to frame
		getContentPane().add(jsp);
		
		setVisible(true);
	}



	public static void main(String[] args) {
		new PredictFrame();
	}


}