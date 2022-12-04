package com.example.bookinghotel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookinghotel.DataBooking;
import com.example.bookinghotel.R;

import java.util.ArrayList;
import java.util.List;

public class ListBookingAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DataBooking> dataBookings;

    public ListBookingAdapter(Context context, int layout, List<DataBooking> dataBookings) {
        this.context = context;
        this.layout = layout;
        this.dataBookings = dataBookings;
    }

    @Override
    public int getCount() {
        return dataBookings.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout, null);
        TextView txtHottel = view.findViewById(R.id.textViewHottel1);
        TextView txtName = view.findViewById(R.id.textViewName1);
        TextView txtPrice = view.findViewById(R.id.textViewPrice1);
        ImageView imgHottel = view.findViewById(R.id.imageViewHottel1);
        DataBooking dataBooking = dataBookings.get(i);
        txtHottel.setText(dataBooking.getHottel());
        txtName.setText(dataBooking.getName());
        txtPrice.setText(dataBooking.getPrice());
        imgHottel.setImageResource(dataBooking.getImgHottel());
        return view;
    }
}
