package talklight.talklight.buttons;

import android.util.Log;
import android.view.View;
import talklight.talklight.interfaces.Paired;
import talklight.talklight.interfaces.TLActionIdentifier;

/**
 * Created by Martin on 11/06/2016.
 */
public class TryAction implements View.OnClickListener
{
    public TryAction(Paired paired, TLActionIdentifier action)
    {
        this.paired = paired;
        this.action = action;
    }

    Paired paired;
    TLActionIdentifier action;

    @Override
    public void onClick(View v)
    {

        if(paired.isActionPermitted(action))
        {
            Log.d("Action", action.toString());
            paired.performAction(action);
        }
    }
}