package talklight.talklight;
import java.util.List;
import java.util.Random;

import com.philips.lighting.hue.sdk.*;
import com.philips.lighting.hue.sdk.utilities.*;
import com.philips.lighting.model.*;
import org.json.hue.*;
public class Main {
	static PHHueSDK HHSDK = PHHueSDK.getInstance();
	static PHAccessPoint accessPoint = new PHAccessPoint();
	private static final int MAX_HUE=65535;
	private static List<PHLight> allLights;
	private static PHBridge bridge;
	static boolean connected = false;
	
	private  static PHSDKListener listener = new PHSDKListener() {

		@Override
		public void onAccessPointsFound(List<PHAccessPoint> accessPoints) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAuthenticationRequired(PHAccessPoint accessPoint) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onBridgeConnected(PHBridge arg0, String arg1) {
			System.out.println("connected");
			System.out.println(HHSDK.isAccessPointConnected(accessPoint));
			connected = true;
			updateBulbs();
        	try {
        		externalAction("WARN");
				Thread.sleep(600);
        		externalAction("Love");
		        Thread.sleep(600);
		        externalAction("SLEEP");
		        Thread.sleep(600);
		        internalAction("SLEEP");
		        Thread.sleep(600);
		        enableLights(true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		}

		@Override
		public void onCacheUpdated(List<Integer> cacheNotificationsList, PHBridge bridge) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConnectionLost(PHAccessPoint accessPoints) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConnectionResumed(PHBridge bridge) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(int code, String message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onParsingErrors(List<PHHueParsingError> parsingErrors) {
			// TODO Auto-generated method stub
			
		}};
		
		
		private static void updateBulbs(){
			bridge = HHSDK.getSelectedBridge();
	        PHBridgeResourcesCache cache = bridge.getResourceCache();
	        allLights = cache.getAllLights();
		}
		private static void enableLights(boolean ok){
			for (PHLight light : allLights){
				PHLightState lightState = new PHLightState();
				lightState.setOn(ok);
	            bridge.updateLightState(light, lightState);
	        }
		}
		private static void setXY(float[] colors, PHLight light){
			PHLightState lightState = new PHLightState();
            lightState.setX(colors[0]);
            lightState.setY(colors[1]);
            bridge.updateLightState(light, lightState); // If no bridge response is required then use this simpler form.
		}
		
		private static void Lights(int[] params) {
	        float[] colors = PHUtilities.calculateXYFromRGB(params[0], params[1], params[2], "LCT001");
	        for (PHLight light : allLights) {
	            setXY(colors, light);
	        }
	    }
		
		//external calls
		private static void externalAction(String action){
			int[] WARN = {255,0,0}, Food = {0,255,0}, SLEEP = {0,0,255};
			switch(action){
			case "WARN":
				Lights(WARN);
				break;
			case "LOVE":
				Lights(Food);
				break;
			case "SLEEP":
				Lights(SLEEP);
				break;
			}
		}
		//internal calls
		private static void internalAction(String action){
			if(action.equals("SLEEP")){
				enableLights(false);
			}
		}
		public static void main(String[] args) throws InterruptedException{
	        accessPoint.setIpAddress("10.0.1.2");
	        accessPoint.setUsername("VPr0mrSTEB1iGDIBOVO3axOWRS5h7QMbRGPiuqmX");
	        HHSDK.connect(accessPoint);
	        HHSDK.getNotificationManager().registerSDKListener(listener);
		}
		
	
	
}
