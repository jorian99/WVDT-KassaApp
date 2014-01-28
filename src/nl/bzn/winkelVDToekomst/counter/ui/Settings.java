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

public class Settings extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
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
		
		textField = new JTextField();
		textField.setBounds(150, 8, 139, 20);
		textField.setText(Config.DATABASE_IP_ADDRESS);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText(Config.DATABASE_NAME);
		textField_1.setColumns(10);
		textField_1.setBounds(150, 46, 139, 20);
		getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setText(Config.DATABASE_USERNAME);
		textField_2.setColumns(10);
		textField_2.setBounds(150, 71, 139, 20);
		getContentPane().add(textField_2);
		
		textField_3 = new JPasswordField();
		textField_3.setText(Config.DATABASE_PASSWORD);
		textField_3.setColumns(10);
		textField_3.setBounds(150, 96, 139, 20);
		getContentPane().add(textField_3);
		
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
	protected void saveSettings() {
		Config.DATABASE_IP_ADDRESS = textField.getText();
		Config.DATABASE_NAME = textField_1.getText();
		Config.DATABASE_USERNAME = textField_2.getText();
		Config.DATABASE_PASSWORD = textField_3.getText();
		
		main.connectToDatabase();
		this.setVisible(false);
		this.dispose();
	}
}
