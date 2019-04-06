package com.example.trackingsurat.model;

import java.util.ArrayList;

public class Barang {
    private String idResi;
    private String pengirim;
    private String penerima;
    private String alamatTujuan;
    private boolean isArrived;
    private long tanggal;
    private String posisi;
    private ArrayList<String> history_posisi;
    private ArrayList<Long> history_tanggal;

    public Barang (String idResi, long tanggal, String posisi){
        this.idResi = idResi;
        this.tanggal =tanggal;
        this.posisi = posisi;
        this.history_tanggal = new ArrayList<>();
        this.history_posisi = new ArrayList<>();
    }
    public Barang(){

    }

    public String getIdResi() {
        return idResi;
    }

    public void setIdResi(String idResi) {
        this.idResi = idResi;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public void setAlamatTujuan(String alamatTujuan) {
        this.alamatTujuan = alamatTujuan;
    }

    public boolean isArrived() {
        return isArrived;
    }

    public void setArrived(boolean arrived) {
        isArrived = arrived;
    }

    public long getTanggal() {
        return tanggal;
    }

    public void setTanggal(long tanggal) {
        this.tanggal = tanggal;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public ArrayList<String> getHistory_posisi() {
        return history_posisi;
    }

    public void setHistory_posisi(ArrayList<String> history_posisi) {
        this.history_posisi = history_posisi;
    }

    public ArrayList<Long> getHistory_tanggal() {
        return history_tanggal;
    }

    public void setHistory_tanggal(ArrayList<Long> history_tanggal) {
        this.history_tanggal = history_tanggal;
    }
}
