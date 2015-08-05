package com.app.copradar.network.requests;

import com.app.copradar.network.base.BaseRequest;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Yossef on 8/4/15.
 */
public class GetCopPresenceRequest extends BaseRequest {

   private double latitude;
   private double longitude;
   private int   radius;


    public GetCopPresenceRequest(double latitude, double longitude, int radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;

    }
}
