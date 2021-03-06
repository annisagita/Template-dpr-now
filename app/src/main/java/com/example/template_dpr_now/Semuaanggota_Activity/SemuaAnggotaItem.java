package com.example.template_dpr_now.Semuaanggota_Activity;

public class SemuaAnggotaItem {
    private String mNama;
    private String mFraksi;
    private String mDapil;
    private String mImageUrl;
    private String mDaftarAkd;

    public SemuaAnggotaItem(String namaanggota, String fraksi, String dapil, String imageurl, String daftarAkd){
        mNama = namaanggota;
        mFraksi = fraksi;
        mDapil = dapil;
        mImageUrl = imageurl;
        mDaftarAkd = daftarAkd;
    }



    public String getNamaAnggota() {
        return mNama;
    }

    public String getFraksi() {
        return mFraksi;
    }

    public String getDapil() {
        return mDapil;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getDaftarAkd(){
        return mDaftarAkd;
    }

}
