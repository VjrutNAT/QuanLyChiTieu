package com.Demo.vjrutnat.quanlyschitieu.QuanLyChiTieu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.Demo.vjrutnat.quanlyschitieu.R;
import com.Demo.vjrutnat.quanlyschitieu.SQLiteDataBase.SQLiteDataBase;
import com.Demo.vjrutnat.quanlyschitieu.model.Account;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    RadioGroup rg;
    RadioButton rbtnKhoanchi,rbtnKhoanthu;
    EditText edtMoney, edtContent;
    Button btnSave, btnSaveClose;
    TextView tvDate, tvTime;
    SQLiteDataBase mSqLiteDataBase ;
    String type;
    int idAccount;
    int surplus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction);
        mSqLiteDataBase = new SQLiteDataBase(AddTransactionActivity.this);
        Init();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = simpleDateFormat.format(calendar.getTime());
        tvDate.setText(strDate);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm:ss");
        String strTime  = simpleDateFormat1.format(calendar.getTime());
        tvTime.setText(strTime);
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnSaveClose.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);

        ArrayList<String> arrayListString = new ArrayList<>();
        final ArrayList<Account> arrayList = new ArrayList<>();
        String sql = "SELECT * FROM " + SQLiteDataBase.TABLE_NAME_ACCOUNT;
        Cursor cursor = mSqLiteDataBase.query(sql);
        if (cursor != null && cursor.moveToFirst()){
            do {
                String title = cursor.getString(cursor.getColumnIndex(SQLiteDataBase.COLUMN_ACCOUNT));
                int id = cursor.getInt(cursor.getColumnIndex(SQLiteDataBase.COLUMN_ID));
                int currentMoney = cursor.getInt(cursor.getColumnIndex(SQLiteDataBase.COLUMN_MONEY_SUM));
                Account account1  = new Account(title, currentMoney, id);
                arrayList.add(account1);
                arrayListString.add(title);
            }while (cursor.moveToNext());
        }
        Spinner mSpinner = (Spinner) findViewById(R.id.spn_gd);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.items_spinner, arrayListString);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idAccount  = arrayList.get(position).getId();
                surplus = arrayList.get(position).getCurrentMoney();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        tvDate.setText(simpleDateFormat.format(calendar.getTime()));
//        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm", Locale.getDefault());
//        tvTime.setText(simpleDateFormat.format(calendar.getTime()));
//        ArrayList<Static> staticArrayList = new ArrayList<>();
    }

    private void Init() {
        rg = (RadioGroup) findViewById(R.id.rg);
        rbtnKhoanchi = (RadioButton) findViewById(R.id.rd_khoan_chi);
        rbtnKhoanthu = (RadioButton) findViewById(R.id.rd_khoan_thu);
        edtMoney = (EditText) findViewById(R.id.edt_addmoney);
        edtContent = (EditText) findViewById(R.id.edt_addreson);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvTime = (TextView) findViewById(R.id.tv_time);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSaveClose = (Button) findViewById(R.id.btn_saveClose);
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        switch (v.getId()){
            case R.id.tv_date:
                new DatePickerDialog(AddTransactionActivity.this, onDateSetListener, year, month, day).show();
                break;
            case R.id.tv_time:
                new TimePickerDialog(AddTransactionActivity.this, onTimeSetListener, hour, minute, false).show();
                break;
            case R.id.btn_save:
                insertTransaction();
                break;
            case R.id.btn_saveClose:
                insertTransaction();
                finish();
                break;
        }
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day);
            String strdate = simpleDateFormat.format(calendar.getTime());
            tvDate.setText(strdate);
        }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.set(0,0,0,hour,minute);
            String strTime = simpleDateFormat.format(calendar.getTime());
            tvTime.setText(strTime);
        }
    };

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rd_khoan_chi:
                type = "khoan chi";
                break;
            case R.id.rd_khoan_thu:
                type = "khoan thu";
                break;
        }
    }
    private void insertTransaction(){
        ContentValues values = new ContentValues();
        String content = edtContent.getText().toString();
        String money = edtMoney.getText().toString();
        String date = tvDate.getText().toString();
        String time = tvTime.getText().toString();
        try {
            if (type.equals("khoan chi")){
                if (Integer.parseInt(money) > surplus){
                    Toast.makeText(AddTransactionActivity.this,"so tien chi vuot qua so tien hien co",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    surplus = surplus - Integer.parseInt(money);
                    Toast.makeText(AddTransactionActivity.this,"Them giao dich thanh cong", Toast.LENGTH_SHORT).show();
                }
            }else {
                surplus = surplus + Integer.parseInt(money);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        values.put(SQLiteDataBase.COLUMN_TRANSACTION_CONTENT,content);
        values.put(SQLiteDataBase.COLUMN_TRANSACTION_MONEY, money);
        values.put(SQLiteDataBase.COLUMN_TRANSACTION_DATE, date);
        values.put(SQLiteDataBase.COLUMN_TRANSACTION_TIME, time);
        values.put(SQLiteDataBase.COLUMN_TRANSACTION_SURPLUS,surplus);
        values.put(SQLiteDataBase.COLUMN_TRANSACTION_ID_ACCOUNT,idAccount);
        values.put(SQLiteDataBase.COLUMN_TRANSACTION_TYPE,type);
        mSqLiteDataBase.insertRecordTransaction(values);
        ContentValues valuesUpdate = new ContentValues();
        valuesUpdate.put(SQLiteDataBase.COLUMN_MONEY_SUM, surplus);
        mSqLiteDataBase.updateRecord(valuesUpdate, new String[] {String.valueOf(idAccount)});
        edtContent.setText("");
        edtMoney.setText("");

        Intent intent = new Intent(MainActivity.ACTION_UPDATE_DATABASE);
        sendBroadcast(intent);
    }
}
