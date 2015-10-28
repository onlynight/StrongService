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
public class ParentService extends IntentService {

    private final String TAG = getClass().getName();
    private static String PROCESS_NAME = "com.superlity.hiqianbei:ChildService";

    private StrongService startChildService = new StrongParentService();


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ParentService() {
        super(PROCESS_NAME);
    }

    private void keepChildService() {
        boolean isRun = Utils.isProessRunning(this, PROCESS_NAME);
        if (!isRun) {
            try {
                Utils.toast("restart ChildService");
                startChildService.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        keepChildService();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.toast("ParentService onCreate...");
        keepChildService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keepChildService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) startChildService;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    private class StrongParentService extends StrongService.Stub {

        @Override
        public void startService() throws RemoteException {
            Intent intent = new Intent(getBaseContext(), ChildService.class);
            getBaseContext().startService(intent);
        }

        @Override
        public void stopService() throws RemoteException {
            Intent intent = new Intent(getBaseContext(), ChildService.class);
            getBaseContext().stopService(intent);
        }
    }
}
