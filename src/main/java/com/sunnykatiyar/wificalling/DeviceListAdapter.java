package com.sunnykatiyar.wificalling;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.net.wifi.WifiConfiguration.Status.DISABLED;
import static android.net.wifi.WifiConfiguration.Status.ENABLED;


/**
 * Created by Sunny Katiyar on 02-01-2018.
 */

public class DeviceListAdapter extends BaseAdapter {

    List<WifiConfiguration> available_devices = new ArrayList<>();
    Context context ;
    Activity activity;
    final String TAG = "Device List Adapter";
    public DeviceListAdapter(List<WifiConfiguration> connected_devices, Context c, Activity a){
        this.available_devices= connected_devices;
        this.context = c;
        this.activity=a;
    }

    private static class ViewHolder{
        TextView device_name;
        TextView mac_address;
        TextView conn_status;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.i(TAG," before setadapter  ");
        ViewHolder viewHolder =new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view==null){
            view = inflater.inflate(R.layout.devices_listitem_activity,parent,false );
            viewHolder.mac_address = view.findViewById(R.id.mac_id_textview);
            viewHolder.device_name = view.findViewById(R.id.device_name_textview);
            viewHolder.conn_status = view.findViewById(R.id.constatus_textview);

            WifiConfiguration wifiConfiguration = available_devices.get(position);

            viewHolder.device_name.setText(wifiConfiguration.SSID.toString());
            viewHolder.mac_address.setText(MainActivity.wifimgr.getConnectionInfo().getMacAddress());
            Log.i(TAG," in getview if  "+ wifiConfiguration.SSID.toString());
            if(wifiConfiguration.status==ENABLED)
                    viewHolder.conn_status.setText("Available");
            else if (wifiConfiguration.status==DISABLED)
                viewHolder.conn_status.setText("Not Available");
            else  viewHolder.conn_status.setText("Connected");

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
            Log.i(TAG," in getview else  ");
        }

        return view;
    }

    @Override
    public int getCount() {
        return available_devices.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
