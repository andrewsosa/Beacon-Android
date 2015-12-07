package com.andrewsosa.beacon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class PagerAdapter extends android.support.v4.view.PagerAdapter {
    FragmentManager fragmentManager;
    List<PagerFragment> fragments;

    public PagerAdapter(FragmentManager fragmentManager, List<PagerFragment> fragments) {
        this.fragmentManager = fragmentManager;
        this.fragments = fragments;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        assert(0 <= position && position < fragments.size());
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.remove(fragments.get(position));
        trans.commit();
        fragments.remove(position);
    }

    @Override
    public Fragment instantiateItem(ViewGroup container, int position){
        Fragment fragment = getItem(position);
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.add(container.getId(),fragment,"fragment:"+position);
        trans.commit();
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object fragment) {
        return ((Fragment) fragment).getView() == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getName();
    }

    public Fragment getItem(int position){
        assert(0 <= position && position < fragments.size());
        return fragments.get(position);
    }

    public void replaceChatKey(String chatKey) {
        ((ChatFragment)fragments.get(1)).updateChatKey(chatKey);
    }
}