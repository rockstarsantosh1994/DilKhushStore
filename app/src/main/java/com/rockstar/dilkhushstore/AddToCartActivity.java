package com.rockstar.dilkhushstore;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rockstar.dilkhushstore.adapter.AddToCartAdapter;
import com.rockstar.dilkhushstore.model.CommonResponse;
import com.rockstar.dilkhushstore.model.LoginResponse;
import com.rockstar.dilkhushstore.model.products.MappingBO;
import com.rockstar.dilkhushstore.model.products.ProductBO;
import com.rockstar.dilkhushstore.services.ApiRequestHelper;
import com.rockstar.dilkhushstore.utility.AllKeys;
import com.rockstar.dilkhushstore.utility.CommonMethods;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;

public class AddToCartActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AddToCartActivity";
    @BindView(R.id.rv_add_to_cart)
    RecyclerView rvAddToCart;
    @BindView(R.id.tv_total_amount)
    public TextView tvTotalAmount;
    @BindView(R.id.btn_checkout)
    AppCompatButton btnCheckout;
    @BindView(R.id.btn_start_shopping)
    AppCompatButton btnStartShopping;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNoData;
    @BindView(R.id.rl_mainL_layout)
    RelativeLayout rlMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //basic intialisation...
        initViews();

        if (DashBoardActivity.productBOArrayList.size() > 0) {
            rlMainLayout.setVisibility(View.VISIBLE);
            rlNoData.setVisibility(View.GONE);
            AddToCartAdapter addToCartAdapter = new AddToCartAdapter(AddToCartActivity.this,DashBoardActivity.productBOArrayList, AddToCartActivity.this);
            rvAddToCart.setAdapter(addToCartAdapter);

            int sum = 0;
            for (int i = 0; i < DashBoardActivity.productBOArrayList.size(); i++) {
                sum += Integer.parseInt(DashBoardActivity.productBOArrayList.get(i).getMappingBO().getPrice());
            }
            tvTotalAmount.setText("" + sum);
        } else {
            rlMainLayout.setVisibility(View.GONE);
            rlNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.activity_add_to_cart;
    }

    private void initViews( ) {
        //Toolbar intialisation...
        Toolbar toolbar = findViewById(R.id.toolbar_add_to_cart);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("My Cart");
        toolbar.setTitleTextColor(Color.WHITE);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        rvAddToCart.setLayoutManager(new LinearLayoutManager(AddToCartActivity.this));

        btnCheckout.setOnClickListener(this);
        btnStartShopping.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_checkout:
                if(CommonMethods.isNetworkAvailable(mContext)){
                    createOrder();
                }else{
                    showDialogWindow("Warning", AllKeys.NO_INTERNET_AVAILABLE);
                }

                break;
            case R.id.btn_start_shopping:
                startActivity(new Intent(AddToCartActivity.this, DashBoardActivity.class));
                finish();
            break;
        }
    }

    private void createOrder(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String orderDate = df.format(c);

        ArrayList<MappingBO> mappingBOArrayList=new ArrayList<>();
        MappingBO mappingBO;
        for(ProductBO productBO:DashBoardActivity.productBOArrayList){
            mappingBO=new MappingBO(productBO.getMappingBO().getMapid());
            mappingBOArrayList.add(mappingBO);
        }
        String data = new Gson().toJson(mappingBOArrayList);

        Map<String, String> params = new HashMap<>();
        params.put("customerid",CommonMethods.getPrefrence(mContext,AllKeys.CUSTOMER_ID));
        params.put("orderdate",orderDate);
        params.put("amount", tvTotalAmount.getText().toString());
        params.put("Invoicedata",data);

        Log.e(TAG, "createOrder: "+params );
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Generating Order...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);
        dilKhush.getApiRequestHelper().placeOrder(params, new ApiRequestHelper.OnRequestComplete() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object object) {
                CommonResponse commonResponse = (CommonResponse) object;
                progress.dismiss();
                if (commonResponse.getResponsecode() == 200) {
                    CommonMethods.showDialogWindowForSuccess(mContext,"Success",commonResponse.getMessage());
                    DashBoardActivity.productBOArrayList.clear();
                    if(DashBoardActivity.productBOArrayList.size()==0){
                        DashBoardActivity.tvCount.setVisibility(View.GONE);
                    }else{
                        DashBoardActivity.tvCount.setVisibility(View.VISIBLE);
                        DashBoardActivity.tvCount.setText(""+DashBoardActivity.productBOArrayList.size());
                    }
                } else {
                    showDialogWindow("Warning", commonResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                showDialogWindow("Warning", apiResponse);
            }
        });
    }

    //Setting text to TextView when deleting or updating the recycler view..
    public void getTotal(int total) {
        tvTotalAmount.setText("" + total);

        if (DashBoardActivity.productBOArrayList.size() > 0) {
            rlMainLayout.setVisibility(View.VISIBLE);
            rlNoData.setVisibility(View.GONE);
        } else {
            rlMainLayout.setVisibility(View.GONE);
            rlNoData.setVisibility(View.VISIBLE);
        }
    }

    private void showDialogWindow(String title, String message) {
        MaterialDialog mDialog = new MaterialDialog.Builder(AddToCartActivity.this)
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed( ) {
        super.onBackPressed();
    }
}