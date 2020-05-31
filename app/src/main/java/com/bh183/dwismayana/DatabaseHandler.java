package com.bh183.dwismayana;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_mybook";
    private final static String TABLE_BUKU = "t_buku" ;
    private final static String KEY_ID_BUKU = "id_buku" ;
    private final static String KEY_JUDUL = "Judul" ;
    private final static String KEY_TAHUN = "Tahun";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_PENULIS = "Penulis";
    private final static String KEY_PENERBIT = "Penerbit" ;
    private final static String KEY_SINOPSIS = "Sinopsis" ;
    private final static String KEY_HARGA = "Harga";
    private final static String KEY_JUMLAH_HALAMAN ="Jumlah_halaman";
    private Context context;

    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BUKU = "CREATE TABLE " + TABLE_BUKU
                + "(" + KEY_ID_BUKU + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TAHUN + " TEXT, "
                + KEY_GAMBAR + " TEXT, " + KEY_PENULIS + " TEXT, "
                + KEY_PENERBIT + " TEXT, " + KEY_SINOPSIS +" TEXT, "
                + KEY_HARGA + " TEXT," + KEY_JUMLAH_HALAMAN + " TEXT" + ")";

        db.execSQL(CREATE_TABLE_BUKU);
        inisialisasiBukuAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BUKU;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahBuku(Buku dataBuku) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudulBuku());
        cv.put(KEY_TAHUN, dataBuku.getThTerbit());
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());
        cv.put(KEY_HARGA, dataBuku.getHarga());
        cv.put(KEY_JUMLAH_HALAMAN, dataBuku.getJumlahHalaman());
        db.insert(TABLE_BUKU, null, cv);
        db.close();
    }

    public void tambahBuku(Buku dataBuku, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudulBuku());
        cv.put(KEY_TAHUN, dataBuku.getThTerbit());
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());
        cv.put(KEY_HARGA, dataBuku.getHarga());
        cv.put(KEY_JUMLAH_HALAMAN , dataBuku.getJumlahHalaman());
        db.insert(TABLE_BUKU, null, cv);
    }

    public void editBuku(Buku dataBuku) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudulBuku());
        cv.put(KEY_TAHUN, dataBuku.getThTerbit());
        cv.put(KEY_GAMBAR, dataBuku.getGambar());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_SINOPSIS, dataBuku.getSinopsis());
        cv.put(KEY_HARGA, dataBuku.getHarga());
        cv.put(KEY_JUMLAH_HALAMAN , dataBuku.getJumlahHalaman());

        db.update(TABLE_BUKU, cv,KEY_ID_BUKU + "=?", new String[]{String.valueOf(dataBuku.getIdBuku())});
        db.close();
    }

    public void hapusBuku(int idBuku) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BUKU, KEY_ID_BUKU + "=?", new String[]{String.valueOf(idBuku)});
        db.close();
    }

    public ArrayList<Buku> getAllBuku() {
        ArrayList<Buku> dataBuku = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BUKU;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do {

                Buku tempBuku = new Buku(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7),
                        csr.getString(8)

                );

                dataBuku.add(tempBuku);
            } while (csr.moveToNext());
        }

        return  dataBuku;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiBukuAwal(SQLiteDatabase db) {
        int idBuku = 0;

        Buku buku1 = new Buku(
                idBuku,
                "Bukan Cinderella",
                "2018",
                storeImageFile(R.drawable.buku1),
                "Dheti Azmi",
                "Gramedia Widiasarana Indonesia",
                "Ini kisah Amora yang kehilangan sepatu converse yang baru saja Ia beli tempo hari. Dan sebelah sepatunya harus tertukar dengan sepatu orang lain.\n" +
                        "\n" +
                        "Namanya Amora Olivia, cewek biasa yang tengah bahagia karena baru saja bisa membeli sepatu baru dengan uang yang susah payah Ia tabung sebulan ini. Dan kini sebalah sepatu kebanggaanya itu harus tertukar dengan sebelah sepatu butut yang ukurannya lebih besar dari sepatu miliknya.\n" +
                        "\n" +
                        "Sumpah sarapah Amora keluarkan saat mencari sebelah sepatunya, bahkan Amora nekat berteriak di koridor sekolah hanya untuk sebelah sepatu. \n" +
                        "\n" +
                        "Ini kisah Amora dkk yang masuk ke dalam kelas buangan, dan harus berurusan dengan antek-antek osis yang berasal dari kelas unggulan yang di gawangi oleh Adan Wijaya.",
                "Rp. 173.000,00" ,
                "440"
        );

        tambahBuku(buku1, db);
        idBuku++;


        Buku buku2 = new Buku(
                idBuku,
                "Dia Adalah Dilanku Tahun 1990",
                "2014",
                storeImageFile(R.drawable.buku2),
                "Pidi Baiq",
                "Pastel Books",
                " Cinta, walaupun sudah berlalu sekian lama, tetap saja, saat dikenang begitu manis.\n" +
                        "\n" +
                        "Milea, dia kembali ke tahun 1990 untuk menceritakan seorang laki-laki yang pernah menjadi seseorang yang sangat dicintainya, Dilan.\n" +
                        "\n" +
                        "Laki-laki yang mendekatinya (milea) bukan dengan seikat bunga atau kata-kata manis untuk menarik perhatiannya. Namun, melalui ramalan seperti tergambarkan pada penggalan cerita berikut :\n" +
                        "\n" +
                        "“Aku ramal, nanti kita bertemu di kantin.” – Dilan -hlm. 20\n" +
                        "\n" +
                        "Tapi, sayang sekali ramalannya salah. Hari itu, Miela tidak ke kantin karena ia harus membicarakan urusan kelas dengan kawan-kawannya. Sebuah cara sederhana namun bikin senyum dipilih Dilan untuk kembali menarik perhatian dari Milea. Dian mengirim Piyan untuk menyampaikan suratnya yang isinya :\n" +
                        "\n" +
                        "“Milea, ramalanku, kita akan bertemu di kantin. Ternyata salah. Maaf, tapi ingin meramal lagi : besok kita akan bertemu.”  – Dilan – halaman. 22\n" +
                        "\n" +
                        "Tunggu, besok yang dimaksud oleh dilan itu adalah hari minggu. Ngga mungkin, kan mereka bertemu? Namun, ternyata ramalannya kali ini benar. Dilan datang ke rumah Miela untuk menyampaikan surat undangannya yang isinya :\n" +
                        "\n" +
                        "“Bismillahirrahmanirrahim. Dengan nama Allah Yang Maha Pengasih lagiPenyayang. Dengan ini, dengan penuh perasaan, mengundang Milea Adnan untuk sekolah pada : Hari Senin, Selasa, Rabu, Kamis, Jumat, dan Sabtu.” – Dilan – hlm. 27\n" +
                        "\n" +
                        "Hal-hal yang sederhana ini nyatanya dapat membuat Milea tersenyum, dan perlahan mulai menaruh perhatiannya kepada Dilan. Sampai-sampai, sebentar dia lupa, ada Beni yaitu pacarnya yang berada di Jakarta.\n" +
                        "\n" +
                        "Milea tak mau kehilangan Dilan. ",
                "Rp. 59.000,00" ,
                "348"
        );

        tambahBuku(buku2, db);
        idBuku++;

    }
}
