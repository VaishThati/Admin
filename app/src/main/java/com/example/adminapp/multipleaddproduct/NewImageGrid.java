package com.example.adminapp.multipleaddproduct;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminapp.DataPart;
import com.example.adminapp.R;
import com.example.adminapp.VolleyMultipartRequest;
import com.example.adminapp.viewProduct.ViewProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewImageGrid extends AppCompatActivity {
    private Button btn, addImg;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    private GridView gvGallery;
    private GalleryAdapter galleryAdapter;
    String imageaddurl = "http://103.150.187.59:54691/api/Product/AddProductDesignWithImages";
    String getExistingdataUrl = "http://103.150.187.59:54691/api/Product/existingData";
    ArrayList<Uri> mArrayUri;
    Uri mImageUri;
    Spinner catSpinner, sizeSpinner;
    EditText size, quantity;
    int printsp, id, sizeId;
    ArrayList<String> ConfessName, SizeName;
    ArrayList<Integer> ConfessIDArray, SizeIDArray;
    public int confessintId, sizeIntID;
    String Category, category_Value, Size, size_Value;
    List<Integer> list2 = new ArrayList<Integer>();
    List<Integer> list3 = new ArrayList<Integer>();

    private ProgressDialog pDialog;
    List<String> str_list = new ArrayList<String>();
    JSONObject obj=new JSONObject();


    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://10.64.1.94/";
    private Uri uri;
    private ApiService uploadService;
    ProgressDialog progressDialog;
    private static final String TAG = NewImageGrid.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_images);
        btn = findViewById(R.id.selImg);
        gvGallery = (GridView)findViewById(R.id.gv);
        pDialog=new ProgressDialog(this);


        Intent mIntent = getIntent();
        printsp = mIntent.getIntExtra("productidsp", 0);
        Log.e("printsp", String.valueOf(printsp));

        ConfessName = new ArrayList<String>();
        ConfessIDArray = new ArrayList<Integer>();
        SizeName = new ArrayList<String>();
        SizeIDArray = new ArrayList<Integer>();
        getColorList(getExistingdataUrl);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
                */

                // This is working
                if(ContextCompat.checkSelfPermission(NewImageGrid.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(NewImageGrid.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            2);
                }else{
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
                }
            }
        });
        addImg = findViewById(R.id.addImg);
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This is Working
                try {
                    obj.put("SizeId", sizeIntID);
                    obj.put("Qty", Integer.parseInt(quantity.getText().toString()));
                    Log.e("sizeidandqty", String.valueOf(obj));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                str_list.add(String.valueOf(obj));
                Log.e("str_list", String.valueOf(str_list));
                uploadBitmap();
                //uploadMultiFile();
            }
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Change base URL to your upload server URL.
        uploadService = new Retrofit.Builder()
                .baseUrl("http://103.150.187.59:54691/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");


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

        sizeSpinner = findViewById(R.id.size_spinner);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int x = sizeSpinner.getSelectedItemPosition();
                sizeIntID = list3.get(x);
                Log.e("sizeIntID", String.valueOf(sizeIntID));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quantity = findViewById(R.id.qtyEt);
    }

    // This is Working
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){
                    mImageUri=data.getData();
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);
                    Log.e("mArrayUri", String.valueOf(mArrayUri));
//                    for (int i=0;i<mArrayUri.size();i++){
//                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
//                        Log.e("bbb", String.valueOf(bitmap));
//                        //Bitmap bmp = intent.getExtras().get("data");
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                        byteArray = stream.toByteArray();
//                        bitmap.recycle();
//                    }
                    galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
//                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            Log.d("uri", String.valueOf(uri));
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            Log.e("imagesEncodedList", String.valueOf(imagesEncodedList));
                            Log.e("mUri", String.valueOf(mArrayUri));

                            cursor.close();

                            galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);
//                            for (int j=0;j<mArrayUri.size();j++){
//                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
//                                Log.e("cccc", String.valueOf(bitmap));
//                                //Bitmap bmp = intent.getExtras().get("data");
//                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                                byteArray = stream.toByteArray();
//                                bitmap.recycle();
//                            }
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, NewImageGrid.this);
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                progressDialog.show();
                String filePath = getRealPathFromURIPath(uri, NewImageGrid.this);
                File file = new File(filePath);
                Log.d(TAG, "filePath=" + filePath);
                //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                Call<UploadObject> fileUpload = uploadService.uploadSingleFile(fileToUpload, filename);
                fileUpload.enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, retrofit2.Response<UploadObject> response) {
                        progressDialog.dismiss();
                        Toast.makeText(NewImageGrid.this, "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                        Toast.makeText(NewImageGrid.this, "Success " + response.body().getSuccess(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d(TAG, "Error " + t.getMessage());
                    }
                });
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

     */


    private void uploadMultiFile() {
        progressDialog.show();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("ProductId", String.valueOf(printsp));
        builder.addFormDataPart("colorID", "1");
//                headers.put("ProductSizeAndQuantityJson", "[{\"SizeId\":4,\"Qty\":33333}]");
        builder.addFormDataPart("ProductSizeAndQuantityJson", String.valueOf(str_list));

        // Map is used to multipart the file using okhttp3.RequestBody
        // Multiple Images
        for (int i = 0; i < mArrayUri.size(); i++) {
            File file = new File(String.valueOf(mArrayUri.get(i)));
            builder.addFormDataPart("ImagesWithDesign", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }

        MultipartBody requestBody = builder.build();
        Call<ResponseBody> call = uploadService.uploadMultiFile(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Toast.makeText(NewImageGrid.this, "Success " + response.message(), Toast.LENGTH_LONG).show();
                Toast.makeText(NewImageGrid.this, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, "Error " + t.getMessage());
            }
        });

    }

    // It is working bas yuhi
    private void uploadBitmap() {
        pDialog.show();
        pDialog.setMessage("Please Wait...");
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, imageaddurl,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                       // a(response);
                        pDialog.dismiss();
                        Log.e("resp", String.valueOf(response));
                        Log.e("reposneupload", response.data.toString());
                        Toast.makeText(NewImageGrid.this, "Image Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewImageGrid.this, ViewProduct.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                        //parseVolleyError(error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> headers = new HashMap<>();
                headers.put("ProductId", String.valueOf(printsp));
                headers.put("colorID", "1");
//                headers.put("ProductSizeAndQuantityJson", "[{\"SizeId\":4,\"Qty\":33333}]");
                headers.put("ProductSizeAndQuantityJson", String.valueOf(str_list));
                headers.put("ImagesWithDesign", String.valueOf(mArrayUri));
                //headers.put("ImagesWithDesign", String.valueOf(byteArray));
                Log.e("hed1", String.valueOf(headers));
                return headers;
            }

            /* This is ArrayList<DataPart>
            @Override
            protected Map<String, ArrayList<DataPart>> getByteData() {
                Map<String, ArrayList<DataPart>> headers = new HashMap<>();
                ArrayList<DataPart> images = new ArrayList<>();
                Bitmap bitmap = null;
                Bitmap bitmap2 = null;
                Log.d("marrayurisize", String.valueOf(mArrayUri.size()));
                for (int i=0;i<mArrayUri.size();i++){
                    long imagename = System.currentTimeMillis();
                    Log.d("marrayuri index", String.valueOf(i));
                    Log.d("marrayuri image", String.valueOf(mArrayUri.get(i)));
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mArrayUri.get(i));
                        images.add(new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                headers.put("ImagesWithDesign", images);
                Log.e("headersxyz", String.valueOf(headers));
                return headers;
            }
            */

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> headers = new HashMap<>();
                long imagename = System.currentTimeMillis();
                Bitmap bitmap = null;
                Bitmap bitmap2 = null;
                for (int i=0;i<mArrayUri.size();i++){
                    try {
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), mArrayUri.get(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    headers.put("ImagesWithDesign", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap2)));
                    Log.e("image 1", String.valueOf(headers));
                }
                Log.e("headersxyz1", String.valueOf(headers));
                return headers;
            }


            @Override
            protected Map<String, DataPart> getByteData2() {
                Map<String, DataPart> headers = new HashMap<>();
                long imagename = System.currentTimeMillis();
                Bitmap bitmap = null;
                Bitmap bitmap2 = null;
                for (int i=0;i<mArrayUri.size();i++){
                    try {
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), mArrayUri.get(1));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    headers.put("ImagesWithDesign", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap2)));
                    Log.e("image2", String.valueOf(headers));
                }
                Log.e("headersxyz2", String.valueOf(headers));
                return headers;
            }


        };
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
   //till here


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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
                    Size = jsonObject.getString("productSizes");
                    Log.e("productSizes", Size);
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
                    JSONArray jsonArray1 = new JSONArray(Size);
                    for (int i=0;i<jsonArray1.length();i++){
                        JSONObject xyz = jsonArray1.getJSONObject(i);
                        size_Value = xyz.getString("value");
                        sizeId = xyz.getInt("id");
                        SizeName.add(size_Value);
                        SizeIDArray.add(sizeId);
                        list3.addAll(Collections.singleton(xyz.getInt("id")));
                    }
                    catSpinner.setAdapter(new ArrayAdapter<String>(NewImageGrid.this, android.R.layout.simple_spinner_dropdown_item, ConfessName));
                    sizeSpinner.setAdapter(new ArrayAdapter<String>(NewImageGrid.this, android.R.layout.simple_spinner_dropdown_item, SizeName));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
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
                Toast.makeText(NewImageGrid.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 1000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
}
