package com.example.adminapp.viewProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.adminapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MultipleProduct extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener{

    // creating object of ViewPager
    ViewPager mViewPager;
    // images array
//    int[] images = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4,
//            R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8};
    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;
    SliderLayout sliderLayout ;
    HashMap<String, String> HashMapForURL ;
    HashMap<String, Integer> HashMapForLocalRes ;
    ArrayList<String> numbersList;
    String imgUrl, desc, vendor, cat, qty, fab, cp, tp;
    //Integer cp, tp;
    TextView prdDesc, prdVen, prdCat, prdQty, prdFab, prdcp, prdtp;
    Button addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_product);

        numbersList = (ArrayList<String>) getIntent().getSerializableExtra("key");
        Log.e("imgurl multiplepr", String.valueOf(numbersList));

        prdDesc = findViewById(R.id.prdDesc);
        prdVen = findViewById(R.id.vendor);
        prdCat = findViewById(R.id.catVal);
        prdQty = findViewById(R.id.qtyVal);
        prdFab = findViewById(R.id.fabVal);
        prdcp = findViewById(R.id.cp);
        prdtp = findViewById(R.id.tp);

        desc = getIntent().getStringExtra("desc");
        vendor = getIntent().getStringExtra("vendor");
        cat = getIntent().getStringExtra("catVal");
        qty = getIntent().getStringExtra("quantity");
        fab = getIntent().getStringExtra("fabric");
        cp = getIntent().getStringExtra("cp");
        tp = getIntent().getStringExtra("tp");

        addImage = findViewById(R.id.addImages);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        // Initializing the ViewPager Object
//        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);
//        // Initializing the ViewPagerAdapter
//        mViewPagerAdapter = new ViewPagerAdapter(MultipleProduct.this, images);
//        // Adding the Adapter to the ViewPager
//        mViewPager.setAdapter(mViewPagerAdapter);
//        //Picasso.with(MultipleProduct.this).load(imgUrl).into(imageView);

        HashMapForURL = new HashMap<String, String>();

        sliderLayout = (SliderLayout)findViewById(R.id.slider);

        //Call this method if you want to add images from URL .
        AddImagesUrlOnline();
        prdDesc.setText(desc);
        prdVen.setText(vendor);
        prdCat.setText(cat);
        prdQty.setText(qty);
        prdFab.setText(fab);
        prdcp.setText(cp);
        prdtp.setText(tp);

        //Call this method to add images from local drawable folder .
        //AddImageUrlFormLocalRes();

        //Call this method to stop automatic sliding.
        sliderLayout.stopAutoCycle();

        for(String name : HashMapForURL.keySet()){
            TextSliderView textSliderView = new TextSliderView(MultipleProduct.this);
            textSliderView
                    //.description(name)
                    .image(HashMapForURL.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            sliderLayout.addSlider(textSliderView);
            Log.e("name", HashMapForURL.get(name));
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(MultipleProduct.this);
    }

    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void AddImagesUrlOnline(){
        HashMapForURL = new HashMap<String, String>();
//        HashMapForURL.put("CupCake", "http://www.hdwallpaperspulse.com/wp-content/uploads/2017/12/14/lovely-view-natural-image.jpg");
//        HashMapForURL.put("Donut", "http://www.hdwallpaperspulse.com/wp-content/uploads/2017/12/14/lovely-view-natural-image.jpg");
//        HashMapForURL.put("Eclair", "http://www.hdwallpaperspulse.com/wp-content/uploads/2017/12/14/lovely-view-natural-image.jpg");
//        HashMapForURL.put("Froyo", "http://www.hdwallpaperspulse.com/wp-content/uploads/2017/12/14/lovely-view-natural-image.jpg");
//        HashMapForURL.put("GingerBread", "http://www.hdwallpaperspulse.com/wp-content/uploads/2017/12/14/lovely-view-natural-image.jpg");
        for (int i = 0; i<numbersList.size();i++) {
            HashMapForURL.put("img", numbersList.get(i));
        }
        HashMapForURL = convertArrayListToHashMap(numbersList);
        Log.e("hashmapforurl", String.valueOf(HashMapForURL));
    }

    private static HashMap<String, String>
    convertArrayListToHashMap(ArrayList<String> arrayList) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (String str : arrayList) {
            hashMap.put(str, str);
        }
        return hashMap;
    }

}