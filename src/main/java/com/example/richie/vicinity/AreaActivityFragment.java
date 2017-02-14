//package com.example.richie.vicinity;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
///**
// * A placeholder fragment containing a simple view.
// */
//public class AreaActivityFragment extends Fragment implements OnMapReadyCallback{
//
//
//    private Toolbar mtoolbar;
//    private Spinner spinZone;
//     private GoogleMap locationmap;
//    private AppCompatActivity contextActivty;
//    private Dialog dlGoogle;
//    Context mapContext = null;
//    private String locationName;
//    private double placeLat;
//    private double placeLng;
//    private double pllt;
//    private double plln;
//    private TextView tlocation;
//
//    int REQUEST_GOOGLE_CODE = 110;
//    View v;
//    private final float DEFAULT_ZOON = 10;
//    private SupportMapFragment mSupportMapFragment;
//  Intent in;
//    public AreaActivityFragment() {
//   setHasOptionsMenu(true);
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//      //  Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
//  v = inflater.inflate(R.layout.fragment_area,container,false);
////        SupportMapFragment mapFragment =
////                (SupportMapFragment) getActivity().getSupportFragmentManager()
////                        .findFragmentById(R.id.fragmentMap);
//    //    mapFragment.getMapAsync(this);
////          spinZone = (Spinner) v.findViewById(R.id.spinner);
////        AppCompatActivity activity = (AppCompatActivity) getActivity();
////        activity.setSupportActionBar(toolbar);
//       // in = getActivity().getIntent().getExtras();
//
////        Intent in =  getActivity().getIntent();
////        String lt = (in.getStringExtra("hospital_lat"));
////        String lg = in.getStringExtra("hospital_lng");
//
//        Bundle b = getActivity().getIntent().getExtras();
//        final String name = b.getString("name");
//        placeLat = b.getDouble("hospital_lat");
//        placeLng = b.getDouble("hospital_lng");
//        pllt = placeLat;
//        plln = placeLng;
//        final LatLng ll = new LatLng(pllt, plln);
//        if(Services()){
//            mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
//            if (mSupportMapFragment == null) {
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                mSupportMapFragment = SupportMapFragment.newInstance();
//                fragmentTransaction.replace(R.id.content_area, mSupportMapFragment).commit();
//                Toast.makeText(getContext(),"Ready for the map",Toast.LENGTH_SHORT).show();
//            }
//
//            if (mSupportMapFragment != null) {
//                mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
//                    @Override
//                    public void onMapReady(GoogleMap googleMap) {
//                        if (googleMap != null) {
//
//                            googleMap.getUiSettings().setAllGesturesEnabled(true);
//
//                            //         -> marker_latlng // MAKE THIS WHATEVER YOU WANT
//
//                            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(pllt,plln)).zoom(15.0f).build();
//                            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
//                            googleMap.moveCamera(cameraUpdate);
//                            gotoLocation(28.67378, 77.11027);
//                        }
//
//                    }
//                });
//        Toast.makeText(getContext(),"Ready for the map",Toast.LENGTH_SHORT).show();
//
//            }
//            else
//            {
//                Toast.makeText(getContext(),"Map unavailable as Googleplay Services is ont connected.",Toast.LENGTH_SHORT).show();
//
//            }
//
//
//
//        }
//        return v;
//    }
//
//    private void gotoLocation(double lat, double lng, float default_zoon) {
//        LatLng ll = new LatLng(lat,lng);
//        CameraUpdate updateCamera = CameraUpdateFactory.newLatLngZoom(ll,default_zoon);
//        locationmap.moveCamera(updateCamera);
//
//    }
//
//
//    public boolean Services() {
//
//        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
//        if (isAvailable == ConnectionResult.SUCCESS) {
//            return true;
//        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
//                dlGoogle = GooglePlayServicesUtil.getErrorDialog(isAvailable, (Activity) getContext(), REQUEST_GOOGLE_CODE);
//                dlGoogle.show();
//
//            } else {
//                Toast.makeText(getContext(), "Google Play Services not available", Toast.LENGTH_SHORT).show();
//            }
//
//            return false;
//        }
//
//
//
//
//    public void gotoLocation(double lat,double lng){
//
//        LatLng ll = new LatLng(lat,lng);
//        CameraUpdate updateCamera = CameraUpdateFactory.newLatLng(ll);
//        locationmap.moveCamera(updateCamera);
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(0, 0))
//                .title("Marker"));
//    }
//
//
////    @Override
////    public void onMapReady(GoogleMap googleMap) {
////        LatLng sydney = new LatLng(-33.867, 151.206);
////      //  gotoLocation(28.67378,77.11027);
////
//////        locationmap.setMyLocationEnabled(true);
////        locationmap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
////
////        locationmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
////    //    googleMap.setMyLocationEnabled(true);
////        locationmap.setTrafficEnabled(true);
////        locationmap.setIndoorEnabled(true);
////        locationmap.setBuildingsEnabled(true);
////        locationmap.getUiSettings().setZoomControlsEnabled(true);
////    }
//}
