package talklight.talklight.buttons;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import talklight.R;
import talklight.talklight.interfaces.Paired;
import talklight.talklight.interfaces.Rankable;

/**
 * Created by Martin on 12/06/2016.
 */
public class ColoredButton extends FrameLayout implements Rankable
{
    public int getHex_color()
    {
        return hex_color;
    }

    public void setHex_color(int hex_color)
    {
        this.hex_color = hex_color;
    }

    int hex_color;

    public ColoredButton(Context context, final Paired paired, int color)
    {
        super(context);
        hex_color = color;
        this.setBackgroundColor(color);
        inflate(getContext(), R.layout.color_button, this);
        this.setOnClickListener(new OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        paired.getColorListAsHex().add
                                                (hex_color);
                                    }
                                }

        );


    }

    @Override
    public double getRank()
    {
        return 0;
    }
}
