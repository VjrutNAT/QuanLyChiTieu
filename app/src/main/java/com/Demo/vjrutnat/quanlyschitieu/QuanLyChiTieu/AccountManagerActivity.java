package com.Demo.vjrutnat.quanlyschitieu.QuanLyChiTieu;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.Demo.vjrutnat.quanlyschitieu.CustomAdapter.AccountManagerAdapter;
import com.Demo.vjrutnat.quanlyschitieu.R;
import com.Demo.vjrutnat.quanlyschitieu.SQLiteDataBase.SQLiteDataBase;
import com.Demo.vjrutnat.quanlyschitieu.model.AccountManager;

import java.util.ArrayList;

/**
 * Created by Acer on 11/24/2016.
 */

public class AccountManagerActivity extends Activity {

    AccountManagerAdapter accountManagerAdapter;
    ArrayList<AccountManager> mData;
    EditText edtAccount;
    EditText edtMoney;
    Button btnAdd;
    SQLiteDataBase mSQliteDataBase;
    ListView lvAccount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_manager);
//        LinearLayout llQuanLy = (LinearLayout) findViewById(R.id.ll_quanly);
//        LayoutInflater inflater = LayoutInflater.from(AccountManagerActivity.this);
//        int n = 20;
//        for(int i =0;i<n;i++){
//            View itemsView = inflater.inflate(R.layout.items_quanly,null);
//            TextView tvnameTk = (TextView) itemsView.findViewById(R.id.tv_nameTKQL);
//            TextView tvmoneyQL = (TextView) itemsView.findViewById(R.id.tv_moneyQL);
//            tvnameTk.setText("tien mat"+i);
//            tvmoneyQL.setText("5405045"+i);
//            llQuanLy.addView(itemsView);
//            Log.d("tien mat","tien mat");
//        }

        edtAccount = (EditText) findViewById(R.id.edt_account);
        edtMoney = (EditText) findViewById(R.id.edt_money);
        lvAccount = (ListView) findViewById(R.id.lv_notification2);
//        SQLiteDataBase = SQLiteDataBase.getmInstance(AccountManagerActivity.this);
        mSQliteDataBase = new SQLiteDataBase(AccountManagerActivity.this);
        String sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_ACCOUNT;
        mData = new ArrayList<AccountManager>();
        Cursor cursor = mSQliteDataBase.query(sql);
        if (cursor != null && cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(mSQliteDataBase.COLUMN_ID));
                String accountName = cursor.getString(cursor.getColumnIndex(mSQliteDataBase.COLUMN_ACCOUNT));
                int money = cursor.getInt(cursor.getColumnIndex(mSQliteDataBase.COLUMN_MONEY_SUM));
                AccountManager accountManager = new AccountManager(accountName, String.valueOf(money));
                accountManager.setId(id);
                mData.add(accountManager);
            }while (cursor.moveToNext());
        }
        accountManagerAdapter = new AccountManagerAdapter(AccountManagerActivity.this, mData, mSQliteDataBase);
        lvAccount.setAdapter(accountManagerAdapter);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                String account = edtAccount.getText().toString();
                String money = edtMoney.getText().toString();
                values.put(SQLiteDataBase.COLUMN_ACCOUNT,account);
                values.put(SQLiteDataBase.COLUMN_MONEY_SUM,money);
                mSQliteDataBase.insertRecordAccount(values);
                edtAccount.setText("");
                edtMoney.setText("");
                AccountManager accountManager = new AccountManager(account, money );
                mData.add(accountManager);
                accountManagerAdapter.notifyDataSetChanged();

                Intent intent = new Intent(MainActivity.ACTION_UPDATE_DATABASE);
                sendBroadcast(intent);
            }
        });
    }
}
