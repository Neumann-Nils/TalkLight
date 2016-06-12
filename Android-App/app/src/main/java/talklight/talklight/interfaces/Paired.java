package talklight.talklight.interfaces;

import android.graphics.Color;

import java.util.List;

/**
 * Created by Martin on 11/06/2016.
 * Should be generated on successful pairing.
 */
public interface Paired
{
    /**
     *
     * @return returns the id of the paired hue
     */
    int getId();

    /**
     *
     * @param action the action the user wants to perform, i.e. TLActionIdentifier.GOODNIGHT     *
     * @return is this permission given for paired hue (e.g. in custom private
     * mode)
     */
    boolean isActionPermitted(TLActionIdentifier action);


    /**
     *
     * @param action the the action the user wants to perform, i.e.
     *               TLActionIdentifier.GOODNIGHT
     * @return boolean to signal if action was performed or not
     */
    boolean performAction(TLActionIdentifier action);


    boolean getPrivate();
    void setPrivate(boolean priv);

    boolean getSilent();
    void setSilent(boolean silent);


    List<Integer> getColorListAsHex();











}
