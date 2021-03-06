package com.howsmart.housemart;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.howsmart.housemart.Adapter.PagerAdapter_Picture;
import com.howsmart.housemart.Adapter.RecyclerViewAdapter_Realprice;
import com.howsmart.housemart.Model.House;
import com.howsmart.housemart.Model.Pictures;
import com.howsmart.housemart.Model.Realprice;
import com.howsmart.housemart.Model.User;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener, PagerAdapter_Picture.OnItemClick {

    private ImageView btn_back;
    private AppCompatButton btn_type1, btn_type2, btn_type3, btn_no_dialog_upload, btn_okay_dialog_upload, btn_typesell, btn_typeothers;
    private LinearLayout lin_price_month, ll_realprice, ll_realprice_show, ll_realprice_noshow;
    private TextInputEditText edit_dong, edit_ho, edit_area1, edit_area2, edit_price_all, edit_price_month, edit_price_manage,
            edit_introduce_short, edit_introduce_long, edit_room, edit_toilet, edit_introduce_livingroom, edit_introduce_kitchen,
            edit_introduce_room1, edit_introduce_room2, edit_introduce_room3, edit_introduce_toilet1, edit_introduce_toilet2, edit_introduce_apartment;
    private TextView tv_area1, tv_area2, tv_ratio1, tv_ratio2, tv_price_ratio1, tv_price_ratio2, tv_price_type, tv_price_all, tv_price_month, tv_price_manage, tv_complete, edit_apartment, tv_movedate, tv_apartaddress_load, tv_name_realprice, tv_price1_realprice, tv_price2_realprice;
    private RangeSlider slider_ratio1, slider_ratio2;
    private CheckBox chk_nego, chk_door, chk_air, chk_ref, chk_kimchi, chk_closet, chk_oven, chk_induction, chk_airsystem, chk_movenow;
    private Calendar cal;
    private LineChart chart;
    private LineGraph lineGraph;

    private long price_first, price_second, price_third;

    private int picture_clicked_position = 0;

    private PagerAdapter_Picture pagerAdapter_picture;

    private DatePickerDialog.OnDateSetListener callbackMethod;
    private ViewPager viewPager;
    private PagerAdapter_Picture adapter;
    private List<Pictures> models;
    private List<String> models_description;

    House mHouse;

    private static final int IMAGE_REQUEST = 0;
    private static final int NAME_REQUEST = 1;
    private Uri imageUri;

    private String userId;//????????? ??????
    private String residence_name;//????????? ??????
    private String code;//????????? ??????
    private String address;//????????? ??????
    private String sido;//??????
    private String sigungoo;//?????????
    private String dongri;//??????
    private String date;//??????????????????
    private Integer allnumber;//?????????
    private Integer parkingnumber;//???????????????
    private String contact;//??????????????? ?????????

    private Integer dong, ho;//???,??????
    private Double net_leaseable_area;//????????????
    private Double leaseable_area;//????????????

    private String residence_type;//?????? ??????(A,V,O)
    private String sale_type;//"??????","??????","??????"
    private Long sale_price;//?????????/?????????/?????????
    private Long monthly_price;//??????
    private Long admin_expenses;//?????????

    private Integer provisional_down_pay_per = 10;//???????????? ??????
    private Integer down_pay_per = 10;//????????? ??????
    private Integer intermediate_pay_per = 30;//????????? ??????
    private Integer balance_per = 60;//?????? ??????

    private Integer room_num;//??? ??????
    private Integer toilet_num;//?????? ??????

    private boolean middle_door;//??????
    private boolean air_conditioner;//????????? ?????????
    private boolean refrigerator;//?????????
    private boolean kimchi_refrigerator;//???????????????
    private boolean closet;//????????????
    private boolean oven;//????????? ??????
    private boolean induction;//?????????
    private boolean airsystem;//????????? ?????????

    private boolean nego;//????????????

    private String short_description;//?????? ??? ??????
    private String long_description;//??? ??? ??????
    private String apartment_description;//????????? ??????
    private String livingroom_description;//?????? ??????
    private String kitchen_description;//?????? ??????
    private String room1_description;//???1 ??????
    private String room2_description;//???2 ??????
    private String room3_description;//???3 ??????
    private String toilet1_description;//?????????1 ??????
    private String toilet2_description;//?????????2 ??????

    private String movedate;//???????????????

    private List<MultipartBody.Part> pictures;
    private int roomcnt;
    private int toiletcnt;

    private User mUser;

    private RecyclerViewAdapter_Realprice adapter_realprice;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    ArrayList<Realprice> arr = null;

    List<String[]> list = null;
    List<String[]> list1 = null; //??????
    List<String[]> list2 = null;
    List<String[]> list3 = null;

    private String realpricetype;
    private AlertDialog alertDialog2;
    private LottieAnimationView animationView, lottie_upload_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        init();

        roomcnt = Integer.parseInt(getIntent().getStringExtra("roomcnt"));
        toiletcnt = Integer.parseInt(getIntent().getStringExtra("toiletcnt"));
        models = new ArrayList<>();
        models.add(new Pictures("????????? ?????? ????????? ??????????????????", R.drawable.image_apartment, "??????, ?????? ??????, ????????? ?????? ?????? ???????????? ???????????????", "????????? ??????"));
        models.add(new Pictures("?????? ????????? ??????????????????", R.drawable.image_frontdoor, "?????? ??????, ?????? ??????, ????????? ?????? ?????? ???????????? ???????????????", "?????? ??????"));
        models.add(new Pictures("?????? ????????? ??????????????????", R.drawable.image_livingroom, "????????? ?????? ??????, ??????, ???????????? ?????? ???????????? ???????????????", "?????? ??????"));
        models.add(new Pictures("?????? ????????? ??????????????????", R.drawable.image_kitchen, "????????????, ?????? ??????, ???????????? ?????? ???????????? ???????????????", "?????? ??????"));
        for (int i = 1; i <= roomcnt; i++) {
            models.add(new Pictures(i + "??? ???" + " ????????? ??????????????????", R.drawable.image_room4, "???????????? ??????, ?????? ?????? ?????? ???????????? ???????????????", i + "??? ??? ??????"));
        }
        for (int i = 1; i <= toiletcnt; i++) {
            models.add(new Pictures(i + "??? ?????????" + " ????????? ??????????????????", R.drawable.image_toilet1, "??????, ????????? ??????, ??????/?????? ?????? ?????? ???????????? ???????????????", i + "??? ????????? ??????"));
        }

        pagerAdapter_picture = new PagerAdapter_Picture();

        adapter = new PagerAdapter_Picture(models, this, this);

        viewPager = findViewById(R.id.viewPager_upload_picture);
        int dpValue = 54;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin / 3);

        viewPager.setAdapter(adapter);

    }

    private void init() {

        recyclerView = findViewById(R.id.recycler_realprice);
        manager = new LinearLayoutManager(UploadActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        cal = Calendar.getInstance();

        btn_back = findViewById(R.id.btn_back_upload);

        btn_type1 = findViewById(R.id.btn_type1);
        btn_type2 = findViewById(R.id.btn_type2);
        btn_type3 = findViewById(R.id.btn_type3);
        btn_typesell = findViewById(R.id.btn_typesell);
        btn_typeothers = findViewById(R.id.btn_typeothers);

        lin_price_month = findViewById(R.id.lin_price_month);
        ll_realprice = findViewById(R.id.ll_realprice);
        ll_realprice.setVisibility(View.GONE);
        ll_realprice_show = findViewById(R.id.ll_realprice_show);
        ll_realprice_noshow = findViewById(R.id.ll_realprice_noshow);


        edit_apartment = findViewById(R.id.edit_apartment);
        edit_dong = findViewById(R.id.edit_dong);
        edit_ho = findViewById(R.id.edit_ho);
        edit_area1 = findViewById(R.id.edit_area1);
        edit_area2 = findViewById(R.id.edit_area2);
        edit_price_all = findViewById(R.id.edit_price_all);
        edit_price_month = findViewById(R.id.edit_price_month);
        edit_price_manage = findViewById(R.id.edit_price_manage);
//        edit_room = findViewById(R.id.edit_room);
//        edit_toilet = findViewById(R.id.edit_toilet);
        edit_introduce_short = findViewById(R.id.edit_introduce_short);
        edit_introduce_long = findViewById(R.id.edit_introduce_long);
//        edit_introduce_apartment = findViewById(R.id.edit_introduce_apartment);
//        edit_introduce_livingroom= findViewById(R.id.edit_introduce_livingroom);
//        edit_introduce_kitchen = findViewById(R.id.edit_introduce_kitchen);
//        edit_introduce_room1 = findViewById(R.id.edit_introduce_room1);
//        edit_introduce_room2 = findViewById(R.id.edit_introduce_room2);
//        edit_introduce_room3 = findViewById(R.id.edit_introduce_room3);
//        edit_introduce_toilet1 = findViewById(R.id.edit_introduce_toilet1);
//        edit_introduce_toilet2 = findViewById(R.id.edit_introduce_toilet2);

        tv_area1 = findViewById(R.id.tv_area1);
        tv_area2 = findViewById(R.id.tv_area2);
        tv_ratio1 = findViewById(R.id.tv_ratio1);
        tv_ratio2 = findViewById(R.id.tv_ratio2);
        tv_price_ratio1 = findViewById(R.id.tv_price_ratio1);
        tv_price_ratio2 = findViewById(R.id.tv_price_ratio2);
        tv_price_type = findViewById(R.id.tv_price_type);
        tv_price_all = findViewById(R.id.tv_price_all);
        tv_price_month = findViewById(R.id.tv_price_month);
        tv_price_manage = findViewById(R.id.tv_price_manage);
        tv_complete = findViewById(R.id.tv_complete);
        tv_movedate = findViewById(R.id.tv_movedate);
        tv_apartaddress_load = findViewById(R.id.tv_apartaddress_load);
        tv_name_realprice = findViewById(R.id.tv_name_realprice);
        tv_price1_realprice = findViewById(R.id.tv_price1_realprice);
        tv_price2_realprice = findViewById(R.id.tv_price2_realprice);

        slider_ratio1 = findViewById(R.id.slider_ratio1);
        slider_ratio2 = findViewById(R.id.slider_ratio2);

        chk_nego = findViewById(R.id.chk_nego);
        chk_door = findViewById(R.id.chk_door);
        chk_air = findViewById(R.id.chk_air);
        chk_ref = findViewById(R.id.chk_ref);
        chk_kimchi = findViewById(R.id.chk_kimchi);
        chk_closet = findViewById(R.id.chk_closet);
        chk_oven = findViewById(R.id.chk_oven);
        chk_induction = findViewById(R.id.chk_induction);
        chk_airsystem = findViewById(R.id.chk_airsystem);
        chk_movenow = findViewById(R.id.chk_movenow);
        chart = findViewById(R.id.lineChart);
        lottie_upload_success = findViewById(R.id.lottie_upload_success);
        lineGraph = new LineGraph(UploadActivity.this, chart);

        btn_back.setOnClickListener(this);
        btn_type1.setOnClickListener(this);
        btn_type2.setOnClickListener(this);
        btn_type3.setOnClickListener(this);
        btn_typesell.setOnClickListener(this);
        btn_typeothers.setOnClickListener(this);

        chk_nego.setOnClickListener(this);
        chk_door.setOnClickListener(this);
        chk_air.setOnClickListener(this);
        chk_ref.setOnClickListener(this);
        chk_kimchi.setOnClickListener(this);
        chk_closet.setOnClickListener(this);
        chk_oven.setOnClickListener(this);
        chk_induction.setOnClickListener(this);
        chk_airsystem.setOnClickListener(this);
        chk_movenow.setOnClickListener(this);
        tv_complete.setOnClickListener(this);
        edit_apartment.setOnClickListener(this);
        tv_movedate.setOnClickListener(this);

        residence_type = "?????????";
        sale_type = "??????";
        realpricetype = "??????";

        nego = false;
        air_conditioner = false;
        middle_door = false;
        refrigerator = false;
        kimchi_refrigerator = false;
        closet = false;

        price_first = -1;
        sale_price = -1L;
        net_leaseable_area = 0.0;

        mUser = (User) getIntent().getSerializableExtra("user");

        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String d, m;
                if (dayOfMonth < 10) {
                    d = "0" + dayOfMonth;
                } else {
                    d = "" + dayOfMonth;
                }
                month++;//???????????? ?????? ?????? ?????? ?????????..
                if (month < 10) {
                    m = "0" + month;
                } else {
                    m = "" + month;
                }
                movedate = year + m + d;
                tv_movedate.setText(year + "??? " + month + "??? " + dayOfMonth + "???");
            }
        };

        edit_dong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_dong.getText().toString().equals("") || edit_dong.getText().toString() == null) {

                } else {
                    dong = Integer.parseInt(edit_dong.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_ho.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_ho.getText().toString().equals("") || edit_ho.getText().toString() == null) {

                } else {
                    ho = Integer.parseInt(edit_ho.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_introduce_short.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_introduce_short.getText().toString().equals("") || edit_introduce_short.getText().toString() == null) {

                } else {
                    short_description = edit_introduce_short.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_introduce_long.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_introduce_long.getText().toString().equals("") || edit_introduce_long.getText().toString() == null) {

                } else {
                    long_description = edit_introduce_long.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_area1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_area1.getText().toString().equals("") || edit_area1.getText().toString() == null) {
                    tv_area1.setText("??????");
                } else {
                    try {
                        net_leaseable_area = Double.parseDouble(edit_area1.getText().toString().trim());
                        tv_area1.setText(translateArea(net_leaseable_area));
                        realPrice();
                    } catch (NumberFormatException e) {
                        tv_area1.setText("??????");
                        Toast.makeText(getBaseContext(), "????????? ??????????????????", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_area2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_area2.getText().toString().equals("") || edit_area2.getText().toString() == null) {
                    tv_area2.setText("??????");
                } else {
                    try {
                        leaseable_area = Double.parseDouble(edit_area2.getText().toString().trim());
                        tv_area2.setText(translateArea(leaseable_area));
                    } catch (NumberFormatException e) {
                        tv_area2.setText("??????");
                        Toast.makeText(getBaseContext(), "????????? ??????????????????", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_price_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_price_all.getText().toString().equals("") || edit_price_all.getText().toString() == null) {
                    tv_price_all.setText("??????");
                } else {
                    sale_price = Long.parseLong(edit_price_all.getText().toString().trim());
                    tv_price_all.setText(translatePrice(sale_price));
                    tv_price_ratio1.setText("??????????????? ???????????? ??????????????? ??????????????????");
                    tv_price_ratio2.setText("??????????????? ???????????? ??????????????? ??????????????????");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_price_month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_price_month.getText().toString().equals("") || edit_price_month.getText().toString() == null) {
                    tv_price_month.setText("??????");
                } else {
                    monthly_price = Long.parseLong(edit_price_month.getText().toString().trim());
                    tv_price_month.setText(translatePrice(monthly_price));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_price_manage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_price_manage.getText().toString().equals("") || edit_price_manage.getText().toString() == null) {
                    tv_price_manage.setText("??????");
                } else {
                    admin_expenses = Long.parseLong(edit_price_manage.getText().toString().trim());
                    tv_price_manage.setText(translatePrice(admin_expenses));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        slider_ratio1.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                if (sale_price == -1) {
                    tv_price_ratio1.setText("??????????????? ???????????? ??????????????? ??????????????????");
                } else {
                    List<Float> thumbs = slider.getValues();
                    down_pay_per = Math.round(thumbs.get(0));
                    intermediate_pay_per = Math.round(thumbs.get(1));
                    intermediate_pay_per = intermediate_pay_per - down_pay_per;
                    balance_per = 100 - down_pay_per - intermediate_pay_per;
                    tv_ratio1.setText(down_pay_per + "% : " + intermediate_pay_per + "% : " + balance_per + "%");
                    price_first = sale_price * down_pay_per / 100;
                    price_second = sale_price * intermediate_pay_per / 100;
                    price_third = sale_price * balance_per / 100;
                    tv_price_ratio1.setText("????????? : " + translatePrice(price_first) + "\n????????? : " + translatePrice(price_second) + "\n    ?????? : " + translatePrice(price_third));
                    tv_price_ratio2.setText("??????????????? ???????????? ??????????????? ??????????????????");
                }
            }
        });
        slider_ratio2.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                if (price_first == -1) {
                    tv_price_ratio2.setText("??????????????? ???????????? ??????????????? ??????????????????");
                } else {
                    List<Float> thumbs = slider.getValues();
                    provisional_down_pay_per = Math.round(thumbs.get(0));
                    long second = 100 - provisional_down_pay_per;
                    tv_ratio2.setText(provisional_down_pay_per + "% : " + second + "%");
                    long first = price_first * provisional_down_pay_per / 100;
                    second = price_first * second / 100;
                    tv_price_ratio2.setText("        ???????????? : " + translatePrice(first) + "\n????????? ????????? : " + translatePrice(second));
                }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_upload:
                onBackPressed();
                break;

            case R.id.btn_type1:
                if (!sale_type.equals("??????")) {
                    btn_type1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type1.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    btn_type2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type2.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type3.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type3.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    lin_price_month.setVisibility(View.VISIBLE);
                    tv_price_type.setText("?????????");
                    sale_type = "??????";
                }
                break;

            case R.id.btn_type2:
                if (!sale_type.equals("??????")) {
                    btn_type1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type1.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type2.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    btn_type3.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type3.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    lin_price_month.setVisibility(View.GONE);
                    tv_price_type.setText("?????????");
                    sale_type = "??????";
                }
                break;

            case R.id.btn_type3:
                if (!sale_type.equals("??????")) {
                    btn_type1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type1.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type2.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type3.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type3.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    lin_price_month.setVisibility(View.GONE);
                    tv_price_type.setText("?????????");
                    sale_type = "??????";
                }
                break;

            case R.id.btn_typesell:
                if (!realpricetype.equals("??????")) {
                    btn_typesell.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_rectangle_blue));
                    btn_typesell.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    btn_typeothers.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_rectangle_whitegray));
                    btn_typeothers.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    realpricetype = "??????";
                    renewlist();
                }
                break;

            case R.id.btn_typeothers:
                if (!realpricetype.equals("???/??????")) {
                    btn_typesell.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_rectangle_whitegray));
                    btn_typesell.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_typeothers.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_rectangle_blue));
                    btn_typeothers.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    realpricetype = "???/??????";
                    renewlist();
                }
                break;


            case R.id.chk_nego:
                if (chk_nego.isChecked()) {
                    nego = true;
                } else {
                    nego = false;
                }
                break;
            case R.id.chk_door:
                if (chk_door.isChecked()) {
                    middle_door = true;
                } else {
                    middle_door = false;
                }
                break;
            case R.id.chk_air:
                if (chk_air.isChecked()) {
                    air_conditioner = true;
                } else {
                    air_conditioner = false;
                }
                break;
            case R.id.chk_ref:
                if (chk_ref.isChecked()) {
                    refrigerator = true;
                } else {
                    refrigerator = false;
                }
                break;
            case R.id.chk_kimchi:
                if (chk_kimchi.isChecked()) {
                    kimchi_refrigerator = true;
                } else {
                    kimchi_refrigerator = false;
                }
                break;
            case R.id.chk_closet:
                if (chk_closet.isChecked()) {
                    closet = true;
                } else {
                    closet = false;
                }
                break;
            case R.id.chk_oven:
                if (chk_oven.isChecked()) {
                    oven = true;
                } else {
                    oven = false;
                }
                break;
            case R.id.chk_induction:
                if (chk_induction.isChecked()) {
                    induction = true;
                } else {
                    induction = false;
                }
                break;
            case R.id.chk_airsystem:
                if (chk_airsystem.isChecked()) {
                    airsystem = true;
                } else {
                    airsystem = false;
                }
                break;

            case R.id.tv_complete:
                upload_dialog(view);
                break;

            case R.id.edit_apartment:
                //?????? uri?????? ???????????? ???????????? ??????
                Bundle bundle = new Bundle();
                for (int i = 0; i < models.size(); i++) {
                    Uri uri = models.get(i).getUri();
                    if (uri != null) {
                        bundle.putParcelable("picture" + i, uri);
                    }
                }
                getIntent().putExtras(bundle);
                Intent intent = new Intent(getBaseContext(), SearchActivity_upload.class);
//                intent.putExtra("models",models);
                startActivityForResult(intent, 1);

            case R.id.chk_movenow:
                if (chk_movenow.isChecked()) {
                    tv_movedate.setVisibility(View.GONE);
                    movedate = "?????? ??????";
                } else {
                    tv_movedate.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tv_movedate:
                DatePickerDialog dialog = new DatePickerDialog(UploadActivity.this, callbackMethod, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.show();
        }
    }


    public String translatePrice(Long price) {

        String a = "", b = "", c = "";
        if (price != null && price == 0) {
            return "0???";
        }
        if (price != null && price == -1) {
            return "?????????";
        }
        if (price != null && price >= 100000000) {
            Long price1 = price / 100000000;
            price %= 100000000;
            a = price1 + "???";
        }
        if (price != null && price >= 10000) {
            Long price2 = price / 10000;
            price %= 10000;
            b = price2 + "???";
            if (!a.equals(c)) {
                b = " " + b;
            }
        }
        if (price != null && price > 0) {
            c = price.toString();
            if (!a.equals(" ") || !b.equals(" ")) {
                c = " " + c;
            }
        }
        return a + b + c + "???";
    }

    public String translateArea(Double area) {
        area *= 0.3025;
        area = Math.round(area * 10) / 10.0;
        return area.toString() + " ???";
    }

    public void upload_dialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_upload, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
        Button ok_btn = dialogView.findViewById(R.id.btn_okay_dialog_upload);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                userId = mUser.getUserId();

                boolean judge_pic = true;
                for (int i = 0; i < models.size(); i++) {
                    if (models.get(i).getUri() == null) judge_pic = false;
                }
                if (judge_pic) {
                    pictures = new ArrayList<>();
                    getPicturesList();
                    getDescription();
                    mHouse = new House(userId, residence_name, code, address, sido, sigungoo, dongri, date,
                            allnumber, parkingnumber, contact, dong, ho, net_leaseable_area, leaseable_area,
                            residence_type, sale_type, sale_price, monthly_price, admin_expenses, provisional_down_pay_per,
                            down_pay_per, intermediate_pay_per, balance_per, roomcnt, toiletcnt, middle_door,
                            air_conditioner, refrigerator, kimchi_refrigerator, closet, oven, induction, airsystem,
                            nego, short_description, long_description, models_description.get(0), models_description.get(1),
                            models_description.get(2), models_description.get(3), models_description.get(4),
                            models_description.get(5), models_description.get(6), models_description.get(7), models_description.get(8), movedate);
                    upload_dialog2(v);
                    doRetrofit();
                } else {
                    Toast.makeText(UploadActivity.this, "?????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button cancle_btn = dialogView.findViewById(R.id.btn_no_dialog_upload);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void getDescription() {
        models_description = new ArrayList<>();
        models_description.add(models.get(0).getDescription());
        models_description.add(models.get(1).getDescription());
        models_description.add(models.get(2).getDescription());
        models_description.add(models.get(3).getDescription());

        for (int i = 0; i < roomcnt; i++) {
            models_description.add(models.get(4 + i).getDescription());
        }
        for (int i = 0; i < 3 - roomcnt; i++) {
            models_description.add("");
        }
        for (int i = 0; i < toiletcnt; i++) {
            models_description.add(models.get(4 + roomcnt + i).getDescription());
        }
        for (int i = 0; i < 2 - toiletcnt; i++) {
            models_description.add("");
        }
    }

    private void doRetrofit() {
        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.uploadHouse(
                mHouse.getUserId(),
                mHouse.getResidence_name(),
                mHouse.getCode(),
                mHouse.getAddress(),
                mHouse.getSido(),
                mHouse.getSigungoo(),
                mHouse.getDongri(),
                mHouse.getDate(),
                mHouse.getAllnumber(),
                mHouse.getParkingnumber(),
                mHouse.getContact(),
                mHouse.getDong(),
                mHouse.getHo(),
                mHouse.getNet_leaseable_area(),
                mHouse.getLeaseable_area(),
                mHouse.getResidence_type(),
                mHouse.getSale_type(),
                mHouse.getSale_price(),
                mHouse.getMonthly_price(),
                mHouse.getAdmin_expenses(),
                mHouse.getProvisional_down_pay_per(),
                mHouse.getDown_pay_per(),
                mHouse.getIntermediate_pay_per(),
                mHouse.getBalance_per(),
                mHouse.getRoom_num(),
                mHouse.getToilet_num(),
                mHouse.isMiddle_door(),
                mHouse.isAir_conditioner(),
                mHouse.isRefrigerator(),
                mHouse.isKimchi_refrigerator(),
                mHouse.isCloset(),
                mHouse.isOven(),
                mHouse.isInduction(),
                mHouse.isAirsystem(),
                mHouse.isNego(),
                mHouse.getShort_description(),
                mHouse.getLong_description(),
                mHouse.getApartment_description(),
                mHouse.getPorch_description(),
                mHouse.getLivingroom_description(),
                mHouse.getKitchen_description(),
                mHouse.getRoom1_description(),
                mHouse.getRoom2_description(),
                mHouse.getRoom3_description(),
                mHouse.getToilet1_description(),
                mHouse.getToilet2_description(),
                mHouse.getMovedate(),
                pictures.get(0),
                pictures.get(1),
                pictures.get(2),
                pictures.get(3),
                pictures.get(4),
                pictures.get(5),
                pictures.get(6),
                pictures.get(7),
                pictures.get(8))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("testtest", "");
                        String test_code = response.headers().get("code");
                        String test_body = response.headers().get("code");
                        Log.d("UploadActivity", "headercode" + test_code);
                        Log.d("UploadActivity", "body" + test_body);
                        Log.d("UploadActivity", "pictures" + pictures);


                        if (test_code != null && test_code.equals("00")) {
                            alertDialog2.dismiss();

                            lottie_upload_success.setVisibility(View.VISIBLE);
                            lottie_upload_success.playAnimation();
                            lottie_upload_success.addAnimatorListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    lottie_upload_success.setVisibility(View.GONE);
                                    Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                                    intent.putExtra("user",mUser);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
//                                    finish();
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });

////                            Toast.makeText(UploadActivity.this, "????????? ??????", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(UploadActivity.this, MainActivity.class);
////                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                            finish();
                        } else {
                            alertDialog2.dismiss();
                            Toast.makeText(UploadActivity.this, "????????? ??????" + test_code, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        alertDialog2.dismiss();
                        Log.d("UploadActivity", throwable.getMessage());
                    }
                });
    }

    private void getPicturesList() {
        for (int i = 0; i < models.size(); i++) {
            Uri uri = models.get(i).getUri();
            if (uri != null) {
                transUriToMultiPartFile(uri, i);
            }
        }
        for (int i = models.size(); i < 9; i++) {
            transUriToMultiPartFile(null, i);
        }
    }

    private void transUriToMultiPartFile(Uri uri, int position) {
        MultipartBody.Part filePart = null;
        Bitmap img = null;

        //change Uri to Bitmap
        if (uri != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    img = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
                } else {
                    img = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            img = BitmapFactory.decodeResource(getResources(), R.drawable.icon_parking);
        }

        try {
            String filenameJPEG = "file" + (position + 1) + ".jpg";
            File f = new File(this.getCacheDir(), filenameJPEG);
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            img.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, fos);
            fos.close();

            String filename = "file" + (position + 1);

            filePart = MultipartBody.Part.createFormData(filename, f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        pictures.add(filePart);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();

                //set Image to mIvPicture
                if (imageUri != null) {
                    models.get(picture_clicked_position).setUri(imageUri);
                    viewPager.setAdapter(adapter);
                    //mIvPicture.setImageURI(imageUri);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "?????? ?????? ??????", Toast.LENGTH_LONG).show();
            }

            viewPager.setCurrentItem(picture_clicked_position, true);
        }
        if (requestCode == NAME_REQUEST) {
            if (resultCode == RESULT_OK) {
                code = data.getStringExtra("code");
                residence_name = data.getStringExtra("name");
                address = data.getStringExtra("address");
                sido = data.getStringExtra("sido");
                sigungoo = data.getStringExtra("sigungoo");
                dongri = data.getStringExtra("dongri");
                date = data.getStringExtra("date");
                allnumber = data.getIntExtra("allnumber", 0);
                parkingnumber = data.getIntExtra("parkingnumber", 0);
                contact = data.getStringExtra("contact");

                edit_apartment.setText(residence_name);
                tv_apartaddress_load.setText(address);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "????????? ?????? ??????", Toast.LENGTH_LONG).show();
            }

            //????????? uri??? ?????? ?????????????????? ??????
            Bundle bundle = getIntent().getExtras();
            for (int i = 0; i < models.size(); i++) {
                Uri uri = bundle.getParcelable("picture" + i);
                if (uri != null) {
                    models.get(i).setUri(uri);
                }
            }

            viewPager.setAdapter(adapter);

            //??????????????? ?????? csv ??????
            renewlist();
            realPrice();
            ll_realprice.setVisibility(View.VISIBLE);
        }
    }

    public void renewlist() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                arr = new ArrayList<>();
                readDataFromCsv();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (arr.size() == 0) {
                            chart.setVisibility(View.GONE);
                            ll_realprice_show.setVisibility(View.GONE);
                            ll_realprice_noshow.setVisibility(View.VISIBLE);
                        } else {

                            ll_realprice_show.setVisibility(View.VISIBLE);
                            ll_realprice_noshow.setVisibility(View.GONE);
                            adapter_realprice = new RecyclerViewAdapter_Realprice(getApplicationContext(), arr);
                            adapter_realprice.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter_realprice);

                            int[] cnt = {0, 0, 0, 0, 0, 0, 0, 0};
                            Float[] sum = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
                            for (Realprice realprice : arr) {
                                int month = Integer.parseInt(realprice.getContract_date().substring(4, 6));
                                int price = changePrice(realprice.getSale_price());
                                int area = changeArea(realprice.getNet_leaseable_area());
                                ++cnt[month];
                                sum[month] += price / (area * 0.3024f);
                            }

                            ArrayList<Entry> values = new ArrayList<>();
                            for (int i = 1; i <= 7; ++i) {
                                values.add(new Entry(i, sum[i] / cnt[i]));
                            }
                            chart.setVisibility(View.VISIBLE);
                            lineGraph.createChart();
                            lineGraph.setData(values);
                        }
                    }
                });
            }
        }).start();
    }

    public void readDataFromCsv() {
        if (list1 == null) {
            InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.realprice1));
            CSVReader reader = new CSVReader(is);
            try {
                list1 = reader.readAll();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        if(list2==null) {
//            InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.realprice2));
//            CSVReader reader = new CSVReader(is);
//            try {
//                list2 = reader.readAll();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        if(list3==null){
//            list3=list1;
//            list3.addAll(list2);
//        }
        if (arr.size() != 0) {
            arr.clear();
            list.clear();
        }

//        if (realpricetype.equals("??????")){
//            list = list1;
//        }
//        else{
//            list = list2;
//        }

        list = list1;

        for (String[] str : list) {

            Realprice entity = new Realprice();
            entity.setNet_leaseable_area(str[1]);
            entity.setContract_date(str[2] + str[3]);
            entity.setSale_price(str[4]);
            entity.setFloor(str[5]);
            entity.setSale_type("??????");

//            if(str.length>7){
//                entity.setMonthly_price(str[7]);
//                entity.setSale_type(str[8]);
//            }
//            else{
//                entity.setSale_type("??????");
//            }

            if (!str[6].equals(" ")) {
                if (address != null && address.contains(str[6])) {  // ???????????? ????????? ??????
                    arr.add(entity); //entity ????????? search??? ???????????? ????????? arr??? ??????
                }
            } else {
                adapter_realprice = null; //???????????? ?????? ????????? adapter??? null??? ???????????
            }
        }
    }

    public void realPrice() {
        double sum = 0.0;
        int num = 0;
        tv_name_realprice.setText(residence_name + " " + net_leaseable_area + "m??");
        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                int temp = changeArea(arr.get(i).getNet_leaseable_area());
                if (temp == Integer.parseInt(String.valueOf(Math.round(net_leaseable_area)))) {
                    sum += changePrice(arr.get(i).getSale_price());
                    num++;
                }
            }
        }
        if (num == 0) {
            tv_price1_realprice.setText("?????? 1?????? ?????? ??????????????? ????????????");
            tv_price2_realprice.setText("");
        } else {
            sum /= num;
            setrealrPrice(sum);
        }
    }

    public void setrealrPrice(double average) {
        int av = (int) average;
        int av2 = (int) (3.3 * average / Integer.parseInt(String.valueOf(Math.round(net_leaseable_area))));
        tv_price1_realprice.setText(translatePrice2(av));
        tv_price2_realprice.setText(translatePrice2(av2));
    }

    public int changeArea(String area) {
        Double temp = Double.parseDouble(area);
        return Integer.parseInt(String.valueOf(Math.round(temp)));
    }

    public int changePrice(String price) {

        String a = "", b = "", c = "";
        a = price.substring(0, price.indexOf(","));
        b = price.substring(price.indexOf(",") + 1);
        return Integer.parseInt(a + b);
    }

    public String translatePrice2(int price) {

        String a = "", b = "", c = "";
        if (price == 0) {
            return "0???";
        }
        if (price >= 10000) {
            int price1 = price / 10000;
            price %= 10000;
            a = price1 + "??? ";
        }
        if (price > 0) {
            b = price + "???";
        }
        return a + b + "???";
    }

    @Override
    public void onClick(int value) {
        picture_clicked_position = value;
    }

    public void upload_dialog2(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        alertDialog2 = builder.create();
        alertDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog2.show();

        animationView = dialogView.findViewById(R.id.lottie_progress);
        animationView.playAnimation();
    }
}
