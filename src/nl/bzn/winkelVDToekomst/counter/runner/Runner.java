package nl.bzn.winkelVDToekomst.counter.runner;

import java.sql.SQLException;

import nl.bzn.winkelVDToekomst.counter.network.Network;
import nl.bzn.winkelVDToekomst.counter.ui.Main;

public class Runner {

	public static void main(String[] args) {
		
		try {
			Network.connect();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Main();
		
	}
	
}
