package com.rockstar.dilkhushstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.rockstar.dilkhushstore.adapter.CustomPagerAdapter;
import com.rockstar.dilkhushstore.adapter.ProductsAdapter;
import com.rockstar.dilkhushstore.model.advertisement.AdvertisementResponse;
import com.rockstar.dilkhushstore.model.products.ProductBO;
import com.rockstar.dilkhushstore.model.products.ProductsResponse;
import com.rockstar.dilkhushstore.services.ApiRequestHelper;
import com.rockstar.dilkhushstore.utility.AllKeys;
import com.rockstar.dilkhushstore.utility.CommonMethods;
import com.rockstar.dilkhushstore.widget.LoopViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;

public class DashBoardActivity extends BaseActivity {

    @BindView(R.id.toolbar_dashboard)
    Toolbar toolbar;
    @BindView(R.id.rrBanner)
    RelativeLayout rrBanner;
    @BindView(R.id.viewpager)
    LoopViewPager viewpager;
    @BindView(R.id.rv_products)
    RecyclerView rvProducts;

    public static TextView tvCount;
    public ImageView ivCart;

    private static final int MESSAGE_SCROLL = 123;

    private static final String TAG = "DashBoardActivity";
    public static final ArrayList<ProductBO> productBOArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Basic intialisation....
        initViews();

        //load advertisement for banner..
        if(CommonMethods.isNetworkAvailable(mContext)){
            loadAdvertisement();

            loadProductsData();
        }else{
            Toast.makeText(mContext, AllKeys.NO_INTERNET_AVAILABLE, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.activity_dash_board;
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(Color.WHITE);
        tvCount=toolbar.findViewById(R.id.tv_count);
        ivCart=toolbar.findViewById(R.id.iv_shopping_cart);

        if(productBOArrayList.size()==0){
            tvCount.setVisibility(View.GONE);
        }else{
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(""+productBOArrayList.size());
        }

        GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,2);
        rvProducts.setLayoutManager(gridLayoutManager);

        ivCart.setOnClickListener(view -> {
            Intent intent = new Intent(mContext,AddToCartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void loadAdvertisement(){
        dilKhush.getApiRequestHelper().loadAds(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                AdvertisementResponse advertisementResponse = (AdvertisementResponse) object;
                if (advertisementResponse.getResponsecode()==200) {
                    if (advertisementResponse.getData().size() > 0) {
                        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(DashBoardActivity.this, advertisementResponse.getData());
                        viewpager.setAdapter(customPagerAdapter);
                    } else {
                        rrBanner.setVisibility(View.GONE);
                    }

                    if (advertisementResponse.getData().size() > 1) {
                        set_slider_animation();
                    } else {
                        try {
                            stopAutoPlay();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                Log.e(TAG, "onFailure: "+apiResponse );
            }
        });
    }

    private void loadProductsData(){
        dilKhush.getApiRequestHelper().loadProducts(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                ProductsResponse productsResponse = (ProductsResponse) object;
                if (productsResponse.getResponsecode()==200) {
                    if (productsResponse.getData().size() > 0) {
                        ProductsAdapter productsAdapter=new ProductsAdapter(mContext,productsResponse.getData(),DashBoardActivity.this);
                        rvProducts.setAdapter(productsAdapter);
                    } else {
                        Toast.makeText(mContext, "No Data Found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                Log.e(TAG, "onFailure: "+apiResponse );
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void set_slider_animation() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            int animationDuration = 1000;
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewpager.getContext(), new AccelerateDecelerateInterpolator(), animationDuration);
            mScroller.set(viewpager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (viewpager != null && viewpager.getAdapter() != null && viewpager.getAdapter().getCount() > 1)
            startAutoPlay();
        else
            stopAutoPlay();
        viewpager.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    stopAutoPlay();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (viewpager != null && viewpager.getAdapter() != null && viewpager.getAdapter().getCount() > 1)
                        startAutoPlay();
                    else
                        stopAutoPlay();
                    break;

            }
            return false;
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_SCROLL) {
                if (viewpager != null) {
                    viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                    startAutoPlay();
                }
            }
        }
    };

    public void stopAutoPlay() {
        handler.removeMessages(MESSAGE_SCROLL);
    }

    public void startAutoPlay() {
        stopAutoPlay();
        int homeColumnScrollInterval = 4;
        handler.sendEmptyMessageDelayed(MESSAGE_SCROLL, homeColumnScrollInterval * 1000);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class FixedSpeedScroller extends Scroller {
        int duration;

        public FixedSpeedScroller(Context context, int duration) {
            super(context);
            this.duration = duration;
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, int duration) {
            super(context, interpolator);
            this.duration = duration;
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel, int duration) {
            super(context, interpolator, flywheel);
            this.duration = duration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}