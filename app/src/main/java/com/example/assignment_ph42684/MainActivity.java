package com.example.assignment_ph42684;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView lvMain;
    List<sanphamModel> sanphamModels;
    AdapterSanpham adapterSanpham;
    APIService apiService;
    EditText ten, gia, tonkho, soluong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMain = findViewById(R.id.lvDanhSach);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(APIService.class);

        loadData();

        findViewById(R.id.floatAddDanhSach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                them(MainActivity.this, 0, null);
            }
        });

    }

    void loadData() {
        Call<List<sanphamModel>> call = apiService.getSanphams();
        call.enqueue(new Callback<List<sanphamModel>>() {
            @Override
            public void onResponse(Call<List<sanphamModel>> call, Response<List<sanphamModel>> response) {
                if (response.isSuccessful()) {
                    sanphamModels = response.body();
                    Log.e(TAG, "onResponse: " + sanphamModels);

                    adapterSanpham = new AdapterSanpham(getApplicationContext(), sanphamModels, MainActivity.this);

                    lvMain.setAdapter(adapterSanpham);
                }
            }

            @Override
            public void onFailure(Call<List<sanphamModel>> call, Throwable t) {
                Log.e("Main", t.getMessage());
            }
        });
    }

    public void them(Context context, int type, sanphamModel sp) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.item_add_update);

        ten = dialog.findViewById(R.id.edtTenSP);
        gia = dialog.findViewById(R.id.edtGiaSP);
        soluong = dialog.findViewById(R.id.edtSoLuongSP);
        tonkho = dialog.findViewById(R.id.edttonkho);
        if (type != 0){
            ten.setText(sp.getTen());
            gia.setText(sp.getGia()+"");
            soluong.setText(sp.getSoluong());
            tonkho.setText(sp.getTonkho());
        }
        dialog.findViewById(R.id.btnUPDATEup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSP =ten.getText().toString();
                String giaSp = gia.getText().toString();
                String soluongSp = soluong.getText().toString();
                String tonkhoSp = tonkho.getText().toString();

                if (tenSP.length() == 0 || giaSp.length() == 0 || soluongSp.length() == 0 || tonkhoSp.length() == 0){
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Double gia = Double.parseDouble(giaSp);
                    if (gia > 0){
                        sanphamModel sanphamModel1 = new sanphamModel(tenSP, gia, soluongSp, tonkhoSp);

                        if (type == 0){
                            Call<Void> call = apiService.addsp(sanphamModel1);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        loadData();
                                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("Home", "Call failed: " + t.toString());
                                    Toast.makeText(MainActivity.this, "Đã xảy ra lỗi khi thêm dữ liệu", Toast.LENGTH_SHORT).show();
                                }

                            });
                        }else{
                            Call<Void> call = apiService.updatesp(sp.get_id(), sanphamModel1);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        loadData();
                                        Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("Home", "Call failed: " + t.toString());
                                    Toast.makeText(MainActivity.this, "Đã xảy ra lỗi khi sửa dữ liệu", Toast.LENGTH_SHORT).show();
                                }

                            });
                        }
                    }else{
                        Toast.makeText(context, "Giá phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    }

                }catch (NumberFormatException e){
                    Toast.makeText(context, "Gía phải là số", Toast.LENGTH_SHORT).show();
                }

            }
        });


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    public  void xoa(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc chắn muốn xóa?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Call<Void> call = apiService.deletesp(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            loadData();
                            Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("Home", "Call failed: " + t.toString());
                        Toast.makeText(MainActivity.this, "Đã xảy ra lỗi khi xóa dữ liệu", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();

    }
}