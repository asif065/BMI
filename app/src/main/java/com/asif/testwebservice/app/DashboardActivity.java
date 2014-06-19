package com.asif.testwebservice.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asif.testwebservice.app.library.UserFunctions;

import java.util.HashMap;

public class DashboardActivity extends Activity {

    UserFunctions userFunctions;
    Button btnLogout;
    TextView welcomeText;
    String userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Dashboard Screen for the application
         * */
        // Check login status in database
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
            // user already logged in show databoard
            setContentView(R.layout.activity_dashboard);
            btnLogout = (Button) findViewById(R.id.btnLogout);
            welcomeText = (TextView) findViewById(R.id.welcome);
            userName = userFunctions.getUserName(getApplicationContext());
            welcomeText.setText("Hello "+userName);


            btnLogout.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    // Closing dashboard screen
                    finish();
                }
            });

        }else{
            // user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing dashboard screen
            finish();
        }
    }

}
