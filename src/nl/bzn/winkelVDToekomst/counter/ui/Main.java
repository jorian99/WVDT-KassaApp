package nl.bzn.winkelVDToekomst.counter.ui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;

import nl.bzn.winkelVDToekomst.counter.network.Network;

public class Main extends JFrame implements ActionListener {
	
	private String mainTitle = "KassaApp";
	private int mainWidth = 200, 
			    mainHeight = 200;
	
	private JTextField textFieldPaymentCode;
	private JButton btnGetPrice;

	public Main() {
		
		this.setTitle(mainTitle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(362, 361);
		getContentPane().setLayout(null);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		textFieldPaymentCode = new JTextField();
		textFieldPaymentCode.setToolTipText("Payment Code");
		textFieldPaymentCode.setBounds(27, 152, 194, 34);
		textFieldPaymentCode.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(textFieldPaymentCode);
		textFieldPaymentCode.setColumns(2);
		
		btnGetPrice = new JButton("Get Price");
		btnGetPrice.setBounds(231, 152, 105, 34);
		getContentPane().add(btnGetPrice);
		btnGetPrice.addActionListener(this);
		
		JLabel lblEnterThePaymentcode = new JLabel("Enter the paymentcode below to calculate the price.");
		lblEnterThePaymentcode.setBounds(27, 105, 313, 14);
		getContentPane().add(lblEnterThePaymentcode);

		
		
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource() == btnGetPrice) {

			float price = 0;
			try {
				price = Network.getPriceByPaymentId(Integer.parseInt(textFieldPaymentCode.getText()));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		
			JOptionPane.showMessageDialog(this, "Prijs product: " + price);
		}
	}
}
