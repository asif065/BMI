package com.asif.testwebservice.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by asif on 4/19/14.
 */
public class HostAvailabilityTask {

    private Context context;
    public HostAvailabilityTask(Context context)
    {
           this.context = context;
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnected()){
            return true;
        }
        return false;
    }
}
