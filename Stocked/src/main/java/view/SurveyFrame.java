/*
 * 
 */

package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class SurveyFrame extends JFrame implements ActionListener{

	private JButton fwdBtn;
	private JButton backBtn;
	private JPanel optionsPanel;
	private JLabel questionLabel;

	public SurveyFrame() {
		super("Survey Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);

		// set up the background image
		ImageIcon backgroundImg = new ImageIcon("images/registerBg.png");
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight()));

		JLabel imageLabel = new JLabel(backgroundImg);
		imageLabel.setBounds(0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		Dimension imageSize = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		imageLabel.setPreferredSize(imageSize);

		// text label/title
		questionLabel = new JLabel("Grow Your Money");
		questionLabel.setBounds(490, 250, 470, 60);
		// set the font
		Font fontTitle = new Font("Arial", Font.BOLD, 50);
		questionLabel.setFont(fontTitle);

		// adding components to the layered pane
		layeredPane.add(imageLabel, Integer.valueOf(0));
		layeredPane.add(questionLabel, Integer.valueOf(1));

		// add the layeredPane to the content pane
		setContentPane(layeredPane);

		setVisible(true);

	}

	//handles user actions
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		RegisterFrame regis = new RegisterFrame();
		regis.setVisible(true);
	}

}
