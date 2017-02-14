package com.example.richie.vicinity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private TextView hpName;
    private TextView hpAddress;
    private TextView hpPhonenumber;
    private TextView hpNearestlandmark;

    private String hospitalName = null;
    private String hospitalAddress = null;
    private String hospitalPhone=null;
    private String hospitallandmark = null;

    private SharedPreferences preferenceData;
    String name = null;
    String address = null;
    String phnumber = null;
    String landmark = null;
    Context con;
    Intent in;

    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        in = getActivity().getIntent();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        hpName = (TextView) v.findViewById(R.id.hospital_Name);
        hpAddress = (TextView) v.findViewById(R.id.hospital_Address);
        hpPhonenumber = (TextView) v.findViewById(R.id.hospital_Phone);
        hpNearestlandmark = (TextView) v.findViewById(R.id.hospital_Landmark);

        name = in.getStringExtra("name");
        address = in.getStringExtra("address");
    //    phnumber = in.getStringExtra("hospitalphoneno");
        hpName.setText(name.toString());
        hpAddress.setText(address.toString());
//        hpPhonenumber.setText(phnumber.toString());
  //     hpNearestlandmark.setText(landmark);

        return v;
    }


//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.con = activity;
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
