package com.rockstar.dilkhushstore;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputLayout;
import com.rockstar.dilkhushstore.model.LoginResponse;
import com.rockstar.dilkhushstore.services.ApiRequestHelper;
import com.rockstar.dilkhushstore.services.GetAddressIntentService;
import com.rockstar.dilkhushstore.utility.AllKeys;
import com.rockstar.dilkhushstore.utility.CommonMethods;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RegistrationActivity";

    @BindView(R.id.til_fname)
    TextInputLayout tilFname;
    @BindView(R.id.til_lname)
    TextInputLayout tilLname;
    @BindView(R.id.til_address)
    TextInputLayout tilAddress;
    @BindView(R.id.til_contact_no)
    TextInputLayout tilContactNo;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    @BindView(R.id.et_fname)
    EditText etFname;
    @BindView(R.id.et_lname)
    EditText etLname;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_contact_no)
    EditText etContactNo;
    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_register)
    AppCompatButton btnRegister;

    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private LocationAddressResultReceiver addressResultReceiver;
    private LocationCallback locationCallback;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressResultReceiver = new LocationAddressResultReceiver(new Handler());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            }
        };
        startLocationUpdates();
        //basic intialisation..
        initViews();
    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.activity_registration;
    }

    private void initViews(){
        //Toolbar intialisation...
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Registration");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                if(isValidated()){
                    if(CommonMethods.isNetworkAvailable(mContext)){
                        registerUser();
                    }else{
                        showDialogWindow("Warning", AllKeys.NO_INTERNET_AVAILABLE);
                    }
                }
                break;
        }
    }

    private void registerUser(){
        Map<String,String> params=new HashMap<>();
        params.put("fname",etFname.getText().toString());
        params.put("lname",etLname.getText().toString());
        params.put("contactnumber",etContactNo.getText().toString());
        params.put("password",etPassword.getText().toString());
        params.put("locationAddress",etAddress.getText().toString());
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Registering...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);
        dilKhush.getApiRequestHelper().addCustomer(params, new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                LoginResponse loginResponse = (LoginResponse) object;
                progress.dismiss();
                if (loginResponse.getResponsecode()==200) {

                    Log.e(TAG, "onSuccess: "+loginResponse );
                    //String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    Toast.makeText(mContext, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    showDialogWindow("Warning",loginResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                showDialogWindow("Warning",apiResponse);
            }
        });
    }


    private void showDialogWindow(String title,String message) {
        MaterialDialog mDialog = new MaterialDialog.Builder(RegistrationActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Try Again",
                        (dialogInterface, which) -> {
                            // Delete Operation
                            dialogInterface.dismiss();
                        })
                //.setNegativeButton("Cancel", (dialogInterface, which) -> dialogInterface.dismiss())
                .build();

        // Show Dialog
        mDialog.show();
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                            String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {
        if (!Geocoder.isPresent()) {
            Toast.makeText(RegistrationActivity.this, "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        startService(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
            int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission not granted, " + "restart the app if you want the feature", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                Log.d("Address", "Location null retrying");
                getAddress();
            }
            if (resultCode == 1) {
                Toast.makeText(mContext, "Address not found, ", Toast.LENGTH_SHORT).show();
            }
            String currentAdd = resultData.getString("address_result");
            showResults(currentAdd);
        }
    }
    private void showResults(String currentAdd) {
        etAddress.setText(currentAdd);
    }
    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }
    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private boolean isValidated(){
        if (etFname.getText().toString().isEmpty()) {
            tilFname.setError("FirstName required!");
            tilFname.setFocusable(true);
            tilFname.requestFocus();
            return false;
        } else {
            tilFname.setErrorEnabled(false);
        }

        if (etLname.getText().toString().isEmpty()) {
            tilLname.setError("FirstName required!");
            tilLname.setFocusable(true);
            tilLname.requestFocus();
            return false;
        } else {
            tilLname.setErrorEnabled(false);
        }

        if (etContactNo.getText().toString().isEmpty()) {
            tilContactNo.setError("Mobile No required!");
            tilContactNo.setFocusable(true);
            tilContactNo.requestFocus();
            return false;
        } else {
            tilContactNo.setErrorEnabled(false);
        }

        if (etContactNo.getText().toString().length()!=10) {
            tilContactNo.setError("Invalid Mobile No!");
            tilContactNo.setFocusable(true);
            tilContactNo.requestFocus();
            return false;
        } else {
            tilContactNo.setErrorEnabled(false);
        }

        if (etAddress.getText().toString().isEmpty()) {
            tilAddress.setError("Address required!");
            tilAddress.setFocusable(true);
            tilAddress.requestFocus();
            return false;
        } else {
            tilAddress.setErrorEnabled(false);
        }

        if (etPassword.getText().toString().isEmpty()) {
            tilPassword.setError("Password required!");
            tilPassword.setFocusable(true);
            tilPassword.requestFocus();
            return false;
        } else {
            tilPassword.setErrorEnabled(false);
        }

        return true;
    }
}