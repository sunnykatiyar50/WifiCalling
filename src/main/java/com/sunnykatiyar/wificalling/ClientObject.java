package com.sunnykatiyar.wificalling;

import android.util.Log;

import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Sunny Katiyar on 27-01-2018.
 */

public class ClientObject {
    protected Socket socket;
    private InetAddress local_inetaddress;
    private InetAddress remote_inetaddress;
    protected String remote_ip;
    protected String local_ip;
    protected int port_nmbr;
    protected int client_nmbr;
    protected ClientThread clientThread;
    protected MessagingFragment clientChatFragment;
    String TAG="in ClientObject : ";

    public ClientObject(Socket s,int nmbr){
        this.socket=s;
        this.local_inetaddress=socket.getLocalAddress();
        this.remote_inetaddress=s.getInetAddress();
        this.local_ip= local_inetaddress.getHostAddress().substring(1);
        this.remote_ip=s.getInetAddress().getHostAddress();
        this.port_nmbr=s.getPort();
        this.client_nmbr =nmbr;
        this.clientChatFragment=new MessagingFragment(this,client_nmbr);
        Log.e(TAG,"Created ClientObject no. "+ client_nmbr +" with ip address "+remote_ip);
    }


    protected void openFragment(){
        //if (this.clientChatFragment==null)
          //  this.clientChatFragment=new MessagingFragment(this,client_nmbr);
        MainActivity.fragmentManager.beginTransaction()
                .addToBackStack("chat_window")
                .replace(R.id.user_details_container,clientChatFragment)
                .commit();
    }



    String getRemoteIp(){
        return this.remote_ip;
    }

    InetAddress getRemoteInetAddress(){
        return this.remote_inetaddress;
    }

}
