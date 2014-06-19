package com.asif.testwebservice.app.library;

/**
 * Created by asif on 4/17/14.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.content.ContextWrapper;

public class UserFunctions {

    private JSONParser jsonParser;

    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://asifakter.com/gpsdata/";
    private static String registerURL = "http://asifakter.com/gpsdata/";

    private static String login_tag = "login";
    private static String register_tag = "register";

    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }

    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String name, String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }

    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }

    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }

    /**
     * Function
     * @param context
     * @return user name
     */
    public String getUserName(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        HashMap<String, String> user = new HashMap<String, String>();
        user = db.getUserDetails();
        return user.get("name");
    }

    /**
     * Function
     * @param context
     * @return user's email address
     */
    public String getUserEmail(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        HashMap<String, String> user = new HashMap<String, String>();
        user = db.getUserDetails();
        return user.get("email");
    }

    /**
     * Check if the database exist
     *
     * @return true if it exists, false if it doesn't
     */
    public boolean doesDatabaseExist(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        return db.doesDatabaseExist(context);
    }

}
