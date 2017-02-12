package com.Demo.vjrutnat.quanlyschitieu.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.Demo.vjrutnat.quanlyschitieu.R;
import com.Demo.vjrutnat.quanlyschitieu.model.Static;

import java.util.ArrayList;

/**
 * Created by VjrutNAT on 11/28/2016.
 */

public class StaticAdapter extends BaseAdapter{


    private Context mContext;
    private ArrayList<Static> mData;
    private LayoutInflater mLayoutInflater;

    public StaticAdapter(Context mContext, ArrayList<Static> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = mLayoutInflater.inflate(R.layout.items_quanly, parent, false);
        ViewHoler viewHoler;
        if (convertView == null){
            viewHoler = new ViewHoler();
            convertView = mLayoutInflater.inflate(R.layout.item_static,parent,false);
            viewHoler.tvDateTime = (TextView) convertView.findViewById(R.id.tv_dateTime);
            viewHoler.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHoler.tvAccount = (TextView) convertView.findViewById(R.id.tv_account);
            viewHoler.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            viewHoler.tvSurplus = (TextView) convertView.findViewById(R.id.tv_surplus);
            viewHoler.tvTransactiontype = (TextView) convertView.findViewById(R.id.tv_type);
            convertView.setTag(viewHoler);
        }else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        viewHoler.tvAccount.setText(mData.get(position).getAccount());
        viewHoler.tvContent.setText(mData.get(position).getContent());
        viewHoler.tvDateTime.setText(mData.get(position).getDate() + mData.get(position).getTime());
        viewHoler.tvMoney.setText(mData.get(position).getMoney());
        viewHoler.tvSurplus.setText(mData.get(position).getSurplus());
        viewHoler.tvTransactiontype.setText(mData.get(position).getTransaction_type());
        return convertView;
    }

    public static class ViewHoler{
        TextView tvDateTime, tvContent, tvMoney, tvSurplus, tvAccount, tvTransactiontype;
    }
}
