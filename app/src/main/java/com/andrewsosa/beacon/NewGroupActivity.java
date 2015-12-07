package com.andrewsosa.beacon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class NewGroupActivity extends AppCompatActivity {

    Toolbar mToolbar;
    EditText mGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mGroupName = (EditText) findViewById(R.id.et_groupname);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_new_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                saveNewGroup();
                break;
        }

        return true;
    }

    private void saveNewGroup() {
        String name = mGroupName.getText().toString();
        Firebase groupsRef = new Firebase(Beacon.URL).child("groups");
        Firebase newGroup = groupsRef.push();
        newGroup.setValue(new GroupModel(newGroup.getKey(), name));

        HashMap<String, Object> members = new HashMap<>();
        members.put(groupsRef.getAuth().getUid(), true);

        newGroup.child("members").updateChildren(members);

        finish();
    }
}
