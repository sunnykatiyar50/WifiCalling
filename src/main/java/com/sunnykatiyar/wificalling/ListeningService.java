package com.sunnykatiyar.wificalling;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ListeningService extends Service {
    public ListeningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
