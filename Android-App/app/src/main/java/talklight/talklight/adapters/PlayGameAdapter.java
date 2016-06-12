package talklight.talklight.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import talklight.talklight.buttons.ColoredButton;
import talklight.talklight.interfaces.Paired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 12/06/2016.
 */
public class PlayGameAdapter extends ArrayAdapter
{

    private List<Integer> colorsGiven;
    private Paired paired;

    public PlayGameAdapter(Context context, int resource)
    {
        super(context, resource);

        colorsGiven = new ArrayList<>();


    }


    public Paired getPaired()
    {
        return paired;
    }

    public void setPaired(Paired paired)
    {
        this.paired = paired;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if(v == null)
        {
            v = new ColoredButton(getContext(),paired,colorsGiven.get
                    (position));
        }

        return v;
    }

    @Override
    public int getCount()
    {
        return this.colorsGiven.size();
    }


//    public void removeColor(int color)


    public void addColor(int color) //button
    {
        colorsGiven.add(color);
    }


}
