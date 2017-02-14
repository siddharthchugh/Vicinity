package com.example.richie.vicinity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


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
    private static final String login_url = "http://nearesthospitals.in/User_Insert.php?";

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
            return false;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnPost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
////            if (!validate()) {
//
//            String userphone = edit_User.getText().toString();
//            String userpwd = edit_Password.getText().toString();
//           //  int vl = Integer.valueOf(userpwd);
////            if (userphone != null) {
//            String str = RandomPassword();
//            String phone = "9999750600";
//            Sending(phone, str);
//
//
////                requestData("http://nearesthospitals.in/User_Insertr.php?");
//                requestData("http://103.247.98.91/API/SendMsg.aspx?uname=20160743&pass=silend&send=SILEND&dest="+userphone+"&msg=Dear%20Customer,%20Your%20One%20Time%20Password%20(OTP)%20to%20register%20at%20SimpliLend%20is%20%20"+str+"%20+%22.%20Do%20not%20disclose%20OTP%20to%20anyone.%20OTP%20will%20expire%20in%2030%20mins.");
//
//  //          }
//                String str = RandomPassword();
//                String phone = edit_User.getText().toString();
//                Sending(phone, str);


            }

        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

//        getActivity().getMenuInflater().inflate(R..menu.menu_conrol__panel,menu);

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




    private void Sending(final String phone,final String str)
    {
        class SendingAsync extends AsyncTask<String,Void,String>
        {
            private Dialog loadingDialog;
            ProgressDialog dialog = new ProgressDialog(getContext());
            @Override
            protected void onPreExecute() {
                dialog.setMessage("Sending");
                dialog.show();
                //loadingDialog = ProgressDialog.show(getContext(), "Please Wait", "");

            }

            @Override
            protected String doInBackground(String... params) {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mobile",phone));
                nameValuePairs.add(new BasicNameValuePair("OTP",str));
                Log.i("User Phone", "++++++++" + phone);
                Log.i("OTP","-----"+str);
                try {
                    String data = "";
                    data += "uname="+ URLEncoder.encode("20160743", "ISO-8859-1");
                    data += "&pass="+ URLEncoder.encode("silend","ISO-8859-1");
                    data += "&send="+ URLEncoder.encode("SILEND","ISO-8859-1");
                    data += "&dest="+ URLEncoder.encode(phone,"ISO-8859-1");
                    data += "&msg=" + URLEncoder.encode("Dear Customer, Your One Time Password (OTP) to register at SimpliLend is " +str +". Do not disclose OTP to anyone. OTP will expire in 30 mins.","ISO-8859-1");
                    HttpClient httpClient = new DefaultHttpClient();
                    String SMSString =  "http://103.247.98.91/API/SendMsg.aspx?"+data;
                    HttpPost httpPost = new HttpPost(SMSString);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");

                    }
                    result = sb.toString();

                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("OTP is : ","+++++++"+result);
                return result;

            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                //Toast.makeText(getApplicationContext(), "Otp Sent Successfully to your Mobile Number",Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                String No = edit_User.getText().toString();
                SharedPreferences saveMobile = getContext().getSharedPreferences("Number", MODE_PRIVATE);
                SharedPreferences.Editor edit = saveMobile.edit();
                edit.putString("Mobile Number", No);
                edit.commit();
//                Intent intent = new Intent(GetInfo.this,OtpGenerator.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                if(login.isChecked()) {
//                    intent.putExtra("login","LoginTrue");
//                    intent.putExtra("register","RegisterFalse");
//                    intent.putExtra("OtpGenerated",str);
//                }
//                else if(register.isChecked())
//                {
//                    intent.putExtra("register","RegisterTrue");
//
//                    intent.putExtra("login","LoginFalse");
//                    intent.putExtra("OtpGenerated",str);
//                }
//                startActivity(intent);
            }

        }
        SendingAsync sa = new SendingAsync();
        sa.execute(phone,str);
    }


    public String RandomPassword() {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));

        return sb.toString();
    }



    class AdminAsyncTask extends AsyncTask<Requestdata,String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (task.size() == 0) {

            }
            task.add(this);



            pb_Adddata.setVisibility(View.VISIBLE);
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

                edit_User.clearAnimation();
                edit_User.setText("");
                edit_Password.setText("");
            }


        }
    }

    private void requestData(String uri) {




        Requestdata data = new Requestdata();

        data.setMethod("POST");
        data.setUri(uri);
//        data.setParam("mobile",phone);
//        data.setParam("OTP",str);

        data.setParam("userID", "Kaaml");
        data.setParam("userPWD","123");


        AdminAsyncTask task = new AdminAsyncTask();
        task.execute(data);

    }


    private boolean validate() {
        if (edit_User.getText().toString().trim().equals(""))
            return false;
        else if (edit_Password.getText().toString().trim().equals(""))
            return false;
        else

            return true;
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


}
