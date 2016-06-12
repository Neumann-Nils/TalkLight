package talklight.talklight.control;

import java.io.PrintStream;
import java.util.List;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

public class TLHueSDK implements PHSDKListener {
	private final PHHueSDK sdk;
	private PHBridge bridge;

	public TLHueSDK(String ip, String userName) {
		sdk = PHHueSDK.getInstance();

		sdk.getNotificationManager().registerSDKListener(this);

		PHAccessPoint ap = new PHAccessPoint(ip, userName, null);
		sdk.connect(ap);
	}

	public TLBridge getBridge() {
		while (bridge == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return new TLBridge(bridge);
	}

	@Override
	public void onAccessPointsFound(List<PHAccessPoint> accessPoints) {
		log("onAccessPointsFound");
	}

	@Override
	public void onAuthenticationRequired(PHAccessPoint accessPoint) {
		log("onAuthenticationRequired");
	}

	@Override
	public void onBridgeConnected(PHBridge bridge, String arg) {
		log("onBridgeConnected");
		this.bridge = bridge;
	}

	@Override
	public void onCacheUpdated(List<Integer> cacheNotificationsList, PHBridge bridge) {
		log("onCacheUpdated");
	}

	@Override
	public void onConnectionLost(PHAccessPoint accessPoints) {
		log("onConnectionLost");
	}

	@Override
	public void onConnectionResumed(PHBridge bridge) {
		log("onConnectionResumed");
	}

	@Override
	public void onError(int code, String message) {
		log("onError: " + message);
	}

	@Override
	public void onParsingErrors(List<PHHueParsingError> parsingErrors) {
		log("onParsingErrors");
	}

	private static void log(String text) {
		log(text, System.out);
	}

	private static void error(String text) {
		log(text, System.err);
	}

	private static void log(String text, PrintStream out) {
		System.out.println("TLSDK: " + text);
	}
}
