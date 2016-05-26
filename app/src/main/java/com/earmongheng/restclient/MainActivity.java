package com.earmongheng.restclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.earmongheng.restclient.adapterlistview.AdapterListViewHouse;
import com.earmongheng.restclient.helper.UpdateTask;
import com.earmongheng.restclient.models.House;
import com.earmongheng.restclient.models.User;
import com.earmongheng.restclient.utility.ConvertData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvHouse;
    private GoogleMap googleMap;
    private String url = "http://192.168.1.138:8080/Realestate/";
    private User user = null;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        extras = getIntent().getExtras();
        if (extras != null) {
            user = (User) extras.getSerializable("user");
        }
        lvHouse = (ListView) findViewById(R.id.lvHouse);

        boolean isChecked = isConnected();
        if(isChecked) {
            Toast.makeText(getBaseContext(), "You are Connected", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getBaseContext(), "You are not Connected to Internet.", Toast.LENGTH_LONG).show();
        }

        new HttpAsyncTask().execute(url);

        AdapterListViewHouse adapterListViewHouse = new AdapterListViewHouse(getBaseContext(),null,lvHouse);
        adapterListViewHouse.viewImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try {
            Intent intent;
            int id = item.getItemId();

            switch (id) {
                case R.id.login :
                    Toast.makeText(getBaseContext(), "Login Form", Toast.LENGTH_LONG).show();
                    intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    break;

                case R.id.update :
                    Toast.makeText(getBaseContext(), "Update Data", Toast.LENGTH_LONG).show();
                    UpdateTask updateTask = new UpdateTask(getBaseContext());
                    updateTask.execute(url + "select/3");
                    break;

                case R.id.signup :
                    intent = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(intent);
                    break;

                case R.id.signout :
                    user = null;
                    intent = new Intent(MainActivity.this, SignoutActivity.class);
                    startActivity(intent);
                    break;

                case R.id.addhouse :
                    intent = new Intent(MainActivity.this, UploadHouseActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                    break;

                default:
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (user != null) {
            menu.findItem(R.id.login).setVisible(false);
            menu.findItem(R.id.signout).setVisible(true);
            menu.findItem(R.id.setting).setVisible(true);
            menu.findItem(R.id.addhouse).setVisible(true);
        }else {
            menu.findItem(R.id.setting).setVisible(false);
            menu.findItem(R.id.signout).setVisible(false);
            menu.findItem(R.id.addhouse).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }else {
            return false;
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private JSONObject imageobject = null;

        @Override
        protected String doInBackground(String... urls) {
            return ConvertData.GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getBaseContext(), "Received", Toast.LENGTH_LONG).show();

            try {
                List<House> houses = ConvertData.extraHousesData(result);
                HouseAdapter houseAdapter = new HouseAdapter(getApplicationContext(),R.id.lvHouse,houses);

                lvHouse.setAdapter(houseAdapter);
                loadMap(houses);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class HouseAdapter extends ArrayAdapter {

        private List<House> houses;
        private int resource;
        private LayoutInflater inflater;

        public HouseAdapter(Context context, int resource, List<House> houses) {
            super(context, resource, houses);
            this.houses = houses;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listhouse,null);
            }

            ImageView ivHouseIcon = (ImageView) convertView.findViewById(R.id.imageView) ;
            TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
            TextView txtDeposit = (TextView) convertView.findViewById(R.id.txtDeposit);
            TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);

            txtPrice.setText(String.valueOf(houses.get(position).getPrice()));
            txtDeposit.setText(String.valueOf(houses.get(position).getDeposit()));
            txtDescription.setText(houses.get(position).getDescription());

            byte [] image = Base64.decode(houses.get(position).getPicture(),Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(image,0,image.length);
            ivHouseIcon.setImageBitmap(bmp);

            return convertView;
        }
    }

    private void loadMap(List<House> houses) {

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            for (House house: houses) {
                LatLng position = new LatLng(house.getLatitude(),house.getLongtitude());
                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(position);
                markerOptions.title("position");
                markerOptions.snippet("Latitude:" + house.getLatitude() + ",Longitude:" + house.getLongtitude());

                googleMap.addMarker(markerOptions);
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(11.5449,104.8922),14.0f));
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}


