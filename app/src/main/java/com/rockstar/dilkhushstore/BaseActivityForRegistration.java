package com.rockstar.dilkhushstore;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.rockstar.dilkhushstore.services.DilKhush;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.paperdb.Paper;

public abstract class BaseActivityForRegistration extends AppCompatActivity {

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

    public abstract void onComplete(@NonNull Task<Location> task);

    public abstract void onLocationChanged(Location location);
}
