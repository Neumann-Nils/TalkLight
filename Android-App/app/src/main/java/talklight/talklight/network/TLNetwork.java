package talklight.talklight.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.json.hue.JSONException;
import org.json.hue.JSONObject;

import talklight.talklight.control.TLHueController;
import talklight.talklight.interfaces.TLActionIdentifier;

public class TLNetwork {
    private static final String path = "kellerkinder.serveblog.net";
    private static final int port = 80;

    private static TLNetwork instance;
    private static TLHueController controller;

    private Socket socket;

    private InputStream in;
    private OutputStream out;

    private BufferedReader reader;
    private BufferedWriter writer;

    private TLNetwork() {
        new Thread() {
            @Override
            public void run() {
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(path, port), 30000);

                    log("Connected to '" + path + ":" + port + "'!");

                    in = socket.getInputStream();
                    out = socket.getOutputStream();

                    reader = new BufferedReader(new InputStreamReader(in));
                    writer = new BufferedWriter(new OutputStreamWriter(out));

                    selectRoom("default");

                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    performReceivedAction(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            log("Reader terminated!");
                        }

                        ;
                    }.start();

                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }.start();
    }

    public static void setController(TLHueController controller) {
        TLNetwork.controller = controller;
    }

    private boolean selectRoom(String room) {
        JSONObject json = new JSONObject();
        json.put("user", "superuser");
        json.put("room", room);
        json.put("action", "selectRoom");

        try {
            writer.write(json.toString() + "\n");
            writer.flush();
            log("Room selected: " + room);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean performAction(TLActionIdentifier action) {
        JSONObject json = new JSONObject();
        json.put("user", "superuser");
        json.put("room", "default");
        json.put("action", actionToString(action));

        try {
            writer.write(json.toString() + "\n");
            writer.flush();
            log("Sending: " + json.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void performReceivedAction(String message) {
        log("Received: " + message);

        try {
            JSONObject json = new JSONObject(message);

            if (controller != null) {
                TLActionIdentifier action = stringToAction(json.getString("action"));
                controller.performActionRemote(action);
                log("Performed external action: " + action);
            }

        } catch (JSONException e) {
            error("Invalid message received: " + message);
        }
    }

    public boolean isConnected() {
        return true;
    }

    private static void log(String text) {
        log(text, System.out);
    }

    private static void error(String text) {
        log(text, System.err);
    }

    private static void log(String text, PrintStream out) {
        System.out.println("TLNetwork: " + text);
    }

    public static TLNetwork getInstance() {
        if (instance == null) {
            log("Initializing TLNetwork!");
            instance = new TLNetwork();
        }
        return instance;
    }

    private static TLActionIdentifier stringToAction(String string) {
        switch (string) {
            case "alert":
                return TLActionIdentifier.ALERT;
            case "dinner":
                return TLActionIdentifier.DINNER;
            case "goodNight":
                return TLActionIdentifier.GOODNIGHT;
            default:
                return null;
        }
    }

    private static String actionToString(TLActionIdentifier action) {
        switch (action) {
            case ALERT:
                return "alert";
            case DINNER:
                return "dinner";
            case GOODNIGHT:
                return "goodNight";
            default:
                return "unknown";
        }
    }
}
