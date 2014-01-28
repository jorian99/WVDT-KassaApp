package nl.bzn.winkelVDToekomst.counter.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import nl.bzn.winkelVDToekomst.counter.config.Config;
import nl.bzn.winkelVDToekomst.counter.network.ConnectionThread;
import nl.bzn.winkelVDToekomst.counter.network.Network;

/**
 * Main class
 * 
 * @author Jorian Plat <jorianplat@hotmail.com>
 * @version 1.0
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField textFieldPaymentCode;
	private JButton btnGetReceipt;
	private JLabel loadingLabel;
	
	ImageIcon iconLoading = createImageIcon("loading.gif");
	ImageIcon iconSuccess = createImageIcon("checked.png");
	ImageIcon iconFailed = createImageIcon("cross.png");
	private JButton btnConnect;

	public Main() {

		setUpFrame();

		connectToDatabase();

	}

	/**
	 * Connect to database.
	 */
	private void connectToDatabase() {
		loadingLabel.setText("Connecting to database...");
		loadingLabel.setIcon(iconLoading);
		btnConnect.setVisible(false);
		
		Thread tr = new Thread(new ConnectionThread(this));
		tr.start();
	}

	/**
	 * If the payment code is valid and the payment exists, get the receipt and
	 * open the payment window
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
		} else {
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
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);

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
		btnGetReceipt.setEnabled(false);
		btnGetReceipt.setBounds(231, 152, 105, 34);
		getContentPane().add(btnGetReceipt);
		btnGetReceipt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getReceipt();
			}
		});

		JLabel lblEnterThePaymentcode = new JLabel(
				"Enter the paymentcode below to calculate the price.");
		lblEnterThePaymentcode.setBounds(27, 105, 313, 14);
		getContentPane().add(lblEnterThePaymentcode);

		JPanel panel = new JPanel();
		loadingLabel = new JLabel("Connecting to database...", iconLoading, JLabel.CENTER);
		loadingLabel.setBounds(10, 5, 289, 14);
		panel.setBounds(27, 230, 309, 66);
		panel.setLayout(null);
		panel.add(loadingLabel);
		getContentPane().add(panel);
		
		btnConnect = new JButton("Try again");
		btnConnect.setBounds(105, 32, 89, 23);
		btnConnect.setVisible(false);
		btnConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				connectToDatabase();
			}
		});
		panel.add(btnConnect);

		this.setVisible(true);
	}

	/**
	 * Create an image icon. Throw error when image not found.
	 * @param path	The path to the image file.
	 * @return		The ImageIcon
	 */
	protected ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Show whether connection had been made, or failed. If succeeded, show button to try again.
	 * @param connected
	 */
	public void setConnection(boolean connected) {
		if (connected) {
			loadingLabel.setText("Connected.");
			loadingLabel.setIcon(iconSuccess);
			
			btnGetReceipt.setEnabled(true);
			
		} else {
			loadingLabel.setText("Failed to connect.");
			loadingLabel.setIcon(iconFailed);
			btnConnect.setVisible(true);
		}
	}
}
