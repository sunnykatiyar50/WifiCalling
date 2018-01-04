package com.sunnykatiyar.wificalling;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static WifiManager wifimgr;
    public static WifiInfo wifiInfo;
    public static WifiConfiguration wificfg;
    public List<WifiConfiguration> availabledevices = new ArrayList<>();
    public List<ScanResult> paired_devices = new ArrayList<ScanResult>();
    public List<ScanResult> connected_devices = new ArrayList<ScanResult>();
    public static Context context;
    public static Activity activity;

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
        wifimgr = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        TextView conn_status_textview = findViewById(R.id.phone_conn_status);
        String network_name="";

        if (wifimgr.isWifiEnabled()) {
            wifiInfo = wifimgr.getConnectionInfo();
            network_name = wifiInfo.getSSID();
            availabledevices = wifimgr.getConfiguredNetworks();
            Log.i(TAG," got configured networks ");

            if (network_name.equals("<unknown ssid>")) {
                conn_status_textview.setText("Please connect to a Network");

                Toast.makeText(context,availabledevices.size() + " neyworks available",Toast.LENGTH_SHORT).show();
            //Toast.makeText(context,availabledevices.get(1).SSID,Toast.LENGTH_LONG);
            } else
                conn_status_textview.setText("You are Connected to " + network_name);
        }
    else   conn_status_textview.setText("Please Enable Wifi And Connect to a Network")  ;


        ListView devices_listview = findViewById(R.id.device_listview);
        Log.i(TAG," before setadapter  ");
        DeviceListAdapter deviceListAdapter = new DeviceListAdapter(availabledevices,context,activity);
        devices_listview.setAdapter(deviceListAdapter);
        Log.i(TAG," after set adapter  ");

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i ;
        if (id == R.id.devices_menuitem) {
            i= new Intent(this, MainActivity.class) ;
            startActivity(i);
        } else if (id == R.id.history_menuitem) {
                i = new Intent(this,CallHistory.class);
                startActivity(i);
        } else if (id == R.id.settings_menuitem) {
            i = new Intent(this,Settings.class);
            startActivity(i);
        } else if (id == R.id.about_menuitem) {
            i = new Intent(this,About.class);
            startActivity(i);
        } else if (id == R.id.exit_menuitem) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
