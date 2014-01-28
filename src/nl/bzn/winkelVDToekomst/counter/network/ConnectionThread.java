package nl.bzn.winkelVDToekomst.counter.network;

import nl.bzn.winkelVDToekomst.counter.ui.Main;

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
