package com.example.trackingsurat.listener;

import com.example.trackingsurat.model.Barang;

import java.text.ParseException;

public interface TrackingListener {
    void onSucces(Barang barang) throws ParseException;
    void onFailure();
}
