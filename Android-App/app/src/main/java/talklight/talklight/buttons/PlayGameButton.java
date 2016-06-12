package talklight.talklight.buttons;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import talklight.R;
import talklight.talklight.fragments.ContentGridFragment;
import talklight.talklight.interfaces.Paired;
import talklight.talklight.interfaces.Rankable;
import talklight.talklight.interfaces.TLActionIdentifier;

/**
 * Created by Martin on 12/06/2016.
 */


public class PlayGameButton extends FrameLayout implements Rankable
{
    TLActionIdentifier action = TLActionIdentifier.STARTGAME;


    public PlayGameButton(final Context context)
    {
        super(context);
        Log.d("playgen", "playgen");
        inflate(getContext(), R.layout.start_game_btn, this);
    }



    @Override
    public double getRank()
    {
        return 0;
    }

}
