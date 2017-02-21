package com.example.richie.vicinity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.richie.vicinity.Activities.AreaActivity;
import com.example.richie.vicinity.Activities.DetailActivity;
import com.example.richie.vicinity.Activities.LocationSelection;
import com.example.richie.vicinity.Activities.LoctionActivity;
import com.example.richie.vicinity.Pojo.HospitalDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListHospitalsActivityFragment extends Fragment implements View.OnClickListener {

    private CoordinatorLayout coordinatorLayout;
    public List<HospitalDetail> mdeicalType;
    private final String URL_HOSPITAL_LINK = "http://nearesthospitals.in/Hospital_Category_select.php?hospital_categoryid=";
    //  private List<MainFragment.MoviewGrid> grid;
    private List<MyVicinity> grid;
    private Toolbar tb;
    HospitalDetail current;

    boolean mDualPane;
    private Spinner choose;
    int mCurCheckPosition = 0;
    ListView lv;
    Button btDetail;

    String id = null;
    String title = null;

    protected MedicalAdapter medicalAdapter;

    //    protected HospitalHospitalDetailAdapter medicalAdapter;
    private Button bt_Location;
    private List<MyVicinity> taskVicinity;
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private final String NETBAMKING_PREFERENCE = "UserAuthentication";

    private ProgressBar bar;
    private Intent valueIntent;
    private String idlink;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar barHospitalDetail;
    private Button select_Location;
    public double lat;
    public double lng;
    public HospitalDetail detail_info;
    private ImageView locationMap;
    private SharedPreferences prfenceHospitalInfo;
    List<HospitalDetail> ls;// = Collections.emptyList();

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    public ListHospitalsActivityFragment() {

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_hospitals, container, false);
           taskVicinity = new ArrayList<>();



        Display();

        return v;
    }


    View.OnClickListener locationSearch = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            lat = detail_info.getLat();
            lng = detail_info.getLng();
            String nm = detail_info.getTitle();
            Intent d_Intent = new Intent(getActivity(), AreaActivity.class);
            d_Intent.putExtra("name", nm);
            d_Intent.putExtra("hospital_lat", lat);
            d_Intent.putExtra("hospital_lng", lng);

//                Bundle bndlanimation =
//                        ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();
            startActivity(d_Intent);

        }
    };



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        locationMap = (ImageView) getActivity().findViewById(R.id.hospitalMapLocation);
    }

    @Override
    public void onClick(View view) {

        startActivity(new Intent(getContext(), LocationSelection.class));

    }


    public boolean isConnected() {
        ConnectivityManager manage = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {

            return true;
        } else {
//            Snackbar snackbar = Snackbar
//                    .make(coordinatorLayout, "No Internet,Try again!", Snackbar.LENGTH_LONG)
//                    .setAction("RETRY", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            isConnected();
//                        }
//                    });
//            snackbar.setActionTextColor(Color.RED);
//            View sbView = snackbar.getView();
//            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//            textView.setTextColor(Color.YELLOW);
//            snackbar.show();
            return false;
        }

    }


    public void Display() {

        if (isOnline()) {
//            valueIntent = getActivity().getIntent();
//            if (valueIntent == null || valueIntent.getData() == null) {
//                idlink = valueIntent.getStringExtra("id");
//            }
            SharedPreferences shareAccountInformation = getContext().getSharedPreferences(NETBAMKING_PREFERENCE, Context.MODE_PRIVATE);
            String restorebankname = shareAccountInformation.getString("id", "No value found").toString();

            requestVicinity(URL_HOSPITAL_LINK + restorebankname);

        }
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

    public void update() {

        medicalAdapter = new MedicalAdapter(getActivity(), mdeicalType);

        mRecyclerView.setAdapter(medicalAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


    }

    @Override
    public void onResume() {
        super.onResume();
        Display();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    public void requestVicinity(String url) {

        MyVicinity mc = new MyVicinity();
        mc.execute(url);
    }


    class MyVicinity extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (taskVicinity.size() == 0) {

            }

            taskVicinity.add(this);


        }

        @Override
        protected String doInBackground(String... strings) {
            String content = HttpManger.getData(strings[0]);


            return content;
        }


        @Override
        protected void onPostExecute(String s) {
            taskVicinity.remove(this);
            mdeicalType = Medical_Category.medicalDescription(s);
            update();
            if (taskVicinity.size() == 0) {
//                bar.setVisibility(View.INVISIBLE);

            }

//            if (s != null) {


//            } else {
//                Toast.makeText(getActivity(), "Conencted to the network please!", Toast.LENGTH_SHORT).show();
//            }
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

     inflater.inflate(R.menu.menu_list_hospitals,menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id){
            case R.id.action_geolocate:
            startActivity(new Intent(getContext(),LoctionActivity.class));
break;
        }




        return false;
    }

    public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MyHolder> {

        private LayoutInflater inflater;
        List<HospitalDetail> ls;// = Collections.emptyList();
        Context mcontext;
        String data;
        String name=null;
        String adress=null;
        String phonenm=null;
        String landmark=null;

        HospitalDetail current;


        public MedicalAdapter(Context con, List<HospitalDetail> hs) {
            this.mcontext = con;
            this.ls = hs;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = LayoutInflater.from(mcontext);
            View vw = inflater.inflate(R.layout.layout_list_hospitals, parent, false);
            MyHolder holder = new MyHolder(vw);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder myHolder, int position) {

            current = ls.get(position);

            myHolder.HospitalDetailTitle.setText(current.getTitle());
            myHolder.btDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    title = current.getTitle();
                    lat = current.getLat();
                    lng = current.getLng();
                    adress = current.getAddresss();
                    phonenm = current.getPhoneno();

                    Intent inNext = new Intent(getContext(),DetailActivity.class);
                    inNext.putExtra("name", title);
                    inNext.putExtra("address", adress);
                    inNext.putExtra("hospitalphoneno", phonenm);

                    Bundle bndlanimation =
                            ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();
                    startActivity(inNext);

                }
            });
//            myHolder.mapLocation.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int itemPosition = mRecyclerView.getChildLayoutPosition(view);
////                    current = mdeicalType.get(itemPosition);
//                    title = current.getTitle();
//                    lat = current.getLat();
//                    lng = current.getLng();
//
//                    Intent inNext = new Intent(getContext(),AreaActivity.class);
//                    inNext.putExtra("name", title);
//                    inNext.putExtra("hospital_lat", lat);
//                    inNext.putExtra("hospital_lng", lng);
//
//                    Bundle bndlanimation =
//                            ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();
//                    startActivity(inNext);
//
//                    Toast.makeText(getContext(),"Clicked"+title,Toast.LENGTH_SHORT).show();
//                }
//            });
//           myHolder.v.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View view) {
//                   current = mdeicalType.get(current);
//
////                id = current.ge;
//                   title = current.getTitle();
//                   lat = current.getLat();
//                   lng = current.getLng();
//
//
//
//
//                   Intent d_Intent = new Intent(getActivity(), AreaActivity.class);
//                   d_Intent.putExtra("name", title);
//                   d_Intent.putExtra("hospital_lat", lat);
//                   d_Intent.putExtra("hospital_lng", lng);
////                Bundle bndlanimation =
////                        ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();
//                   startActivity(d_Intent);
//               }
//           });
        }

        @Override
        public int getItemCount() {
            return ls.size();
        }

        class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView iv;
            TextView HospitalDetailID;
            TextView HospitalDetailTitle;
            Button btDetail;
            ImageView mapLocation;
            View v;

            public MyHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                HospitalDetailTitle = (TextView) itemView.findViewById(R.id.hospital_detail);
                mapLocation = (ImageView) itemView.findViewById(R.id.hospitalMapLocation);
                 btDetail = (Button) itemView.findViewById(R.id.detail_Info);
            }

            @Override
            public void onClick(View view) {

//                int itemPosition = getAdapterPosition();
//
//                String id = HospitalDetailID.getText().toString();
//
//                    Intent i = new Intent(context, HospitalDetailActivity.class);
//                    i.putExtra("id", id);
//                    startActivity(i);

                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                current = mdeicalType.get(itemPosition);

                title = current.getTitle();
                lat = current.getLat();
                lng = current.getLng();

                Intent d_Intent = new Intent(getActivity(), AreaActivity.class);
                d_Intent.putExtra("name", title);
//                d_Intent.putExtra("address",adress);
                d_Intent.putExtra("hospital_lat", lat);
                d_Intent.putExtra("hospital_lng", lng);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();
                startActivity(d_Intent);
//                Toast.makeText(getContext(), "Clicked" + title, Toast.LENGTH_SHORT).show();

            }
        }
    }

}