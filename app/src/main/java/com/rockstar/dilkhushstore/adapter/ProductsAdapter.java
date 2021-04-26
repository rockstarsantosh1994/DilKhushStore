package com.rockstar.dilkhushstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rockstar.dilkhushstore.DashBoardActivity;
import com.rockstar.dilkhushstore.R;
import com.rockstar.dilkhushstore.fragment.ShowMappingFragment;
import com.rockstar.dilkhushstore.model.products.ProductBO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>{

    private Context context;
    private ArrayList<ProductBO> productBOArrayList;
    private DashBoardActivity dashBoardActivity;

    public ProductsAdapter(Context context, ArrayList<ProductBO> productBOArrayList, DashBoardActivity dashBoardActivity) {
        this.context = context;
        this.productBOArrayList = productBOArrayList;
        this.dashBoardActivity=dashBoardActivity;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.row_products,parent,false);
        return new ProductsViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {

        Glide.with(context).load(productBOArrayList.get(position).getImagepath()).into(holder.ivProductImage);

        holder.tvTitle.setText(productBOArrayList.get(position).getProductname());

        holder.tvPrice.setText(productBOArrayList.get(position).getPrice()+" -/"+productBOArrayList.get(position).getUnitprice());

        holder.btnAddToCart.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            if(productBOArrayList.get(position).getMapping().size()==0){

            }else{
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ShowMappingFragment dialog = new ShowMappingFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("product_bo",productBOArrayList.get(position));
                dialog.setArguments(bundle);
                dialog.show(ft, "Tag");
            }
        });
    }

    @Override
    public int getItemCount( ) {
        return productBOArrayList.size();
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_product_img)
        ImageView ivProductImage;
        @BindView(R.id.btn_add_to_cart)
        AppCompatButton btnAddToCart;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
