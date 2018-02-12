package com.sunnykatiyar.wificalling;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sunnykatiyar.wificalling.MainActivity.context;

/**
 * Created by Sunny Katiyar on 28-01-2018.
 */

public class MessagingFragment extends Fragment {

    String TAG = "Msg Fragment : ";
    ClientObject selected_client;
    ClientThread clientThread;
    ServerThread serverThread;
    int client_nmbr;
    Handler handler;
    public static MessageAdapter msg_adapter;
    public List<String> all_messages;

    ListView msg_listview;
    EditText editText;
    String msg;
    final int SEND_NEW_MESSAGE=1;
    Message message;
    public MessagingFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public MessagingFragment(ClientObject c,int i) {
        this.selected_client=c;
        this.client_nmbr=i;
        all_messages=new ArrayList<>();
        Log.e(TAG,"clientobject local ip is :"+selected_client.local_ip);
   }
/*
    @SuppressLint("ValidFragment")
    public MessagingFragment(ServerThread s) {
        this.serverThread=new ServerThread(s.selected_server);
       // this.messages=new ArrayList<>();
        //messages.removeAll(messages);
        this.selected_client = remote_clients.get(thread_num);
        Log.e(TAG,"clientobject local ip is :"+selected_client.local_ip);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_layout, container, false);

        Button cancel_button = view.findViewById(R.id.cancel_btn);
        Button send_button = view.findViewById(R.id.send_btn);
        Button clear_button = view.findViewById(R.id.clear_btn);
        editText = view.findViewById(R.id.msg_edittext);
        msg_listview = view.findViewById(R.id.msg_list_listview);

        clientThread=new ClientThread(selected_client,client_nmbr,this);
        clientThread.start();
        msg_adapter = new MessageAdapter(all_messages);
        msg_listview.setAdapter(msg_adapter);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
                msg_adapter.notifyDataSetChanged();
                Log.e(TAG, "in Handler");
            }
        };

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onclick :");
                msg=editText.getText().toString();
                all_messages.add("You : "+msg+"/t");
                msg_adapter.notifyDataSetChanged();
                message = new Message();
                message.obj=""+msg;
                clientThread.myClientThreadHandler.sendMessage(message);
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
/*
    public void startThread(){
        this.clientThread.start();
        Log.i(TAG,"Thread started for client "+client_nmbr);
    }
*/
}
