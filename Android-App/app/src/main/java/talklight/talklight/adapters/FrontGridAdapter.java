package talklight.talklight.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.Collections;
import java.util.List;

import talklight.talklight.buttons.AlertButton;
import talklight.talklight.buttons.DinnerButton;
import talklight.talklight.buttons.GoodNightButton;
import talklight.talklight.buttons.PlayGameButton;
import talklight.talklight.fragments.ContentGridFragment;
import talklight.talklight.interfaces.Paired;
import talklight.talklight.interfaces.TLActionIdentifier;

/**
 * Created by Henry on 11.06.2016.
 */
public class FrontGridAdapter extends ArrayAdapter{
    private List<TLActionIdentifier> myActions;
    private Paired myPaired;



    public FrontGridAdapter(Context context, int resource) {
        super(context, resource);
    }

    public List<TLActionIdentifier> getMyActions() {
        return myActions;
    }

    public void setMyActions(List<TLActionIdentifier> myActions) {
        this.myActions = myActions;
    }

    @Override
    public int getCount()
    {
        return this.myActions.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TLActionIdentifier action = this.getMyActions().get(position);
        Log.d("INFLATE", String.format("%d", getCount()));
        if (v == null)
        {
            View btn = null;
            if(action == TLActionIdentifier.GOODNIGHT)
            {
                btn = new GoodNightButton(getContext(), this.getMyPaired());
            }else if(action == TLActionIdentifier.ALERT)
            {
                btn = new AlertButton(getContext(), this.getMyPaired());

            }else if(action == TLActionIdentifier.DINNER)
            {
                btn = new DinnerButton(getContext(), this.getMyPaired());
            }
            else if(action == TLActionIdentifier.STARTGAME)
            {
                Log.d("playgame", "reached");
                btn = new PlayGameButton(getContext());
            }
            v = btn;

        }


        return v;
    }

    public Paired getMyPaired() {
        return myPaired;
    }

    public void setMyPaired(Paired myPaired) {
        this.myPaired = myPaired;
    }

    @Override
    public Object getItem(int position)
    {
        return myActions.get(position);
    }
}
