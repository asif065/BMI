package com.asif.testwebservice.app;

/**
 * Created by asif on 4/22/14.
 */

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.asif.testwebservice.app.library.UserFunctions;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {
    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;
    private UserFunctions userFunctions;

    public CustomDrawerAdapter(Context context, int layoutResourceID,
                               List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
        userFunctions = new UserFunctions();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view
                    .findViewById(R.id.drawer_itemName);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

            drawerHolder.spinner = (Spinner) view
                    .findViewById(R.id.drawerSpinner);

            drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);

            drawerHolder.headerLayout = (LinearLayout) view
                    .findViewById(R.id.headerLayout);
            drawerHolder.itemLayout = (LinearLayout) view
                    .findViewById(R.id.itemLayout);
            drawerHolder.spinnerLayout = (LinearLayout) view
                    .findViewById(R.id.spinnerLayout);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

        if (dItem.isSpinner()) {
            drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.spinnerLayout.setVisibility(LinearLayout.VISIBLE);

            List<SpinnerItem> userList = new ArrayList<SpinnerItem>();

            if (userFunctions.doesDatabaseExist(context)){
                if (userFunctions.isUserLoggedIn(context)){
                    userList.add(new SpinnerItem(R.drawable.user1, userFunctions.getUserName(context), userFunctions.getUserEmail(context)));
                }
            }

            /*userList.add(new SpinnerItem(R.drawable.user1, "Asif Akter",
                    "moishawon@gmail.com"));*/

            userList.add(new SpinnerItem(R.drawable.user2, "Shawon",
                    "shawon065@gmail.com"));

            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context,
                    R.layout.custom_spinner_item, userList);

            drawerHolder.spinner.setAdapter(adapter);

            drawerHolder.spinner
                    .setOnItemSelectedListener(new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent,
                                                   View view, int pos, long id) {

                            Log.i("test","onItemSelected");
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub

                        }
                    });

        } else if (dItem.getTitle() != null) {
            drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.title.setText(dItem.getTitle());

        } else {

            drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);

            drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                    dItem.getImgResID()));
            drawerHolder.ItemName.setText(dItem.getItemName());

        }
        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName, title;
        ImageView icon;
        LinearLayout headerLayout, itemLayout, spinnerLayout;
        Spinner spinner;
    }

}

