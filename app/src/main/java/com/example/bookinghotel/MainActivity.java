package com.example.bookinghotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.bookinghotel.adapter.HottelAdapter;
import com.example.bookinghotel.adapter.PayMentAdapter;
import com.example.bookinghotel.adapter.TopHottelAdapter;
import com.example.bookinghotel.adapter.ViewPager2Adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database=null;
    private RecyclerView recyclerView1, recyclerView2;
    private LinearLayoutManager layoutManager1, layoutManager2;
    private ArrayList<DataHottel> arrayList;
    private ArrayList<DataHottel> topArrayList;
    private ArrayList<PayMent> payMentArrayList;
    private PayMentAdapter payMentAdapter;
    private HottelAdapter hottelAdapter1, hottelAdapter2;
    private ImageButton btn1, btn2;
    private DataUser dataUser;
    private GridView gridView;
    private ImageView imgHeader;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Slider> sliderList;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == sliderList.size() - 1){
                viewPager2.setCurrentItem(0);
            }
            else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };
    public void khoiTao(){
        arrayList = new ArrayList<>();
        topArrayList = new ArrayList<>();
        btn1 = (ImageButton) findViewById(R.id.buttonAll1);
        btn2 = (ImageButton) findViewById(R.id.buttonAll2);
        imgHeader = (ImageView) findViewById(R.id.imageViewHeader);
        gridView = (GridView) findViewById(R.id.gridView);
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2);
        circleIndicator3 = (CircleIndicator3) findViewById(R.id.circleIndicator3);
        recyclerView1 = (RecyclerView) findViewById(R.id.RecyclerView1);
        recyclerView2 = (RecyclerView) findViewById(R.id.RecyclerView2);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        khoiTao();
        showSlider();
        readFile();
        receiveData();
        onClick();
        setRecyclerView1();
        setRecyclerView2();
        setGridView();
    }
    public void onClick(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData3(dataUser);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData3(dataUser);
            }
        });
        imgHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData3(dataUser);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.traveloka.com/vi-vn/how-to/pay"));
                startActivity(myIntent);
            }
        });
    }
    private List<Slider> getListSlider(){
        List<Slider> list = new ArrayList<>();
        list.add(new Slider(R.drawable.slider_1));
        list.add(new Slider(R.drawable.slider_2));
        list.add(new Slider(R.drawable.slider_3));
        list.add(new Slider(R.drawable.slider_4));
        list.add(new Slider(R.drawable.slider_5));
        return list;
    }
    public void showSlider(){
        sliderList = getListSlider();
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(sliderList);
        viewPager2.setAdapter(viewPager2Adapter);
        circleIndicator3.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    public void readFile(){
        try{
            database = openOrCreateDatabase("hotel.db", MODE_PRIVATE, null);
            Cursor c = database.query("infoHotel",null,null,null,null,null,null);
            c.moveToFirst();
            while (c.isAfterLast() == false)
            {
                int img = getResources().getIdentifier(c.getString(0), "drawable", getPackageName());
                String name = c.getString(1);
                String price = c.getString(2);
                String quality = c.getString(3);
                String rate = c.getString(4);
                int phone = Integer.parseInt(c.getString(5));
                String address = c.getString(6);
                String description = c.getString(7);
                arrayList.add(new DataHottel(img,
                        name,
                        price,
                        quality,
                        rate,
                        phone,
                        address,
                        description));
                c.moveToNext();
            }
            c.close();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
//    public void readFile(){
//        try {
//            InputStream in = getAssets().open("file/data.txt");
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//            String line = "";
//            while (true) {
//                line = br.readLine();
//                if (line == null) {
//                    break;
//                }
//                String []array = line.split(";");
//                int img = getResources().getIdentifier(array[0].trim(), "drawable", getPackageName());
//                String name = array[1].trim();
//                String price = array[2].trim();
//                String quality = array[3].trim();
//                String rate = array[4].trim();
//                int phone = Integer.parseInt(array[5].trim());
//                String address = array[6].trim();
//                String description = array[7].trim();
//                if (array.length == 8){
//                    arrayList.add(new DataHottel(img,
//                            name,
//                            price,
//                            quality,
//                            rate,
//                            phone,
//                            address,
//                            description));
//                }
//            }
//            br.close();
//            in.close();
//        }
//        catch (Exception e){
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
    public void receiveData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        dataUser = (DataUser) bundle.get("data");
    }
    public void sendData1(DataHottel dataHottel, DataUser dataUser){
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", dataHottel);
        bundle.putSerializable("data1", dataUser);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void sendData2(DataUser dataUser){
        Intent intent = new Intent(MainActivity.this, HotelReservationListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", dataUser);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void sendData3(DataUser dataUser){
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", dataUser);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void setGridView(){
        payMentArrayList = new ArrayList<>();
        payMentArrayList.add(new PayMent("", R.drawable.payment_1));
        payMentArrayList.add(new PayMent("", R.drawable.payment_2));
        payMentArrayList.add(new PayMent("", R.drawable.payment_3));
        payMentArrayList.add(new PayMent("", R.drawable.payment_4));
        payMentArrayList.add(new PayMent("", R.drawable.payment_5));
        payMentArrayList.add(new PayMent("", R.drawable.payment_6));
        payMentAdapter = new PayMentAdapter(this, R.layout.gridview_row, payMentArrayList);
        gridView.setAdapter(payMentAdapter);
    }
    public void setRecyclerView1(){
        recyclerView1.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager1);
        Collections.shuffle(arrayList);
        hottelAdapter1 = new HottelAdapter(arrayList,getApplicationContext());
        recyclerView1.setAdapter(hottelAdapter1);
        hottelAdapter1.setMyOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onClick(DataHottel dataHottel) {
                sendData1(dataHottel, dataUser);
            }
        });
    }
    public void setRecyclerView2(){
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);
        for(DataHottel data: arrayList)
        {
            if (Float.parseFloat(data.getQualityHottel()) > 8.4){
                //set theo quality
                //topArrayList.add(data);
            }
            if(data.getRateHottel().contains("★★★★★") || data.getRateHottel().contains("★★★★"))
            {
                //set theo sao
                topArrayList.add(data);
            }
        }
        hottelAdapter2 = new HottelAdapter(topArrayList,getApplicationContext());
        recyclerView2.setAdapter(hottelAdapter2);
        hottelAdapter2.setMyOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onClick(DataHottel dataHottel) {
                sendData1(dataHottel, dataUser);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSearch:
                sendData3(dataUser);
                break;
            case R.id.menuUser:
                sendData2(dataUser);
                break;
            case R.id.menuLogout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}