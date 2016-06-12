package talklight.talklight.control;


import android.graphics.Color;

import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHLight.PHLightAlertMode;
import com.philips.lighting.model.PHLightState;

public class TLProfile {
	private boolean[] enabled;
	private int[] color = new int[3];
	private double[] brightness = new double[] { 1, 1, 1 };
	private boolean[] power = new boolean[] { true, true, true };
	private boolean[] alert = new boolean[3];
	
	public TLProfile() {
	}

	public TLProfile(boolean enabled) {
		this.enabled = new boolean[] { enabled, enabled, enabled };
	}

	public void setColor(int index, int color) {
		if (index < 0 || index >= this.color.length) {
			return;
		}

		this.color[index] = color;
		enabled[index] = true;
	}
	
	public void setColor(int c) {
		setColor(c, c, c);
	}
	
	public void setColor(int c0, int c1, int c2) {
		color = new int[] { c0, c1, c2 };
		this.enabled = new boolean[] { true, true, true };
	}

	public void setPower(int index, boolean power) {
		if (index < 0 || index >= this.power.length) {
			return;
		}

		this.power[index] = power;
		enabled[index] = true;
	}

	public void setPower(boolean p) {
		setPower(p, p, p);
	}
	
	public void setPower(boolean p0, boolean p1, boolean p2) {
		power = new boolean[] { p0, p1, p2 };
		this.enabled = new boolean[] { true, true, true };
	}
	
	public void setBrightness(double b) {
		setBrightness(b, b, b);
	}

	public void setAlert(boolean a) {
		setAlert(a, a, a);
	}
	
	public void setAlert(boolean a0, boolean a1, boolean a2) {
		alert = new boolean[] { a0, a1, a2 };
		this.enabled = new boolean[] { true, true, true };
	}
	
	public void setBrightness(double b0, double b1, double b2) {
		brightness = new double[] { b0, b1, b2 };
		this.enabled = new boolean[] { true, true, true };
	}

	public PHLightState getLightState(int index) {
		if (!enabled[index]) {
			return null;
		}

		float[] xy;
		if (color[index] != 0) {
			int c = color[index];
			xy = PHUtilities.calculateXY(c, "LCT001");
		} else {
			xy = new float[] { 0.5f, 0.5f };
		}
		
		PHLightState ls = new PHLightState();
		ls.setX(xy[0]);
		ls.setY(xy[1]);
		ls.setOn(power[index]);
		ls.setBrightness((int) (brightness[index] * 254));
		
		ls.setAlertMode(alert[index] ? PHLightAlertMode.ALERT_LSELECT : PHLightAlertMode.ALERT_NONE);

		return ls;
	}
}
