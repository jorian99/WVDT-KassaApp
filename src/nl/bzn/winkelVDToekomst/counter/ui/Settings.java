package nl.bzn.winkelVDToekomst.counter.ui;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JPanel;

import nl.bzn.winkelVDToekomst.counter.config.Config;

/**
 * Settings frame class
 * @author Jorian Plat <jorianplat@hotmail.com>
 * @version 1.0
 */
public class Settings extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField textFieldIP;
	private JTextField textFieldName;
	private JTextField textFieldUserName;
	private JTextField textFieldPassword;
	
	private Main main;
	
	public Settings(Main main) {
		this.main = main;
		this.setTitle("Settings");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(310, 197);
		getContentPane().setLayout(null);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
		
		JLabel lblIpaddress = new JLabel("IP-Address");
		lblIpaddress.setBounds(10, 11, 130, 14);
		getContentPane().add(lblIpaddress);
		
		JLabel lblNewLabel = new JLabel("Database name");
		lblNewLabel.setBounds(10, 49, 130, 14);
		getContentPane().add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 279, 2);
		getContentPane().add(separator);
		
		JLabel lblDatabaseUsername = new JLabel("Database username");
		lblDatabaseUsername.setBounds(10, 74, 130, 14);
		getContentPane().add(lblDatabaseUsername);
		
		JLabel lblDatabasePassword = new JLabel("Database password");
		lblDatabasePassword.setBounds(10, 99, 130, 14);
		getContentPane().add(lblDatabasePassword);
		
		textFieldIP = new JTextField();
		textFieldIP.setBounds(150, 8, 139, 20);
		textFieldIP.setText(Config.DATABASE_IP_ADDRESS);
		getContentPane().add(textFieldIP);
		textFieldIP.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setText(Config.DATABASE_NAME);
		textFieldName.setColumns(10);
		textFieldName.setBounds(150, 46, 139, 20);
		getContentPane().add(textFieldName);
		
		textFieldUserName = new JTextField();
		textFieldUserName.setText(Config.DATABASE_USERNAME);
		textFieldUserName.setColumns(10);
		textFieldUserName.setBounds(150, 71, 139, 20);
		getContentPane().add(textFieldUserName);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setText(Config.DATABASE_PASSWORD);
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(150, 96, 139, 20);
		getContentPane().add(textFieldPassword);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 124, 279, 2);
		getContentPane().add(separator_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 137, 279, 29);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBounds(97, 0, 89, 23);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveSettings();
			}
		});
		panel.add(btnNewButton);
		
		this.setVisible(true);
	}
	
	
	/**
	 * Save the settings to the config class, and close this frame. After closing try to make a connection.
	 */
	protected void saveSettings() {
		Config.DATABASE_IP_ADDRESS = textFieldIP.getText();
		Config.DATABASE_NAME = textFieldName.getText();
		Config.DATABASE_USERNAME = textFieldUserName.getText();
		Config.DATABASE_PASSWORD = textFieldPassword.getText();
		
		main.connectToDatabase();
		this.setVisible(false);
		this.dispose();
	}
}
