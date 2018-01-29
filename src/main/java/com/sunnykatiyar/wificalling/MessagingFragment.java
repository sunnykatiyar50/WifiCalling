package com.sunnykatiyar.wificalling;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import static com.sunnykatiyar.wificalling.MainActivity.remote_client_thread;
import static com.sunnykatiyar.wificalling.MainActivity.context;

/**
 * Created by Sunny Katiyar on 28-01-2018.
 */

public class MessagingFragment extends Fragment {

    ClientObject selected_client;
    String TAG ="Msg Fragment : ";

    public MessagingFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    public MessagingFragment(ClientObject c){
        this.selected_client=c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_layout,container,false);
        final EditText editText = view.findViewById(R.id.msg_edittext);
        Button cancel_button = view.findViewById(R.id.cancel_btn);
        Button send_button = view.findViewById(R.id.send_btn);
        Button clear_button = view.findViewById(R.id.clear_btn);
        ListView msg_list = view.findViewById(R.id.msg_list_listview);

        remote_client_thread[selected_client.position] = new ClientThread(selected_client);
        remote_client_thread[selected_client.position].start();
       ArrayAdapter<String> msg_adapter =new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,android.R.id.text1,selected_client.messages);

       msg_list.setAdapter(msg_adapter);
       msg_adapter.setNotifyOnChange(true);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                       String msg=editText.getText().toString();
                        send_msg(msg);
            }
        });
        return view;
    }

    public void send_msg(String msg){
        remote_client_thread[selected_client.position].sendMsg(msg);
    }

}
