package com.bh183.dwismayana;

import java.util.Date;

public class Buku {

    private int idBuku;
    private String judulBuku;
    private String thTerbit;
    private String gambar;
    private String penulis;
    private String penerbit;
    private String sinopsis;
    private String harga ;
    private String jumlahHalaman ;

    public Buku(int idBuku , String judulBuku , String thTerbit , String gambar , String penulis , String penerbit , String sinopsis , String harga , String jumlahHalaman) {
        this.idBuku = idBuku;
        this.judulBuku = judulBuku ;
        this.thTerbit = thTerbit ;
        this.gambar = gambar ;
        this.penulis = penulis ;
        this.penerbit = penerbit ;
        this.sinopsis = sinopsis ;
        this.harga = harga ;
        this.jumlahHalaman = jumlahHalaman ;
    }

    public void setIdBuku(int idBuku){this.idBuku = idBuku ;}
    public void setjudulBuku(String judulBuku) {this.judulBuku = judulBuku;}
    public void setThTerbit(String thTerbit) {this.thTerbit = thTerbit ;}
    public void setGambar(String gambar) {this.gambar = gambar ;}
    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }
    public void setPenerbit(String penerbit) {this.penerbit = penerbit ;}
    public void setSinopsis(String sinopsis) {this.sinopsis = sinopsis ;}
    public void setHarga(String harga) {this.harga = harga ;}
    public void setJumlahHalaman(String jumlahHalaman) {this.jumlahHalaman = jumlahHalaman;}


    public int getIdBuku() {return idBuku ;}
    public String getJudulBuku() {return judulBuku ;}
    public String getThTerbit() {return  thTerbit ;} ;
    public String getGambar() {
        return gambar;
    }
    public String getPenulis() {
        return penulis;
    }
    public String getPenerbit() {return  penerbit;}
    public String getSinopsis() {return sinopsis ;}
    public String getHarga() {return harga ;}
    public String getJumlahHalaman() {return jumlahHalaman ;}

}
