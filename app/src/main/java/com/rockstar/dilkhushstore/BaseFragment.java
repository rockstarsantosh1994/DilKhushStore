package com.rockstar.dilkhushstore;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rockstar.dilkhushstore.services.DilKhush;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.paperdb.Paper;

public abstract class BaseFragment extends Fragment {
    public Context mContext;
    public Unbinder unbinder;
    public DilKhush dilKhush;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dilKhush = (DilKhush) getActivity().getApplication();
        mContext = getActivity();
        Paper.init(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(getActivityLayout(), container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    protected abstract int getActivityLayout();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
    }
}
