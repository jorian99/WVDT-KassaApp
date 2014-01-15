package nl.bzn.winkelVDToekomst.counter.network;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;

public class Network {
	
	private static Connection connection;
	private static String ipAddress = "localhost";//"145.37.92.146";
	private static String databaseName = "wvdt";
	private static String userName = "root";
	private static String password = "";
	
	public static void connect() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");

		connection = DriverManager
	          .getConnection("jdbc:mysql://" + ipAddress + "/" + databaseName + "?"
	              + "user=" + userName + "&password=" + password);

	}
	
	public static float getPriceByPaymentId(int paymentId) throws SQLException {
	
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT price FROM tbl_product WHERE id = " + paymentId);
		rs.next();
		return rs.getFloat("price");
	
	}
	
}
