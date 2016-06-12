package backend;


import talklight.talklight.interfaces.TLActionIdentifier;
import talklight.talklight.network.TLNetwork;

public class LocalTest {
	public static void main(String[] args) throws InterruptedException {
		TLNetwork.getInstance();

		LocalLogic.startSession(args[0], args[1]);
		while (!LocalLogic.connected) {
			Thread.sleep(60);
			System.out.println("waiting");
		}
		LocalLogic.enableLights(true);
		Thread.sleep(600);
		LocalLogic.externalAction(TLActionIdentifier.ALERT);
		Thread.sleep(600);
		LocalLogic.internalAction(TLActionIdentifier.GOODNIGHT);
		Thread.sleep(6000);
		LocalLogic.enableLights(true);
		new LocalLogic().performAction(TLActionIdentifier.GOODNIGHT);
		Thread.sleep(6000);
		LocalLogic.enableLights(true);
	}
}
