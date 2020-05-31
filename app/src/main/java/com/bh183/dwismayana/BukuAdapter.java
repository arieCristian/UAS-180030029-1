package com.bh183.dwismayana;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.BukuViewHolder> {

    private Context context;
    private ArrayList<Buku> dataBuku;

    public BukuAdapter(Context context, ArrayList<Buku> dataBuku) {
        this.context = context;
        this.dataBuku = dataBuku;
    }

    @NonNull
    @Override
    public BukuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_buku, parent, false);
        return new BukuViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull BukuViewHolder holder, int position) {
        Buku tempBuku =dataBuku.get(position);
        holder.idBuku = tempBuku.getIdBuku();
        holder.tvjudul.setText(tempBuku.getJudulBuku());
        holder.tahun = tempBuku.getThTerbit();
        holder.gambar = tempBuku.getGambar();
        holder.tvpenulis.setText(tempBuku.getPenulis());
        holder.penerbit = tempBuku.getPenerbit() ;
        holder.sinopsis = tempBuku.getSinopsis() ;
        holder.harga = tempBuku.getHarga() ;
        holder.jumlahHalaman = tempBuku.getJumlahHalaman();

        try {
            File file = new File(holder.gambar);
            Bitmap bitmap =  BitmapFactory.decodeStream((new FileInputStream(file)));
            holder.imgBuku.setImageBitmap(bitmap);
            holder.imgBuku.setContentDescription(holder.gambar);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(context, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return dataBuku.size();
    }

    public class BukuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView imgBuku;
        private TextView tvjudul, tvpenulis;
        private int idBuku;
        private String tahun, gambar,penerbit , sinopsis , harga , jumlahHalaman;

        public BukuViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBuku = itemView.findViewById(R.id.iv_buku);
            tvjudul = itemView.findViewById(R.id.tv_judul);
            tvpenulis = itemView.findViewById(R.id.tv_penulis);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent bukaBuku = new Intent(context, TampilActivity.class);
            bukaBuku.putExtra("ID", idBuku);
            bukaBuku.putExtra("JUDUL", tvjudul.getText().toString());
            bukaBuku.putExtra("TAHUN",tahun);
            bukaBuku.putExtra("GAMBAR", gambar);
            bukaBuku.putExtra("PENULIS", tvpenulis.getText().toString());
            bukaBuku.putExtra("PENERBIT", penerbit);
            bukaBuku.putExtra("SINOPSIS", sinopsis);
            bukaBuku.putExtra("HARGA", harga);
            bukaBuku.putExtra("JUMLAHHALAMAN" , jumlahHalaman) ;
            context.startActivity(bukaBuku);
        }

        @Override
        public boolean onLongClick(View v) {

            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("OPERASI", "update");
            bukaInput.putExtra("ID", idBuku);
            bukaInput.putExtra("JUDUL", tvjudul.getText().toString());
            bukaInput.putExtra("TAHUN",tahun);
            bukaInput.putExtra("GAMBAR", gambar);
            bukaInput.putExtra("PENULIS", tvpenulis.getText().toString());
            bukaInput.putExtra("PENERBIT", penerbit);
            bukaInput.putExtra("SINOPSIS", sinopsis);
            bukaInput.putExtra("HARGA", harga);
            bukaInput.putExtra("JUMLAHHALAMAN" , jumlahHalaman) ;
            context.startActivity(bukaInput);

            return true;
        }
    }
}
