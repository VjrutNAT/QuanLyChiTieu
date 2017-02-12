package com.Demo.vjrutnat.quanlyschitieu.QuanLyChiTieu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.Demo.vjrutnat.quanlyschitieu.CustomAdapter.AccountAdapter;
import com.Demo.vjrutnat.quanlyschitieu.R;
import com.Demo.vjrutnat.quanlyschitieu.SQLiteDataBase.SQLiteDataBase;
import com.Demo.vjrutnat.quanlyschitieu.model.Account;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String ACTION_UPDATE_DATABASE = "com.vjrutnat.quanlychitieu. ACTION_UPDATE_DATABASE";
    AccountAdapter accountAdapter;
    SQLiteDataBase mSqLiteDataBase;
    ListView lvAccount;
    ArrayList<Account> mData;
    TextView tvNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        LinearLayout ll_money = (LinearLayout) findViewById(R.id.ll_show);
//        final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
//        int n =20;
//        for (int i = 0; i<n;i++){
//            View itemsView  = inflater.inflate(R.layout.items_money1,null);
//            TextView tvNameTK = (TextView)itemsView.findViewById(R.id.tv_nameTK);
//            TextView tvMoney = (TextView) itemsView.findViewById(R.id.tv_money);
//            tvNameTK.setText("Tai khoan"+i);
//            tvMoney.setText("50000"+i);
//            ll_money.addView(itemsView);
//        }
        registerBroadcast();
        init();
        mSqLiteDataBase = new SQLiteDataBase(MainActivity.this);
        String sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_ACCOUNT;
        mData = new ArrayList<Account>();
        Cursor cursor = mSqLiteDataBase.query(sql);
        if (cursor != null && cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(SQLiteDataBase.COLUMN_ID));
                String account = cursor.getString(cursor.getColumnIndex(SQLiteDataBase.COLUMN_ACCOUNT));
                String money = cursor.getString(cursor.getColumnIndex(SQLiteDataBase.COLUMN_MONEY_SUM));
                Account accountManager = new Account(account, Integer.parseInt(money));
                mData.add(accountManager);
            }while (cursor.moveToNext());
            tvNote.setVisibility(View.GONE);
        }else {
            tvNote.setVisibility(View.VISIBLE);
        }
        accountAdapter = new AccountAdapter(MainActivity.this, mData);
        lvAccount = (ListView) findViewById(R.id.lv_notification);
        lvAccount.setAdapter(accountAdapter);
    }
    private void init(){
        Button btnAddTransaction = (Button) findViewById(R.id.btn_add_transaction);
        Button btnStatistic = (Button) findViewById(R.id.btn_Trading_statistics);
        Button btnIntroduce = (Button) findViewById(R.id.btn_introduce);
        Button btnManager = (Button) findViewById(R.id.btn_manager);

        btnAddTransaction.setOnClickListener(this);
        btnStatistic.setOnClickListener(this);
        btnIntroduce.setOnClickListener(this);
        btnManager.setOnClickListener(this);

        tvNote = (TextView) findViewById(R.id.tv_introduce);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_transaction:
                Intent intentTransaction = new Intent(MainActivity.this, AddTransactionActivity.class);
                startActivity(intentTransaction);
                break;
            case R.id.btn_Trading_statistics:
                Intent intentStatic = new Intent(MainActivity.this, StaticActivity.class);
                startActivity(intentStatic);
                break;
            case R.id.btn_introduce:
                Intent intentIntroduce = new Intent(MainActivity.this, IntroduceActivity.class);
                startActivity(intentIntroduce);
                break;
            case R.id.btn_manager:
                Intent intentManager = new Intent(MainActivity.this, AccountManagerActivity.class);
                startActivity(intentManager);
                break;
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_UPDATE_DATABASE.equals(action)){
                String sql2 = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_ACCOUNT;
                Cursor cursor = mSqLiteDataBase.query(sql2);
                mData.clear();
                if (cursor != null && cursor.moveToFirst()){
                    do {
                        String accountName = cursor.getString(cursor.getColumnIndex(SQLiteDataBase.COLUMN_ACCOUNT));
                        int money = cursor.getInt(cursor.getColumnIndex(SQLiteDataBase.COLUMN_MONEY_SUM));
                        Account account = new Account(accountName, money);
                        mData.add(account);
                    }while (cursor.moveToNext());
                }
                accountAdapter.notifyDataSetChanged();
                tvNote.setVisibility(View.GONE);
            }
        }
    };
    private void registerBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE_DATABASE);
        registerReceiver(broadcastReceiver, filter);
    }

    private void unregisterBroadcast(){
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }
}
