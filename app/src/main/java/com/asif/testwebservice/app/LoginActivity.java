package com.asif.testwebservice.app;

/**
 * Created by asif on 4/17/14.
 */

import java.util.HashMap;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.asif.testwebservice.app.library.DatabaseHandler;
import com.asif.testwebservice.app.library.UserFunctions;


public class LoginActivity extends Activity {
    Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";

    private Context context;

    public HostAvailabilityTask havt;
    public ProgressBar bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        havt = new HostAvailabilityTask(this.getApplicationContext());
        setContentView(R.layout.login);
        bar = (ProgressBar) findViewById(R.id.progress);

        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if(havt.isOnline()) {

                    String email = inputEmail.getText().toString();
                    String password = inputPassword.getText().toString();
                    if(!isValidEmail(email)){
                        Toast toast = Toast.makeText(context, "Please insert valid email address.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    }
                    else if (password == null || password.trim().equals("")){
                        Toast toast = Toast.makeText(context, "Please insert password.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    }
                    else {
                        new LoginTask().execute(email, password);
                    }
                }
                else
                {

                    Toast toast = Toast.makeText(context, "Please connect to internet to proceed.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public boolean isValidEmail(String email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private class LoginTask extends AsyncTask<String, Void, String> {

        private JSONObject json;
        private UserFunctions userFunction;

        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                userFunction = new UserFunctions();
                json = userFunction.loginUser(params[0], params[1]);
                if (json.getString(KEY_SUCCESS) != null){
                    return json.getString(KEY_SUCCESS);
                }

            } catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(s);
            if(result == null)
            {

            }
            else
            {
                String res;
                res = result.replace("/", "");
                res = res.trim();
                if (res.length() > 0){
                    if(Integer.parseInt(res) == 1){
                        loginErrorMsg.setText("");
                        try {
                            DatabaseHandler db = new DatabaseHandler(context);
                            JSONObject json_user;
                            if(json != null && userFunction != null) {
                                json_user = json.getJSONObject("user");
                                // Clear all previous data in database
                                userFunction.logoutUser(context);
                                db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));

                                /*HashMap<String,String> user = new HashMap<String,String>();
                                user = db.getUserDetails();
                                Toast toast;
                                toast = Toast.makeText(context, user.get("name"), Toast.LENGTH_LONG);
                                toast.show();*/


                                // Launch Dashboard Screen
                                Intent dashboard = new Intent(context, DashboardActivity.class);

                                // Close all views before launching Dashboard
                                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(dashboard);

                                // Close Login Screen
                                finish();

                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    if(Integer.parseInt(res) == 0) {
                        loginErrorMsg.setText("email address and password mismatch");
                        inputEmail.setText("");
                        inputPassword.setText("");
                        bar.setVisibility(View.GONE);
                    }
                }

            }
        }
    }
}
