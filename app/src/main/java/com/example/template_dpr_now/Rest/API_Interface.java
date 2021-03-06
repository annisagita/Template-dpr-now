package com.example.template_dpr_now.Rest;

import com.example.template_dpr_now.Pengaduan_Activity.GetPengaduan;
import com.example.template_dpr_now.Pengaduan_Activity.Pengaduan;
import com.example.template_dpr_now.Pengaduan_Activity.PostPutDelPengaduan;
import com.example.template_dpr_now.Streaming_Activity.YouTubeModel;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface API_Interface {
    @GET("/pengaduan/")
    Call<GetPengaduan> getPengaduan();

    @GET("/pengaduan/")
    Call<List<Pengaduan>>  ambilPengaduan();

    //mengirim raw data, tanpa di FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("/pengaduan/")
    Call<PostPutDelPengaduan> postPengaduan(@Body  Map <String,String> mapPengaduan);


    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("/login/")
    Call<ResponseBody> postAkun(@Body  @Field("nama") String nama,
                                @Field("password") String password);

    @Headers("Content-Type: application/json")
    @POST("/login/")
    Call<ResponseBody> postAkunn(@Body Map <String,String> mapAkun);

    //get Youtube model punya babeh faiz
    @GET
    Call<YouTubeModel> getVideos(@Url String url);
//    @FormUrlEncoded
//    @PUT("kontak")
//    Call<PostPutDelPengaduan> putKontak(@Field("id") String id,
//                                     @Field("nama") String nama,
//                                     @Field("nomor") String nomor);
//    @FormUrlEncoded
//    @HTTP(method = "DELETE", path = "kontak", hasBody = true)
//    Call<PostPutDelPengaduan> deleteKontak(@Field("id") String id);
}
