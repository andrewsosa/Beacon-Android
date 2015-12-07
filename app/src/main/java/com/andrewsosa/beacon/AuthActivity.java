package com.andrewsosa.beacon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.Map;

public class AuthActivity extends AppCompatActivity{

    EditText mEmailText;
    Button mContinue;
    Firebase ref;
    String email;
    String emailKey = "prefs_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);




        ref = new Firebase(Beacon.URL);

        mEmailText = (EditText) findViewById(R.id.et_email_init);
        mContinue = (Button) findViewById(R.id.btn_continue);
        final NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        mEmailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nestedScrollView.fullScroll(View.FOCUS_DOWN);
            }

            @Override
            public void afterTextChanged(Editable s) {
                email = s.toString();
            }
        });

        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences(Beacon.PREFS, MODE_PRIVATE);
                if (sp.contains(emailKey) && sp.getString(emailKey, "").equals(email)) {
                    launchLogin();
                } else {
                    launchLogin();
                }

            }
        });

    }

    public void launchLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("email", email);

        String transitionName = getString(R.string.transition_login_email);

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        mEmailText,   // The view which starts the transition
                        transitionName    // The transitionName of the view we’re transitioning to
                );
        ActivityCompat.startActivity(this, intent, options.toBundle());
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void launchSignup() {

    }




}
