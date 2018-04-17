package com.stvjuliengmail.smartmeds.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.activity.RxInfoActivity;
import com.stvjuliengmail.smartmeds.model.MyMed;

import java.lang.ref.WeakReference;


public class MyMedFragment extends android.support.v4.app.Fragment {
    private WeakReference<RxInfoActivity> weakActivity;
    private OnFragmentInteractionListener mListener;
    private View rootView;
    private MyMed myMed;
    private TextView tvDosage, tvDoctor, tvDirections, tvPharmacy;
    private FloatingActionButton fabEdit, fabDelete;

    public MyMedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        weakActivity = new WeakReference<RxInfoActivity>((RxInfoActivity)getActivity());
        rootView = inflater.inflate(R.layout.fragment_my_med, container, false);
        //longName = getArguments().getString("longName");
        myMed = getArguments().getParcelable("myMed");
        initializeUiComponents();
        wireUpClicks();
        displayTextInViews();
        return rootView;
    }

    private void initializeUiComponents(){
        tvDosage = rootView.findViewById(R.id.tvDosage);
        tvDoctor = rootView.findViewById(R.id.tvDoctor);
        tvDirections = rootView.findViewById(R.id.tvDirections);
        tvPharmacy = rootView.findViewById(R.id.tvPharmacy);
        fabEdit = rootView.findViewById(R.id.fabEdit);
        fabDelete = rootView.findViewById(R.id.fabDelete);
    }

    private void wireUpClicks(){
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxInfoActivity activity = weakActivity.get();
                if(activity != null&& !activity.isFinishing() && !activity.isDestroyed()) {
                    activity.dieAndStartAddMed();
                }
            }
        });
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxInfoActivity activity = weakActivity.get();
                if(activity != null&& !activity.isFinishing() && !activity.isDestroyed()) {
                    activity.deleteMed();
                }
            }
        });
    }

    private void displayTextInViews(){
        tvDirections.setText(myMed.getDirections());
        tvDoctor.setText(myMed.getDoctor());
        tvDosage.setText(myMed.getDosage());
        tvPharmacy.setText(myMed.getPharmacy());
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
