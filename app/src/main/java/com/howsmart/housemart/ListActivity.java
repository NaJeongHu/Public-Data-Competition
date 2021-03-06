package com.howsmart.housemart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.howsmart.housemart.Model.Filter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.howsmart.housemart.Model.PermittedHouse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HouseList";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private String mSido;
    private String mSigungu;
    private String mSearch;
    private String mSubject;
    private FloatingActionButton mToTop;
    private Button mRegionButton, mSearchButton;
    private ImageView mBackButton, mFilterButton;
    private TextView mItemCount, mToolbarTitle;
    private ArrayList<PermittedHouse> PermittedList;
    private ArrayList<PermittedHouse> PermittedList_filterd;
    private ListRecyclerAdapter adapter;
    private Filter mFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();

        addScrollListenerOnRecyclerView();
        addItemTouchListenerOnRecyclerView();
        getDataFromServer();
    }

    // when user scroll recycler
    private void addScrollListenerOnRecyclerView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItem = mManager.findFirstVisibleItemPosition();

                if (firstVisibleItem > 1) {
                    mToTop.setVisibility(View.VISIBLE);
                } else {
                    mToTop.setVisibility(View.GONE);
                }
            }
        });
    }

    // when user touch item
    private void addItemTouchListenerOnRecyclerView() {
        mRecyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getApplicationContext(), mRecyclerView,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(getApplicationContext(), HouseInfoActivity.class);
                        intent.putExtra("idx", PermittedList.get(position).getIdx());
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }

                    @Override
                    public void onItemLongClick(View v, int position) {

                    }
                }));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_list_region:
                Intent intent = new Intent(getApplicationContext(), SidoActivity.class);
                intent.putExtra("search", mSearch);
                intent.putExtra("subject", mSubject);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.btn_list_back:
                onBackPressed();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.iv_list_filter:
                Intent intent2 = new Intent(getApplicationContext(), FilterActivity.class);
                intent2.putExtra("search", mSearch);
                intent2.putExtra("subject", mSubject);
                startActivity(intent2);
                break;
            case R.id.btn_list_search:
                Intent intent3 = new Intent(getApplicationContext(), SearchActivity.class);
                intent3.putExtra("Imfrom", "recyclerview");
                intent3.putExtra("subject", mSubject);
                startActivity(intent3);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
        }
    }

    private void init() {
        mManager = new LinearLayoutManager(ListActivity.this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView = findViewById(R.id.recyclerview_list);
        mToTop = findViewById(R.id.fb_totop);
        mRegionButton = findViewById(R.id.btn_list_region);
        mSearchButton = findViewById(R.id.btn_list_search);
        mBackButton = findViewById(R.id.btn_list_back);
        mFilterButton = findViewById(R.id.iv_list_filter);
        mItemCount = findViewById(R.id.tv_list_itemcount);
        mToolbarTitle = findViewById(R.id.tv_list_title);

        mRecyclerView.setLayoutManager(mManager);
        mToTop.setOnClickListener(this);
        mRegionButton.setOnClickListener(this);
        mSearchButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mFilterButton.setOnClickListener(this);

        mSido = MyLocation.getInstance().getSIDO();
        mSigungu = MyLocation.getInstance().getSIGUNGU();
        mSubject = getIntent().getStringExtra("subject");
        mSearch = getIntent().getStringExtra("search");
        mFilter = (Filter) getIntent().getSerializableExtra("filter");

        setToolbarTitle();
    }

    private void setToolbarTitle() {
        mToolbarTitle.setText(mSubject);

        String name;
        if (mSido != null) {
            if (mSido.length() == 4) {
                name = "" + mSido.charAt(0) + mSido.charAt(2);
            } else {
                name = mSido.substring(0, 2);
            }
            if (mSigungu != null) {
                mRegionButton.setText(" " + name + " - " + mSigungu + " ");
            }
        }
    }

    private void getDataFromServer() {
        // TODO: 2021-07-23 connect retrofit

        Log.d(TAG, "getDataFromServer...");

        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.getList().enqueue(new Callback<List<PermittedHouse>>() {
            @Override
            public void onResponse(Call<List<PermittedHouse>> call, Response<List<PermittedHouse>> response) {
                Log.d(TAG, "getList()...");

                List<PermittedHouse> Result = (List<PermittedHouse>) response.body();

                for(PermittedHouse permittedHouse : Result) {
                    Log.d(TAG, permittedHouse.toString());
                }
                PermittedList = (ArrayList) Result;
                mItemCount.setText(PermittedList.size() + "?????? ?????? ???????????????");
                connectToAdapter();
            }

            @Override
            public void onFailure(Call<List<PermittedHouse>> call, Throwable throwable) {
                Log.d(TAG, throwable.getMessage());
            }
        });


        // todo : 1. ???????????? upload activity, filter ????????????
        // todo : 2. ???????????? unchecked house ????????????
        // todo : 3. ???????????? houseinfo activity ????????????
        // todo : 4. ???????????? ?????????????????? ?????? ????????????
        // todo : 5. ???????????? ???????????? ????????? ?????? ?????????
        // todo : 6. ???????????? ??????????????? ???????????? ?????? ??? ??????, ????????? ?????? ?????? ??????
        // todo : 7. ???????????? ???????????? ?????? List view, detail layout ??????, data binding
        // todo : 8. ???????????? ??????????????? ????????????, ??????????????? ?????? list, ????????????, ???????????? ??????

        // todo : final. Design


        // TODO: 2021-07-23 connect adapter on another thread

    }

    private void connectToAdapter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        todo : ????????? ???????????? ?????? ??????, ???????????? PermittedList_filterd ??? ?????? ??????
//                        filtering();
                        if (PermittedList.isEmpty() == false || PermittedList.size() != 0) {
//                            Collections.sort(arr,new Filtering_for_ganada());
                            mItemCount.setText(PermittedList.size() + "?????? ???????????? ???????????????");
                            adapter = new ListRecyclerAdapter(getApplicationContext(), PermittedList);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else if (PermittedList.size() == 0) {
                            mItemCount.setText("?????? ????????? ?????????.");
                        }
//                        base_progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    private void filtering() {
        if (PermittedList.isEmpty() == false || PermittedList.size() != 0) {
            for (int i = 0; i < PermittedList.size(); i++) {
                // ??????, ????????? ?????? ??????
                if (PermittedList.get(i).getSido().equals(mSido) && PermittedList.get(i).getSigungoo().equals(mSigungu)) {
                    // ????????? ?????? ????????? ????????? ?????? ??????
                    if (!mSearch.equals("") && PermittedList.get(i).getResidence_name().contains(mSearch)) {
                        // ?????? ?????? ?????? ????????????
                        if (mFilter == null) {
                            PermittedList_filterd.add(PermittedList.get(i));
                        } else {
                            if (mFilter.getType().equals("??????") || mFilter.getType().equals(PermittedList.get(i).getSale_type())) {
                                // ??????????????? ?????? ???????????? ??? ???
                            }
                        }
                    }
                }
            }
        }
    }

    public void calDateBetweenAandB(String date1, String date2) {
//        String date1 = "2016-09-21";
//        String date2 = "2016-09-10";

        try { // String Type??? Date Type?????? ?????????????????? ????????? ????????? ?????? ????????? ???????????? ????????? ????????? ?????????????????? ????????? ???????????? ???????????? ??? ??? ??????.
            SimpleDateFormat format = new SimpleDateFormat("yyyymmdd");
            // date1, date2 ??? ????????? parse()??? ?????? Date????????? ??????.
            Date FirstDate = format.parse(date1);
            Date SecondDate = format.parse(date2);

            // Date??? ????????? ??? ????????? ????????? ??? ??? ??????????????? long type ????????? ????????? ?????? ??????.
            // ???????????? -950400000. long type ?????? return ??????.
            long calDate = FirstDate.getTime() - SecondDate.getTime();

            // Date.getTime() ??? ??????????????? ????????????1970??? 00:00:00 ?????? ??? ?????? ??????????????? ???????????????.
            // ?????? 24*60*60*1000(??? ???????????? ?????? ?????????) ??? ???????????? ????????? ?????????.
            long calDateDays = calDate / (24 * 60 * 60 * 1000);

            calDateDays = Math.abs(calDateDays);

            System.out.println("??? ????????? ?????? ??????: " + calDateDays);
        } catch (ParseException e) {
            // ?????? ??????
            String createDate = "2021-07-29 00:00:00";
            String test = createDate.substring(0, 3) + createDate.substring(5, 6) + createDate.substring(8, 9);

            // ?????? ??? ?????? ??????
            Date from = new Date();
            SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
            String to = fm.format(from);
            Log.d("to test", to);
        }
    }
}
