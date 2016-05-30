package com.earmongheng.restclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.earmongheng.restclient.helper.SaveTask;
import com.earmongheng.restclient.models.House;
import com.earmongheng.restclient.models.User;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public class UploadHouseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnSelectImage;
    private Button btnSave;
    private Button btnCancel;
    private EditText etPrice;
    private EditText etDeposit;
    private EditText etHouseNo;
    private EditText etStreet;
    private EditText etDescription;
    private TextView tvSelectImage;
    private Spinner spinnerCity;

    private static final int PICK_IMAGE = 1;
    private String encodedString;
    private User user;
    private String city;
    private String url = "http://192.168.1.138:8080/Realestate/save/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_house);
        initialComponent();

        user = (User) getIntent().getExtras().getSerializable("user");

        spinnerCity.setOnItemSelectedListener(this);

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
                String address = etHouseNo.getText().toString() + ", St " + etStreet.getText().toString() + ", " + city;
                String laLong = "St " + etStreet.getText().toString() + ", " + city;
                LatLng location = getLocationFromAddress(getBaseContext(), laLong);
                House house = new House();
                house.setPrice(Float.parseFloat(etPrice.getText().toString()));
                house.setDeposit(Float.parseFloat(etDeposit.getText().toString()));
                house.setLatitude(location.latitude);
                house.setLongtitude(location.longitude);
                house.setAddress(address);
                house.setDescription(etDescription.getText().toString());
                house.setPicture(encodedString);

                new SaveTask(user, house).execute(url);
                Toast.makeText(getBaseContext(), "Save Data Successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void initialComponent() {
        btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etDeposit = (EditText) findViewById(R.id.etDeposit);
        etHouseNo = (EditText) findViewById(R.id.etHouseNo);
        etStreet = (EditText) findViewById(R.id.etStreet);
        etDescription = (EditText) findViewById(R.id.etDescription);
        tvSelectImage = (TextView) findViewById(R.id.tvSelectImage);
        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        city = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        LatLng latLng = null;

        try{
            addresses = geocoder.getFromLocationName(strAddress, 5);
            if (addresses == null) {
                return null;
            }
            Address address = addresses.get(0);
            latLng = new LatLng(address.getLatitude(),address.getLongitude());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return latLng;
    }
}
