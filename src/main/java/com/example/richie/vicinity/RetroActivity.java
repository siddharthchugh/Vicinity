package com.example.richie.vicinity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.richie.vicinity.Jpojo.Model;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RetroActivity extends AppCompatActivity {

    TextView city, status, humidity, pressure;
    String url = "http://api.openweathermap.org/data/2.5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_retro);

        city = (TextView) findViewById(R.id.txt_city);
        status = (TextView) findViewById(R.id.txt_status);
        humidity = (TextView) findViewById(R.id.txt_humidity);
        pressure = (TextView) findViewById(R.id.txt_press);

        //making object of RestAdapter
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(url).build();

        //Creating Rest Services
        RestInterface restInterface = adapter.create(RestInterface.class);

        //Calling method to get whether report
        restInterface.getWheatherReport(new Callback<Model>() {
            @Override
            public void success(Model model, Response response) {
                city.setText("city :"+model.getName());
                status.setText("Status :"+model.getWeather().get(0).getDescription());
                humidity.setText("humidity :"+model.getMain().getHumidity().toString());
                pressure.setText("pressure :"+model.getMain().getPressure().toString());
            }

            @Override
            public void failure(RetrofitError error) {

                String merror = error.getMessage();
            }
        });

    }


}
