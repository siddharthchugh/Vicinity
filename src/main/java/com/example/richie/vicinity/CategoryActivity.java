package com.example.richie.vicinity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import static java.security.AccessController.getContext;


public class CategoryActivity extends AppCompatActivity {


    private final String CATEGORY_PREFERENCE = "UserCategory";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        SharedPreferences shareAccountInformation = getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
        String restorebankname = shareAccountInformation.getString("title", "No value found").toString();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Category :" + restorebankname);
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}
