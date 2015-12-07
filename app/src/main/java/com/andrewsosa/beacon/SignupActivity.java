package com.andrewsosa.beacon;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailView;
    private EditText passwordView;
    private EditText usernameView;
    private Button register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);
        
        

        emailView = (EditText) findViewById(R.id.et_email);
        passwordView = (EditText) findViewById(R.id.et_password);
        usernameView = (EditText) findViewById(R.id.et_username);
        
        register = (Button) findViewById(R.id.btn_register);
        register.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                signup(emailView.getText().toString(),
                        passwordView.getText().toString(),
                        usernameView.getText().toString());
        }
    }


    public boolean signup(String email, String password, String username) {

        final Firebase ref = new Firebase(Beacon.URL);

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Signing you up...");
        pd.show();

        final String emailx = email.trim();
        final String passwordx = password.trim();
        final String usernamex = username.trim();

        if(email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            Snackbar.make(register, "Please enter all fields.", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        ref.createUser(emailx, passwordx, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                String uid = (String) result.get("uid");
                Log.d("Register user", "Successfully created user account with key: " + uid);

                User u = new User(emailx, usernamex);
                Firebase userRef = ref.child("users").child(uid);
                //userRef.child("email").setValue(emailx);
                //userRef.child("username").setValue(usernamex);
                userRef.setValue(u);

                //startActivity(new Intent(AuthActivity.this, DispatchActivity.class));
                //finish();

            }
            @Override
            public void onError(FirebaseError firebaseError) {
                pd.hide();
                Snackbar.make(register, "Error during registration.", Snackbar.LENGTH_SHORT).show();
                Log.e("Register User", firebaseError.getMessage());
            }
        });

        return true;
    }
}