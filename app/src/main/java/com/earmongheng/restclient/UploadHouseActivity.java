package com.earmongheng.restclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.earmongheng.restclient.helper.SaveTask;
import com.earmongheng.restclient.models.House;
import com.earmongheng.restclient.models.User;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class UploadHouseActivity extends AppCompatActivity implements LocationListener {

    private Button btnSelectImage;
    private Button btnSave;
    private Button btnCancel;
    private EditText etPrice;
    private EditText etDeposit;
    private EditText etLatitude;
    private EditText etLongtitude;
    private EditText etDescription;
    private TextView tvSelectImage;

    private static final int PICK_IMAGE = 1;
    private LocationManager locationManager;
    private String provider;
    private Location location;
    private String encodedString;
    private User user;
    private String url = "http://192.168.1.138:8080/Realestate/save/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_house);
        initialComponent();
        location = getLocation();
        /*GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            double l = gpsTracker.getLatitude();
            double lt = gpsTracker.getLongitude();
            Toast.makeText(getBaseContext(), "Latitude :" + l + ", Longitude :" + lt, Toast.LENGTH_LONG).show();
        }else {
            gpsTracker.showSettingsAlert();
        }*/
        user = (User) getIntent().getExtras().getSerializable("user");

        btnSelectImage = (Button) findViewById(R.id.btnSelectImage);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                User user = (User) intent.getExtras().getSerializable("user");

                selectImageFromGallery();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                House house = new House();
                house.setPrice(Float.parseFloat(etPrice.getText().toString()));
                house.setDeposit(Float.parseFloat(etDeposit.getText().toString()));
                house.setLatitude(Double.parseDouble(etLatitude.getText().toString()));
                house.setLongtitude(Double.parseDouble(etLongtitude.getText().toString()));
                house.setDescription(etDescription.getText().toString());
                house.setPicture(encodedString);

                new SaveTask(user, house).execute(url);
                Toast.makeText(getBaseContext(), "Save Data Successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void initialComponent() {
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etDeposit = (EditText) findViewById(R.id.etDeposit);
        etLatitude = (EditText) findViewById(R.id.etLatitude);
        etLongtitude = (EditText) findViewById(R.id.etLongtitude);
        etDescription = (EditText) findViewById(R.id.etDescription);
        tvSelectImage = (TextView) findViewById(R.id.tvSelectImage);
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        Intent chooserIntent = Intent.createChooser(intent, "Select Picture");
        chooserIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == PICK_IMAGE) {
                    InputStream inputStream = getBaseContext().getContentResolver().openInputStream(data.getData());

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    String type = getBaseContext().getContentResolver().getType(data.getData());

                    if (type.equals("image/png")) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    } else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    }

                    byte[] b = baos.toByteArray();
                    encodedString = Base64.encodeToString(b, Base64.DEFAULT);
                    tvSelectImage.setText(data.getData().getPath());
                    tvSelectImage.setTextColor(Color.BLUE);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Location getLocation(){
        Location currentLocation = null;
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            provider = locationManager.getBestProvider(criteria, true);
            if (provider != null && !provider.equals("")) {
                currentLocation = (Location) locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                locationManager.requestLocationUpdates(provider, 20000, 1, this);

                if (currentLocation != null) {
                    onLocationChanged(currentLocation);
                } else {
                    Toast.makeText(getApplicationContext(), "location not found", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(getApplicationContext(),"Provider is null",Toast.LENGTH_LONG).show();
            }
        }
        catch (SecurityException ex) {
            ex.printStackTrace();
        }
        return currentLocation;
    }


    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(getBaseContext(), "Latitude :" + location.getLatitude() + ", Longitude :" + location.getLongitude(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
