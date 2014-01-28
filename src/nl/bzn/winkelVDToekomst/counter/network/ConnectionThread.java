package nl.bzn.winkelVDToekomst.counter.network;

import nl.bzn.winkelVDToekomst.counter.ui.Main;

/**
 * Connection Thread
 * @author Jorian Plat <jorianplat@hotmail.com>
 * @version 1.0
 */
public class ConnectionThread implements Runnable {
	
	private Main main;
	
	public ConnectionThread(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		
		main.setConnection(Network.connect());
		
	}

}
