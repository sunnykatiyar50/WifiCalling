package com.sunnykatiyar.wificalling;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.sunnykatiyar.wificalling.DeviceListFragment.myDefaultPort;
import static com.sunnykatiyar.wificalling.DeviceListFragment.myServer;
import static com.sunnykatiyar.wificalling.MainActivity.activity;
import static com.sunnykatiyar.wificalling.MainActivity.context;
import static com.sunnykatiyar.wificalling.R.color.pink;

public class ListeningService extends Service {


    protected int client_nmbr ;
    List<ServerObject> serverObjects_list = new ArrayList<>();
    Socket new_client;
    Boolean stop_server=true;
    String TAG = "LISTENING SERVICE";
    ServerObject new_serverObject;
    int noti_id=100;
    public NotificationChannel myNotiChannel;
    public static NotificationManager notimgr ;
    Intent resultIntent;
    TaskStackBuilder stackBuilder;
    public static Notification.Builder noti_builder;
    PendingIntent pendingIntent;
    Context context_service;


    @Override
    public void onCreate() {
        super.onCreate();
        context_service = getApplicationContext();
        notimgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        resultIntent = new Intent(this,MainActivity.class);
        stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        noti_builder = new Notification.Builder(context_service);
        noti_builder.setContentTitle("Wifi Calling Service")
                .setContentText("Server is Running")
                .setSmallIcon(R.drawable.ic_perm_phone_msg_black_18dp);
        pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        noti_builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            noti_builder.setChannelId("SERVER_SERVICE");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startServer();
        connectToClients();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void startServer() {
        try {
                if(myServer!=null)
                   myServer = null;
                myServer=new ServerSocket(myDefaultPort);
                notimgr.notify(noti_id,noti_builder.build());
                Log.e(TAG,"Server Started Successfully ....");
            } catch (IOException e) {
                Log.e(TAG,"Error Starting server ....");
            }
    }

    protected void connectToClients() {
        client_nmbr = 1 ;
        while (stop_server==false) {
            try {
                new_client = myServer.accept();
                new_serverObject = new ServerObject(myServer,new_client,client_nmbr);
                Log.e(TAG,client_nmbr+" client at "+new_client.getInetAddress().getHostName()+" connected successfully ");
                new ServerThread(new_serverObject).start();
                serverObjects_list.add(new_serverObject);
                client_nmbr++;
            } catch (IOException e) {
                Log.e(TAG,"Unable to accept client requests. ");
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notimgr.cancel(noti_id);
    }
}


