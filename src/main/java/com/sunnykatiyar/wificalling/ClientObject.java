package com.sunnykatiyar.wificalling;

import android.util.Log;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Katiyar on 27-01-2018.
 */

public class ClientObject extends Socket {
    Socket socket;
    String remote_ip;
    String local_ip;
    InetAddress local_inetaddress;
    InetAddress remote_inetaddress;
    int port_nmbr;
    int position;
    String TAG="in ClientObject : ";
    List<String> messages=new ArrayList<>();

    public ClientObject(Socket s, String remote_ip, int conn_port, InetAddress i, int nmbr){
        this.socket=s;
        this.local_inetaddress =socket.getLocalAddress();
        this.remote_inetaddress=i;
        local_ip= local_inetaddress.getHostAddress().substring(1);
        Log.e(TAG,"created server object no. "+position+" with ip address "+remote_ip);
        this.remote_ip = remote_ip;
        this.port_nmbr=conn_port;
        this.position=nmbr;
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
