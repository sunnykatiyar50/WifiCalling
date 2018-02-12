package com.sunnykatiyar.wificalling;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Katiyar on 29-01-2018.
 */

public class ServerThread extends Thread {

    final String TAG = "in ServerThread : ";
    ServerObject selected_server;
    PrintWriter printWriter;
    BufferedReader bufferedReader;
    protected boolean start_chat;
    String new_msg;
    Boolean first_msg=true;
    MessagingFragment receive_msg_fragment;
    List<String> messages=new ArrayList<>();
    Handler myServerThreadHandler;
    final int UPDATE_UI=1;
    Message msgObject = Message.obtain();

    public ServerThread(ServerObject serverObject){
        this.selected_server=serverObject;
    }

    @Override
    public void run() {
        this.setStreams();
        this.startChatting();
        Log.e(TAG,"Ready for chat with - "+selected_server.remote_ip);
        myServerThreadHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                sendMsg(msg.obj.toString());
            }};
    }


    void sendMsg(String msg){
            printWriter.write(msg);
            printWriter.flush();
            Log.e(TAG,"in send msg : "+msg);
            this.messages.add("\t You  : "+msg);
    }

    void setStreams(){
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(selected_server.remote_client.getInputStream()));
            printWriter = new PrintWriter(selected_server.remote_client.getOutputStream());
            printWriter.flush();
            Log.e(TAG,"Streams set for client at "+ selected_server.remote_ip);
        } catch (IOException e) {
            Log.e(TAG,"Error Setting Streams for server  "+selected_server.remote_ip);
        }
    }

    public void startChatting(){
        while(true) {
            try {  new_msg = bufferedReader.readLine();
                    Log.e(TAG,"msg received = "+new_msg);

                if(!new_msg.equals("bye"))
                    {if(first_msg) {
                        this.receive_msg_fragment = new MessagingFragment();
                        MainActivity.fragmentManager.beginTransaction()
                                .addToBackStack("back")
                                .replace(R.id.user_details_container, this.receive_msg_fragment)
                                .commit();
                        first_msg = false;
                    }
                    new NotifyUser().startNotification(new_msg,selected_server);
                    displayMsg(new_msg);
                }
                else break;
            } catch (IOException e) {
                Log.e(TAG, "Error Reading msg");
            }finally {
                closeConnnections();
                Log.i(TAG,"Connection Closed");
            }
        }
    }

    void displayMsg(String msg) {
        Log.e(TAG,"Entered GetMsg :");
        messages.add(selected_server.remote_ip +" : "+new_msg);
        receive_msg_fragment.handler.sendEmptyMessage(UPDATE_UI);
       // MessagingFragment.msg_adapter.setNotifyOnChange(true);
    }

    void closeConnnections(){
        if(selected_server.isConnected()==true){
            try {
                sendMsg("bye");
                printWriter.flush();
                bufferedReader.close();
                printWriter.close();
                selected_server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void openFragment(){
        if(receive_msg_fragment==null){
            receive_msg_fragment=new MessagingFragment();
        }
        MainActivity.fragmentManager.beginTransaction()
                .addToBackStack("chat_window")
                .replace(R.id.user_details_container,receive_msg_fragment)
                .commit();
    }




}
