package com.rockstar.dilkhushstore;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import butterknife.BindView;

public class AddToCartActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rv_add_to_cart)
    RecyclerView rvAddToCart;
    @BindView(R.id.tv_total_amount)
    public TextView tvTotalAmount;
    @BindView(R.id.btn_checkout)
    AppCompatButton btnCheckout;
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
            /*AddToCartAdapter addToCartAdapter = new AddToCartAdapter(AddToCartActivity.this, productBOArrayList, AddToCartActivity.this);
            rvAddToCart.setAdapter(addToCartAdapter);

            int sum = 0;
            for (int i = 0; i < productBOArrayList.size(); i++) {
                sum += productBOArrayList.get(i).getProductTotal();
            }
            tvTotalAmount.setText("" + sum);*/
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_checkout:

                break;
            case R.id.btn_start_shopping:
                startActivity(new Intent(AddToCartActivity.this, DashBoardActivity.class));
                finish();
            break;
        }
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