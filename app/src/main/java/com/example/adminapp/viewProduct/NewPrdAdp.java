package com.example.adminapp.viewProduct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapp.R;
import com.example.adminapp.filsort.Item;
import com.example.adminapp.productgetset.Example;
import com.example.adminapp.productgetset.Vendor;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewPrdAdp extends RecyclerView.Adapter<NewPrdAdp.ViewHolder> {

    private ArrayList<Example> arrayData;
    private Activity activity;
    Bitmap myBitmap;

//    public ProductAdapter(Activity activity, ArrayList<ListOfPrduct> arrayData){
//        this.activity = activity;
//        this.arrayData = arrayData;
//    }

    private Example[]    data;
    private Context context;
    List<Item> items;
    ArrayList<String> numbers = new ArrayList<>();

    public NewPrdAdp(Context context, Example[] data){
        this.context  = context;
        this.data = data;
    }

    @NonNull
    @Override
    public NewPrdAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewPrdAdp.ViewHolder holder, int position) {
        Example mainn = data[position];

//        if (mainn.getImageUrls() !=  null) {
        if (mainn.getPrdDesignRes() !=  null) {
            holder.tv_android.setText(mainn.getDescription());
            holder.tp.setText(Integer.toString(mainn.getTraderPrice()));
            Log.e("categoryvalue", String.valueOf(mainn.getCategory().getCategoryValue()));
            holder.cp.setText(Integer.toString(mainn.getCustomerPrice()));
            holder.byvendor.setText(String.valueOf(mainn.getVendor().getVendorName()));
            for (int i=0; i<mainn.getImageUrls().size();i++){
                Log.e("imgurl", mainn.getImageUrls().get(i).getUrl());
                Picasso.with(context).load(mainn.getPrdDesignRes().get(0).getImageUrls().get(0).getUrl()).resize(240, 120).into(holder.img_android);
            }
            //Picasso.with(context).load(mainn.getImageUrls().get(0).getUrl()).resize(240, 120).into(holder.img_android);

            /*
            holder.img_android.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    getBitmapFromURL(mainn.getImageUrls().get(0).getUrl());
//                    Intent intent = new Intent(context, ProductDetail.class);
//                    intent.putExtra("image", String.valueOf(mainn.getImageUrls().get(0).getUrl()));
//                    intent.putExtra("desc", mainn.getDescription());
//                    intent.putExtra("vendor", String.valueOf(mainn.getVendor().getVendorName()));
//                    intent.putExtra("catVal", String.valueOf(mainn.getCategory().getCategoryValue()));
//                    intent.putExtra("cp", Integer.toString(mainn.getCustomerPrice()));
//                    intent.putExtra("tp", Integer.toString(mainn.getTraderPrice()));
//                    intent.putExtra("fabric", String.valueOf(mainn.getFabric().getValue()));
//                    intent.putExtra("quantity", String.valueOf(mainn.getPrdDesignRes().get(0).quantity));
//                    Log.e("intent", String.valueOf(mainn.getImageUrls().get(0).getUrl()));
//                    context.startActivity(intent);

                    //its working broooo
//                    for (int i=0;i<mainn.getImageUrls().size();i++) {
                    for (int i=0;i<mainn.getPrdDesignRes().size();i++) {
                        //intent.putExtra("image", String.valueOf(mainn.getImageUrls().get(i).getUrl()));
                       // numbers.add(String.valueOf(mainn.getImageUrls().get(i).getUrl()));
                        numbers.add(mainn.getPrdDesignRes().get(i).getImageUrls().get(i).getUrl());
                    }
                    Intent intent = new Intent(context, MultipleProduct.class);
                    intent.putExtra("key", numbers);
                    intent.putExtra("desc", mainn.getDescription());
                    intent.putExtra("vendor", String.valueOf(mainn.getVendor().getVendorName()));
                    intent.putExtra("catVal", String.valueOf(mainn.getCategory().getCategoryValue()));
                    intent.putExtra("cp", Integer.toString(mainn.getCustomerPrice()));
                    intent.putExtra("tp", Integer.toString(mainn.getTraderPrice()));
                    intent.putExtra("fabric", String.valueOf(mainn.getFabric().getValue()));
                    intent.putExtra("quantity", String.valueOf(mainn.getPrdDesignRes().get(0).quantity));
                    Log.e("intent", String.valueOf(mainn.getImageUrls().get(0).getUrl()));
                    context.startActivity(intent);
                }
            });
            */


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    numbers.clear();
                    String quantity = null;
                    if (mainn.getPrdDesignRes().equals("[]")) {
                        numbers.add("http://www.hdwallpaperspulse.com/wp-content/uploads/2017/12/14/lovely-view-natural-image.jpg");
                        quantity = String.valueOf(0);
                    }
                    else if (mainn.getPrdDesignRes().size()>0){
                        for (int i = 0; i < mainn.getPrdDesignRes().get(0).getImageUrls().size(); i++) {
                            //  if(mainn.)
                            numbers.add(mainn.getPrdDesignRes().get(0).getImageUrls().get(i).getUrl());
                            quantity = String.valueOf(mainn.getPrdDesignRes().get(0).quantity);
                        }
                    }
                    Intent intent = new Intent(context, MultipleProduct.class);
                    intent.putExtra("key", numbers);
                    intent.putExtra("desc", mainn.getDescription());
                    intent.putExtra("vendor", String.valueOf(mainn.getVendor().getVendorName()));
                    intent.putExtra("catVal", String.valueOf(mainn.getCategory().getCategoryValue()));
                    intent.putExtra("cp", Integer.toString(mainn.getCustomerPrice()));
                    intent.putExtra("tp", Integer.toString(mainn.getTraderPrice()));
                    intent.putExtra("fabric", String.valueOf(mainn.getFabric().getValue()));
                    intent.putExtra("quantity", quantity);
                   // Log.e("intent", String.valueOf(mainn.getImageUrls().get(0).getUrl()));
                    context.startActivity(intent);
                }
            });


        }
        else {
            holder.tp.setText("90890");
            Picasso.with(context).load(R.drawable.ic_baseline_person_24).resize(240, 120).into(holder.img_android);
        }
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
    @Override
    public int getItemCount() {
        return data.length;
       // return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_android, byvendor, cp, tp;
        private ImageView img_android;
        CardView cardView;
        public ViewHolder(View view) {
            super(view);
            tv_android = (TextView)view.findViewById(R.id.name);
            img_android = (ImageView) view.findViewById(R.id.image);
            byvendor = (TextView) view.findViewById(R.id.byVendor);
            cp = (TextView) view.findViewById(R.id.custprice);
            tp = (TextView) view.findViewById(R.id.traderprice);
            cardView = view.findViewById(R.id.itemCard);
        }
    }
}
