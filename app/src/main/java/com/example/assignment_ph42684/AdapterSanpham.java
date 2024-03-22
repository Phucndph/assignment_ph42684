package com.example.assignment_ph42684;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterSanpham extends BaseAdapter {
    Context context;
    List<sanphamModel> sanphamModels;
    MainActivity home;
    public AdapterSanpham(Context context, List<sanphamModel> sanphamModels, MainActivity home) {
        this.context = context;
        this.sanphamModels = sanphamModels;
        this.home= home;
    }

    @Override
    public int getCount() {
        return sanphamModels.size();
    }

    @Override
    public Object getItem(int i) {
        return sanphamModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_sp, viewGroup, false);
        sanphamModel sp = sanphamModels.get(position);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvSoluong = (TextView) rowView.findViewById(R.id.tvSoluong);
        TextView tvTonkho = (TextView) rowView.findViewById(R.id.tvTonkho);
        TextView tvGia = (TextView) rowView.findViewById(R.id.tvGia);

        Button btnup= rowView.findViewById(R.id.btnChinhSua);
        Button btnde= rowView.findViewById(R.id.btnXoa);
        tvName.setText(String.valueOf(sanphamModels.get(position).getTen()));
        tvSoluong.setText(String.valueOf(sanphamModels.get(position).getSoluong()));
        tvTonkho.setText(String.valueOf(sanphamModels.get(position).getTonkho()));
        tvGia.setText(String.valueOf(sanphamModels.get(position).getGia()));

        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home.xoa(sp.get_id());
            }
        });
        btnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home.them(context,1, sp);
            }
        });
        return rowView;
    }
}
