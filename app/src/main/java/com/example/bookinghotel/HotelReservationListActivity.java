package com.example.bookinghotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.bookinghotel.adapter.ListBookingAdapter;
import java.util.ArrayList;


public class HotelReservationListActivity extends AppCompatActivity {
    ArrayList<DataBooking> dataBookings;
    ListView listView;
    DataBooking dataBooking;
    ListBookingAdapter listBookingAdapter;
    DataUser dataUser;
    TextView txtMessage;
    //SQLiteDatabase database=null;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_reservation_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.booking_history);
        khoiTao();
        receiveData();
        readFile();
        onClick();
    }

    public void khoiTao(){
        listView = (ListView) findViewById(R.id.listView);
        dataBookings = new ArrayList<>();
        txtMessage = (TextView) findViewById(R.id.textViewMessage);
        //database = openOrCreateDatabase("hotel.db", MODE_PRIVATE, null);
        database = new Database(this, "hotel.db", null, 1);
    }

    public void receiveData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        dataUser = (DataUser) bundle.get("data");
    }

    public void readFile(){
        Cursor c = database.GetData("SELECT * FROM bookRoom");
        Cursor d = database.GetData("SELECT * FROM infoHotel");
        String image = "";
        try {
            c.moveToFirst();
            while (c.isAfterLast() == false)
            {
                if(dataUser.getUser().equals((c.getString(1)))) {
                    d.moveToFirst();
                    while (d.isAfterLast() == false)
                    {
                        if (c.getString(4).trim().equals(d.getString(1).trim())){
                            image = d.getString(0);
                        }
                        d.moveToNext();
                    }

                    int img = getResources().getIdentifier(image, "drawable", getPackageName());
                    dataBookings.add(new DataBooking(
                            c.getString(2) + "",
                            c.getString(3) + "",
                            c.getString(4) + "",
                            c.getString(5) + "",
                            Integer.parseInt(c.getString(6)),
                            c.getString(7) + "",
                            c.getString(8) + "",
                            img));
                }
                c.moveToNext();
            }
            d.close();
            c.close();
            if (dataBookings.size() < 1){
                txtMessage.setVisibility(View.VISIBLE);
            }
            else {
                txtMessage.setVisibility(View.GONE);
            }
            listBookingAdapter = new ListBookingAdapter(this, R.layout.item_oder, dataBookings);
            listView.setAdapter(listBookingAdapter);
        }
        catch (Exception e){
            txtMessage.setVisibility(View.VISIBLE);
            Log.d("loi", e.getMessage());
        }
    }

    public void onClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dataBooking = dataBookings.get(i);
                showDialog();
                btnDestroy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            database.QueryData("DELETE FROM bookRoom WHERE name = '"+ dataBooking.getName() +"' AND nameHotel = '"+ dataBooking.getHottel() +"'");
                            //database.delete("bookRoom", "name= ?", new String[]{dataBooking.getName()});
                            dataBookings.remove(i);
                            listBookingAdapter.notifyDataSetChanged();
                            if (dataBookings.size() < 1){
                                txtMessage.setVisibility(View.VISIBLE);
                            }
                            dialog.cancel();
                        }
                        catch (Exception e){
                           Log.d("loi", e.getMessage());
                        }

                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
    }
    Button btnDestroy;
    Button btnCancel;
    Dialog dialog;
    public void showDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_oder);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txtName = (TextView) dialog.findViewById(R.id.textViewName5);
        TextView txtPhone = (TextView) dialog.findViewById(R.id.textViewPhone5);
        TextView txtHottel = (TextView) dialog.findViewById(R.id.textViewHottel5);
        TextView txtTime = (TextView) dialog.findViewById(R.id.textViewTime5);
        TextView txtPrice = (TextView) dialog.findViewById(R.id.textViewPrice5);
        btnDestroy = (Button) dialog.findViewById(R.id.buttonDestroy);
        btnCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        txtName.setText(dataBooking.getName());
        txtPhone.setText(dataBooking.getPhone());
        txtHottel.setText(dataBooking.getHottel());
        txtTime.setText(dataBooking.getTime() + " đêm   " + dataBooking.getDay());
        txtPrice.setText(dataBooking.getPrice() + " VNĐ");
        dialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}