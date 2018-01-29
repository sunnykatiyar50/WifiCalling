package com.sunnykatiyar.wificalling;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static com.sunnykatiyar.wificalling.DeviceListFragment.deviceListAdapter;

/**
* Created by Sunny Katiyar on 28-01-2018.
*/
class SearchDevices extends AsyncTask<Void,Void ,Void> {
    final String TAG = "in AsyncTask";
    String access_point_ip ;

    public SearchDevices(String ip){
        this.access_point_ip=ip;
        Log.e(TAG, " router ip = "+this.access_point_ip);
    }

    @Override
    protected Void doInBackground(Void...voids) {
        //Log.e(TAG, " router ip = "+access_point_ip);
        String[] parts = this.access_point_ip.split("\\.",4);
        String ipSubString = parts[0]+"."+parts[1]+"."+parts[2]+".";

        InetAddress i;
        MainActivity.remote_clients = new ArrayList<>();
        Socket test_socket;
        String test_ip;
        int count=0;

        for (int j = 0; j < 256; j++) {
            try {   test_ip= ipSubString+j;
                    i = InetAddress.getByName(test_ip);
                   // Log.e(TAG, " checking ip " + i.toString());
                        if (i.isReachable(30))
                        {   test_socket = new Socket(i,MainActivity.myDefaultPort);
                         if(test_socket!=null)
                            {
                                MainActivity.remote_clients
                                            .add(new ClientObject(test_socket,test_ip,MainActivity.myDefaultPort,i,count++));
                                Log.e(TAG, " Listener found at " + test_ip);
                            }
                        }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                 Log.e(TAG, " No. of Connected Devices = " + MainActivity.remote_clients.size());
        return null;
    }

        @Override
        protected void onPostExecute (Void aVoid){
            deviceListAdapter.notifyDataSetChanged();
            //deviceListAdapter = new DeviceListAdapter(connected_devices,context,activity);
            super.onPostExecute(aVoid);
        }

}
