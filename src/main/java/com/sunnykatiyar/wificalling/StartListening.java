package com.sunnykatiyar.wificalling;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.sunnykatiyar.wificalling.DeviceListFragment.myServer;

/**
 * Created by Sunny Katiyar on 27-01-2018.
 */
class StartListening extends Thread{

    protected int client_nmbr ;
    String TAG = "StartListening " ;
    List<ServerObject> serverObjects_list = new ArrayList<>();
    Socket new_client;


    @Override
    public void run() {
        startServer();
        connectToClients();
    }

    void startServer()
    { try { if(myServer==null)
    {   myServer = new ServerSocket(MainActivity.myDefaultPort);
        MainActivity.isServerRunning=true;
        Log.e(TAG,"Server Started Successfully ....");
    }
    } catch (IOException e) {
        Log.e(TAG,"Error Starting server ....");
    }

    }


    protected void connectToClients() {
        client_nmbr = 0 ;
        while (true) {
            try {
                    new_client = myServer.accept();
                    serverObjects_list.add(new ServerObject(myServer,new_client,client_nmbr));
                    new ServerThread(serverObjects_list.get(client_nmbr));
                    Log.e(TAG,"client no. "+client_nmbr+" created");
                    client_nmbr++;
                 } catch (IOException e) {
                        e.printStackTrace();
                }

            }
    }

}
