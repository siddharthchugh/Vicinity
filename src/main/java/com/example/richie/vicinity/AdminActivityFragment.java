package com.example.richie.vicinity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.example.richie.vicinity.Pojo.Login;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminActivityFragment extends Fragment {

    TextView tvIsConnected;
    EditText edit_User, edit_Password;
    Button btnPost;
    String author = null;
    public Login person;
    View v;
    List<AdminAsyncTask> task;
    CoordinatorLayout coordinatorLayout;
    InputStream is;
    private static final String BOOK_SHARE_HASHTAG = " #MY BOOKS ";
    private final String LOG_TAG = "The Book";
    private ProgressBar pb_Adddata;
    String result;
    BufferedReader reader;
    String userData = null;
    String userPwd=null;
   private Toolbar tbVicinity;
    public List<Login> moviedetails;
    private static final String login_url = "http://nearesthospitals.in/User_Insert.php?";
    private Spinner vsSpin;
// String url = "http://nearesthospitals.in/login_users.php?";
    /*The below given code provides the menu option to the
    * Fragment in Add Data fragment.
    * */

    public AdminActivityFragment() {
        setHasOptionsMenu(true);

    }

    /* The below given provides the creation of the structure
      * of Fragment using the layout and function of Post
      * Data on Click listner
       * * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_admin, container, false);
        coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout);
//       tbVicinity = (Toolbar) v.findViewById(R.id.toolbarVicinity);
//        vsSpin = (Spinner) v.findViewById(R.id.spinnerZone);
//
//        AppCompatActivity ac = new AppCompatActivity();
//        ac.setSupportActionBar(tbVicinity);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item);

        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//   vsSpin.setAdapter(dataAdapter);

                edit_User = (EditText) v.findViewById(R.id.adminUser);
        edit_Password = (EditText) v.findViewById(R.id.adminPassword);
        pb_Adddata = (ProgressBar) v.findViewById(R.id.progressBarAddData);
        pb_Adddata.setVisibility(View.INVISIBLE);
        btnPost = (Button) v.findViewById(R.id.btnPost);
        task = new ArrayList<>();

        return v;
    }

    /*This code is getting the getting the pojo data from the
     * and accumlating the data into the json object
     * so tat it can set te json String into String entity
     * and post the data HttpResponse to converted into
     * the ConvertinputStrem.
         *
    */

    /*The Below data is connected to internet
    */







    public boolean isConnected() {
        ConnectivityManager manage = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No Internet,Try again!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            isConnected();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
//            LOginUSer();
            return false;
        }

    }


    public void LOginUSer(){


        if(isConnected()){


             String userphone = edit_User.getText().toString();
             String userpwd = edit_Password.getText().toString();


            requestData(login_url,userData,userpwd);        }
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        btnPost.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
////            if (!validate()) {
//
//           // String userphone = edit_User.getText().toString();
//           // String userpwd = edit_Password.getText().toString();
//            // int vl = Integer.valueOf(userpwd);
////            if (userphone != null) {
////            String str = RandomPassword();
////            String phone = "9999750600";
////            Sending(phone, str);
//
//
////                userData = userName.getText().toString();
//  //              userPwd = userPassword.getText().toString();
//  LOginUSer();
////                requestData("http://103.247.98.91/API/SendMsg.aspx?uname=20160743&pass=silend&send=SILEND&dest="+userphone+"&msg=Dear%20Customer,%20Your%20One%20Time%20Password%20(OTP)%20to%20register%20at%20SimpliLend%20is%20%20"+str+"%20+%22.%20Do%20not%20disclose%20OTP%20to%20anyone.%20OTP%20will%20expire%20in%2030%20mins.");
//
//  //          }
////                String str = RandomPassword();
////                String phone = edit_User.getText().toString();
////                Sending(phone, str);
//
//
//            }
//
//        });
//
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        getActivity().getMenuInflater().inflate(R.menu.menu_login,menu);
        MenuItem menuItem = menu.findItem(R.id.menu_spinner);

        vsSpin = (Spinner) MenuItemCompat.getActionView(menuItem);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
vsSpin.setAdapter(countryAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

/*
            case R.id.action_add:

                startActivity(new Intent(getContext(),Conrol_PanelActivity.class));
                break;

            case R.id.action_delete:

                startActivity(new Intent(getContext(),Conrol_PanelActivity.class));
                break;
            case R.id.action_update:

                startActivity(new Intent(getContext(),Conrol_PanelActivity.class));
                break;
*/

        }

        return super.onOptionsItemSelected(item);
    }

    public void update( ) {

//        if(moviedetails !=null) {
//            for (Login lg : moviedetails) {
////Login lg = new Login();
//                String user = lg.getMailAddress();
//                String position = lg.getPositionid();
//
//                String uData = edit_User.getText().toString();
//                String uPwd = edit_Password.getText().toString();
//
//                if (uData.equals(user)) {
////                    Intent in = new Intent(getContext(),MainActivity.class);
//                    //                in.putExtra("user",uData);
//                    //              in.putExtra("positionid",position);
//                    //                  startActivityForResult(in,1);
//
//                } else {
//
//                    Toast.makeText(getContext(), "Invalid user", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }

//        AdminAsyncTask at = new AdminAsyncTask();
//        at.execute();
    }


    private void requestData(String uri,String user,String pwd) {




        Requestdata data = new Requestdata();

        data.setMethod("POST");
        data.setUri(uri);
        data.setParam("userID", "John121212");
        data.setParam("userPWD","12312121212");


        AdminAsyncTask task = new AdminAsyncTask();
        task.execute(data);

    }






    class AdminAsyncTask extends AsyncTask<Requestdata,String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (task.size() == 0) {
                pb_Adddata.setVisibility(View.VISIBLE);
            }
            task.add(this);



        }

        @Override
        protected String doInBackground(Requestdata... params) {

            String content = HttpManger.getdata(params[0]);

            return content;// "Task on Completed ";

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            task.remove(this);

            if (task.size() == 0) {
                pb_Adddata.setVisibility(View.INVISIBLE);

//                edit_User.clearAnimation();
//                edit_User.setText("");
//                edit_Password.setText("");
            }
            moviedetails = parseFeed(result);
            update();



        }




    private boolean validate() {
        if (edit_User.getText().toString().trim().equals(""))
            return false;
        else if (edit_Password.getText().toString().trim().equals(""))
            return false;
        else

            return true;
    }


    public List<Login> parseFeed(String content) {

        Login list;

        try {

            JSONArray ar = new JSONArray(content);
            List<Login> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {


                JSONObject obj = ar.getJSONObject(i);
                list = new Login();
                list.setMailAddress(obj.getString("username"));
                list.setPositionid(obj.getString("pid"));
                movieList.add(list);

            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}


}
