package backend;

import talklight.talklight.interfaces.NotPairedException;
import talklight.talklight.interfaces.Paired;

public class LocalMe implements talklight.talklight.interfaces.Myself{

	@Override
	//System connected to a Bridge
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return LocalLogic.connected;
	}


	@Override
	//initialize System (Bridge)
	public boolean pairTo(int id) {
		// TODO Auto-generated method stub
		try {
			LocalLogic.startSession();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	//see getPair
	public boolean unPair() throws NotPairedException {
		// TODO Auto-generated method stub
		return true;
	}

}
