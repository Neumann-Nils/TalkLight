package talklight.talklight.buttons;

import android.content.Context;
import android.widget.FrameLayout;

import talklight.talklight.interfaces.Paired;
import talklight.talklight.interfaces.Rankable;
import talklight.talklight.interfaces.TLActionIdentifier;
import talklight.R;

/**
 * Created by Henry on 11.06.2016.
 */
public class DinnerButton  extends FrameLayout implements Rankable {
    TLActionIdentifier action = TLActionIdentifier.DINNER;
    public DinnerButton(Context context, Paired paired) {
        super(context);
        inflate(getContext(), R.layout.dinner_btn, this);
        this.setOnClickListener(new TryAction(paired, this.action));
    }

    @Override
    public double getRank() {
        return 0;
    }
}
