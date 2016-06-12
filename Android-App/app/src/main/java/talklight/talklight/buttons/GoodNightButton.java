package talklight.talklight.buttons;

import android.content.Context;
import android.widget.FrameLayout;

import talklight.talklight.interfaces.Paired;
import talklight.talklight.interfaces.Rankable;
import talklight.talklight.interfaces.TLActionIdentifier;
import talklight.R;

import java.util.Calendar;

/**
 * Created by Martin on 11/06/2016.
 */
public class GoodNightButton extends FrameLayout implements Rankable
{
    public GoodNightButton(Context context, Paired paired)
    {
        super(context);
        inflate(getContext(), R.layout.good_night_btn, this);
        this.setOnClickListener(new TryAction(paired, this.action));
    }

    TLActionIdentifier action = TLActionIdentifier.GOODNIGHT;
    int timePoint = 22;


    @Override
    public double getRank()
    {
        return Math.abs(Calendar.HOUR_OF_DAY - this.timePoint)*100.0;
    }
}














