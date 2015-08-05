package com.app.copradar.activities;

import android.os.Bundle;

import com.app.copradar.R;
import com.app.copradar.activities.base.BaseActivity;
import com.app.copradar.controller.NetworkTask;
import com.app.copradar.network.base.BaseResponse;
import com.app.copradar.util.util.Consts;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends BaseActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        double latitude = map.getMyLocation().getLatitude();
        double longitude = map.getMyLocation().getLongitude();

        app.getCopPresences(latitude,longitude, Consts.RADIUS_GET_PRESENCES,new NetworkTask.NetworkTaskListener() {
            @Override
            public void onSuccess(BaseResponse response) {

            }

            @Override
            public void onError(int errorCode) {

            }
        });





//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
//
//        map.addMarker(new MarkerOptions()
//                .title("Sydney")
//                .snippet("The most populous city in Australia.")
//                .position(sydney));
    }
}
