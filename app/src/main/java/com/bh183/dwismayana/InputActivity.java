package com.bh183.dwismayana;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editJudul, editTahun, editPenerbit, editPenulis, editSinopsis, editHarga , editJumlahHalaman;
    private ImageView ivBuku;
    private DatabaseHandler dbHandler;
    private boolean updateData = false;
    private int idBuku = 0;
    private Button btnSimpan, btnPilihTanggal;
    private String tahunTerbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editJudul = findViewById(R.id.edit_Judul);
        editTahun = findViewById(R.id.edit_tahun);
        editPenerbit = findViewById(R.id.edit_penerbit);
        editPenulis = findViewById(R.id.edit_penulis);
        editSinopsis = findViewById(R.id.edit_sinopsis);
        editHarga = findViewById(R.id.edit_harga);
        editJumlahHalaman = findViewById(R.id.edit_jumlahHalaman);
        ivBuku = findViewById(R.id.iv_buku);
        btnSimpan= findViewById(R.id.btn_simpan);

        dbHandler = new DatabaseHandler(this);

        Intent terimaIntent = getIntent();
        Bundle data = terimaIntent.getExtras();
        if (data.getString("OPERASI").equals("insert")) {
            updateData = false;
        } else {
            updateData =true;
            idBuku = data.getInt("ID");
            editJudul.setText(data.getString("JUDUL"));
            editTahun.setText(data.getString("TAHUN"));
            editPenulis.setText(data.getString("PENULIS"));
            editPenerbit.setText(data.getString("PENERBIT"));
            editSinopsis.setText(data.getString("SINOPSIS"));
            editHarga.setText(data.getString("HARGA"));
            editJumlahHalaman.setText(data.getString("JUMLAHHALAMAN"));
            loadImageFromInternalStorage(data.getString("GAMBAR"));
        }

        ivBuku.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(3,4)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
                try {
                    Uri imageUri = result.getUri();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    String location = saveImageToInternalStorage(selectedImage, getApplicationContext());
                    loadImageFromInternalStorage(location);
                } catch (FileNotFoundException er) {
                    er.printStackTrace();
                    Toast.makeText(this, "Ada kesalahan dalam pengambilan gambar", Toast.LENGTH_SHORT).show();
                }
        } else {
            Toast.makeText(this, "Anda belum memilih gambar", Toast.LENGTH_SHORT).show();
        }
    }

    public static String saveImageToInternalStorage(Bitmap bitmap, Context ctx ) {
        ContextWrapper ctxWrapper = new ContextWrapper(ctx);
        File file = ctxWrapper.getDir("images", MODE_PRIVATE);
        String uniqueID = UUID.randomUUID().toString();
        file = new File(file, "berita-"+ uniqueID + ".jpg");
        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
        } catch (IOException er) {
            er.printStackTrace();
        }

        Uri savedImage = Uri.parse(file.getAbsolutePath());
        return savedImage.toString();
    }

    private void loadImageFromInternalStorage(String imageLocation) {
        try {
            File file = new File(imageLocation);
            Bitmap bitmap =  BitmapFactory.decodeStream((new FileInputStream(file)));
            ivBuku.setImageBitmap(bitmap);
            ivBuku.setContentDescription(imageLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.item_menu_hapus);

        if (updateData== true) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.input_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item_menu_hapus) {
            hapusData();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void simpanData() {
        String judul, gambar, tahun, penulis, penerbit,sinopsis,harga,jumlahHalaman;
        judul = editJudul.getText().toString();
        tahun = editTahun.getText().toString();
        gambar = ivBuku.getContentDescription().toString();
        penulis = editPenulis.getText().toString();
        penerbit = editPenerbit.getText().toString() ;
        sinopsis = editSinopsis.getText().toString();
        harga = "Rp.    " + editHarga.getText().toString() + ",00    ,-";
        jumlahHalaman = editJumlahHalaman.getText().toString();


        Buku tempBuku = new Buku(
                idBuku, judul, tahun, gambar, penulis, penerbit ,sinopsis,harga,jumlahHalaman
        );

        if (updateData == true) {
            dbHandler.editBuku(tempBuku);
            Toast.makeText(this, "Data Buku diperbaharui", Toast.LENGTH_SHORT).show();
        } else {
            dbHandler.tambahBuku(tempBuku);
            Toast.makeText(this, " Data Buku Ditambahkan", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void hapusData() {
        dbHandler.hapusBuku(idBuku);
        Toast.makeText(this, "Data Buku dihapus", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onClick(View v) {
        int idView = v.getId();

        if (idView == R.id.btn_simpan) {
            simpanData();
        } else if (idView == R.id.iv_buku) {
            pickImage();
        }

    }
}
