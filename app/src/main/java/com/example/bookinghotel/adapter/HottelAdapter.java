package com.example.bookinghotel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookinghotel.DataHottel;
import com.example.bookinghotel.MyOnItemClickListener;
import com.example.bookinghotel.R;

import java.util.ArrayList;

public class HottelAdapter extends RecyclerView.Adapter<HottelAdapter.ViewHolder>{
    ArrayList<DataHottel> dataHottels;
    Context context;
    MyOnItemClickListener myOnItemClickListener;

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public HottelAdapter(ArrayList<DataHottel> dataHottels, Context context) {
        this.dataHottels = dataHottels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_hottel,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgHottel.setImageResource(dataHottels.get(position).getImgHottel());
        holder.nameHottel.setText(dataHottels.get(position).getNameHottel());
        holder.priceHottel.setText(dataHottels.get(position).getPriceHottel());
        holder.qualityHottel.setText(dataHottels.get(position).getQualityHottel());
        holder.rateHottel.setText(dataHottels.get(position).getRateHottel());
        int i = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOnItemClickListener.onClick(dataHottels.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataHottels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameHottel, priceHottel, qualityHottel, rateHottel;
        ImageView imgHottel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHottel = (ImageView) itemView.findViewById(R.id.imgHottel);
            nameHottel = (TextView) itemView.findViewById(R.id.nameHottel);
            priceHottel = (TextView) itemView.findViewById(R.id.priceHottel);
            qualityHottel = (TextView) itemView.findViewById(R.id.qualityHottel);
            rateHottel = (TextView) itemView.findViewById(R.id.rateHottel1);
        }
    }
}
