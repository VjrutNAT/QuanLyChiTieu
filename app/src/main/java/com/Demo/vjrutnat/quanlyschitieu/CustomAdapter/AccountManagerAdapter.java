package com.Demo.vjrutnat.quanlyschitieu.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.Demo.vjrutnat.quanlyschitieu.QuanLyChiTieu.MainActivity;
import com.Demo.vjrutnat.quanlyschitieu.R;
import com.Demo.vjrutnat.quanlyschitieu.SQLiteDataBase.SQLiteDataBase;
import com.Demo.vjrutnat.quanlyschitieu.model.AccountManager;

import java.util.ArrayList;

/**
 * Created by VjrutNAT on 11/30/2016.
 */

public class AccountManagerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<AccountManager> DataManager;
    private LayoutInflater mInflater;
    private SQLiteDataBase sQliteDataBase;

    public AccountManagerAdapter(Context mContext, ArrayList<AccountManager> dataManager, SQLiteDataBase sQliteDataBase) {
        this.mContext = mContext;
        this.DataManager = dataManager;
        this.sQliteDataBase = sQliteDataBase;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return DataManager.size();
    }

    @Override
    public Object getItem(int position) {
        return DataManager.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoller viewHoller;
        if (convertView == null){
            viewHoller = new ViewHoller();
            convertView = mInflater.inflate(R.layout.items_quanly, parent, false);
            viewHoller.tvAccount = (TextView) convertView.findViewById(R.id.tv_nameAccountManager);
            viewHoller.tvMoney = (TextView) convertView.findViewById(R.id.tv_moneyManager);
            viewHoller.btnDelete = (Button) convertView.findViewById(R.id.btn_delete);
            convertView.setTag(viewHoller);
        }else {
            viewHoller = (ViewHoller) convertView.getTag();
        }
        viewHoller.tvAccount.setText(DataManager.get(position).getNameAccount());
        viewHoller.tvMoney.setText(DataManager.get(position).getMoneyManager());
        viewHoller.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = DataManager.get(position).getId()+"";
                sQliteDataBase.deleteRecord(new String[] {id});
                DataManager.remove(DataManager.get(position));
                notifyDataSetChanged();

                Intent intent = new Intent(MainActivity.ACTION_UPDATE_DATABASE);
                mContext.sendBroadcast(intent);
            }
        });

        return convertView;
    }

    public static class ViewHoller{
        TextView tvAccount;
        TextView tvMoney;
        Button btnDelete;
    }
}
