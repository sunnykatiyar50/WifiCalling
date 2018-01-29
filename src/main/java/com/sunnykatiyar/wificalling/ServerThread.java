package com.sunnykatiyar.wificalling;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;

/**
 * Created by Sunny Katiyar on 29-01-2018.
 */

public class ServerThread extends Thread {

    final String TAG = "in ServerThread : ";
    ServerObject selected_server;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    public ServerThread(ServerObject serverObject){
        this.selected_server=serverObject;
    }

    @Override
    public void run() {
        setStreams();
        startChatting();
//        sendMsg( selected_server.local_ip+" : ");
    }



    void setStreams(){
        try {
            Log.e(TAG,"Setting Streams for server at "+selected_server.remote_ip);
            bufferedReader = new BufferedReader(new InputStreamReader(selected_server. socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(selected_server.socket.getOutputStream()));
            sendMsg("Server at "+selected_server.remote_ip +" is now connected");
            getMsg();
        } catch (IOException e) {
            Log.e(TAG,"Error Setting Streams for server  "+selected_server.remote_ip);
            this.closeConnnections();
        }
    }

    public void startChatting(){
        while(true){
            getMsg();
        }
    }

    void sendMsg(String msg){
        try {   bufferedWriter.write(msg);
            selected_server.messages.add(msg);
            bufferedWriter.flush();
        } catch (IOException e) {
            Log.i(TAG,"unable to send msg :");
        }

    }

    void getMsg() {
        String msg = null;
        try {
            msg = bufferedReader.readLine();
            selected_server.messages.add(msg);
        } catch (IOException e) {
            Log.e(TAG,"Error Reading msg");
        }

        if(msg.equals("xxx")){
            this.closeConnnections();
        } else{
            Toast.makeText(MainActivity.context,msg,Toast.LENGTH_SHORT).show();
        }
    }


    void closeConnnections(){
        if(selected_server.socket.isConnected()==true){
            try {
                sendMsg("xxx");
                bufferedReader.close();
                bufferedWriter.close();
                selected_server.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
