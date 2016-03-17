package com.example.megan.commlogin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;



public class Login extends AppCompatActivity {

    private EditText uwiID;
    private EditText emailView;
    private EditText passwordView;
    private RadioGroup radioGender;
    private RadioButton radioGenderButton;
    private View progressView;
    private final String URL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.content_login);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        uwiID = (EditText) findViewById(R.id.studentID);
        emailView = (EditText) findViewById(R.id.email);
        addListenerOnRadioButton();
        passwordView = (EditText) findViewById(R.id.password);
        progressView = findViewById(R.id.login_progress);
    }

    public void addListenerOnRadioButton(){
        radioGender= (RadioGroup) findViewById(R.id.radioGender);
        int selectedId= radioGender.getCheckedRadioButtonId();
        radioGenderButton= (RadioButton) findViewById(selectedId);
    }

    private void attemptLogin() {

        // Reset errors.
        uwiID.setError(null);
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        // all the isValid checks are string properties: length that email contains @ etc.
        String id= uwiID.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String gender= radioGenderButton.getText().toString();


        boolean cancel = false;
        View focusView = null;

        if(!TextUtils.isEmpty(id) ){
            uwiID.setError(getString(R.string.error_field_required));
            focusView=uwiID;
            cancel=true;
        }

        if(!isIDValid(id)){
            uwiID.setError(getString(R.string.error_invalid_id));
            focusView=uwiID;
            cancel= true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            // check database to if creds were valid on database if not return access denied
            JSONObject jObj= new JSONObject();
            try {
                //change to params
                jObj.put("id", id);
                jObj.put("email",email);
                jObj.put("pass",password);
                jObj.put("gender",gender);
               // webService(jObj);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

//    public void webService(JSONObject obj){
//
//        JsonObjectRequest jsonObjectReq= new JsonObjectRequest(Request.Method.POST, URL, obj,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.e("ME", " response from server: " + response.toString());
//
//                        if(response.toString().equals("ok")){
//                            Toast.makeText(getApplicationContext(), "priviledged page", Toast.LENGTH_LONG).show();
//                            showProgress(true);
//                        }
//                        // hideprogress dialog();
//                    }
//                },
//                    new Response.ErrorListener(){
//                        @Override
//                        public  void onErrorResponse(VolleyError error){
//                            // hide progress dialog
//                            showProgress(false);
//                            VolleyLog.d("ME", "Error: "+error.getMessage());
//                            Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_LONG);
//                        }
//
//
//
//
//        });
//
//
//
//    }

    private boolean isIDValid(String uwiID){
        return uwiID.length()>7;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

//           // mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            //mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
           // mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

}
