package com.example.bookinghotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {
    EditText edtName, edtPhone,edtDate;
    TextView txtNameHottel, txtAddress, txtPrice;
    RadioGroup radioGroup;
    DataHottel dataHottel;
    DataUser dataUser;
    Button btnBooking;
    Calendar calendar;
    Database database;
    int priceKhoiTao, price, img;
    int time = 1;
    int ngay, thang, nam;
    public void khoiTao(){
        edtName = (EditText) findViewById(R.id.editTextName);
        edtPhone = (EditText) findViewById(R.id.editTextPhone);
        edtDate = (EditText) findViewById(R.id.editTextDate);
        txtPrice = (TextView) findViewById(R.id.textViewPrice);
        txtNameHottel = (TextView) findViewById(R.id.textViewNameHottel);
        txtAddress = (TextView) findViewById(R.id.textViewAddress);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnBooking = (Button) findViewById(R.id.buttonBooking);
        calendar = Calendar.getInstance();
        ngay = calendar.get(Calendar.DATE);
        thang = calendar.get(Calendar.MONTH);
        nam = calendar.get(Calendar.YEAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.tittle_booking);
        khoiTao();
        receiveData();
        btnOnClick();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void sendData(DataUser dataUser){
        Intent intent = new Intent(BookingActivity.this, HotelReservationListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", dataUser);
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }
    public void receiveData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        dataHottel = (DataHottel) bundle.get("data");
        dataUser = (DataUser) bundle.get("data1");
        txtNameHottel.setText(dataHottel.getNameHottel());
        txtAddress.setText(dataHottel.getAddressHotel());
        priceKhoiTao = Integer.parseInt(dataHottel.getPriceHottel().replaceAll("\\W", ""));
        txtPrice.setText(getFormatedAmount(priceKhoiTao));
        img = dataHottel.getImgHottel();
        edtDate.setText(ngay + "/" + (thang + 1) + "/" + nam);
    }
    private String getFormatedAmount(int amount){
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

    public void btnOnClick(){
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edtDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },nam, thang, ngay);
                datePickerDialog.show();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButton1Day:
                        time = 1;
                        price = priceKhoiTao;
                        break;
                    case R.id.radioButton2Day:
                        time = 2;
                        price =  priceKhoiTao * 2;
                        break;
                    case R.id.radioButton7Day:
                        time = 7;
                        price = priceKhoiTao * 7;
                        break;
                }
                txtPrice.setText(getFormatedAmount(price));
            }
        });

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
    Dialog dialog;
    Button btnCheck;
    public void showDialog(){
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String nameHotel = txtNameHottel.getText().toString().trim();
        String address = txtAddress.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String price = txtPrice.getText().toString().trim();
        if(TextUtils.isEmpty(name)) {
            edtName.setError("Please Enter Your Name");
            return;
        }
        else if(TextUtils.isEmpty(phone)) {
            edtPhone.setError("Please Enter Your Phone Number");
            return;
        }
        else{
            try {
                database = new Database(this, "hotel.db", null, 1);
//                database.QueryData("CREATE TABLE IF NOT EXISTS bookRoom(id INTEGER PRIMARY KEY AUTOINCREMENT, user VARCHAR(255), name VARCHAR(255), phone VARCHAR(255), nameHotel VARCHAR(255), address VARCHAR(255), time INTEGER(255), day VARCHAR(255), price VARCHAR(255), img INTEGER(255))");
                database.QueryData("INSERT INTO bookRoom VALUES(null, '"+ dataUser.getUser() +"', '"+ name +"', '"+ phone +"', '"+ nameHotel +"', '"+ address +"', '"+ time +"', '"+ date +"', '"+ price +"', '"+ img +"')");
                Log.d("loi", "ok");
            }
            catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("loi", e.getMessage());
            }
            dialog = new Dialog(BookingActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_booking);
            btnCheck = (Button) dialog.findViewById(R.id.buttondlLogin);
            Window window = dialog.getWindow();
            if (window == null){
                return;
            }
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendData(dataUser);
                    dialog.cancel();
                }
            });
        }
    }
}