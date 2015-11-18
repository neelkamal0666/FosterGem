package com.fostergem.home;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.fostergem.CreateFragment;
import com.fostergem.DrawerItemCustomAdapter;
import com.fostergem.HelpFragment;
import com.fostergem.ObjectDrawerItem;
import com.fostergem.R;
import com.fostergem.ReadFragment;

/**
 * Created by Neelkamal on 02/11/15.
 */
public class HomeActivity extends Activity{
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        // for proper titles

        // initialize properties
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // list the drawer items
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[3];

        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_action_copy, "Create");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_action_refresh, "Read");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_action_share, "Help");

        // Pass the folderData to our ListView adapter
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);

        // Set the adapter for the list view
        mDrawerList.setAdapter(adapter);

        // set the item click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


    }

    // navigation drawer click listener
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        // update the main content by replacing fragments

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new CreateFragment();
                break;
            case 1:
                fragment = new ReadFragment();
                break;
            case 2:
                fragment = new HelpFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            // error in creating fragment
            //Log.e("MainActivity", "Error in creating fragment");
        }
    }

}
