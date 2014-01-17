package nl.bzn.winkelVDToekomst.counter.ui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;

import nl.bzn.winkelVDToekomst.counter.config.Config;
import nl.bzn.winkelVDToekomst.counter.network.Network;

/**
 * Main class
 * @author Jorian Plat <jorianplat@hotmail.com>
 * @version 1.0
 */
public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField textFieldPaymentCode;
	private JButton btnGetReceipt;

	public Main() {
		
		setUpFrame();
		
		if (!Network.connect()) {
			JOptionPane.showMessageDialog(this, "Connecting to database failed. Try again later.");
			btnGetReceipt.setEnabled(false);
		}
		
	}

	/**
	 * If the payment code is valid and the payment exists, get the receipt and open the payment window
	 */
	private void getReceipt() {
		int paymentId;
		
		try {
			paymentId = Integer.parseInt(textFieldPaymentCode.getText());
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "Invalid number!");
			return;
		}
		
		if (Network.paymentExists(paymentId)) {
			new Payment(paymentId);
			
			this.setVisible(false);
			this.dispose();
		}else {
			JOptionPane.showMessageDialog(this, "Payment does not exist!");
		}
	}
	
	/**
	 * Set up the frame.
	 */
	private void setUpFrame() {
		this.setTitle(Config.MAIN_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(Config.MAIN_WIDTH, Config.MAIN_HEIGHT);
		getContentPane().setLayout(null);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		textFieldPaymentCode = new JTextField();
		textFieldPaymentCode.setToolTipText("Payment Code");
		textFieldPaymentCode.setBounds(27, 152, 194, 34);
		textFieldPaymentCode.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(textFieldPaymentCode);
		textFieldPaymentCode.setColumns(2);
		textFieldPaymentCode.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent keyEvent) {
			}
			
			@Override
			public void keyReleased(KeyEvent keyEvent) {
			}
			
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					getReceipt();
				}
			}
		});
		
		btnGetReceipt = new JButton("Get Receipt");
		btnGetReceipt.setBounds(231, 152, 105, 34);
		getContentPane().add(btnGetReceipt);
		btnGetReceipt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getReceipt();
			}
		});
		
		JLabel lblEnterThePaymentcode = new JLabel("Enter the paymentcode below to calculate the price.");
		lblEnterThePaymentcode.setBounds(27, 105, 313, 14);
		getContentPane().add(lblEnterThePaymentcode);

		this.setVisible(true);
	}
	
}
