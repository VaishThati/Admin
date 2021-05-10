package com.example.adminapp.viewProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adminapp.R;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    TouchImageView imageView;
    String imgUrl, desc, vendor, cat, qty, fab, cp, tp;
    //Integer cp, tp;
    TextView prdDesc, prdVen, prdCat, prdQty, prdFab, prdcp, prdtp;
    boolean isImageFitToScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageView = findViewById(R.id.imageview);
        prdDesc = findViewById(R.id.prdDesc);
        prdVen = findViewById(R.id.vendor);
        prdCat = findViewById(R.id.catVal);
        prdQty = findViewById(R.id.qtyVal);
        prdFab = findViewById(R.id.fabVal);
        prdcp = findViewById(R.id.cp);
        prdtp = findViewById(R.id.tp);

        imgUrl = getIntent().getStringExtra("image");
        desc = getIntent().getStringExtra("desc");
        vendor = getIntent().getStringExtra("vendor");
        cat = getIntent().getStringExtra("catVal");
        qty = getIntent().getStringExtra("quantity");
        fab = getIntent().getStringExtra("fabric");
//        cp = Integer.valueOf(getIntent().getStringExtra("cp"));
//        tp = Integer.valueOf(getIntent().getStringExtra("tp"));
        cp = getIntent().getStringExtra("cp");
        tp = getIntent().getStringExtra("tp");

//        Picasso.with(ProductDetail.this).load(url).resize(240, 120).into(imageView);
        Picasso.with(ProductDetail.this).load(imgUrl).into(imageView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isImageFitToScreen) {
//                    isImageFitToScreen=false;
//                    imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
//                    imageView.setAdjustViewBounds(true);
//                }else{
//                    isImageFitToScreen=true;
//                    imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
//            }
//        });
        prdDesc.setText(desc);
        prdVen.setText(vendor);
        prdCat.setText(cat);
        prdQty.setText(qty);
        prdFab.setText(fab);
        prdcp.setText(cp);
        prdtp.setText(tp);
    }
}