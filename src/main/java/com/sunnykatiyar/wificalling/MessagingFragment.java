package com.sunnykatiyar.wificalling;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import static com.sunnykatiyar.wificalling.MainActivity.remote_client_thread;
import static com.sunnykatiyar.wificalling.MainActivity.context;
import static com.sunnykatiyar.wificalling.MainActivity.remote_clients;

/**
 * Created by Sunny Katiyar on 28-01-2018.
 */

public class MessagingFragment extends Fragment {

    ClientObject selected_client;
    ClientThread clientThread;
    int thread_num;
    String TAG = "Msg Fragment : ";
    List<String> messages;
    public static ArrayAdapter<String> msg_adapter;
    public MessagingFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public MessagingFragment(List Msgs,ClientThread c) {
        //      thread_num=c;
        this.messages=Msgs;
        this.clientThread =c;
        this.selected_client = remote_clients.get(thread_num);
        Log.e(TAG,"clientobject local ip is :"+selected_client.local_ip);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_layout, container, false);
        final EditText editText = view.findViewById(R.id.msg_edittext);

        Button cancel_button = view.findViewById(R.id.cancel_btn);
        Button send_button = view.findViewById(R.id.send_btn);
        Button clear_button = view.findViewById(R.id.clear_btn);
        ListView msg_list = view.findViewById(R.id.msg_list_listview);

//        remote_client_thread[selected_client.position] = new ClientThread(selected_client);
//        remote_client_thread[selected_client.position].start();


        clientThread = DeviceListFragment.clientThreads[thread_num];
//        clientThread = new ClientThread(selected_client);
  //      clientThread.start();
        msg_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1,this.messages);
        msg_list.setAdapter(msg_adapter);
        msg_adapter.setNotifyOnChange(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"onclick :");
/*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        clientThread.sendMsg(editText.getText().toString(),msg_adapter);
                    }
                });
  */            clientThread.sendMsg(editText.getText().toString());
                msg_adapter.setNotifyOnChange(true);
                editText.setText("");
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.popBackStackImmediate();
            }
        });

        return view;
    }


}
