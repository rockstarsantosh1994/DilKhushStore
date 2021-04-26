package com.rockstar.dilkhushstore.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rockstar.dilkhushstore.AddToCartActivity;
import com.rockstar.dilkhushstore.R;
import com.rockstar.dilkhushstore.model.products.ProductBO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.AddToCartViewHolder>{

    private Context context;
    private ArrayList<ProductBO> productBOArrayList;
    private AddToCartActivity addToCartActivity;

    public AddToCartAdapter(Context context, ArrayList<ProductBO> productBOArrayList,AddToCartActivity addToCartActivity) {
        this.context = context;
        this.productBOArrayList = productBOArrayList;
        this.addToCartActivity=addToCartActivity;
    }

    @NonNull
    @Override
    public AddToCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.row_add_to_cart,parent,false);
        return new AddToCartViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AddToCartViewHolder holder, int position) {
        Glide.with(context).load(productBOArrayList.get(position).getImagepath()).into(holder.ivProductImage);

        holder.tvTitle.setText(productBOArrayList.get(position).getProductname());

        holder.tvPrice.setText(productBOArrayList.get(position).getPrice()+" -/"+productBOArrayList.get(position).getUnitprice());

        holder.tvQty.setText("1* "+productBOArrayList.get(position).getMappingBO().getPvalue()+" "+productBOArrayList.get(position).getMappingBO().getPunit());

        holder.tvTotalProduct.setText("Rs- "+productBOArrayList.get(position).getMappingBO().getPrice()+" -/");

        holder.ivDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.app_name)
                    .setMessage("Are you sure want to remove product?")
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.

                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Continue with delete operation
                        productBOArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,productBOArrayList.size());
                        Paper.book().write("product_cart",productBOArrayList);
                        try{
                            int sum = 0;
                            for(int i = 0; i <productBOArrayList.size(); i++)
                                sum += Integer.parseInt(productBOArrayList.get(i).getMappingBO().getPrice());
                            addToCartActivity.getTotal(sum);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                       /* Intent intent=new Intent(context,AddToCartActivity.class);
                        context.startActivity(intent);
                        ((AppCompatActivity)context).finish();*/
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }

    @Override
    public int getItemCount( ) {
        return productBOArrayList.size();
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
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        public AddToCartViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
