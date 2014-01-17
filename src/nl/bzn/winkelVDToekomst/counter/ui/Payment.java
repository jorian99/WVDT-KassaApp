package nl.bzn.winkelVDToekomst.counter.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JFrame;

import nl.bzn.winkelVDToekomst.counter.config.Config;
import nl.bzn.winkelVDToekomst.counter.network.Network;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;

/**
 * Payment class
 * @author Jorian Plat <jorianplat@hotmail.com>
 * @version 1.0
 */
public class Payment extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblPaymentID;
	private JLabel lblUserID;
	private JLabel lblUserEmail;
	private JLabel lblTotalPrice;
	private JLabel lblCreatedAt;
	private JLabel lblPaidAt;
	
	private JTable tblReceipt;
	private JButton btnMarkAsPaid;

	private int paymentId;

	public Payment(int paymentId) {

		this.paymentId = paymentId;

		setUpFrame();

		showPaymentInfo();

		showReceipt();

	}

	/**
	 * Show the information of a payment/receipt.
	 * If the receipt already has been paid, disable the "Mark as paid" button.
	 */
	private void showPaymentInfo() {
		ResultSet rs = Network.getPaymentById(paymentId);

		if (rs != null) {
			try {
				lblUserID.setText(Integer.toString(rs.getInt("user_id")));
				lblPaymentID.setText(Integer.toString(rs.getInt("id")));
				lblUserEmail.setText(rs.getString("email"));
				lblCreatedAt.setText(rs.getString("created_at"));
				if (rs.getString("paid_at") == null) {
					lblPaidAt.setText("Not paid yet");
				} else {
					lblPaidAt.setText(rs.getString("paid_at"));
					btnMarkAsPaid.setEnabled(false);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(this, "Something wen't wrong!");
		}
	}

	/**
	 * Mark a receipt as paid.
	 */
	protected void markReceiptAsPaid() {
		if (Network.markReceiptAsPaid(paymentId)) {
			showPaymentInfo();
			
			JOptionPane.showMessageDialog(this, "The receipt has been marked as paid!");
		}else {
			JOptionPane.showMessageDialog(this, "Something went wrong. Try again later.");
		}
		
		
	}

	/**
	 * Go back to the main screen.
	 */
	protected void goBack() {
		new Main();

		this.setVisible(false);
		this.dispose();
	}

	/**
	 * Show the list of all products on the receipt, with at the end a total price.
	 */
	private void showReceipt() {
		DecimalFormat df = new DecimalFormat("##,##0.00");
		try {
			ResultSet rs = Network.getReceiptByPaymentId(paymentId);
			DefaultTableModel model = (DefaultTableModel) tblReceipt.getModel();

			double price = 0, totalPrice = 0;
			while (rs.next()) {
				price = rs.getFloat("productPrice") * rs.getInt("amount");
				model.addRow(new Object[] { rs.getInt("productId"),
						rs.getString("productName"),
						"€ " + df.format(rs.getFloat("productPrice")),
						rs.getInt("amount"), "€ " + df.format(price) });
				totalPrice += price;
			}

			lblTotalPrice.setText("€ " + df.format(totalPrice));

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Failed to load the receipt. Try again later.");
		}

	}
	
	/**
	 * Set up the frame.
	 */
	private void setUpFrame() {
		this.setTitle(Config.MAIN_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Config.PAYMENT_WIDTH, Config.PAYMENT_HEIGHT);
		getContentPane().setLayout(null);

		JButton btnGoBack = new JButton("Go back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				goBack();
			}
		});
		btnGoBack.setBounds(10, 11, 89, 28);
		getContentPane().add(btnGoBack);

		JLabel lblUserId = new JLabel("Payment ID:");
		lblUserId.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUserId.setBounds(10, 60, 89, 14);
		getContentPane().add(lblUserId);

		JLabel lblEmail = new JLabel("User ID:");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setBounds(10, 85, 89, 14);
		getContentPane().add(lblEmail);

		JLabel lblNewLabel = new JLabel("Email:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 110, 89, 14);
		getContentPane().add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 50, 490, 2);
		getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 185, 490, 2);
		getContentPane().add(separator_1);

		lblPaymentID = new JLabel("");
		lblPaymentID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPaymentID.setBounds(114, 60, 46, 14);
		getContentPane().add(lblPaymentID);

		lblUserID = new JLabel("");
		lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUserID.setBounds(114, 85, 46, 14);
		getContentPane().add(lblUserID);

		lblUserEmail = new JLabel("");
		lblUserEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUserEmail.setBounds(114, 110, 222, 14);
		getContentPane().add(lblUserEmail);

		JPanel panel = new JPanel();
		panel.setBounds(10, 198, 490, 435);
		getContentPane().add(panel);

		tblReceipt = new JTable();
		tblReceipt.setRowSelectionAllowed(false);

		tblReceipt
				.setModel(new DefaultTableModel(new Object[][] {},
						new String[] { "ID", "Name", "Price", "Amount",
								"Total price" }) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int arg0, int arg1) {
						return false;
					}
				});
		tblReceipt.getColumnModel().getColumn(1).setPreferredWidth(150);
		JScrollPane scrollPane = new JScrollPane(tblReceipt);
		panel.add(scrollPane);

		JLabel lbl = new JLabel("Total price:");
		lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl.setBounds(20, 630, 136, 34);
		getContentPane().add(lbl);

		lblTotalPrice = new JLabel("");
		lblTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalPrice.setBounds(142, 630, 106, 34);
		getContentPane().add(lblTotalPrice);

		btnMarkAsPaid = new JButton("Mark as paid");
		btnMarkAsPaid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				markReceiptAsPaid();
			}
		});
		btnMarkAsPaid.setBounds(388, 11, 112, 28);
		getContentPane().add(btnMarkAsPaid);
		
		JLabel lbl_2 = new JLabel("Created at:");
		lbl_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_2.setBounds(10, 135, 89, 14);
		getContentPane().add(lbl_2);
		
		JLabel lbl_3 = new JLabel("Paid at:");
		lbl_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_3.setBounds(10, 160, 89, 14);
		getContentPane().add(lbl_3);
		
		lblCreatedAt = new JLabel("");
		lblCreatedAt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCreatedAt.setBounds(114, 135, 222, 14);
		getContentPane().add(lblCreatedAt);
		
		lblPaidAt = new JLabel("");
		lblPaidAt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPaidAt.setBounds(114, 160, 222, 14);
		getContentPane().add(lblPaidAt);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2 - 15);

		this.setVisible(true);
	}
}
