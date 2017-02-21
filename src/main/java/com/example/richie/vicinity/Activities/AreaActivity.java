package com.example.richie.vicinity.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.richie.vicinity.*;
import com.example.richie.vicinity.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class AreaActivity extends AppCompatActivity implements OnMapReadyCallback  {

    private final float DEFAULT_ZOOM = 19;
    private static final String TAG = AreaActivity.class.getSimpleName();
    public GoogleMap googleMap;
    private String locationName;
    private double placeLat;
    private double placeLng;
    private double pllt;
    private double plln;
    private TextView tlocation;
    private ViewGroup info_Window;
    private TextView info_Text,info_Snippet,info_Title;
    private  CameraPosition cameraPosition;
    private int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    SupportMapFragment mapFragment;
    public InfoWindowpopup infoButtonClickLayout;
    private Button info_Send;
    MapWrapperLayout mapWrapperLayout;
    Intent in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.richie.vicinity.R.layout.activity_area);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        in = getIntent();
        final String name = in.getStringExtra("name");
        getSupportActionBar().setTitle("Location :"+ name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setCollapsible(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Display();


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        //in = new Intent();
        //locationName = in.getStringExtra("name");
     in = getIntent();
//        final String name = in.getStringExtra("name");

        Bundle b = getIntent().getExtras();
        placeLat = b.getDouble("hospital_lat");
        placeLng = b.getDouble("hospital_lng");
        pllt = placeLat;
        plln = placeLng;
        final LatLng ll = new LatLng(pllt, plln);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
        mapFragment.getMapAsync(AreaActivity.this);
        mapWrapperLayout.init(googleMap, getPixelsFromDp(AreaActivity.this, 32 + 30));
        cameraPosition =
                new CameraPosition.Builder().target(new LatLng(pllt, plln))
                        .zoom(DEFAULT_ZOOM)
                        .bearing(100)
                        .tilt(30)
                        .build();
//        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
  //              .getString(R.string.style_json)));
        this.info_Window = (ViewGroup)getLayoutInflater().inflate(R.layout.map_windowpopup, null);
        this.info_Title = (TextView)info_Window.findViewById(R.id.title);
        this.info_Snippet = (TextView)info_Window.findViewById(R.id.snippet);
        this.info_Send = (Button)info_Window.findViewById(R.id.send);

        infoButtonClickLayout = new InfoWindowpopup(info_Title,
                getResources().getDrawable(R.mipmap.ic_launcher),
                getResources().getDrawable(R.mipmap.ic_launcher))
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button

                Toast.makeText(AreaActivity.this, "Clicked", Toast.LENGTH_SHORT).show();

//             Intent in = new Intent(AreaActivity.this,GeocodeService.class);
//                startActivity(in);
            }
        };

       this.info_Send.setOnTouchListener(infoButtonClickLayout);

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {

                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                info_Window = (ViewGroup) getLayoutInflater().inflate(R.layout.map_windowpopup,null);

                Button btinfo = (Button) info_Window.findViewById(R.id.detail_Info);
                LatLng ll = marker.getPosition();
                Bundle b = getIntent().getExtras();
                placeLat = b.getDouble("hospital_lat");
                placeLng = b.getDouble("hospital_lng");
                pllt = placeLat;
                plln = placeLng;
                in = getIntent();
//                final String name = in.getStringExtra("name");
//                info_Title.setText(name.toString());
                info_Snippet.setText(Double.toString(plln)+Double.toString(pllt));
                infoButtonClickLayout.setMarker(marker);
                mapWrapperLayout.setMarkerWithInfoWindow(marker, info_Window);

                return info_Window;
            }
        });

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            checkAndRequestPermissions();
            return;
        }
      //  googleMap.setMyLocationEnabled(true);

//        if (!success) {
//            Log.e(TAG, "Style parsing failed.");
//
//}
        googleMap.addMarker(new MarkerOptions().position(ll)
        );
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, DEFAULT_ZOOM));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //ShowInfoWindow();

    }



    public void ShowInfoWindow(){
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
        mapFragment.getMapAsync(AreaActivity.this);
        mapWrapperLayout.init(googleMap, getPixelsFromDp(AreaActivity.this, 32 + 30));

//        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
        //              .getString(R.string.style_json)));
        this.info_Window = (ViewGroup)getLayoutInflater().inflate(R.layout.map_windowpopup, null);
        this.info_Title = (TextView)info_Window.findViewById(R.id.title);
        this.info_Snippet = (TextView)info_Window.findViewById(R.id.snippet);
        this.info_Send = (Button)info_Window.findViewById(R.id.send);

        infoButtonClickLayout = new InfoWindowpopup(info_Title,
                getResources().getDrawable(R.mipmap.ic_launcher),
                getResources().getDrawable(R.mipmap.ic_launcher))
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button

                Toast.makeText(AreaActivity.this, "Clicked", Toast.LENGTH_SHORT).show();

//             Intent in = new Intent(AreaActivity.this,GeocodeService.class);
//                startActivity(in);
            }
        };

        this.info_Send.setOnTouchListener(infoButtonClickLayout);

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {

                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                info_Window = (ViewGroup) getLayoutInflater().inflate(R.layout.map_windowpopup,null);

                Button btinfo = (Button) info_Window.findViewById(R.id.detail_Info);
                LatLng ll = marker.getPosition();
                Bundle b = getIntent().getExtras();
               String name = b.getString("name");
//                placeLat = b.getDouble("hospital_lat");
//                placeLng = b.getDouble("hospital_lng");
//                pllt = placeLat;
//                plln = placeLng;

                info_Title.setText(name);
                info_Snippet.setText(Double.toString(plln)+Double.toString(pllt));
                infoButtonClickLayout.setMarker(marker);
                mapWrapperLayout.setMarkerWithInfoWindow(marker, info_Window);

                return info_Window;
            }
        });
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    public void Display() {
        if (isReadStorageAllowed()) {
            //If permission is already having then showing the toast

            Toast.makeText(getApplicationContext(),"Already have the permission to access !",Toast.LENGTH_SHORT).show();

            //Existing the method with return
            return;
        }
        checkAndRequestPermissions();
    }


    private boolean checkAndRequestPermissions() {
//        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        int locationFinelocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (locationFinelocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    //    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        int resultLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        if (resultLocation == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_area,menu);
       return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

       return super.onOptionsItemSelected(item);
    }
}
