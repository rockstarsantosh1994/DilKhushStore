package com.rockstar.dilkhushstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.rockstar.dilkhushstore.R;
import com.rockstar.dilkhushstore.model.products.MappingBO;

import java.util.ArrayList;

public class MappingSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    public Context context;
    public ArrayList<MappingBO> mappingBOArrayList;

    public MappingSpinnerAdapter(Context context, ArrayList<MappingBO> mappingBOArrayList) {
        this.context = context;
        this.mappingBOArrayList = mappingBOArrayList;
    }

    @Override
    public int getCount( ) {
        return mappingBOArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mappingBOArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = View.inflate(context, R.layout.layout_spinner_main, null);
        TextView textView = view.findViewById(R.id.main);
        textView.setText(mappingBOArrayList.get(position).getPvalue() + ""
                + mappingBOArrayList.get(position).getPunit() + "  Rs"
                + mappingBOArrayList.get(position).getPrice() + "/-");
        return textView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        view = View.inflate(context, R.layout.layout_spinner_row, null);
        final TextView textView = view.findViewById(R.id.dropdown);
        textView.setText(mappingBOArrayList.get(position).getPvalue() + ""
                + mappingBOArrayList.get(position).getPunit() + "  Rs"
                + mappingBOArrayList.get(position).getPrice() + "/-");
        return view;
    }
}
