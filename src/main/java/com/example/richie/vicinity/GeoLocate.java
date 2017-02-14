package com.example.richie.vicinity;

/**
 * Created by Richie on 03-02-2017.
 */

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class GeoLocate extends IntentService {
    private final static String TAG = "GEOCODER_SERVICE";
    protected ResultReceiver mReceiver;
    protected String resultMsg = "";
    String errorMessage = "";


    public GeoLocate() {
        super("GeocodeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Intent received");

        // Get the receiver and the Location data from the incoming Intent
        mReceiver = intent.getParcelableExtra(ConstantsGoogle.RECEIVER);
        final String action = intent.getAction();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        Location location = null;
        // Address found using the Geocoder.
        List<Address> addresses = null;

        try {
            if (action.equals(ConstantsGoogle.ACTION_ADDRESS_FROM_LOC)) {
                location = intent.getParcelableExtra(ConstantsGoogle.LOCATION_KEY);

                // TODO: use getFromLocation() to get the address
                try {
                    addresses = geocoder.getFromLocation(
                            location.getLatitude(),
                            location.getLongitude(),
                            // In this sample, get just a single address.
                            1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if (action.equals(ConstantsGoogle.ACTION_LOC_FROM_ADDR)) {
                String locName = intent.getStringExtra(ConstantsGoogle.PLACE_NAME_KEY);

                // TODO: use getFromLocationName() to get the Address
                try {
                    addresses = geocoder.getFromLocationName(
                            locName,
                            // In this sample, get just a single address.
                            1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (IllegalArgumentException illegalArgumentException) {
            resultMsg = "Illegal arguments passed to Geocoder";
            Log.d(TAG, "Illlegal arguments: " + illegalArgumentException.getLocalizedMessage());
            deliverResult(ConstantsGoogle.RESULT_ERROR, resultMsg);
        }
//        try {
//            addresses = geocoder.getFromLocation(
//                    location.getLatitude(),
//                    location.getLongitude(),
//                    // In this sample, get just a single address.
//                    1);
//        } catch (IOException ioException) {
//            // Catch network or other I/O problems.
//            errorMessage = "Service Not available";
//            Log.e(TAG, errorMessage, ioException);
//        } catch (IllegalArgumentException illegalArgumentException) {
//            // Catch invalid latitude or longitude values.
//            errorMessage = "INvalid LAT LAng used";
//            Log.e(TAG, errorMessage + ". " +
//                    "Latitude = " + location.getLatitude() +
//                    ", Longitude = " +
//                    location.getLongitude(), illegalArgumentException);
//        }

        if (addresses == null || addresses.isEmpty()) {
            resultMsg = "No addresses found for this location";
            deliverResult(ConstantsGoogle.RESULT_ERROR, resultMsg);
        }
        else {
            // put the address into the result
            Address addr = addresses.get(0);
            String addrString = "";

            // Get the address lines
            for(int i = 0; i < addr.getMaxAddressLineIndex(); i++) {
                addrString += addr.getAddressLine(i) + "\n";
            }
            // Use other functions to get additional address information
            if (addr.getCountryName() != null)
                addrString += addr.getCountryName() + "\n";;

            if (action.equals(ConstantsGoogle.ACTION_ADDRESS_FROM_LOC)) {
                if (addr.getLocality() != null)
                    addrString += addr.getLocality() +"\n"+addr.getSubLocality() + "\n";;
                if (addr.getFeatureName() != null) {
                    addrString += addr.getFeatureName() + "\n";;
                }
            }
            else if (action.equals(ConstantsGoogle.ACTION_LOC_FROM_ADDR)) {
                if (addr.hasLatitude())
                    addrString += String.format("Lat: %f", addr.getLatitude()) + "\n";;
                if (addr.hasLongitude()) {
                    addrString += String.format("Long: %f", addr.getLongitude()) + "\n";;
                }
            }

            Log.i(TAG, "Address Found");
            deliverResult(ConstantsGoogle.RESULT_SUCCESS, addrString);
        }
    }

    /**
     * Sends the given result back to our calling Activity
     */
    protected void deliverResult(int resultCode, String result) {
        Bundle b = new Bundle();
        b.putString(ConstantsGoogle.ADDRESS_RESULT_KEY, result);
        mReceiver.send(resultCode, b);
    }
}
