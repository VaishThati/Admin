package com.example.adminapp.addproduct;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminapp.R;
import com.example.adminapp.VolleyMultipartRequest;
import com.example.adminapp.viewProduct.ViewProduct;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddImages extends AppCompatActivity {

    String imageaddurl = "http://103.150.187.59:54691/api/Product/AddProductDesignWithImages";
    String getExistingdataUrl = "http://103.150.187.59:54691/api/Product/existingData";
    String encodeImageString;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private String filePath;
    int printsp, id;
    Button selectImg, addImg;
    String Category, category_Value;
    ArrayList<String> ConfessName;
    ArrayList<Integer> ConfessIDArray;
    public int confessintId;
    List<Integer> list2 = new ArrayList<Integer>();
    Spinner catSpinner;
//    [{"SizeId":4,"Qty":33333}]
    Map<String, String> newmaps = new HashMap<String, String>();
    List<String> str_list = new ArrayList<String>();
    JSONObject obj=new JSONObject();
    private Bitmap bitmap;
    EditText size, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_images);

        Intent mIntent = getIntent();
        printsp = mIntent.getIntExtra("productidsp", 0);
        Log.e("printsp", String.valueOf(printsp));

        ConfessName = new ArrayList<String>();
        ConfessIDArray = new ArrayList<Integer>();
        getColorList(getExistingdataUrl);

        catSpinner = findViewById(R.id.color_spinner);
        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int x = catSpinner.getSelectedItemPosition();
                confessintId = list2.get(x);
                Log.e("confessintID", String.valueOf(confessintId));

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quantity = findViewById(R.id.qtyEt);

//        newmaps.put("SizeId", size.getText().toString());
//        Log.e("newmaps", String.valueOf(newmaps));
//        try {
//            obj.put("SizeId", size.getText().toString());
//            Log.e("obj", String.valueOf(obj));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
////                str_list.add(String.valueOf(newmaps));
//        str_list.add(String.valueOf(obj));

        selectImg = findViewById(R.id.selImg);
        /*
        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(AddImages.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(AddImages.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(AddImages.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");
                    showFileChooser();
                }

            }
        });
        */

        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AddImages.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        addImg = findViewById(R.id.addImg);
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size.getText().toString().isEmpty() || quantity.getText().toString().isEmpty()){
                    Toast.makeText(AddImages.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        //obj.put("SizeId", Integer.parseInt(size.getText().toString()));
                        obj.put("Qty", Integer.parseInt(quantity.getText().toString()));
                        Log.e("sizeidandqty", String.valueOf(obj));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    str_list.add(String.valueOf(obj));
                    Log.e("str_list", String.valueOf(str_list));
                   // uploadBitmap(); //use this
                   // uploaddatatodb();
                }

            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Uri filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
               // img.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    */

    /*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            List<Bitmap> list = new ArrayList<>();
            ClipData clipData = data.getClipData();
            if (clipData!=null){
                for (int i = 0; i<clipData.getItemCount();i++){
                    Uri imageUrr = clipData.getItemAt(i).getUri();
                    filePath = getPath(imageUrr);
                    Log.e("fp", filePath);
                    try {
                        InputStream is = getContentResolver().openInputStream(imageUrr);
                        Bitmap bmp = BitmapFactory.decodeStream(is);
                        list.add(bmp);
                        Log.e("many", String.valueOf(list));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                Uri img = data.getData();
                filePath = getPath(img);
                try {
                    InputStream is = getContentResolver().openInputStream(img);
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    list.add(bmp);
                    Log.e("one", String.valueOf(list));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            filePath = getPath(picUri);
            if (filePath != null) {
                try {
                    Log.d("filePath", String.valueOf(filePath));
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                    //uploadBitmap(bitmap);
//                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(AddImages.this,"no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

//    private void uploadBitmap() {
//        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, imageaddurl,
//                new Response.Listener<NetworkResponse>() {
//                    @Override
//                    public void onResponse(NetworkResponse response) {
//                        a(response);
//                        Log.e("resp", String.valueOf(response));
//                        Log.e("reposneupload", response.data.toString());
//                        Toast.makeText(AddImages.this, "Image Added Successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(AddImages.this, ViewProduct.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                },
//                new ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                        Log.e("GotError",""+error.getMessage());
//                        //parseVolleyError(error);
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> headers = new HashMap<>();
////                headers.put("ProductId", String.valueOf(printsp));
//                headers.put("ProductId", String.valueOf(printsp));
//                headers.put("colorID", String.valueOf(confessintId));
//                headers.put("ProductSizeAndQuantityJson", String.valueOf(str_list));
//                Log.e("hed1", String.valueOf(headers));
//                return headers;
//            }
//
//            @Override
//            protected Map<String, DataPart> getByteData() {
//                Map<String, DataPart> headers = new HashMap<>();
//                long imagename = System.currentTimeMillis();
//                headers.put("ImagesWithDesign", new VolleyMultipartRequest.DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
//            //    headers.put("ImagesWithDesign", new DataPart(imagename + ".png", getFileDataFromDrawable(list2)));
//                Log.e("headers", String.valueOf(headers));
//                return headers;
//            }
//        };
//        //adding the request to volley
//        Volley.newRequestQueue(this).add(volleyMultipartRequest);
//    }


    /* use this
    private void uploadBitmap() {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, imageaddurl,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        a(response);
                        Log.e("resp", String.valueOf(response));
                        Log.e("reposneupload", response.data.toString());
                        Toast.makeText(AddImages.this, "Image Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddImages.this, ViewProduct.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                        //parseVolleyError(error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> headers = new HashMap<>();
//                headers.put("ProductId", String.valueOf(printsp));
                headers.put("ProductId", String.valueOf(printsp));
                headers.put("colorID", String.valueOf(confessintId));
                headers.put("ProductSizeAndQuantityJson", String.valueOf(str_list));
                Log.e("hed1", String.valueOf(headers));
                return headers;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> headers = new HashMap<>();
                long imagename = System.currentTimeMillis();
                headers.put("ImagesWithDesign", new VolleyMultipartRequest.DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                //    headers.put("ImagesWithDesign", new DataPart(imagename + ".png", getFileDataFromDrawable(list2)));
                Log.e("headers", String.valueOf(headers));
                return headers;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
    */

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void a(NetworkResponse response){
        try {
            String responseB = new String(response.data, "utf-8");
            JSONObject data = new JSONObject(responseB);
            JSONArray errors = data.getJSONArray("errors");
            JSONObject jsonMessage = errors.getJSONObject(0);
            String message = jsonMessage.getString("message");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void uploaddatatodb()
    {
        StringRequest request=new StringRequest(Request.Method.POST, imageaddurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.e("addimagereso", response);
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> map=new HashMap<String, String>();
                map.put("ProductId", String.valueOf(printsp));
                map.put("colorID", String.valueOf(confessintId));
                map.put("ProductSizeAndQuantityJson", String.valueOf(str_list));
                map.put("ImagesWithDesign",encodeImageString);
                return map;
            }
        };


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


    private void getColorList(String confessionList_url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(confessionList_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String responseOrg) {
                Log.e("respn", responseOrg);
                try{
                    JSONObject jsonObject = new JSONObject(responseOrg);
                    Category = jsonObject.getString("productColor");
                    Log.e("productColor", Category);

                    JSONArray jsonArray = new JSONArray(Category);
                    Log.e("jsonaarya", String.valueOf(jsonArray));
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject abc = jsonArray.getJSONObject(i);
                        category_Value = abc.getString("productColorValue");
                        Log.e("productColorValue", category_Value);
                        id = abc.getInt("productColor_ID");
                        Log.e("productColor_ID", String.valueOf(id));
                        ConfessName.add(category_Value);
                        ConfessIDArray.add(id);
                        list2.addAll(Collections.singleton(abc.getInt("productColor_ID")));
                    }

                    catSpinner.setAdapter(new ArrayAdapter<String>(AddImages.this, android.R.layout.simple_spinner_dropdown_item, ConfessName));
                                    }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(AddImages.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 1000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
}