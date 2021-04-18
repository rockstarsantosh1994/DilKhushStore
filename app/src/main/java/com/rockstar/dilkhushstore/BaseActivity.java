package com.rockstar.dilkhushstore;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rockstar.dilkhushstore.services.DilKhush;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.paperdb.Paper;

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;
    public Unbinder unbinder;
    public DilKhush dilKhush;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        unbinder = ButterKnife.bind(this);
        dilKhush = (DilKhush) getApplication();
        mContext = this;
        Paper.init(this);
    }

    protected abstract int getActivityLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
