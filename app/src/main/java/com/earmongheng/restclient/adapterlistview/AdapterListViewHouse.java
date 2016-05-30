package com.earmongheng.restclient.adapterlistview;

import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.earmongheng.restclient.R;
import com.earmongheng.restclient.models.House;
import com.earmongheng.restclient.utility.ConvertData;
import com.earmongheng.restclient.utility.MapLoad;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earmongheng on 5/13/2016.
 */
public class AdapterListViewHouse {

    private Context context;
    private View view;
    private ListView listView;
    private GoogleMap googleMap;
    private FragmentManager fragmentManager;
    private String url = "http://192.168.1.138:8080/Realestate/selecthouse/";

    public AdapterListViewHouse(Context context, View view, ListView listView, GoogleMap googleMap, FragmentManager fragmentManager) {
        this.context = context;
        this.view = view;
        this.listView = listView;
        this.googleMap = googleMap;
        this.fragmentManager = fragmentManager;
    }

    public void viewImage() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "Position:" + parent.getItemIdAtPosition(position), Toast.LENGTH_LONG).show();
                String price = (String)((TextView)view.findViewById(R.id.txtPrice)).getText();
                String latitude = ((TextView)view.findViewById(R.id.tvLatitude)).getText().toString();
                String longtitude = ((TextView)view.findViewById(R.id.tvLongtitude)).getText().toString();
                String houseid = ((TextView)view.findViewById(R.id.tvHouseId)).getText().toString();
                LatLng latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longtitude));

                new HouseSelectAsyncTask(googleMap, fragmentManager, latLng, houseid).execute(url);
            }
        });
    }

    private class HouseSelectAsyncTask extends AsyncTask<String, Void, String> {

        private GoogleMap googleMap;
        private FragmentManager fragmentManager;
        private LatLng latLng;
        private String houseid;

        public HouseSelectAsyncTask(GoogleMap googleMap, FragmentManager fragmentManager, LatLng latLng, String houseid) {
            this.googleMap = googleMap;
            this.fragmentManager = fragmentManager;
            this.latLng = latLng;
            this.houseid = houseid;
        }

        @Override
        protected void onPostExecute(String house) {
            super.onPostExecute(house);
            try {
                House currentHouse = ConvertData.getEntityObject(house, House.class);
                List<House> houses = new ArrayList<>();
                houses.add(currentHouse);
                MapLoad.loadMap(houses,googleMap,fragmentManager, latLng);
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return ConvertData.GET(params[0] + houseid);
        }
    }
}
