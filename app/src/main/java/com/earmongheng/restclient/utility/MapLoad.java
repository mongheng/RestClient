package com.earmongheng.restclient.utility;

import android.app.FragmentManager;

import com.earmongheng.restclient.R;
import com.earmongheng.restclient.models.House;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by earmongheng on 5/29/2016.
 */
public class MapLoad {

    public static void loadMap(List<House> houses, GoogleMap googleMap, FragmentManager fragmentManager, LatLng latLng) {

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) fragmentManager.findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            if (houses != null) {
                for (House house : houses) {
                    LatLng position = new LatLng(house.getLatitude(), house.getLongtitude());
                    MarkerOptions markerOptions = new MarkerOptions();

                    markerOptions.position(position);
                    markerOptions.title("position");
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
}
