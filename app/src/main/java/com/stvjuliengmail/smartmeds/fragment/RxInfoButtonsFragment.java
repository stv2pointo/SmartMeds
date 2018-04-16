package com.stvjuliengmail.smartmeds.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.activity.RxInfoActivity;

import java.lang.ref.WeakReference;

public class RxInfoButtonsFragment extends android.support.v4.app.Fragment {
    private WeakReference<RxInfoActivity> weakActivity;
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fabSaveMyMeds;
    private Button btnInteractions;

    public RxInfoButtonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        weakActivity = new WeakReference<RxInfoActivity>((RxInfoActivity)getActivity());
        View view = inflater.inflate(R.layout.fragment_rx_info_buttons, container, false);
        fabSaveMyMeds = view.findViewById(R.id.fabSaveMyMeds);
        btnInteractions = view.findViewById(R.id.btnInteractions);
        wireUpSaveToMyMedsButton();
        wireUpInteractionsButton();
        return view;
    }

    private void wireUpSaveToMyMedsButton() {
        fabSaveMyMeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxInfoActivity activity = weakActivity.get();
                if(activity != null&& !activity.isFinishing() && !activity.isDestroyed()) {
                    activity.dieAndStartAddMed();
//               mListener.dieAndStartAddMed();
                }
            }
        });
    }

    private void wireUpInteractionsButton(){
        btnInteractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxInfoActivity activity = weakActivity.get();
                if(activity != null&& !activity.isFinishing() && !activity.isDestroyed()) {
                    activity.startInteractions();
                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
