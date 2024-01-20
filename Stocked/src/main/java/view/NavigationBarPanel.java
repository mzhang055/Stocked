/*
 * this class is responsible for creating a nav bar panel (menu panel) that is used 
 * for all frames in this program.
 */

package view;

import javax.swing.*;

import controller.LoginController;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationBarPanel extends JPanel implements ActionListener {

	// create buttons
	private JButton homeBtn, investBtn, predictionBtn, signOutBtn;

	// instance of classes
	private static HomeFrame home;


	// constructor sets up the panel with all components
	public NavigationBarPanel() {
		
		// set up panel
		setOpaque(false);
		setPreferredSize(new Dimension(1440, 115));

		// create buttons
		homeBtn = createButton(null, "images/logo.png");
		investBtn = createButton("Investment Tool", null);
		predictionBtn = createButton("Predictions", null);

		signOutBtn = createButton("Sign Out", null);

		// set font size for text buttons
		Font buttonFont = new Font("Arial", Font.BOLD, 18);
		Color fontColor = Color.BLACK;

		// add to buttons
		investBtn.setFont(buttonFont);
		investBtn.setForeground(fontColor);
		predictionBtn.setFont(buttonFont);
		predictionBtn.setForeground(fontColor);
		signOutBtn.setFont(buttonFont);
		signOutBtn.setForeground(fontColor);


		// set layout manager and space out buttons
		setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));

		// add buttons to panel
		add(homeBtn);
		add(investBtn);
		add(predictionBtn);
	
		add(signOutBtn);


		// add action listeners
		homeBtn.addActionListener(this);
		investBtn.addActionListener(this);
		predictionBtn.addActionListener(this);

		signOutBtn.addActionListener(this);


	}

	// this method creates and styles the buttons with either a text or image
	// and returns the styled button
	private JButton createButton(String text, String image) {
		JButton button;
		if (image != null) {
			ImageIcon icon = new ImageIcon(image);
			button = new JButton(text, icon);
		} else {
			button = new JButton(text);
		}

		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setOpaque(false);

		return button;
	}

	

	// this method handles all user actions in the frame
	@Override
	public void actionPerformed(ActionEvent e) {

		// open home frame
		if (e.getSource() == homeBtn) {



			// open recoemmdned investments
		} else if (e.getSource() == investBtn) {
			

		}

		// open stock predictions 
		else if (e.getSource() == predictionBtn) {

			
		}

		// handle signout. this closes the application
		else if (e.getSource() == signOutBtn) {
			new HomeFrame();
			System.exit(0);


		}


	}

}