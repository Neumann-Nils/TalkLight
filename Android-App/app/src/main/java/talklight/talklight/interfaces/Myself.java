package talklight.talklight.interfaces;

/**
 * Created by Martin on 11/06/2016.
 */
public interface Myself //represents the user of the app
{
    /**
     *
     * @return checks if the app is connected to the server
     *
     */
    boolean isConnected();


    /** Should try to pair to specified pairable. should use the id (or user
     * object).
     *  Should store the Paired hue to use for other methods
     *
     * @param id id of the hue to pair (could be changed to user object)
     * @return boolean to specify if pairing succeeded
     */
    boolean pairTo(int id);



    boolean unPair() throws NotPairedException;

}
