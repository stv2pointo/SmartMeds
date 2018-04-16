package com.stvjuliengmail.smartmeds.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.model.MyMed;


public class MyMedFragment extends android.support.v4.app.Fragment {

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private MyMed myMed;
    private TextView tvDosage, tvDoctor, tvDirections, tvPharmacy;

    public MyMedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_med, container, false);
        //longName = getArguments().getString("longName");
        myMed = getArguments().getParcelable("myMed");
        initializeUiComponents();
        displayTextInViews();
        return rootView;
    }

    private void initializeUiComponents(){
        tvDosage = rootView.findViewById(R.id.tvDosage);
        tvDoctor = rootView.findViewById(R.id.tvDoctor);
        tvDirections = rootView.findViewById(R.id.tvDirections);
        tvPharmacy = rootView.findViewById(R.id.tvPharmacy);
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
