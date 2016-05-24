package com.earmongheng.restclient;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import com.earmongheng.restclient.models.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class UploadHouseActivity extends AppCompatActivity implements LocationListener {

    private Button btnSelectImage;

    private static final int PICK_IMAGE = 1;
    private LocationManager locationManager;
    private String provider;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_house);
        location = getLocation();
        User user = (User) getIntent().getExtras().getSerializable("user");

        btnSelectImage = (Button) findViewById(R.id.btnSelectImage);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                User user = (User) intent.getExtras().getSerializable("user");

                selectImageFromGallery();
            }
        });
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
                    String encodedString = Base64.encodeToString(b, Base64.DEFAULT);
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
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            //Criteria criteria = new Criteria();
            //provider = locationManager.getBestProvider(criteria, false);
            //if (provider != null && !provider.equals("")) {
                currentLocation = (Location) locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                //locationManager.requestLocationUpdates(provider, 20000, 1, this);
            //}
        }
        catch (SecurityException ex) {
            ex.printStackTrace();
        }
        return currentLocation;
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
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
