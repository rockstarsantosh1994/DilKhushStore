package com.rockstar.dilkhushstore.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rockstar.dilkhushstore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.AddToCartViewHolder>{

    @NonNull
    @Override
    public AddToCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AddToCartViewHolder holder, int position) {

    }

    @Override
    public int getItemCount( ) {
        return 0;
    }

    public class AddToCartViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_total_of_product)
        TextView tvTotalProduct;
        @BindView(R.id.tv_qty)
        TextView tvQty;
        @BindView(R.id.iv_product_img)
        ImageView ivProductImage;

        public AddToCartViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
