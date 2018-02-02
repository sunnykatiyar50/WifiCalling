package com.sunnykatiyar.wificalling;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Sunny Katiyar on 27-01-2018.
 */

public class ServerObject extends Socket {

    ServerSocket serverSocket;
    Socket remote_client;
    String remote_ip;
    int client_nmbr;
    String local_ip;
    String new_msg;
   // BufferedReader bufferedReader;
   // BufferedWriter bufferedWriter;
    String TAG = "ServerObject";
    InetAddress client_inetaddress;
   // Boolean first_msg=true;
   // MessagingFragment receive_msg_fragment;
   // ArrayList<String> messages = new ArrayList<>();

    public ServerObject(ServerSocket myServer, Socket new_client, int client_nmbr) throws IOException {
        this.serverSocket=myServer;
        this.client_nmbr=client_nmbr;
        this.remote_client=new_client;
        this.remote_ip=new_client.getInetAddress().getHostName();
        Log.e(TAG,"client_ip = "+this.remote_ip);
        this.local_ip=myServer.getInetAddress().getHostName();
        Log.e(TAG,"local_ip = "+this.local_ip);
        this.client_inetaddress = new_client.getInetAddress();
    }

}

