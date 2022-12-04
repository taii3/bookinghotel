package com.example.bookinghotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bookinghotel.adapter.HottelAdapter;
import com.example.bookinghotel.adapter.TopHottelAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    SQLiteDatabase database=null;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<DataHottel> arrayList;
    TopHottelAdapter topHottelAdapter;
    SearchView searchView;
    DataUser dataUser;
    public void khoiTao(){
        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView3);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        khoiTao();
        read();
        //readFile();
        receiveData();
        setRecyclerView1();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.tittle_search);
    }

    public void read(){
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
            Log.d("loi", e.getMessage() + "");
        }
    }
    public void senData1(DataHottel dataHottel , DataUser dataUser){
        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", dataHottel);
        bundle.putSerializable("data1", dataUser);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void sendData2(DataUser dataUser){
        Intent intent = new Intent(SearchActivity.this, HotelReservationListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", dataUser);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void receiveData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        dataUser = (DataUser) bundle.get("data");
    }
    public void setRecyclerView1(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        topHottelAdapter = new TopHottelAdapter(arrayList,getApplicationContext());
        recyclerView.setAdapter(topHottelAdapter);
        topHottelAdapter.setMyOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onClick(DataHottel dataHottel) {
                senData1(dataHottel, dataUser);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Enter hotel name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                topHottelAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                topHottelAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuUser:
                sendData2(dataUser);
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}