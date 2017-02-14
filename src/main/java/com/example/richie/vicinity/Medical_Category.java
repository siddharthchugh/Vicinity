package com.example.richie.vicinity;


import com.example.richie.vicinity.Pojo.Category;
import com.example.richie.vicinity.Pojo.Dashboard_items;
import com.example.richie.vicinity.Pojo.HospitalDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richie on 01-11-2015.
 */
public class Medical_Category {


    public static List<Category> parseFeed(String content) {

         Category list;

        try {
            JSONArray ar = new JSONArray(content);
            List<Category> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                list = new Category()  ;
                JSONObject obj = ar.getJSONObject(i);
                list.setC_id(obj.getString("C_ID"));
                list.setC_name(obj.getString("C_Name"));
                movieList.add(list);

            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    public static List<HospitalDetail> medicalDescription(String content) {


        HospitalDetail ls;

        try {

/*
             JSONObject ob = new JSONObject(content);
            List<HospitalDetail> movieList = new ArrayList<>();
            HospitalDetail list = new HospitalDetail();
                 list.setTitle(ob.getString("Name"));
             */
/*  list.setBackdrop_path(ob.getString("backdrop_path"));
            list.setRelease_date(ob.getString("release_date"));
            list.setVote_average(ob.getString("vote_average"));
            list.setOverview(ob.getString("synopsis"));
*//*

            movieList.add(list);
*/
            JSONArray ar = new JSONArray(content);
            List<HospitalDetail> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                ls = new HospitalDetail()  ;
                JSONObject obj = ar.getJSONObject(i);
                ls.setTitle(obj.getString("Name"));
                ls.setCity(obj.getString("City"));
                ls.setAddresss(obj.getString("Address"));
                ls.setLat(obj.getDouble("Latitude"));
                ls.setLng(obj.getDouble("Longitude"));
                ls.setPhoneno(obj.getString("PhoneNumber"));

                movieList.add(ls);

            }


            //}
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    public static List<Dashboard_items> medicaltype(String content) {


        Dashboard_items ls;

        try {

/*
             JSONObject ob = new JSONObject(content);
            List<HospitalDetail> movieList = new ArrayList<>();
            HospitalDetail list = new HospitalDetail();
                 list.setTitle(ob.getString("Name"));
             */
/*  list.setBackdrop_path(ob.getString("backdrop_path"));
            list.setRelease_date(ob.getString("release_date"));
            list.setVote_average(ob.getString("vote_average"));
            list.setOverview(ob.getString("synopsis"));
*//*

            movieList.add(list);
*/
            JSONArray ar = new JSONArray(content);
            List<Dashboard_items> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                ls = new Dashboard_items()  ;
                JSONObject obj = ar.getJSONObject(i);
                ls.setId(obj.getString("D_id"));
                ls.setTitle(obj.getString("D_name"));

                movieList.add(ls);

            }


            //}
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

}
