package com.rockstar.dilkhushstore.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.rockstar.dilkhushstore.R;
import com.rockstar.dilkhushstore.model.advertisement.AdvertismentData;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {
    private final int widthPixels;
    private final float scale;
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<AdvertismentData> advertisements;
    public static final String TAG="CustomPagerAdapter";
    int flag = 0;

    public CustomPagerAdapter(Context mContext, ArrayList<AdvertismentData> advertisements) {
        this.mContext = mContext;
        this.advertisements = advertisements;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
        scale = displayMetrics.density;
    }

    @Override
    public int getCount() {
        return advertisements.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.viewpager_image, container, false);
        ImageView iv_banner = itemView.findViewById(R.id.iv_banner);
        TextView tvAdName = itemView.findViewById(R.id.tvAdName);
        RelativeLayout rlCustomeAdapter = itemView.findViewById(R.id.rl_customeadapter);
        AdvertismentData advertisement = advertisements.get(position);

        Glide.with(mContext).load(advertisement.getImagepath()).into(iv_banner);

        if (!TextUtils.isEmpty(advertisement.getAdtitle())) {
            tvAdName.setText(advertisement.getAdtitle());
            tvAdName.setVisibility(View.VISIBLE);
        } else {
            tvAdName.setVisibility(View.GONE);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}