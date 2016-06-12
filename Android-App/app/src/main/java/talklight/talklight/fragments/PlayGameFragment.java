package talklight.talklight.fragments;


import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import talklight.R;
import talklight.talklight.adapters.PlayGameAdapter;
import talklight.talklight.buttons.TryAction;
import talklight.talklight.interfaces.Paired;
import talklight.talklight.interfaces.TLActionIdentifier;

/**
 * Created by Martin on 12/06/2016.
 */
public class PlayGameFragment extends Fragment
{
    public PlayGameFragment()
    {
    }

    private ListView playGameListView;
    private OnFragmentInteractionListener mListener;
    private Paired myPaired;

    public Paired getMyPaired()
    {
        return myPaired;
    }

    public void setMyPaired(Paired myPaired)
    {
        this.myPaired = myPaired;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_play_game, container,false);
        playGameListView = (ListView) v.findViewById(R.id.playgrid);
        PlayGameAdapter adapter = new PlayGameAdapter(getContext(), 0);
        adapter.setPaired(myPaired);
        Button frame = (Button) v.findViewById(R.id.launch_game);
        frame.setOnClickListener(new TryAction(myPaired, TLActionIdentifier.STARTGAME));
        adapter.addColor(Color.RED);
        adapter.addColor(Color.BLUE);
        adapter.addColor(Color.CYAN);
        playGameListView.setAdapter(adapter);
        return v;


    }

    public static PlayGameFragment newInstance()
    {
        PlayGameFragment fragment = new PlayGameFragment();
        return fragment;
    }





    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
            Log.d("PlayAttach","attached");
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement ContentFragListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating
     * .html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}








