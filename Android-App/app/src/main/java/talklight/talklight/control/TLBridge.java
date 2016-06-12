package talklight.talklight.control;

import java.util.List;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class TLBridge {
	private final PHBridge bridge;
	private final PHLight[] lights;
	
	public TLBridge(PHBridge bridge) {
		this.bridge = bridge;

		PHBridgeResourcesCache cache = bridge.getResourceCache();

		List<PHLight> list = cache.getAllLights();
		list.toArray(lights = new PHLight[3]);
	}

	public void applyProfile(TLProfile profile) {
		for (int i = 0; i < lights.length; i++) {
			PHLightState ls = profile.getLightState(i);
			if (ls != null) {
				bridge.updateLightState(lights[i], ls);
			}
		}
	}
}
