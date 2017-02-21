package com.example.richie.vicinity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    private Toolbar tbVicinity;
    private Spinner vsSpin;

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

   View v =    inflater.inflate(R.layout.fragment_loginuser, container, false);
//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbarVicinity);
//        vsSpin = (Spinner) v.findViewById(R.id.spinnerZone);
//        //set toolbar appearance
//        toolbar.setBackgroundColor(Color.CYAN);
//
//        //for crate home button
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbar);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        String[] value={"20","40","60","80","100","All"};
//       // aa=new ArrayAdapter<String>(this,R.layout.spinner_item_profile,value);
//     //   spinner_counter.setAdapter(aa);
//                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,value);
//
////         Drop down layout style - list view with radio button
////        dataAdapter.setDropDownViewResource(R.layout.toolbar);
//
//   vsSpin.setAdapter(dataAdapter);


        return  v;


    }
}
