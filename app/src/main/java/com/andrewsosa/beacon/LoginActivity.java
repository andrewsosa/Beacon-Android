package com.andrewsosa.beacon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailView;
    private EditText passwordView;
    private Firebase ref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ref = new Firebase(Beacon.URL);
        emailView = (EditText) findViewById(R.id.et_email);
        passwordView = (EditText)findViewById(R.id.et_password);

        Button login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(this);

        Intent i = getIntent();
        emailView.setText(i.getStringExtra("email"));
        
    }

    
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                login(emailView.getText().toString(), passwordView.getText().toString());
        }
    }

    public boolean login(String email, String password) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Logging you in...");
        pd.show();

        email = email.trim();
        password = password.trim();

        if(email.isEmpty() || password.isEmpty()) {
            Snackbar.make(emailView, "Please enter all fields.", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        // Create a handler to handle the result of the authentication
        Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                // Authenticated successfully with payload authData
                startActivity(new Intent(LoginActivity.this, DispatchActivity.class));
                finish();
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                pd.hide();
                Snackbar.make(emailView, "Error during login.", Snackbar.LENGTH_SHORT).show();
                Log.e("Register User", firebaseError.getMessage());
            }
        };

        // Or with an email/password combination
        ref.authWithPassword(email, password, authResultHandler);

        return true;
    }
}