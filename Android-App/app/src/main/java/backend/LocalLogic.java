package backend;

import java.util.ArrayList;
import java.util.List;

//Import of the Philips API
import com.philips.lighting.hue.sdk.*;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.*;
//Own imports
import talklight.talklight.interfaces.TLActionIdentifier;
import talklight.talklight.network.TLNetwork;

public class LocalLogic implements talklight.talklight.interfaces.Paired {
	// static class Variables
	public static PHHueSDK HHSDK = PHHueSDK.getInstance(); // SDK Connection
	public static PHAccessPoint accessPoint = new PHAccessPoint(); // Accesspoint
																	// to the
																	// bulbs
	public static final int MAX_HUE = 65535; // Max Value for Color
	public static List<PHLight> allLights; // List of available Bulbs
	public static PHBridge bridge; // Philips Bridge
	public static boolean connected = false; // connected to bridge
	private static boolean silentMode, privateMode; // Private & Silent mode

	private static PHSDKListener listener = new PHSDKListener() { // sdk
																	// Listener

		@Override
		public void onAccessPointsFound(List<PHAccessPoint> accessPoints) {
		}

		@Override
		public void onAuthenticationRequired(PHAccessPoint accessPoint) {
		}

		@Override
		public void onBridgeConnected(PHBridge arg0, String arg1) {
			connected = true; // update status
			updateBulbs(); // update Bulbs list
			System.out.println("con established"); // DEBUG
		}

		@Override
		public void onCacheUpdated(List<Integer> cacheNotificationsList, PHBridge bridge) {
		}

		@Override
		public void onConnectionLost(PHAccessPoint accessPoints) {
		}

		@Override
		public void onConnectionResumed(PHBridge bridge) {
		}

		@Override
		public void onError(int code, String message) {
		}

		@Override
		public void onParsingErrors(List<PHHueParsingError> parsingErrors) {
		}
	};

	// updates list of available bulbs
	private static void updateBulbs() {
		bridge = HHSDK.getSelectedBridge();
		PHBridgeResourcesCache cache = bridge.getResourceCache();
		allLights = cache.getAllLights();
	}

	// enable lightbulbs or disable them, depending on Parameter ok
	// true = light on
	public static void enableLights(boolean ok) {
		for (PHLight light : allLights) {
			PHLightState lightState = new PHLightState();
			lightState.setOn(ok);
			bridge.updateLightState(light, lightState);
		}
	}

	// calculate Philips colors
	private static void setXY(float[] colors, PHLight light) {
		PHLightState lightState = new PHLightState();
		lightState.setX(colors[0]);
		lightState.setY(colors[1]);
		bridge.updateLightState(light, lightState); // If no bridge response is
													// required then use this
													// simpler form.
	}

	// set all lightbulbs to a color
	private static void Lights(int[] params) {
		float[] colors = PHUtilities.calculateXYFromRGB(params[0], params[1], params[2], "LCT001");
		for (PHLight light : allLights) {
			setXY(colors, light);
		}
	}

	// internal Actions (caused by user)
	public static void internalAction(TLActionIdentifier action) {
		if (!silentMode) {

			if (action == TLActionIdentifier.GOODNIGHT) {
				enableLights(false);
			}
		}
	}

	// external calls (caused by Girlfriend or Boyfriend)
	public static void externalAction(TLActionIdentifier action) {
		if (!privateMode) {
			int[] WARN = { 255, 0, 0 }, DINNER = { 0, 255, 0 }, SLEEP = { 0, 0, 255 };
			enableLights(true);
			switch (action) {
			case ALERT:
				Lights(WARN);
				break;
			case DINNER:
				Lights(DINNER);
				break;
			case GOODNIGHT:
				Lights(SLEEP);
				break;
			}
		}
	}

	// LEGACY version, used for first Philips station
	public static void startSession() throws InterruptedException {
		startSession("10.3.10.170", "VPr0mrSTEB1iGDIBOVO3axOWRS5h7QMbRGPiuqmX");
	}

	// new Version, starts a connection to a Bridge depending on IP and Username
	public static void startSession(String ip, String userName) throws InterruptedException {
		accessPoint.setIpAddress(ip);
		accessPoint.setUsername(userName);
		HHSDK.connect(accessPoint);
		HHSDK.getNotificationManager().registerSDKListener(listener);
	}

	@Override
	// get ID of Users, not implemented as User Service was not created (Time?
	// What's that?)
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	// created in order to test private & silent Mode, plus user interaction, to
	// be edited
	public boolean isActionPermitted(TLActionIdentifier action) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	// launch an action, first local Version, then start remote call
	public boolean performAction(TLActionIdentifier act) {
		final TLActionIdentifier action = act;
		internalAction(action);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TLNetwork net = TLNetwork.getInstance();

					net.performAction(action);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}).start();
		return true;
	}

	@Override
	public boolean getPrivate() {
		return privateMode;
	}

	@Override
	public void setPrivate(boolean priv) {
		privateMode = priv;

	}

	@Override
	public boolean getSilent() {
		return silentMode;
	}

	@Override
	public void setSilent(boolean silent) {
		silentMode = silent;

	}

	@Override
	public List<Integer> getColorListAsHex()
	{
		return ColorList;
	}

	private List<Integer> ColorList = new ArrayList<>();

}
