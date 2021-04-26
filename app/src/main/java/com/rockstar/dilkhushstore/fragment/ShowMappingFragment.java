package com.rockstar.dilkhushstore.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rockstar.dilkhushstore.DashBoardActivity;
import com.rockstar.dilkhushstore.R;
import com.rockstar.dilkhushstore.adapter.MappingSpinnerAdapter;
import com.rockstar.dilkhushstore.model.products.MappingBO;
import com.rockstar.dilkhushstore.model.products.ProductBO;
import com.rockstar.dilkhushstore.services.DilKhush;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowMappingFragment extends DialogFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "ShowMappingFragment";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.iv_product_img)
    ImageView ivProductImage;
    @BindView(R.id.spin_mapping)
    AppCompatSpinner spinMapping;
    @BindView(R.id.btn_add_to_cart)
    AppCompatButton btnAddToCart;

    private Context context;
    private DilKhush dilKhush;
    private ProductBO productBO;
    private MappingBO mappingBO;
    private final ArrayList<MappingBO> mappingBOArrayList=new ArrayList<>();

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
    }


    public ShowMappingFragment( ) {
        // Required empty public constructor
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_show_mapping, container, false);
        ButterKnife.bind(this,view);
        dilKhush=(DilKhush) Objects.requireNonNull(getActivity()).getApplication();

        spinMapping.setOnItemSelectedListener(this);
        btnAddToCart.setOnClickListener(this);

        Bundle arguments=getArguments();
        assert arguments != null;

        if(arguments.getParcelable("product_bo")!=null){
            productBO=arguments.getParcelable("product_bo");

            //setData To View..
            setData();
        }
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mappingBO=mappingBOArrayList.get(position);
        Log.e(TAG, "onItemSelected: "+mappingBO );
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.e(TAG, "onNothingSelected: " );
    }

    @SuppressLint("SetTextI18n")
    private void setData(){
        Glide.with(Objects.requireNonNull(getContext())).load(productBO.getImagepath()).into(ivProductImage);

        tvTitle.setText(productBO.getProductname());

        tvPrice.setText(productBO.getPrice()+" -/"+productBO.getUnitprice());

        if(productBO.getMapping().size()>0){
            mappingBOArrayList.addAll(productBO.getMapping());
            MappingSpinnerAdapter mappingSpinnerAdapter = new MappingSpinnerAdapter(getContext(),mappingBOArrayList);
            spinMapping.setAdapter(mappingSpinnerAdapter);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_to_cart) {
            DashBoardActivity.productBOArrayList.add(new ProductBO(productBO.getProductid(),
                    productBO.getProductname(),
                    productBO.getPrice(),
                    productBO.getImagepath(),
                    productBO.getUnitprice(),
                    mappingBO));

            Toast.makeText(dilKhush, "Added to cart", Toast.LENGTH_SHORT).show();

            if(DashBoardActivity.productBOArrayList.size()==0){
                DashBoardActivity.tvCount.setVisibility(View.GONE);
            }else{
                DashBoardActivity.tvCount.setVisibility(View.VISIBLE);
                DashBoardActivity.tvCount.setText(""+DashBoardActivity.productBOArrayList.size());
            }

            dismiss();
        }
    }
}