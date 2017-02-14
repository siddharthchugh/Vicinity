package com.example.richie.vicinity;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Richie on 13-06-2016.
 */
public interface Insertdata {
    @FormUrlEncoded
    @POST("/RetrofitExample/insert.php")
    public void insertUser(
       //     @Field("name") String name,
            @Field("Username") String username,
            @Field("Password") String password,
         //   @Field("email") String email,
            Callback<Response> callback);
}