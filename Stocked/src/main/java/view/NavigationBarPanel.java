/*
 *
 */

package view;

import javax.swing.*;

import controller.LoginController;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationBarPanel extends JPanel implements ActionListener {

	// create buttons
	private JButton homeBtn, investBtn, predictionBtn, portfolioBtn, signOutBtn;

	// instance of classes
	private static HomeFrame home;


	// constructor
	public NavigationBarPanel() {
		
		
		// set up panel
		setOpaque(false);
		setPreferredSize(new Dimension(1440, 115));

		// create buttons
		homeBtn = createButton(null, "images/logo.png");
		investBtn = createButton("Investment Tool", null);
		predictionBtn = createButton("Predictions", null);
		portfolioBtn = createButton("My Portfolio", null);
		signOutBtn = createButton("Sign Out", null);

		// set font size for text buttons
		Font buttonFont = new Font("Arial", Font.BOLD, 18);
		Color fontColor = Color.BLACK;

		// add to buttons
		investBtn.setFont(buttonFont);
		investBtn.setForeground(fontColor);
		predictionBtn.setFont(buttonFont);
		predictionBtn.setForeground(fontColor);
		portfolioBtn.setFont(buttonFont);
		portfolioBtn.setForeground(fontColor);
		signOutBtn.setFont(buttonFont);
		signOutBtn.setForeground(fontColor);


		// set layout manager and space out buttons
		setLayout(new FlowLayout(FlowLayout.CENTER, 90, 10));

		// add buttons to panel
		add(homeBtn);
		add(investBtn);
		add(predictionBtn);
		add(portfolioBtn);
		add(signOutBtn);


		// add action listeners
		homeBtn.addActionListener(this);
		investBtn.addActionListener(this);
		predictionBtn.addActionListener(this);
		portfolioBtn.addActionListener(this);
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



			// open browse programs
		} else if (e.getSource() == investBtn) {
			

		}

		// open interactive map
		else if (e.getSource() == predictionBtn) {

			
		}

		// open survey button
		else if (e.getSource() == portfolioBtn) {
			

		
		}

		// open browse jobs button
		else if (e.getSource() == signOutBtn) {
			new HomeFrame();
			System.exit(0);


		}


	}

}