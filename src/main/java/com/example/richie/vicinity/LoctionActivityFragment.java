package com.example.richie.vicinity;

import android.*;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoctionActivityFragment extends Fragment
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    protected final String TAG = "GET_ADDRESS_SAMPLE";



    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected String mAddress;
    protected boolean mHaveLocPerm;
    private TextView mAddressView;
    private EditText mPlaceName;

    protected AddressReceiver mAddressReceiver;
    TextView distancePoints;

    public LoctionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_loction, container, false);
        mLastLocation = null;
        mHaveLocPerm = false;
        mAddress = "";

        mAddressView = (TextView)v.findViewById(R.id.tvAddress);
        mPlaceName = (EditText)v.findViewById(R.id.etNamedLoc);
        distancePoints = (TextView) v.findViewById(R.id.distance);
        mAddressReceiver = new AddressReceiver(new Handler());

        Location startPoint=new Location("locationA");
        startPoint.setLatitude(28.668667);
        startPoint.setLongitude(77.101940);

        Location endPoint=new Location("locationA");
        endPoint.setLatitude(28.664129);
        endPoint.setLongitude(77.089542);

        double distance=startPoint.distanceTo(endPoint);
        distancePoints.setText(String.valueOf(distance));


        // Listen for clicks on our Get Address From Location Button
        v.findViewById(R.id.btnGetAddress).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        getAddressFromLoc();
                    }
                }
        );

        // Listen for clicks on the Get Address From Name
       v.findViewById(R.id.btnNamedLoc).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String theName = mPlaceName.getText().toString();
                        getAddressFromName(theName);
                    }
                }
        );

        // build the Play Services client object
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Only enable the button if we have a Geocoder installed
       v.findViewById(R.id.btnGetAddress).setEnabled(Geocoder.isPresent());

        return v;

    }

    public void getAddressFromLoc() {
        if (mGoogleApiClient.isConnected() && mHaveLocPerm) {
            try {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            }
            catch (SecurityException e) {
                Log.d(TAG, "Could not access LocationServices");
            }

            if (mLastLocation != null) {
                // Create the intent service responsible for getting the address.
                Intent intent = new Intent(getContext(), GeoLocate.class);

                intent.setAction(ConstantsGoogle.ACTION_ADDRESS_FROM_LOC);
                intent.putExtra(ConstantsGoogle.RECEIVER, mAddressReceiver);
                intent.putExtra(ConstantsGoogle.LOCATION_KEY, mLastLocation);

                // Start the service. If the service isn't already running, it is instantiated and started
                // (creating a process for it if needed); if it is running then it remains running. The
                // service kills itself automatically once all intents are processed.
                getActivity().startService(intent);
            }
        }
    }

    protected void getAddressFromName(String name) {
        if (name != null && !name.isEmpty()) {
            // Create the intent service responsible for getting the address.
            Intent intent = new Intent(getContext(), GeoLocate.class);

            intent.setAction(ConstantsGoogle.ACTION_LOC_FROM_ADDR);
            intent.putExtra(ConstantsGoogle.RECEIVER, mAddressReceiver);
            intent.putExtra(ConstantsGoogle.PLACE_NAME_KEY, name);

            getActivity().startService(intent);
        }
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        int permCheck = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            mHaveLocPerm = true;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "Connection was suspended for some reason");
        mGoogleApiClient.connect();
    }    class AddressReceiver extends ResultReceiver {
        public AddressReceiver(Handler handler) {
            super(handler);
        }
        /**
         *  Receives data sent from GeocoderService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string or an error message sent from the intent service.
            mAddress = resultData.getString(ConstantsGoogle.ADDRESS_RESULT_KEY);
            updateUI();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    protected void updateUI() {
        mAddressView.setText(mAddress);
    }


}
