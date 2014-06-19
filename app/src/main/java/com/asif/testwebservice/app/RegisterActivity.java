package com.asif.testwebservice.app;

/**
 * Created by asif on 4/17/14.
 */

import org.json.JSONException;
import org.json.JSONObject;

import com.asif.testwebservice.app.library.DatabaseHandler;
import com.asif.testwebservice.app.library.UserFunctions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    Button btnRegister;
    Button btnLinkToLogin;
    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword;
    TextView registerErrorMsg;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";

    public HostAvailabilityTask havt;

    public ProgressBar bar;

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        havt = new HostAvailabilityTask(this.getApplicationContext());
        context = this.getApplicationContext();
        setContentView(R.layout.register);

        // Importing all assets like buttons, text fields
        inputFullName = (EditText) findViewById(R.id.registerName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);

        bar = (ProgressBar) findViewById(R.id.progress);

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(havt.isOnline()) {
                    String name = inputFullName.getText().toString();
                    String email = inputEmail.getText().toString();
                    String password = inputPassword.getText().toString();
                    new RegisterTask().execute(name, email, password);
                    //UserFunctions userFunction = new UserFunctions();
                    //JSONObject json = userFunction.registerUser(name, email, password);

                    // check for login response
                    /*try {
                        if (json.getString(KEY_SUCCESS) != null) {
                            registerErrorMsg.setText("");
                            String res = json.getString(KEY_SUCCESS);
                            if (Integer.parseInt(res) == 1) {
                                // user successfully registred
                                // Store user details in SQLite Database
                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                JSONObject json_user = json.getJSONObject("user");

                                // Clear all previous data in database
                                userFunction.logoutUser(getApplicationContext());
                                db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
                                // Launch Dashboard Screen
                                Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                                // Close all views before launching Dashboard
                                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(dashboard);
                                // Close Registration Screen
                                finish();
                            } else {
                                // Error in registration
                                registerErrorMsg.setText("Error occured in registration");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
                else {
                    Toast toast = Toast.makeText(context, "Please connect to internet to proceed", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                // Close Registration View
                finish();
            }
        });
    }

   private class RegisterTask extends AsyncTask<String, Void, String>{
       UserFunctions userFunction;
       JSONObject json;

       @Override
       protected void onPreExecute() {
           bar.setVisibility(View.VISIBLE);
       }

       @Override
       protected String doInBackground(String... params) {

           try {
               userFunction = new UserFunctions();
               json = userFunction.registerUser(params[0], params[1], params[2]);
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

           if(result == null){

           }
           else{
               String res;
               res = result.replace("/", "");
               res = res.trim();
               if (res.length() > 0){

                   registerErrorMsg.setText("");

                   if(Integer.parseInt(res) == 1){

                       // user successfully registred
                       // Store user details in SQLite Database
                       DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                       try {
                           if(json != null && userFunction != null) {
                               JSONObject json_user = json.getJSONObject("user");
                               // Clear all previous data in database
                               userFunction.logoutUser(getApplicationContext());
                               db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
                               // Launch Dashboard Screen
                               Intent dashboard = new Intent(context, DashboardActivity.class);
                               // Close all views before launching Dashboard
                               dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               startActivity(dashboard);
                               // Close Registration Screen
                               finish();
                           }
                       } catch (JSONException e){
                           e.printStackTrace();
                       }

                   }
                   if(Integer.parseInt(res) == 0) {
                       registerErrorMsg.setText("Error occurred in registration");
                       inputEmail.setText("");
                       inputPassword.setText("");
                       bar.setVisibility(View.GONE);
                   }
               }

           }
       }
   }
}
