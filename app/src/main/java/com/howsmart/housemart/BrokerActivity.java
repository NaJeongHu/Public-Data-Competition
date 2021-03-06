package com.howsmart.housemart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.howsmart.housemart.Adapter.BrokerRecyclerAdapter;
import com.howsmart.housemart.Model.ProvisionalHouse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.howsmart.housemart.Model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrokerActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private String mSido;
    private String mSigungu;
    private String mType;
    private FloatingActionButton mToTop;
    private Button mRegionButton, mTypeButton;
    private ImageView mBackButton;
    private TextView mItemCount;
    private ArrayList<ProvisionalHouse> ProvisionalList, ProvisionalList_Filterd;
    private BrokerRecyclerAdapter adapter;
    private User mUser;

    private RESTApi mRESTApi;
    private LottieAnimationView lottie_upload_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broker);

        init();

        addScrollListenerOnRecyclerView();
        addItemTouchListenerOnRecyclerView();
        getDataFromServer();
    }

    private void init() {
        mManager = new LinearLayoutManager(BrokerActivity.this, LinearLayoutManager.VERTICAL, false);
        mRESTApi = RESTApi.retrofit.create(RESTApi.class);

        mRecyclerView = findViewById(R.id.recyclerview_broker);
        mToTop = findViewById(R.id.fb_totop_broker);
        mRegionButton = findViewById(R.id.btn_broker_region);
        mBackButton = findViewById(R.id.btn_back_broker);
        mItemCount = findViewById(R.id.tv_broker_itemcount);
        mTypeButton = findViewById(R.id.btn_broker_type);
        lottie_upload_success = findViewById(R.id.lottie_upload_success);

        mType = "?????????";

        mRecyclerView.setLayoutManager(mManager);
        mToTop.setOnClickListener(this);
        mRegionButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mTypeButton.setOnClickListener(this);

        mUser = (User) getIntent().getSerializableExtra("user");

        mSido = MyLocation.getInstance().getSIDO();
        mSigungu = MyLocation.getInstance().getSIGUNGU();

        ProvisionalList_Filterd = new ArrayList<>();

        String name;
        if (mSido != null && mSido.length() == 4) {
            name = "" + mSido.charAt(0) + mSido.charAt(2);
        } else {
            name = mSido.substring(0, 2);
        }
        if (mSido != null && mSigungu != null) {
            mRegionButton.setText(" " + name + " - " + mSigungu + " ");
        }
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
                        upload_dialog2(v,ProvisionalList_Filterd.get(position).getIdx());
                        // todo : ???????????? ????????? ??????????????????, ???????????? ?????? ???????????? ?????????????????? ??????
//                        Intent intent = new Intent(getApplicationContext(), HouseInfoActivity.class);
//                        intent.putExtra("idx",ProvisionalList.get(position).getIdx());
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }

                    @Override
                    public void onItemLongClick(View v, int position) {

                    }
                }));
    }
    private void getDataFromServer() {
        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.getProvisionalHouseList().enqueue(new Callback<List<ProvisionalHouse>>() {
            @Override
            public void onResponse(Call<List<ProvisionalHouse>> call, Response<List<ProvisionalHouse>> response) {
                List<ProvisionalHouse> Result = (List<ProvisionalHouse>) response.body();
                ProvisionalList = (ArrayList) Result;
//                mItemCount.setText(ProvisionalList.size() + "?????? ????????? ????????? ???????????? ?????????");
                connectToAdapter();
            }

            @Override
            public void onFailure(Call<List<ProvisionalHouse>> call, Throwable throwable) {
                Log.d("BrokerActivity ?????? ??????", "");
            }
        });
    }

    private void connectToAdapter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        todo : ????????? ???????????? ?????? ??????, ???????????? ProvisionalList_filterd ??? ?????? ??????
                        filtering();
                        if(ProvisionalList_Filterd.isEmpty() == false || ProvisionalList_Filterd.size() != 0){
//                            Collections.sort(arr,new Filtering_for_ganada());
                            mItemCount.setText(ProvisionalList_Filterd.size()+ "?????? ???????????? ???????????????");
                            adapter = new BrokerRecyclerAdapter(getApplicationContext(), ProvisionalList_Filterd);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else if(ProvisionalList_Filterd.size() == 0){
                            mItemCount.setText("?????? ????????? ?????????.");
                        }
//                        base_progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_broker_region:
                Intent intent = new Intent(getApplicationContext(), SidoActivity.class);
                intent.putExtra("from", "BrokerActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.btn_back_broker:
                onBackPressed();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.btn_broker_type:
                upload_dialog(view);
                break;
        }
    }

    public void upload_dialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_broker_type, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        Button apart = dialogView.findViewById(R.id.btn_broker_dialog_apart);
        Button office = dialogView.findViewById(R.id.btn_broker_dialog_office);
        Button villa = dialogView.findViewById(R.id.btn_broker_dialog_villa);

        if (mType.equals("?????????")) {
            apart.setBackgroundResource(R.drawable.button_round_blue);
            office.setBackgroundResource(R.drawable.button_round_white);
            villa.setBackgroundResource(R.drawable.button_round_white);
            apart.setTextColor(getColor(R.color.white));
            office.setTextColor(getColor(R.color.black));
            villa.setTextColor(getColor(R.color.black));
        } else if (mType.equals("????????????")) {
            apart.setBackgroundResource(R.drawable.button_round_white);
            office.setBackgroundResource(R.drawable.button_round_blue);
            villa.setBackgroundResource(R.drawable.button_round_white);
            apart.setTextColor(getColor(R.color.black));
            office.setTextColor(getColor(R.color.white));
            villa.setTextColor(getColor(R.color.black));
        } else if (mType.equals("??????")) {
            apart.setBackgroundResource(R.drawable.button_round_white);
            office.setBackgroundResource(R.drawable.button_round_white);
            villa.setBackgroundResource(R.drawable.button_round_blue);
            apart.setTextColor(getColor(R.color.black));
            office.setTextColor(getColor(R.color.black));
            villa.setTextColor(getColor(R.color.white));
        }

        apart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mType = "?????????";
                mTypeButton.setText(" ????????? ");
                connectToAdapter();
            }
        });
        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mType = "????????????";
                mTypeButton.setText(" ???????????? ");
                connectToAdapter();
            }
        });
        villa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mType = "??????";
                mTypeButton.setText(" ?????? ");
                connectToAdapter();
            }
        });
    }

    public void upload_dialog2(View v, Long idx) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_broker_info, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();


        Button no = dialogView.findViewById(R.id.btn_no_broker);
        Button yes = dialogView.findViewById(R.id.btn_okay_broker);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ????????? ???????????? ??????
                mRESTApi.startContract(idx,mUser.getUserId()).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        String code = response.headers().get("code");
                        if(code.equals("00")){
                            // todo : lottie??? ?????? ?????? ???????????? ?????? ??? / lottie ???????????? ????????? ??????
//                            Toast.makeText(BrokerActivity.this, "????????? ???????????????", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            lottie_upload_success.setVisibility(View.VISIBLE);
                            lottie_upload_success.playAnimation();
                            lottie_upload_success.addAnimatorListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) { }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    lottie_upload_success.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) { }

                                @Override
                                public void onAnimationRepeat(Animator animator) { }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable throwable) {
                        Toast.makeText(BrokerActivity.this, "????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
                getDataFromServer();
            }
        });

    }

    private void filtering() {
        if (ProvisionalList_Filterd.size() != 0) {
            ProvisionalList_Filterd.clear();
        }
        if (ProvisionalList.isEmpty() == false || ProvisionalList.size() != 0) {
            for (int i = 0 ; i < ProvisionalList.size() ; i++) {
                // ??????, ????????? ?????? ??????
                ProvisionalList_Filterd.add(ProvisionalList.get(i));

//                if (ProvisionalList.get(i).getSido().equals(mSido) && ProvisionalList.get(i).getSigungoo().equals(mSigungu)) {
//                    // ????????? ?????? ????????? ????????? ?????? ??????
//                    if (!mType.equals("") && ProvisionalList.get(i).getResidence_type().equals(mType)) {
//                        ProvisionalList_Filterd.add(ProvisionalList.get(i));
//                        // ?????? ?????? ?????? ????????????
//                    }
//                }
            }
        }
    }
}