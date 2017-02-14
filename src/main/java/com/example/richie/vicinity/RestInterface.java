package com.example.richie.vicinity;

import com.example.richie.vicinity.Jpojo.Model;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by kundan on 8/8/2015.
 */
public interface RestInterface {

    @GET("/weather?q=London,uk&appid=abc098c77eb7b4274aa7b1ecababd36e")
    void getWheatherReport(Callback<Model> cb);

}
