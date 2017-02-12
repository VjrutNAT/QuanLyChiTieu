package com.Demo.vjrutnat.quanlyschitieu.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.Demo.vjrutnat.quanlyschitieu.R;
import com.Demo.vjrutnat.quanlyschitieu.model.Account;

import java.util.ArrayList;

/**
 * Created by VjrutNAT on 12/4/2016.
 */

public class AccountAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Account> mData;
    LayoutInflater mInflater;

    public AccountAdapter(Context context, ArrayList<Account> mData) {
        this.context = context;
        this.mData = mData;
        mInflater = LayoutInflater.from(context);
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
        ViewHoller mViewHoller;
        if (convertView == null){
            mViewHoller = new ViewHoller();
            convertView = mInflater.inflate(R.layout.items_money1, parent, false);
            mViewHoller.title = (TextView) convertView.findViewById(R.id.tv_nameTK);
            mViewHoller.currentMoney = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(mViewHoller);
        }else
        mViewHoller = (ViewHoller) convertView.getTag();
        mViewHoller.title.setText(mData.get(position).getTitle());
        mViewHoller.currentMoney.setText(mData.get(position).getCurrentMoney()+"");
        return convertView;
    }

    public static class ViewHoller{
        TextView title,currentMoney;
    }
}
