package com.example.user.truckonlinetaixe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class YeuCauBaoGiaKHAdapter extends BaseAdapter{
    private YeuCauBaoGiaKHActivity context;
    private int layout;
    private ArrayList<DonHangDangChoKH> donHanglist;
    LinearLayout lndonhang;
    public DonHangDangChoKH donhangchapnhan;

    @Override
    public int getCount() {
        return donHanglist.size();
    }

    @Override
    public Object getItem(int position) {
        return donHanglist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
//            holder.txtHoTen1 = (TextView) convertView.findViewById(R.id.textViewTenKH1);
//            holder.txtID1 = (TextView) convertView.findViewById(R.id.textViewId1);
            holder.txtDiemDi1 = (TextView) convertView.findViewById(R.id.textViewDiemDi1);
            holder.txtLoaiXe = (TextView) convertView.findViewById(R.id.txtLoaiXe);

            holder.txtDiemDen1 = (TextView) convertView.findViewById(R.id.textViewDiemDen1);
            holder.txtGiaCuoc1 = (TextView) convertView.findViewById(R.id.txtGiaCuoc1);
            holder.txtGiaCuoc = (TextView) convertView.findViewById(R.id.txtGiaCuoc);

            holder.txtThoiGianKhoiHanh1 = (TextView) convertView.findViewById(R.id.textViewThoiGianKhoiHanh1);
            holder.txtThoiGianKhoiHanh = (TextView) convertView.findViewById(R.id.textViewThoiGianKhoiHanh);

            holder.textViewTinhTrang1 = (TextView) convertView.findViewById(R.id.textViewTinhTrang1);

            holder.btnTuChoi1 = (Button) convertView.findViewById(R.id.btnTuChoi);
            holder.btnChapNhan = (Button) convertView.findViewById(R.id.btnChapNhan);
            holder.lndonhang=(LinearLayout)convertView.findViewById(R.id.lndonhang) ;

            convertView.setTag(holder);

        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        DonHangDangChoKH donHang=donHanglist.get(position);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = chuyenxe.getThoiGianKhoiHanh();
//        String dateFormat = formatter.format(date);

//        holder.txtID1.setText(donHang.getID()+"");
//        holder.txtHoTen1.setText(donHang.getTenKH());
        holder.txtLoaiXe.setText(donHang.getLoaiXe()+"-"+donHang.getTrongTaiXe());

        holder.txtDiemDi1.setText(donHang.getDiemDi());
        holder.txtGiaCuoc.setText(donHang.getGiaCuoc()+""+" VNĐ");

        holder.txtDiemDen1.setText(donHang.getDiemDen());
        holder.txtGiaCuoc1.setText(donHang.getGiaCuoc()+""+" VNĐ");
        holder.txtThoiGianKhoiHanh1.setText(donHang.getThoiGianKhoiHanh());
        holder.txtThoiGianKhoiHanh.setText(donHang.getThoiGianKhoiHanh());

//        holder.textViewTinhTrang1.setText((donHang.getTinhTrang()));

        holder.btnTuChoi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                XacNhanHuy(donHang.getID(),donHang.getTokenKH());

            }
        });


//
//        holder.btnChapNhan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                XacNhanChapNhan(donHang.getID(),donHang.getTokenKH());
//                donhangchapnhan = donHang;
//            }
//        });

        return convertView;
    }


    private void XacNhanHuy(int idchuyenxe, String tokenKH){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn chắc chắn hủy đơn hàng này ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.HuyDonHang(idchuyenxe);

            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

    private void XacNhanChapNhan(int idchuyenxe, String tokenKH){
        AlertDialog.Builder dialogChapNhan = new AlertDialog.Builder(context);
        dialogChapNhan.setMessage("Bạn chắc chắn chấp nhận đơn hàng này ?");
        dialogChapNhan.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.guiThongBaoChoKhachHang2(tokenKH);
                Intent intent =new Intent(context, ChapNhanTaiXeActivity.class);
                context.startActivity(intent);
                Toast.makeText(context,
                        "Bạn đã chấp nhận đơn hàng thành công !!!", Toast.LENGTH_SHORT).show();
            }
        });
        dialogChapNhan.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogChapNhan.show();
    }

    public YeuCauBaoGiaKHAdapter(YeuCauBaoGiaKHActivity context, int layout, ArrayList<DonHangDangChoKH> donHanglist) {
        this.context = context;
        this.layout = layout;
        this.donHanglist = donHanglist;
    }

    private class ViewHolder {
        TextView txtHoTen1, txtDiemDi1, txtDiemDen1, txtGiaCuoc1,textViewTinhTrang1,txtThoiGianKhoiHanh;
        TextView txtThoiGianKhoiHanh1,txtID1,txtLoaiXe,txtGiaCuoc;
        Button btnTuChoi1,btnChapNhan;
        LinearLayout lndonhang;


    }

}
