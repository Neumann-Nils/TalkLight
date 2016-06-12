package talklight.talklight.control;

import android.graphics.Color;

import java.util.Hashtable;
import java.util.List;

import talklight.talklight.interfaces.Paired;
import talklight.talklight.interfaces.TLActionIdentifier;
import talklight.talklight.network.TLNetwork;


public class TLHueController implements Paired{
	private static String[][] bridgeCredentials = { { "10.3.10.170", "VPr0mrSTEB1iGDIBOVO3axOWRS5h7QMbRGPiuqmX" },
			{ "10.3.10.13", "E7faxTwN5yfUDLIf8eyqzvmnPf47XyCcs-4xIatp" } };
			
	private final TLHueSDK sdk;
	private final TLBridge bridge;
	
	private boolean silentMode;
	private boolean privateMode;
	
	private final Hashtable<TLActionIdentifier, TLProfile> profilesLocal;
	private final Hashtable<TLActionIdentifier, TLProfile> profilesRemote;

	public TLHueController(int clientIndex) {
		TLNetwork.getInstance();

		if (clientIndex < 0 || clientIndex >= bridgeCredentials.length) {
			System.err.println("ERROR! Client index not supported: " + clientIndex);
			System.exit(0);
		}
		
		sdk = new TLHueSDK(bridgeCredentials[clientIndex][0], bridgeCredentials[clientIndex][1]);
		bridge = sdk.getBridge();
		
		if (clientIndex == 0) {
			profilesLocal = getProfilesLocal0();
			profilesRemote = getProfilesRemote0();
		} else {
			profilesLocal = getProfilesLocal1();
			profilesRemote = getProfilesRemote1();
		}

		TLNetwork.setController(this);
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public boolean isActionPermitted(TLActionIdentifier action) {
		return true;
	}

	@Override
	public boolean performAction(TLActionIdentifier action) {
		if (!privateMode) {
			TLNetwork.getInstance().performAction(action);
		}

		TLProfile profile = profilesLocal.get(action);
		bridge.applyProfile(profile);
		return true;
	}

	@Override
	public boolean getPrivate() {
		return this.privateMode;
	}

	@Override
	public void setPrivate(boolean priv) {
		this.privateMode = priv;
	}

	@Override
	public boolean getSilent() {
		return this.silentMode;
	}

	@Override
	public void setSilent(boolean silent) {
		this.silentMode = silent;
	}

	@Override
	public List<Integer> getColorListAsHex() {
		return null;
	}

	public void performActionRemote(TLActionIdentifier action) {
		if (!silentMode) {
			TLProfile profile = profilesRemote.get(action);
			bridge.applyProfile(profile);
		}
	}

	private Hashtable<TLActionIdentifier, TLProfile> getProfilesLocal0() {
		Hashtable<TLActionIdentifier, TLProfile> table = new Hashtable<>();
		
		TLProfile p;
		
		table.put(TLActionIdentifier.ALERT, new TLProfile(false));
		
		p = new TLProfile();
		p.setColor(Color.YELLOW);
		table.put(TLActionIdentifier.DINNER, p);
		
		p = new TLProfile();
		p.setBrightness(0);
		p.setColor(Color.parseColor("#ffffff"));
		table.put(TLActionIdentifier.GOODNIGHT, p);
		
		return table;
	}
	
	private Hashtable<TLActionIdentifier, TLProfile> getProfilesRemote0() {
		Hashtable<TLActionIdentifier, TLProfile> table = new Hashtable<>();
		
		TLProfile p;
		
		p = new TLProfile();
		p.setAlert(true);
		p.setColor(Color.MAGENTA);
		table.put(TLActionIdentifier.ALERT, p);
		
		p = new TLProfile();
		p.setColor(Color.WHITE);
		table.put(TLActionIdentifier.DINNER, p);
		
		p = new TLProfile();
		p.setBrightness(0.3);
		p.setColor(Color.GREEN);
		table.put(TLActionIdentifier.GOODNIGHT, p);
		
		return table;
	}
	
	private Hashtable<TLActionIdentifier, TLProfile> getProfilesLocal1() {
		Hashtable<TLActionIdentifier, TLProfile> table = new Hashtable<>();
		
		TLProfile p;
		
		table.put(TLActionIdentifier.ALERT, new TLProfile(false));
		
		p = new TLProfile();
		int r,g,b;
		r = Color.red(Color.YELLOW);
		g = Color.green(Color.YELLOW);
		b = Color.blue(Color.YELLOW);
		r += 30;
		g += 30;
		b += 30;
		p.setColor(Color.rgb(r, g, b));
		table.put(TLActionIdentifier.DINNER, p);
		
		p = new TLProfile();
		p.setPower(false);
		table.put(TLActionIdentifier.GOODNIGHT, p);
		
		return table;
	}
	
	private Hashtable<TLActionIdentifier, TLProfile> getProfilesRemote1() {
		Hashtable<TLActionIdentifier, TLProfile> table = new Hashtable<>();
		
		TLProfile p;
		
		p = new TLProfile();
		p.setAlert(true);
		p.setColor(Color.RED);
		table.put(TLActionIdentifier.ALERT, p);
		
		p = new TLProfile();
		p.setColor(Color.WHITE);
		table.put(TLActionIdentifier.DINNER, p);
		
		p = new TLProfile();
		p.setBrightness(0.3);
		p.setColor(Color.RED);
		table.put(TLActionIdentifier.GOODNIGHT, p);
		
		return table;
	}
}
