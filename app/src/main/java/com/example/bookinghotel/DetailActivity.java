package com.example.bookinghotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    DataHottel dataHottel;
    ImageButton btnCall, btnBook;
    Intent intentBooking;
    DataUser dataUser;
    ImageView imgDetail;
    TextView nameDetail, priceDetail, qualityDetail, descriptionDetail, rateDetail, addressDetail;
    public void khoiTao(){
        btnCall = (ImageButton) findViewById(R.id.callDetail);
        btnBook = (ImageButton) findViewById(R.id.bookRoomDetail);
        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        nameDetail = (TextView) findViewById(R.id.nameDetail);
        priceDetail = (TextView) findViewById(R.id.priceDetail);
        qualityDetail = (TextView) findViewById(R.id.qualityDetail);
        descriptionDetail = (TextView) findViewById(R.id.descriptionDetail);
        rateDetail = (TextView) findViewById(R.id.rateDetail);
        addressDetail = (TextView) findViewById(R.id.addressDetail);
    }
    public void xuLy(){
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callActivity(0);
            }
        });
    }
    public void callActivity(int number){
        Intent intent;
        switch (number){
            case 0:
                startActivity(intentBooking);
                break;
            case 1:
                intent = new Intent(DetailActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(DetailActivity.this, HotelReservationListActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        khoiTao();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.tittle_information);
        receiveData();
        xuLy();
        sendData(dataHottel, dataUser);
    }
    public void receiveData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        dataHottel = (DataHottel) bundle.get("data");
        dataUser = (DataUser) bundle.get("data1");
        imgDetail.setImageResource(dataHottel.getImgHottel());
        nameDetail.setText(dataHottel.getNameHottel());
        priceDetail.setText(dataHottel.getPriceHottel());
        qualityDetail.setText(dataHottel.getQualityHottel());
        descriptionDetail.setText(dataHottel.getDescriptionHottel());
        rateDetail.setText(dataHottel.getRateHottel());
        addressDetail.setText(dataHottel.getAddressHotel());
    }
    public void call(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        else {
            String phoneNumber = "tel:0" + dataHottel.getPhoneHottel();
            intent.setData(Uri.parse(phoneNumber));
            startActivity(intent);
        }
    }

    public void sendData(DataHottel dataHottel, DataUser dataUser) {
        intentBooking = new Intent(DetailActivity.this, BookingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", dataHottel);
        bundle.putSerializable("data1", dataUser);
        intentBooking.putExtras(bundle);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            call();
        }
        else {

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

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
}