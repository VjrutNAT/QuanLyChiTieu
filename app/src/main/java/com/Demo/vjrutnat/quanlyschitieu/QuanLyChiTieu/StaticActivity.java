package com.Demo.vjrutnat.quanlyschitieu.QuanLyChiTieu;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Demo.vjrutnat.quanlyschitieu.CustomAdapter.StaticAdapter;
import com.Demo.vjrutnat.quanlyschitieu.R;
import com.Demo.vjrutnat.quanlyschitieu.SQLiteDataBase.SQLiteDataBase;
import com.Demo.vjrutnat.quanlyschitieu.model.Static;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StaticActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvNote;
    TextView tvTuNgay;
    TextView tvDenNgay;
    Spinner spAccount;
    Spinner spType;
    ListView lvStatic;
    ArrayList<Static> mData;
    SQLiteDataBase mSqLiteDataBase;
    ArrayList<String> dataAccount;
    ArrayList<String> dataType;
    private int mSpinerTypeSelected = 0;
    private int mSpinnerAccountSelected = 0;
    StaticAdapter adapter;
    ArrayList<Integer> idAccount;
    String type;
    int idTaiKhoan = 0;
    String tvDateFrom;
    String tvDateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);
        init();
        tvTuNgay = (TextView) findViewById(R.id.tv_date);
        tvTuNgay.setText("Chon ngay");
        tvDenNgay = (TextView) findViewById(R.id.tv_date2);
        tvDenNgay.setText("Chon ngay");
        tvTuNgay.setOnClickListener(this);
        tvDenNgay.setOnClickListener(this);
        mSqLiteDataBase = new SQLiteDataBase(StaticActivity.this);
        mData = new ArrayList<>();
        String sql1 = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION;
        adapter = new StaticAdapter(StaticActivity.this, mData);
//        queryStatic(sql1);
        if (mData != null){
            tvNote.setVisibility(View.VISIBLE);
        }else {
         tvNote.setVisibility(View.GONE);
        }
        lvStatic.setAdapter(adapter);
        dataAccount = new ArrayList<>();
        idAccount = new ArrayList<>();
        dataAccount.add("Tài Khoản");
        String sql2 = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_ACCOUNT;
        Cursor cursorAccount = mSqLiteDataBase.query(sql2);
        if (cursorAccount != null && cursorAccount.moveToFirst()){
            do {
                int id = cursorAccount.getInt(cursorAccount.getColumnIndex(SQLiteDataBase.COLUMN_ID));
                String account = cursorAccount.getString(cursorAccount.getColumnIndex(SQLiteDataBase.COLUMN_ACCOUNT));
                idAccount.add(id);
                dataAccount.add(account);
            }while (cursorAccount.moveToNext());
            cursorAccount.close();
        }

        final ArrayAdapter<String> adapterAccount = new ArrayAdapter<String>(StaticActivity.this,android.R.layout.simple_list_item_1,dataAccount);
        spAccount.setAdapter(adapterAccount);
        spAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAccountSelected =  mSpinnerAccountSelected + 1;
                if (mSpinnerAccountSelected > 1 ) {
                    String sql;
                    if ("Loại Giao dịch".equals(spType.getSelectedItem())){
                        if (position == 0){
                            idTaiKhoan = 0;
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION ;
                        }else {
                            idTaiKhoan = idAccount.get(position - 1);
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE "  + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')" + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_ID_ACCOUNT +
                                    " = " + idTaiKhoan ;
                        }
                    }else {
                        if (position == 0){
                            idTaiKhoan = 0;
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_TYPE
                            + " = '" + type + "'";
                        }else {
                            idTaiKhoan = idAccount.get(position - 1);
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE "  + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')" + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_ID_ACCOUNT +
                                    " = " + idTaiKhoan + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_TYPE
                                    + " = '" + type + "'";
                        }
                    }
                    queryStatic(sql);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dataType = new ArrayList<>();
        dataType.add("Loại Giao dịch");
        dataType.add("khoan chi");
        dataType.add("khoan thu");
        final ArrayAdapter<String> arrayType = new ArrayAdapter<String>(StaticActivity.this,android.R.layout.simple_list_item_1, dataType);
        spType.setAdapter(arrayType);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinerTypeSelected = mSpinerTypeSelected + 1;
                if (mSpinerTypeSelected > 1) {
                    String sql;
                    if (idTaiKhoan == 0){
                        if (position == 0 ) {
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION;
                        }else {
                            type = dataType.get(position);
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')" + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_TYPE + " = '"
                                    + type + "'";
                        }
                    }else {
                        if (position == 0 ) {
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_ID_ACCOUNT
                            + " = " + idTaiKhoan;
                        }else {
                            type = dataType.get(position);
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')" + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_TYPE + " = '"
                                    + type + "'" + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_ID_ACCOUNT + " = " + idTaiKhoan;
                        }
                    }
                    queryStatic(sql);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    DatePickerDialog.OnDateSetListener onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            String strDate1 = simpleDateFormat.format(calendar.getTime());
            tvTuNgay.setText(strDate1);
            tvNote.setVisibility(View.GONE);
            try{
                Date date1 = simpleDateFormat.parse(tvTuNgay.getText().toString());
                if (!"Chon ngay".equals(tvDenNgay.getText())){
                    Date date2 = simpleDateFormat.parse(tvDenNgay.getText().toString());
                    if (date1.after(date2)){
                        Toast.makeText(StaticActivity.this,"Thời gian k hợp lệ",Toast.LENGTH_SHORT).show();
                    }else {
                        tvDateFrom = tvTuNgay.getText().toString();
                        tvDateTo = tvDenNgay.getText().toString();
                        String sql;
                        if (idTaiKhoan == 0 && "Loại Giao dịch".equals(spType.getSelectedItem())){
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')";
                        }else if (idTaiKhoan == 0 && !"Loại Giao dịch".equals(spType.getSelectedItem())){
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')" + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_TYPE + " = '"
                                    + type + "'" ;
                        }else if (idTaiKhoan != 0 && "Loại Giao dịch".equals(spType.getSelectedItem())){
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')" + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_ID_ACCOUNT + idTaiKhoan;
                        }else {
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')" + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_ID_ACCOUNT + idTaiKhoan
                                    + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_TYPE + " = '" + type + "'";
                        }
                        queryStatic(sql);
                    }
                }

            }catch (ParseException e){
                e.printStackTrace();
            }
        }
    };

    DatePickerDialog.OnDateSetListener onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            String strDate2 = simpleDateFormat.format(calendar.getTime());
            tvDenNgay.setText(strDate2);
            try {
                Date date2 = simpleDateFormat.parse(tvDenNgay.getText().toString());
                if (!"Chon ngay".equals(tvDenNgay.getText())) {
                    Date date1 = simpleDateFormat.parse(tvDenNgay.getText().toString());
                    if (date2.before(date1)){
                        Toast.makeText(StaticActivity.this, "thoi gian k hop le" , Toast.LENGTH_SHORT).show();
                    }else {
                        tvDateFrom = tvTuNgay.getText().toString();
                        tvDateTo = tvDenNgay.getText().toString();
                        String sql;
                        if (idTaiKhoan == 0 && "Loại Giao dịch".equals(spType.getSelectedItem())){
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')";
                        }else if (idTaiKhoan == 0 && !"Loại Giao dịch".equals(spType.getSelectedItem())){
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')" + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_TYPE + " = '"
                                    + type + "'" ;
                        }else if (idTaiKhoan != 0 && "Loại Giao dịch".equals(spType.getSelectedItem())){
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')" + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_ID_ACCOUNT + idTaiKhoan;
                        }else {
                            sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_TRANSACTION + " WHERE " + SQLiteDataBase.COLUMN_TRANSACTION_DATE
                                    + " BETWEEN DATETIME ( '" + tvDateFrom  + "') AND DATETIME('" + tvDateTo + "')" + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_ID_ACCOUNT + idTaiKhoan
                                    + " AND " + SQLiteDataBase.COLUMN_TRANSACTION_TYPE + " = '" + type + "'";
                        }
                        queryStatic(sql);
                    }
                }

            }catch (ParseException e){
                e.printStackTrace();
            }
        }
    };
    public void init(){
        tvNote = (TextView) findViewById(R.id.tv_noteStatic);
        lvStatic = (ListView) findViewById(R.id.lv_table);
        spAccount = (Spinner) findViewById(R.id.sp_account);
        spType = (Spinner) findViewById(R.id.sp_transaction_type);
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        switch (v.getId()){
            case R.id.tv_date:
                new DatePickerDialog(StaticActivity.this, onDateSetListener1,year, month, day).show();
                break;
            case R.id.tv_date2:
                new DatePickerDialog(StaticActivity.this, onDateSetListener2, year, month, day).show();
                default:
                break;
        }
    }

    public void queryStatic(String sql){
        Log.d("StaticActivity", " sql-> " + sql);
        final Cursor cursor = mSqLiteDataBase.query(sql);
        if ( mData != null && mData.size() > 0 ) {
            mData.clear();
        }
        if(cursor != null && cursor.moveToFirst()){
            do {
                String account = cursor.getString(cursor.getColumnIndex(SQLiteDataBase.COLUMN_TRANSACTION_ID_ACCOUNT));
                int id = cursor.getInt(cursor.getColumnIndex(SQLiteDataBase.COLUMN_TRANSACTION_ID));
                String date = cursor.getString(cursor.getColumnIndex(SQLiteDataBase.COLUMN_TRANSACTION_DATE));
                String time =  cursor.getString(cursor.getColumnIndex(SQLiteDataBase.COLUMN_TRANSACTION_TIME));
                String content = cursor.getString(cursor.getColumnIndex(SQLiteDataBase.COLUMN_TRANSACTION_CONTENT));
                int money = cursor.getInt(cursor.getColumnIndex(SQLiteDataBase.COLUMN_TRANSACTION_MONEY));
                int surplus = cursor.getInt(cursor.getColumnIndex(SQLiteDataBase.COLUMN_TRANSACTION_SURPLUS));
                String type = cursor.getString(cursor.getColumnIndex(SQLiteDataBase.COLUMN_TRANSACTION_TYPE));
                String sql3 = "SELECT " + SQLiteDataBase.COLUMN_ACCOUNT + " FROM " + SQLiteDataBase.TABLE_NAME_ACCOUNT + " WHERE "
                        + SQLiteDataBase.COLUMN_ID + " = " +  account;
                Cursor cursor3 = mSqLiteDataBase.query(sql3);
                String tk = "";
                if (cursor3 != null && cursor3.moveToFirst()){
                    do {
                        tk = cursor3.getString(cursor3.getColumnIndex(SQLiteDataBase.COLUMN_ACCOUNT));

                    }while (cursor3.moveToNext());
                }
                Static aStatic = new Static(date ,time,content, String.valueOf(surplus),tk, type, String.valueOf(money));
                mData.add(aStatic);
            }while (cursor.moveToNext());
            cursor.close();
        }else {
            Toast.makeText(StaticActivity.this, "không có giao dịch nào được thực hiện ", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    public void  daeSelected (){

    }
}
