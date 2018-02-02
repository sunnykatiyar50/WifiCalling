package com.sunnykatiyar.wificalling;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static WifiManager wifimgr;
    public static WifiInfo wifiInfo;
    public static WifiConfiguration wificfg;
    public static List<WifiConfiguration> available_devices = new ArrayList<>();
    public static List<InetAddress> connected_devices = new ArrayList<>();
    public static Context context;
    public static Activity activity;
    public static int myDefaultPort = 44447;
    public static String myStringIpAddress;
    public static int myIntIpAddress;
    public static DeviceListFragment deviceListFragment ;
    public static AboutFragment aboutFragment = new AboutFragment();
    public static SettingsFragment settingsFragment = new SettingsFragment();
    public static FragmentManager fragmentManager;
    public static List<ClientObject> remote_clients;
    public static ClientThread[] remote_client_thread;
    public static boolean isServerRunning=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String TAG ="MainActivity" ;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        context = this.getApplicationContext();
        fragmentManager = getFragmentManager();
        refreshlist();


    }

    protected void refreshlist(){
        deviceListFragment =  new DeviceListFragment();
        fragmentManager.beginTransaction()
                        .add(R.id.user_details_container, deviceListFragment)
                        .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            fragmentManager.beginTransaction().replace(R.id.user_details_container,new SettingsFragment()).commit();
        }else  if (id == R.id.action_refresh){
                refreshlist();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.devices_menuitem) {
            fragmentManager.beginTransaction().replace(R.id.user_details_container, new DeviceListFragment()).commit();
        } else if (id == R.id.history_menuitem) {
            fragmentManager.beginTransaction().replace(R.id.user_details_container,new CallHistoryFragment()).commit();
        } else if (id == R.id.settings_menuitem) {
            fragmentManager.beginTransaction().replace(R.id.user_details_container,settingsFragment).commit();
        } else if (id == R.id.about_menuitem) {
            fragmentManager.beginTransaction().replace(R.id.user_details_container,aboutFragment).commit();
        } else if (id == R.id.myprofile_menuitem) {
            fragmentManager.beginTransaction().replace(R.id.user_details_container, new MyProfileFragment()).commit();
        }   else if (id == R.id.exit_menuitem) {
                            this.finishActivity(0);
                            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
