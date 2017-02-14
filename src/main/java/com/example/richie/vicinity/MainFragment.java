package com.example.richie.vicinity;


import android.*;
import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.beltaief.flowlayout.FlowLayout;
//import com.beltaief.flowlayout.util.ViewMode;
import com.example.richie.vicinity.Pojo.Dashboard_items;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//import butterknife.InjectView;


/**
 * A placeholder fragment containing a simple view.
 */

public class MainFragment extends Fragment {

    private ListView forecastList;

    int position = 0;
    private TextView vCount;
    int count;
    private View rootView;
    private ProgressBar bar;

    private final String URL_HOSPITAL_LINK = "http://nearesthospitals.in/Medical_Select.php";

    private final String STATE_MOVIES = "movie_list";
    private TextView movieData;
    public List<Dashboard_items> mdeicalType;
    private Dashboard_items home_Items;
    private List<MoviewGrid> grid;
    private Toolbar tb;
    boolean mDualPane;
    private Spinner choose;
    int mCurCheckPosition = 0;
    ListView lv;
    protected MedicalAdapter medicalAdapter;
    private Button bt_Location;
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    private  final int MINIMUM_SESSION=5000;
    private static final String MEDICAL_SERVICES = "MedicalCategory";

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    //    private final String NETBAMKING_PREFERENCE = "UserAuthentication";
    private final String CATEGORY_PREFERENCE = "UserCategory";

    private int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;


    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;

    protected RecyclerView.LayoutManager mLayoutManager;
    private FirebaseAnalytics fbAnalytics;
    //FlowLayout flowLayout;
 //   @InjectView(R.id.recyclerView)
//    @BindView(R.id.recyclerView)
RecyclerView mRecyclerView;
    public MainFragment() {

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//         tb = (Toolbar) rootView.findViewById(R.id.toolbar);
//         tb.setTitle("Critical Care");
        //      bt_Location = (Button) rootView.findViewById(R.id.selectLocation);
//       flowLayout = (FlowLayout) rootView.findViewById(R.id.flow_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        //      bar = (ProgressBar) rootView.findViewById(R.id.progressBar);
//        bar.setVisibility(View.INVISIBLE);
//        flowLayout.setConnectivityAware(true);
//        flowLayout.setMode(ViewMode.PROGRESS);
    //    ButterKnife.inject(rootView);
        fbAnalytics = FirebaseAnalytics.getInstance(getContext());
        fbAnalytics.setMinimumSessionDuration(MINIMUM_SESSION);
        grid = new ArrayList<>();

        Display();

//        ButterKnife.bind(this, rootView);
        return rootView;

    }


    private void getData() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showContent();
            }
        }, 2000);
    }

    private void showContent() {
        //      flowLayout.setMode(ViewMode.ERROR);
    }
/*
    View.OnClickListener locate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            startActivity(new Intent(getContext(),SelectLocation.class));

        }
    };

*/


    public void DisplayLocation() {
        if (isReadStorageAllowed()) {
            //If permission is already having then showing the toast

            //Existing the method with return
            return;
        }
        checkAndRequestPermissions();

    }

    private boolean checkAndRequestPermissions() {
//        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION);
        int locationPermission = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);

        int locationFinelocation = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (locationFinelocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    //    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);

        int resultFineLocation = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }


    protected void updated() {

        medicalAdapter = new MedicalAdapter(getActivity(), mdeicalType);

        mRecyclerView.setAdapter(medicalAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);


    }


//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        if(savedInstanceState == null || !savedInstanceState.containsKey("flavors")) {
//            flavorList = savedInstanceState.getParcelableArrayList("flavors");
//        }
//    }
//
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if(outState == null || !outState.containsKey("flavors")) {
//            flavorList = outState.getParcelableArrayList("flavors");
//        }
//        }

    public void Display() {

        if (isOnline()) {

            requestData(URL_HOSPITAL_LINK);

        }
    }


    private void requestData(String url) {

        MoviewGrid mg = new MoviewGrid();
        mg.execute(url);

    }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public class MoviewGrid extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (grid.size() == 0) {

                //      bar.setVisibility(View.VISIBLE);
            }
            grid.add(this);


        }

        @Override
        protected String doInBackground(String... params) {


            String content = HttpManger.getData(params[0]);

            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            grid.remove(this);
            if (grid.size() == 0) {
                //   bar.setVisibility(View.INVISIBLE);

            }

//            if (s == null) {


            mdeicalType = Medical_Category.medicaltype(s);
            updated();
            //          } else {
            //            Toast.makeText(getActivity(), "Conencted to the network please!", Toast.LENGTH_SHORT).show();
            //      }
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

/*
            case R.id.action_sort:
                startActivity(new Intent(getActivity(),SettingActivity.class));

                if (isConnection()) {
                    requestData(URL_POPULARMOVIE_LINK);

                } else {
                    Toast.makeText(getContext(), "Please connect to the network", Toast.LENGTH_SHORT).show();

                }




                break;

            case R.id.action_popular:
                if (isConnection()) {
                    requestData(URL_POPULARMOVIE_LINK);

                } else {
                    Toast.makeText(getContext(), "Please connect to the network", Toast.LENGTH_SHORT).show();

                }


                break;

            case R.id.action_highestrated:
                if (isConnection()) {
                    requestData(URL_TOPRATEDMOVIE_LINK);

                } else {
                    Toast.makeText(getContext(), "Please connect to the network", Toast.LENGTH_SHORT).show();

                }


                break;
*/

        }


        return super.onOptionsItemSelected(item);

    }

    public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MyHolder> {

        private LayoutInflater inflater;
        // List<Dashboard_items> ls = Collections.emptyList();
        List<Dashboard_items> ls;
        Intent d_Intent;
        Context mcontext;
        Dashboard_items current;
        String id = null;
        String title = null;
         private final String CATEGORY_PREFERENCE = "UserCategory";


        public MedicalAdapter(Context con, List<Dashboard_items> hs) {
            this.mcontext = con;
            this.ls = hs;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = LayoutInflater.from(mcontext);
            View vw = inflater.inflate(R.layout.list_item_dashboard, parent, false);
            MyHolder holder = new MyHolder(vw);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder myHolder, final int position) {

            current = ls.get(position);

            myHolder.categoryTitle.setText(current.getTitle());

//            myHolder.v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    id = current.getId();
//                    title = current.getTitle();
//                    SharedPreferences savenb = mcontext.getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editNtb = savenb.edit();
//                    editNtb.putString("id", id);
//                    editNtb.commit();
//
//
//                    d_Intent = new Intent(mcontext, CategoryActivity.class);
//                    d_Intent.putExtra("id", id);
//
//                    startActivity(d_Intent);
//
//
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return ls.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView categoryTitle;

            View v;

            public MyHolder(View itemView) {
                super(itemView);

                itemView.setOnClickListener(this);
                categoryTitle = (TextView) itemView.findViewById(R.id.categoryTitle);

            }

                       @Override
                     public void onClick(View view) {

//                int itemPosition = getAdapterPosition();

//                String id = categoryID.getText().toString();
//
//                    Intent i = new Intent(getContext(), CategoryActivity.class);
//                    i.putExtra("id", id);
//                    startActivity(i);

                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                //     if (home_Items != null) {
                Intent d_Intent;
                home_Items = mdeicalType.get(itemPosition);


                   id = home_Items.getId();
                    title = home_Items.getTitle();
                    SharedPreferences savenb = getContext().getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editNtb = savenb.edit();
                    editNtb.putString("id", id);
                      editNtb.putString("title",title);
                    editNtb.commit();


                         d_Intent = new Intent(getContext(), CategoryActivity.class);
                        d_Intent.putExtra("id", id);
                           d_Intent.putExtra("title", title);


                        startActivity(d_Intent);
//

//                   d_Intent.putExtra("movietitle", title);
//                Bundle bndlanimation =
//                        ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();


//                    Intent d_Intent = new Intent(getContext(), CategoryActivity.class);
//                    d_Intent.putExtra("id", id);

//                   d_Intent.putExtra("movietitle", title);
//                Bundle bndlanimation =
//                        ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();
//                    startActivity(d_Intent);
//Toast.makeText(getContext(),"Clicked"+id,Toast.LENGTH_SHORT).show();
            }

            //          }
        }

    }


}



