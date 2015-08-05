package com.app.copradar.controller;

import android.content.Context;

import com.app.copradar.network.requests.GetCopPresenceRequest;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Yossef on 8/2/15.
 */
public class AppController {

    private static AppController controller;

    public static AppController getInstance(Context context) {
        if (controller == null) {
            controller = new AppController(context);
        }
        return controller;
    }

    private AppController(Context context) {

    }

    public void getCopPresences(double latitude,double longitude, int radius, NetworkTask.NetworkTaskListener listener) {
        NetworkTask task = new NetworkTask(listener);
        task.execute(new GetCopPresenceRequest(latitude,longitude, radius));
    }


}
