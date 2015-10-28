package com.superlity.strongservice;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by lion on 15-9-29.
 */
public class ChildService extends IntentService {

    private String TAG = getClass().getName();
    private static final String PROCESS_NAME = "com.superlity.strongservice:ParentService";

    private StrongService strongService = new StrongChildService();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ChildService() {
        super(PROCESS_NAME);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) strongService;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.toast("ChildService onCreate ...");
        keepService();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        keepService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keepService();
    }

    private void keepService() {
        boolean isRun = Utils.isProessRunning(ChildService.this, PROCESS_NAME);
        if (!isRun) {
            try {
                Utils.toast("restart ParentService");
                strongService.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO
        return START_STICKY;
    }

    private class StrongChildService extends StrongService.Stub {

        @Override
        public void startService() throws RemoteException {
            Intent intent = new Intent(getBaseContext(), ParentService.class);
            getBaseContext().startService(intent);
        }

        @Override
        public void stopService() throws RemoteException {
            Intent intent = new Intent(getBaseContext(), ParentService.class);
            getBaseContext().stopService(intent);
        }
    }
}
