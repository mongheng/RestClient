package com.earmongheng.restclient.utility;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.earmongheng.restclient.R;
import com.earmongheng.restclient.SignupActivity;
import com.earmongheng.restclient.models.House;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by earmongheng on 5/29/2016.
 */
public class MapLoad /*implements GoogleMap.OnMarkerClickListener*/ {

    private Context context;

    public MapLoad(Context context) {
        this.context = context;
    }

    public void loadMap(List<House> houses, GoogleMap googleMap, FragmentManager fragmentManager, LatLng latLng) {

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) fragmentManager.findFragmentById(R.id.map)).getMap();
            }
            //googleMap.setOnMarkerClickListener(this);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            if (houses != null) {
                for (House house : houses) {
                    LatLng position = new LatLng(house.getLatitude(), house.getLongtitude());
                    MarkerOptions markerOptions = new MarkerOptions();

                    markerOptions.position(position);
                    markerOptions.title(String.valueOf(house.getHouseid()));
                    markerOptions.snippet("Address : " + house.getAddress());

                    googleMap.addMarker(markerOptions);
                }
            }
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            if (latLng == null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(11.5449, 104.8922), 14.0f));
            }else {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
/*
    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(context,marker.getTitle(), Toast.LENGTH_LONG).show();
        return true;
    }*/
}
