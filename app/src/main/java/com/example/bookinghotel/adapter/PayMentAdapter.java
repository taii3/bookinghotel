package com.example.bookinghotel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookinghotel.PayMent;
import com.example.bookinghotel.R;

import java.util.List;

public class PayMentAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<PayMent> payMentList;

    public PayMentAdapter(Context context, int layout, List<PayMent> payMentList) {
        this.context = context;
        this.layout = layout;
        this.payMentList = payMentList;
    }

    @Override
    public int getCount() {
        return payMentList.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        ImageView img = (ImageView) view.findViewById(R.id.imageViewPayment);
        PayMent payMent = payMentList.get(i);
        img.setImageResource(payMent.getImg());
        return view;
    }
}
