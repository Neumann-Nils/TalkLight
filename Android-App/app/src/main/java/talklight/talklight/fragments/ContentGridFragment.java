package talklight.talklight.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.*;

import backend.LocalLogic;
import talklight.R;

import talklight.talklight.adapters.FrontGridAdapter;

import talklight.talklight.buttons.PlayGameButton;
import talklight.talklight.control.TLHueController;
import talklight.talklight.interfaces.Myself;
import talklight.talklight.interfaces.NotPairedException;
import talklight.talklight.interfaces.TLActionIdentifier;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContentFragListener} interface
 * to handle interaction events.
 * Use the {@link ContentGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentGridFragment extends Fragment
{
    private static final String ARG_PARAM1 = "id";

    private int mId;

    private Myself self;

    private ListView frontListView;

    private ContentFragListener mListener;

    private Timer timer;

    View v;

    TLHueController controller;


    public ContentGridFragment()
    {
        // Required empty public constructor
    }

    public void setSelf(Myself self)
    {
        this.self = self;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContentGridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContentGridFragment newInstance(int mId)
    {
        ContentGridFragment fragment = new ContentGridFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, mId);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mId = getArguments().getInt(ARG_PARAM1);

        }
    }

    public List<TLActionIdentifier> tryConnect()
    {
        List<TLActionIdentifier> actions = new ArrayList<>();

        actions.add(TLActionIdentifier.GOODNIGHT);
        actions.add(TLActionIdentifier.DINNER);
        actions.add(TLActionIdentifier.ALERT);
        actions.add(TLActionIdentifier.STARTGAME);

        return actions;
    }

    public void init()
    {
        Log.d("INIT", "initialized");
        if(timer != null)
        {
            timer.cancel();
        }
        List<TLActionIdentifier> actions = tryConnect();


        frontListView = (ListView) v.findViewById(R.id.grid);
        final FrontGridAdapter adapter = new FrontGridAdapter(getContext(), 0);


        adapter.setMyActions(actions);
        adapter.setMyPaired(controller);

        frontListView.setAdapter(adapter);
        frontListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id)
                    {
                        TLActionIdentifier action = (TLActionIdentifier)
                                adapter.getItem(position);
                        if(action == TLActionIdentifier.STARTGAME)
                        {
                            onPlayButtonPressed();
                        }
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_content_grid, container, false);

        Log.d("ID", mId + " is the ID");
        (new Thread(){
            @Override
            public void run() {
                controller = new TLHueController(mId);
                getActivity().runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        init();
                    }
                });

            }
        }).start();



        return v;
    }



    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof ContentFragListener)
        {
            mListener = (ContentFragListener) context;
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



    public void onPlayButtonPressed()
    {
        PlayGameFragment frag = PlayGameFragment.newInstance();
        frag.setMyPaired(controller);

        final android.support.v4.app.FragmentTransaction ft =
                getActivity().getSupportFragmentManager().beginTransaction();
        if(frag.isDetached())
        {
            Log.d("beforeTrans", "Trans starting");

        }
        ft.replace(R.id.contentFragment,frag, "NewFragmentTag2");
        ft.commit();
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
    public interface ContentFragListener
    {
        // TODO: Update argument type and name
        void onContentFragmentInteraction();
    }
}
