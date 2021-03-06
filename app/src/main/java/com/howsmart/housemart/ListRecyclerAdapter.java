package com.howsmart.housemart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.howsmart.housemart.Model.PermittedHouse;

import java.util.ArrayList;

class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.CustomViewHolder> {

    private ArrayList<PermittedHouse> mList;
    private LayoutInflater mInflate;
    private Context context;

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private CardView card_item_list_titleimg;
        private TextView residence_name, info, sale_price;
        private ImageView iv_item_list_title;

        public CustomViewHolder(View view) {
            super(view);
            this.residence_name = view.findViewById(R.id.tv_item_list_name);
            this.info = view.findViewById(R.id.tv_item_list_info);
            this.sale_price = view.findViewById(R.id.tv_item_list_price);
            this.card_item_list_titleimg = view.findViewById(R.id.card_item_list_titleimg);
            this.iv_item_list_title = view.findViewById(R.id.iv_item_list_title);
        }
    }

    public ListRecyclerAdapter(Context context, ArrayList<PermittedHouse> items) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_list, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        //holder.card_item_list_titleimg.setBackground();
        holder.residence_name.setText(mList.get(position).getResidence_name());
        String info = null;
        String pyeong = String.format("%.1f", mList.get(position).getLeaseable_area() / 3.3);
        info = mList.get(position).getDongri() + " ?? " + pyeong + "??? ?? " + mList.get(position).getDong() + "??? ?? "
                + mList.get(position).getHo() / 100  + "???";
        holder.info.setText(info);


        long uk = mList.get(position).getSale_price() / 100000000;
        long man = (mList.get(position).getSale_price() / 10000) % 10000;
        if (mList.get(position).getSale_type().equals("??????")) {
            if (uk > 0) {
                if (man == 0) holder.sale_price.setText(mList.get(position).getSale_type() + " " + mList.get(position).getMonthly_price() / 10000 + " / " + uk + "???");
                else holder.sale_price.setText(mList.get(position).getSale_type() + " " + mList.get(position).getMonthly_price() / 10000 + " / " + uk + "??? " + man + "???");
            } else {
                holder.sale_price.setText(mList.get(position).getSale_type() + " " + mList.get(position).getMonthly_price() / 10000 + " / " + man + "???");
            }
        } else {
            if (uk > 0) {
                if (man == 0) holder.sale_price.setText(mList.get(position).getSale_type() + " " + uk + "???");
                else holder.sale_price.setText(mList.get(position).getSale_type() + " " + uk + "??? " + man + "???");
            } else {
                holder.sale_price.setText(mList.get(position).getSale_type() + " " + man + "???");
            }
        }
//
//        if (mList.get(position).getSale_price() != 0) {
//            holder.sale_price.setText("??????" + mList.get(position).getSale_price());
//        } else if (mList.get(position).getDeposit() != 0) {
//            holder.sale_price.setText( "?????? " + mList.get(position).getDeposit());
//        } else {
//            String price = "?????? " + mList.get(position).getMonthly_price() + "/" + mList.get(position).getMonthly_deposit();
//            holder.sale_price.setText(price);
//        }

        Glide.with(context).load(mList.get(position).getTitleImg()).into(holder.iv_item_list_title);
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
