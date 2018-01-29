package com.sunnykatiyar.wificalling;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Sunny Katiyar on 27-01-2018.
 */

public class ServerObject extends Socket {

    ServerSocket serverSocket;
    Socket socket;
    String remote_ip;
    int client_nmbr;
    String local_ip;
    String TAG = "ServerObject";
    InetAddress client_inetaddress;
    ArrayList<String> messages = new ArrayList<>();

    public ServerObject(ServerSocket myServer, Socket new_client, int client_nmbr) throws IOException {
        this.serverSocket=myServer;
        this.client_nmbr=client_nmbr;
        this.socket=new_client;
        this.remote_ip=new_client.getInetAddress().getHostName();
        Log.e(TAG,"client_ip = "+this.remote_ip);
        this.local_ip=myServer.getInetAddress().getHostName();
        Log.e(TAG,"local_ip = "+this.local_ip);
        this.client_inetaddress = new_client.getInetAddress();
    }

}

