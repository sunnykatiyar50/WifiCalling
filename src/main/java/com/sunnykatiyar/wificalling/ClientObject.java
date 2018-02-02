package com.sunnykatiyar.wificalling;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Katiyar on 27-01-2018.
 */

public class ClientObject extends Socket {
    protected Socket socket;
    protected String remote_ip;
    protected String local_ip;
    private InetAddress local_inetaddress;
    private InetAddress remote_inetaddress;
    int port_nmbr;
    int position;
    ClientThread clientThread;
   // String new_msg;
   // protected boolean start_chat;
   // private BufferedReader bufferedReader;
   // private BufferedWriter bufferedWriter;
    String TAG="in ClientObject : ";
    //List<String> messages=new ArrayList<>();

    public ClientObject(Socket s, String remote_ip, int conn_port, InetAddress i, int nmbr){
        this.socket=s;
        this.local_inetaddress=socket.getLocalAddress();
        this.remote_inetaddress=i;
        local_ip= local_inetaddress.getHostAddress().substring(1);
        Log.e(TAG,"created server object no. "+position+" with ip address "+remote_ip);
        this.remote_ip = remote_ip;
        this.port_nmbr=conn_port;
        this.position=nmbr;
    }

    public void setClientThread(){
        clientThread =new ClientThread(this);
        clientThread.start();
    }

    public String convertIp(int ip){
        String s =  (ip&0xFF)+"."+
                ((ip>>8)&0xFF)+"."+
                ((ip>>16)&0xFF)+"."+
                ((ip>>24)&0xFF);
        return  s;
    }


    String getRemoteIp(){
        return this.remote_ip;
    }

    InetAddress getRemoteInetAddress(){
        return this.remote_inetaddress;
    }

}
