package com.andrewsosa.beacon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        OverviewFragment.OnFragmentInteractionListener,
        GroupsFragment.OnFragmentInteractionListener,
        UpdatesFragment.OnFragmentInteractionListener{

    DrawerLayout drawer;
    BottomSheetLayout bottomSheetLayout;
    OverviewFragment overviewFragment;
    ViewPager mMainViewPager;
    //Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_feeds);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mMainViewPager.getCurrentItem() != 0) {
            FragmentManager fm = getSupportFragmentManager();
            OverviewFragment fg = (OverviewFragment) fm.findFragmentById(R.id.overviewFragment);
            fg.changeViewPagerFocus(0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_settings:
                Firebase ref = new Firebase(Beacon.URL);
                ref.unauth();
                startActivity(new Intent(this, DispatchActivity.class));
                finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void registerToolbar(Toolbar toolbar) {

        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen(GravityCompat.START))
                    drawer.openDrawer(GravityCompat.START);
            }
        });
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.toolbar_main);
        toolbar.setOnMenuItemClickListener(new ToolbarMenuListener());
        toolbar.setTitle("Beacon");
    }


    @Override
    public void onGroupSelect(String groupKey, String groupName) {

        FragmentManager fm = getSupportFragmentManager();
        OverviewFragment fragment = (OverviewFragment) fm.findFragmentById(R.id.overviewFragment);
        String chatKey = getChatKey(groupKey);

        SharedPreferences sp = getSharedPreferences(Beacon.PREFS, MODE_PRIVATE);
        sp.edit().putString(Beacon.ACTIVE_GROUP_KEY, groupKey).apply();
        sp.edit().putString(Beacon.ACTIVE_GROUP_NAME, groupName).apply();

        fragment.replaceChatFragment(chatKey);
        fragment.setTitle(groupName);

    }

    @Override
    public void registerViewPager(ViewPager viewPager) {
        mMainViewPager = viewPager;
    }


    public String getChatKey(String groupKey) {
        return groupKey;
    }

    @Override
    public void onUpdateSelect(Uri uri) {

    }

    public class ToolbarMenuListener implements Toolbar.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch(item.getItemId()) {
                case R.id.action_create:
                    MenuSheetView menuSheetView =
                            new MenuSheetView(MainActivity.this, MenuSheetView.MenuType.LIST, "Create new...",
                                    new MenuSheetView.OnMenuItemClickListener() {
                                        @Override
                                        public boolean onMenuItemClick(MenuItem item) {
                                            if(item.getItemId() == R.id.action_create_group) {
                                                startActivity(new Intent(MainActivity.this, NewGroupActivity.class));
                                                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                bottomSheetLayout.dismissSheet();
                                            }
                                            return true;

                                        }
                                    });
                    menuSheetView.inflateMenu(R.menu.sheet_create);
                    bottomSheetLayout.showWithSheetView(menuSheetView);
            }

            return true;
        }
    }
}
