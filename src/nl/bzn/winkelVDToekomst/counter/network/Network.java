package nl.bzn.winkelVDToekomst.counter.network;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;

import nl.bzn.winkelVDToekomst.counter.config.Config;

/**
 * Network class
 * @author Jorian Plat <jorianplat@hotmail.com>
 * @version 1.0
 */
public class Network {
	
	private static Connection connection;
	
	/**
	 * Try to connect to the MySQL-database.
	 * @return true if connection succeeded, false if not.
	 */
	public static boolean connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager
				      .getConnection("jdbc:mysql://" + Config.DATABASE_IP_ADDRESS + "/" + Config.DATABASE_NAME + "?"
				          + "user=" + Config.DATABASE_USERNAME + "&password=" + Config.DATABASE_PASSWORD);
			return true;
		} catch (ClassNotFoundException|SQLException e) {
			return false;
		}

	}
	
	/**
	 * Check whether payment exists.
	 * @param paymentId		The ID of the payment in the table `tbl_payment`
	 * @return 				true if the payment exists, false if not.
	 */
	public static boolean paymentExists(int paymentId) {
		Statement statement;
		ResultSet rs;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT id FROM tbl_payment WHERE id = " + paymentId);
			return rs.next();
		} catch (SQLException e) {
			return false;
		}

	}
	
	/**
	 * Get the information of a payment.
	 * @param paymentId		The ID of the payment in the table `tbl_payment`
	 * @return ResultSet	The information of a payment.	
	 */
	public static ResultSet getPaymentById(int paymentId) {
		
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT payment.*, payment.id, user.email FROM tbl_user user, tbl_payment payment WHERE user.id = payment.user_id AND payment.id = " + paymentId);
			rs.next();
			return rs;
		} catch (SQLException e) {
			return null;
		}

	}	
	
	/**
	 * Get a list of the products on the receipt.
	 * @param paymentId		The ID of the payment in the table `tbl_payment`	
	 * @return	ResultSet	A list of products
	 */
	public static ResultSet getReceiptByPaymentId(int paymentId) {
		
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT product.id AS productId, product.name AS productName, product.price AS productPrice, productPayment.amount FROM tbl_product product, tbl_product_payment productPayment, tbl_payment payment WHERE payment.id = productPayment.payment_id AND product.id = productPayment.product_id AND productPayment.payment_id = " + paymentId);
			return rs;
		} catch (SQLException e) {
			return null;
		}

	}

	/**
	 * Mark a receipt as paid.
	 * @param paymentId		The ID of the payment in the table `tbl_payment`
	 * @return boolean		Return true if the update succeeded, return false if not
	 */
	public static boolean markReceiptAsPaid(int paymentId) {
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE tbl_payment SET paid_at = NOW() WHERE id = " + paymentId);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
}
