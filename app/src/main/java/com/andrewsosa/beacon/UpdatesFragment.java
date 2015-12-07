package com.andrewsosa.beacon;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdatesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatesFragment extends PagerFragment {

    private OnFragmentInteractionListener mListener;

    // Required empty public constructor
    public UpdatesFragment() {}

    public static UpdatesFragment newInstance() {
        return new UpdatesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_updates, container, false);
        return v;
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

    @Override
    public String getName() {
        return "Updates";
    }

    public interface OnFragmentInteractionListener {
        void onUpdateSelect(Uri uri);
    }
}
