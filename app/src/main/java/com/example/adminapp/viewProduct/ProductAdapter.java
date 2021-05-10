package com.example.adminapp.viewProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapp.addproduct.AndroidVersion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.example.adminapp.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {



    private ArrayList<AndroidVersion> android;
    private Context context;

    public ProductAdapter(Context context,ArrayList<AndroidVersion> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_product, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder viewHolder, int i) {

//        viewHolder.tv_android.setText(android.get(i).getDescription());
//        Picasso.with(context).load(android.get(i).getImageUrls().get(i).getUrl()).resize(240, 120).into(viewHolder.img_android);
        viewHolder.tv_android.setText(android.get(i).getAndroid_version_name());
        Picasso.with(context).load(android.get(i).getAndroid_image_url()).resize(240, 120).into(viewHolder.img_android);

    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_android;
        private ImageView img_android;
        public ViewHolder(View view) {
            super(view);

            tv_android = (TextView)view.findViewById(R.id.name);
            img_android = (ImageView) view.findViewById(R.id.image);
        }
    }

}