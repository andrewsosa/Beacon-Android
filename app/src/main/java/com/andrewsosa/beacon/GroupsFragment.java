package com.andrewsosa.beacon;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupsFragment extends PagerFragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Required empty public constructor
    public GroupsFragment() {}

    public static GroupsFragment newInstance() {
        return new GroupsFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Firebase groupsRef = new Firebase(Beacon.URL).child("groups");

        mAdapter =
            new FirebaseRecyclerAdapter<GroupModel, GroupViewHolder>(GroupModel.class,
                    R.layout.tile_group, GroupViewHolder.class, groupsRef) {
                @Override
                public void populateViewHolder(GroupViewHolder viewHolder, final GroupModel group, int position) {
                    viewHolder.header.setText(group.getName());

                    String subheader = ""+group.getPopulation() + " members";
                    viewHolder.subheader.setText(subheader);

                    viewHolder.tile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onGroupSelect(group.getKey(), group.getName());
                            //Snackbar.make(mRecyclerView, group.getKey(), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            };

        mRecyclerView.setAdapter(mAdapter);
    }
    
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_groups, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
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
        return "Groups";
    }

    public interface OnFragmentInteractionListener {
        void onGroupSelect(String groupKey, String groupName);
    }


    public static class GroupViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public View tile;
        public TextView header;
        public TextView subheader;
        public ImageView icon;
      
        // Constructor
        public GroupViewHolder(View v) {
            super(v);
            tile = v.findViewById(R.id.tile);
            header = (TextView) v.findViewById(R.id.tv_tile_header);
            subheader = (TextView) v.findViewById(R.id.tv_tile_subheader);
        }
    }
}
