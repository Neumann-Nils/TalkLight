package talklight.talklight.buttons;

import android.content.Context;
import android.widget.FrameLayout;

import talklight.talklight.interfaces.Paired;
import talklight.talklight.interfaces.Rankable;
import talklight.talklight.interfaces.TLActionIdentifier;
import talklight.R;

/**
 * Created by Martin on 11/06/2016.
 */
public class AlertButton extends FrameLayout implements Rankable
{
    public AlertButton(Context context, Paired paired)
    {
        super(context);
        inflate(getContext(), R.layout.alert_btn, this);
        this.setOnClickListener(new TryAction(paired, this.action));
        this.fixedRank = fixedRank;

    }

    TLActionIdentifier action = TLActionIdentifier.ALERT;
    int fixedRank;


    @Override
    public double getRank()
    {
        return this.fixedRank;
    }

}
