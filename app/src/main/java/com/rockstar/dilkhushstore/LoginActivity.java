package com.rockstar.dilkhushstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputLayout;
import com.rockstar.dilkhushstore.model.LoginResponse;
import com.rockstar.dilkhushstore.services.ApiRequestHelper;
import com.rockstar.dilkhushstore.utility.AllKeys;
import com.rockstar.dilkhushstore.utility.CommonMethods;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.til_username)
    TextInputLayout tilUserName;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_signin)
    AppCompatButton btnSignIn;
    @BindView(R.id.btn_register)
    AppCompatButton btnRegister;

    @BindView(R.id.ch_remember_me)
    CheckBox chRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //basic intialisation...
        initViews();

    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.activity_login;
    }

    private void initViews( ) {

        //Click Listeners....
        btnSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        if (CommonMethods.getPrefrence(LoginActivity.this, AllKeys.LOGIN_USER_NAME).equals(AllKeys.DNF)) {
        } else {
            etUsername.setText(CommonMethods.getPrefrence(LoginActivity.this, AllKeys.LOGIN_USER_NAME));
            etPassword.setText(CommonMethods.getPrefrence(LoginActivity.this, AllKeys.LOGIN_PASSWORD));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Login click....
            case R.id.btn_signin:
                if (isValidated()) {
                    if (CommonMethods.isNetworkAvailable(LoginActivity.this)) {
                        userAuthentication();
                    } else {
                        Toast.makeText(LoginActivity.this, AllKeys.NO_INTERNET_AVAILABLE, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.btn_register:
                Intent intent = new Intent(mContext, RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    private void userAuthentication() {
        Map<String,String> params=new HashMap<>();
        params.put("username",etUsername.getText().toString());
        params.put("password",etPassword.getText().toString());
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Logging in...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);
        dilKhush.getApiRequestHelper().login(params, new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                LoginResponse loginResponse = (LoginResponse) object;
                progress.dismiss();
                if (loginResponse.getResponsecode()==200) {

                    Log.e(TAG, "onSuccess: "+loginResponse );
                    //String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    CommonMethods.setPreference(LoginActivity.this, AllKeys.CUSTOMER_ID, String.valueOf(loginResponse.getData().get(0).getCustomerid()));
                    CommonMethods.setPreference(LoginActivity.this, AllKeys.FNAME, loginResponse.getData().get(0).getFname());
                    CommonMethods.setPreference(LoginActivity.this, AllKeys.LNAME, loginResponse.getData().get(0).getLname());
                    CommonMethods.setPreference(LoginActivity.this, AllKeys.CONTACT_NO, loginResponse.getData().get(0).getContactnumber());
                    CommonMethods.setPreference(LoginActivity.this, AllKeys.ADDRESS, loginResponse.getData().get(0).getLocationAddress());

                    Intent intent = new Intent(mContext, DashBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                    if (chRememberMe.isChecked()) {
                        CommonMethods.setPreference(LoginActivity.this, AllKeys.LOGIN_USER_NAME, etUsername.getText().toString());
                        CommonMethods.setPreference(LoginActivity.this, AllKeys.LOGIN_PASSWORD, etPassword.getText().toString());
                    } else {
                        CommonMethods.setPreference(LoginActivity.this, AllKeys.LOGIN_USER_NAME, AllKeys.DNF);
                        CommonMethods.setPreference(LoginActivity.this, AllKeys.LOGIN_PASSWORD, AllKeys.DNF);
                    }
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
        MaterialDialog mDialog = new MaterialDialog.Builder(LoginActivity.this)
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

    //proper validations....
    private boolean isValidated( ) {
        if (etUsername.getText().toString().isEmpty()) {
            tilUserName.setError("Username required!");
            tilUserName.setFocusable(true);
            tilUserName.requestFocus();
            return false;
        } else {
            tilUserName.setErrorEnabled(false);
        }

        if (etPassword.getText().toString().isEmpty()) {
            tilPassword.setError("Password required!");
            tilPassword.setFocusable(true);
            tilPassword.requestFocus();
            return false;
        } else {
            tilPassword.setErrorEnabled(false);
        }

        if (etUsername.getText().length() != 10) {
            tilUserName.setError("Invalid username!");
            tilUserName.setFocusable(true);
            tilUserName.requestFocus();
            return false;
        } else {
            tilUserName.setErrorEnabled(false);
        }
        return true;
    }
}