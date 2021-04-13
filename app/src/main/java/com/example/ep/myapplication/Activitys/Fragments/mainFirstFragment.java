package com.example.ep.myapplication.Activitys.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ep.myapplication.Activitys.Activitys.MainActivity;
import com.example.ep.myapplication.Activitys.Adapters.StateAdapter;
import com.example.ep.myapplication.Activitys.Model.State;
import com.example.ep.myapplication.Activitys.Services.DataService;
import com.example.ep.myapplication.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mainFirstFragment.OnFirstFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mainFirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mainFirstFragment extends Fragment  {  // first fragment - listview with all states
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private StateAdapter stateAdapter;
    private RecyclerView theRecycleView;
    private ArrayList<State> allstates;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFirstFragmentInteractionListener mListener;

    public mainFirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mainFirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static mainFirstFragment newInstance(String param1, String param2) {
        mainFirstFragment fragment = new mainFirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final DataService ds = new DataService();
        final View v = inflater.inflate(R.layout.fragment_main_first, container, false);
        theRecycleView = (RecyclerView) v.findViewById(R.id.RecycleView_1);
        allstates = ds.getArrState();

        stateAdapter = new StateAdapter(getContext(),allstates);
        stateAdapter.setOnItemClickListener(new StateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position,View view,State state)
            {
                Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                view.startAnimation(hyperspaceJumpAnimation);
                MainActivity ma =(MainActivity) getContext();
                ma.LoadSecFragment(state);
            }
        });
        theRecycleView.setHasFixedSize(true);
        theRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        theRecycleView.setAdapter(stateAdapter);
        //stateAdapter = new StateAdapter(getActivity(), allstates);


        EditText inputSearch = v.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {
                ArrayList<State> filteredList =new ArrayList<>();
                for(State stateItem:allstates)
                {
                    if(stateItem.getName().toLowerCase().contains(cs.toString()))
                    {
                        filteredList.add(stateItem);
                    }
                }
                ((StateAdapter)theRecycleView.getAdapter()).filterList(filteredList);
                // When user changed the Text

                    //theAdapter = new StateAdapter(getActivity(), theAdapter.custumeFilter(allstates, cs.toString()));
                    //theRecycleView.setAdapter(theAdapter);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFirstFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFirstFragmentInteractionListener) {
            mListener = (OnFirstFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFirstFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFirstFragmentInteraction(Uri uri);
    }
}
