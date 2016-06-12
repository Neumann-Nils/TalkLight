package talklight.talklight.json;

import org.json.hue.JSONObject;

abstract public class TLMessage {
	private String action;

	public TLMessage(String message) throws TLMessageFormatException {
		JSONObject json = new JSONObject(message);

		if (!json.has("action")) {
			throw new TLMessageFormatException();
		}

		action = json.getString("string");
	}

	public void getAction() {

	}

	public String toJson() {
		JSONObject json = new JSONObject();
		json.put("action", action);
		return json.toString();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public class TLMessageFormatException extends Exception {
		private static final long serialVersionUID = -6423897667201374016L;
	}
}
