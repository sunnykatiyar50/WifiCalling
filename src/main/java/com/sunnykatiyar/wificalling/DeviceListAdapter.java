package com.sunnykatiyar.wificalling;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Katiyar on 02-01-2018.
 */

public class DeviceListAdapter extends BaseAdapter {

    List<ClientObject> user_list = new ArrayList<>();
    Context context ;
    Activity activity;
    final String TAG = "Device List Adapter";

    public DeviceListAdapter(List<ClientObject> connected_devices, Context c, Activity a){
        this.user_list= connected_devices;
        this.context = c;
        this.activity=a;
    }

    private static class ViewHolder{
        TextView device_name;
        TextView ip_address;
        TextView conn_status;
        Button message_user;
        Button call_user;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
    //    Log.i(TAG," before setadapter  ");
        String hostname = "" ;
        String hostaddress = "" ;
        ViewHolder viewHolder =new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view==null)
        {
            view = inflater.inflate(R.layout.devices_listitem, parent, false);
            viewHolder.ip_address = view.findViewById(R.id.mac_id_textview);
            viewHolder.device_name = view.findViewById(R.id.device_name_textview);
            viewHolder.conn_status = view.findViewById(R.id.constatus_textview);
            viewHolder.message_user=view.findViewById(R.id.btn_message);
            viewHolder.call_user=view.findViewById(R.id.btn_call);

            ClientObject clientObject2 = user_list.get(position);
            clientObject2.position=position;
            viewHolder.device_name.setText(clientObject2.getRemoteInetAddress().toString());
            viewHolder.ip_address.setText(clientObject2.getRemoteIp());
            view.setTag(viewHolder);
         }
        else {   viewHolder = (ViewHolder) view.getTag();          }

        viewHolder.message_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        MainActivity.fragmentManager.beginTransaction()
                                .replace(R.id.user_details_container,new MessagingFragment(user_list.get(position)))
                                .commit();
            }
        });

        viewHolder.call_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction()
                        .addToBackStack("Call_Button")
                        .replace(R.id.user_details_container,new CallScreenFragment())
                        .commit();
            }
        });


        return view;



    }

    @Override
    public int getCount() {
        return user_list.size();
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

/*
class GetInfo extends AsyncTask<InetAddress, Void, Void> {

    @Override
    protected Void doInBackground(InetAddress... voids) {



        return null;
    }
}*/