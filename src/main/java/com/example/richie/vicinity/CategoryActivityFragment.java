package com.example.richie.vicinity;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

import com.example.richie.vicinity.Activities.ListHospitalsActivity;
import com.example.richie.vicinity.Pojo.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */

public class CategoryActivityFragment extends Fragment {

    private ListView forecastList;

    int position = 0;
    private TextView vCount;
    int count;
    private View rootView;

    private final String URL_HOSPITAL_LINK = "http://nearesthospitals.in/Category_select.php?categoryid=";
    private final String STATE_MOVIES = "movie_list";
    private TextView movieData;
    public List<Category> mdeicalType;
    private Category home_Items;
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
    private static final String MEDICAL_SERVICES = "MedicalCategory";

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    private final String CATEGORY_PREFERENCE = "UserCategory";

    private final String NETBAMKING_PREFERENCE = "UserAuthentication";
    List<Category> cat;
    Category ct;
    String id = null;
    String title = null;


    private ProgressBar bar;
    private Intent valueIntent;
    private String idlink;
    private Toolbar barCategory;
    private TextView tData;


    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    private TextView data;

    public CategoryActivityFragment() {

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        // tb = (Toolbar) rootView.findViewById(R.id.toolbar);
        //      bt_Location = (Button) rootView.findViewById(R.id.selectLocation);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        //      bar = (ProgressBar) rootView.findViewById(R.id.progressBar);
//        bar.setVisibility(View.INVISIBLE);
        grid = new ArrayList<>();

        Display();
        DisplayCategory();
        return rootView;

    }



/*
    View.OnClickListener locate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            startActivity(new Intent(getContext(),SelectLocation.class));

        }
    };

*/


    @Override
    public void onResume() {
        super.onResume();
//        if (isOnline()) {
//            valueIntent = getActivity().getIntent();
//            if (valueIntent == null || valueIntent.getData() == null) {
//                idlink = valueIntent.getStringExtra("id");
//            }
//            String us = idlink;
//            SharedPreferences shareAccountInformation = getContext().getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
//            String restorebankname = shareAccountInformation.getString("id", "No value found").toString();
//
//            requestVicinity(URL_HOSPITAL_LINK + restorebankname);
        Display();
//        }
    }

    protected void updated() {

        medicalAdapter = new MedicalAdapter(getActivity(), mdeicalType);

        mRecyclerView.setAdapter(medicalAdapter);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
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
//            valueIntent = getActivity().getIntent();
//            if (valueIntent == null || valueIntent.getData() == null) {
//                idlink = valueIntent.getStringExtra("id");
//            }
//            String us = idlink;

            String us = idlink;
            SharedPreferences shareAccountInformation = getContext().getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
            String restorebankname = shareAccountInformation.getString("id", "No value found").toString();

            requestVicinity(URL_HOSPITAL_LINK + restorebankname);


//            requestVicinity(URL_HOSPITAL_LINK + us);

        }
    }


    public void DisplayCategory() {
        SharedPreferences shareAccountInformation = getContext().getSharedPreferences(CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
        String restorebankname = shareAccountInformation.getString("id", "No value found").toString();

        requestVicinity(URL_HOSPITAL_LINK + restorebankname);

    }

    //    private void requestData(String url) {
//
//        MoviewGrid mg = new MoviewGrid();
//        mg.execute(url);
//
//    }
    public void requestVicinity(String url) {
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


            mdeicalType = Medical_Category.parseFeed(s);
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

        return super.onOptionsItemSelected(item);

    }

    public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MyHolder> {

        private LayoutInflater inflater;
        List<Category> ls = Collections.emptyList();
        Context mcontext;
        String data;
        Category current;


        public MedicalAdapter(Context con, List<Category> hs) {
            this.mcontext = con;
            this.ls = hs;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int i) {
            inflater = LayoutInflater.from(mcontext);

            View vw = inflater.inflate(R.layout.category_items, parent, false);
            MyHolder holder = new MyHolder(vw);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder myHolder, int position) {

            current = ls.get(position);

            myHolder.categoryTitle.setText(current.getC_name());

        }

        @Override
        public int getItemCount() {
            return ls.size();
        }

        class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView iv;
            TextView categoryID;
            TextView categoryTitle;
            RelativeLayout storeItemLayout;

            public MyHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                categoryTitle = (TextView) itemView.findViewById(R.id.categoryTitle);
                categoryID = (TextView) itemView.findViewById(R.id.categoryID);
              //  v = itemView;
            }

            @Override
            public void onClick(View view) {

//                int itemPosition = getAdapterPosition();
//
//                String id = categoryID.getText().toString();
//
//                    Intent i = new Intent(context, CategoryActivity.class);
//                    i.putExtra("id", id);
//                    startActivity(i);

                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                home_Items = mdeicalType.get(itemPosition);

                id = home_Items.getC_id();
                title = home_Items.getC_name();
                SharedPreferences savenb = getContext().getSharedPreferences(NETBAMKING_PREFERENCE,Context.MODE_PRIVATE);
                SharedPreferences.Editor editNtb = savenb.edit();
                editNtb.putString("id", id);
                editNtb.commit();
//
                Intent d_Intent = new Intent(getContext(), ListHospitalsActivity.class);
                d_Intent.putExtra("id", id);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getContext(), R.animator.anim, R.animator.animate1).toBundle();
                startActivity(d_Intent);
Toast.makeText(getContext(),"Clicked"+title,Toast.LENGTH_SHORT).show();

            }
        }
    }

}
