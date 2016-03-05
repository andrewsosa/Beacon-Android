package com.andrewsosa.beacon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabs;
    private PagerAdapter mAdapter;
    private FloatingActionButton mFAB;

    // Required Empty Constructor
    public OverviewFragment() {}


    public static OverviewFragment newInstance() {
        return new OverviewFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener.registerToolbar(mToolbar);
        mListener.registerViewPager(mViewPager);

        mToolbar.setTitle("Beacon");

        String chatKey = getContext()
                .getSharedPreferences(Beacon.PREFS, Context.MODE_PRIVATE)
                .getString(Beacon.ACTIVE_GROUP_KEY, null);

        List<PagerFragment> fragments = new ArrayList<>();
        fragments.add(GroupsFragment.newInstance());
        fragments.add(ChatFragment.newInstance(chatKey));

        mAdapter = new PagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new OnPageChangeListener());
        mTabs.setupWithViewPager(mViewPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_overview, container, false);

        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
        mTabs = (TabLayout) v.findViewById(R.id.tabs);
        mViewPager = (ViewPager) v.findViewById(R.id.viewpager);
        mFAB = (FloatingActionButton) v.findViewById(R.id.fab);

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void replaceChatFragment(String chatKey) {
        mAdapter.replaceChatKey(chatKey);
        changeViewPagerFocus(1);
    }

    public void changeViewPagerFocus(int position) {
        mViewPager.setCurrentItem(position);
    }

    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void registerToolbar(Toolbar toolbar);
        void registerViewPager(ViewPager viewPager);
    }

    public class OnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            if(position == 0) {
                mToolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
                mListener.registerToolbar(mToolbar); // Resets the toolbar to init view
            } else {

                // Navigation updoots
                mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeViewPagerFocus(0);
                    }
                });

                // Menu updoots
                mToolbar.getMenu().clear();

                // Name updoots
                String groupName = getContext()
                        .getSharedPreferences(Beacon.PREFS, Context.MODE_PRIVATE)
                        .getString(Beacon.ACTIVE_GROUP_NAME, "");
                mToolbar.setTitle(groupName);
            }

            mTabs.getTabAt(position).select();
        }


    }

}
