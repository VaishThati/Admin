package com.example.adminapp.multipleaddproduct;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @Multipart
    @POST("/upload")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name);

//    @Multipart
//    @POST("file_upload/fileUpload.php")
//    Call<ResponsePojo> submitData(@Part MultipartBody.Part image,
//                                  @Part("email") String email,
//                                  @Part("website") String website);


    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadMultipleFilesDynamic(
            @Part("ProductId") int ProductId,
            @Part("colorID") int colorID,
            @Part("ProductSizeAndQuantityJson") ArrayList<String> ProductSizeAndQuantityJson,
            @Part List<MultipartBody.Part> files);


    @Multipart
    @POST("/upload_multi_files/fileUpload.php")
    Call<UploadObject> uploadSingleFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);


    @Multipart
    //@POST("/upload_multi_files/MultiUpload.php")
    @POST("/upload_multi_files/MultiPartUpload.php")
    Call<UploadObject> uploadMultiFile(@Part MultipartBody.Part file1, @Part MultipartBody.Part file2, @Part MultipartBody.Part file3);

    //@Multipart
    //@FormUrlEncoded
    //@POST("/upload_multi_files/MultiUpload.php")
//    http://103.150.187.59:54691/api/Product/AddProductDesignWithImages
    @POST("/Product/AddProductDesignWithImages")
    Call<ResponseBody> uploadMultiFile(@Body RequestBody file);
}
