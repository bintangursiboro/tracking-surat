package com.example.trackingsurat.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackingsurat.R;
import com.example.trackingsurat.listener.TrackingListener;
import com.example.trackingsurat.model.Barang;
import com.example.trackingsurat.presenter.TrackingPresenter;
import com.example.trackingsurat.util.TimeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrackingActivity extends AppCompatActivity implements TrackingListener {

    Button btnTrack;
    TrackingPresenter presenter;
    TextView txtPosisi;
    TextView txtResiId;
    TextView txtTanggal;
    EditText edtResiId;
    ProgressBar progressBar;
    CardView cardView;
    LinearLayout layoutHistory;
    TextView initialHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences preferences = getSharedPreferences("Preference", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        btnTrack = findViewById(R.id.btnTrack);
        presenter = new TrackingPresenter(this,this);

        initUI();

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtResiId.getText().toString().length() > 0){
                    presenter.track(edtResiId.getText().toString());
                    btnTrackClicked();
                }else {
                    edtResiId.setError("Tidak Boleh Kosong");
                }
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getBoolean("isLogin", false)){
                    Intent intent = new Intent(TrackingActivity.this, UpdateBarangActivity.class);
                    intent.putExtra("idResi",txtResiId.getText().toString());
                    intent.putExtra("tanggal",txtTanggal.getText().toString());
                    intent.putExtra("posisi",txtPosisi.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onSucces(Barang barang) {
        cardView.setVisibility(View.VISIBLE);
        String tgl = TimeConverter.longToDate(barang.getTanggal());
        txtTanggal.setText(tgl);
        txtResiId.setText(barang.getIdResi());
        txtPosisi.setText(barang.getPosisi());

        ArrayList<String> listHistoryPosisi = new ArrayList<>();
        ArrayList<Long> listHistoryTanggal = new ArrayList<>();
        listHistoryTanggal.addAll(barang.getHistory_tanggal());
        listHistoryPosisi.addAll(barang.getHistory_posisi());
        if (listHistoryPosisi.size() == 1){
            showInitialLocation(listHistoryPosisi.get(0));
            addHistory(barang.getPosisi(),barang.getTanggal());
        }
        else if (listHistoryPosisi.size() > 1){
//            for (String x: listHistoryPosisi){
//                addHistory(x);
//            }
            showInitialLocation(listHistoryPosisi.get(0));
            int counter = 1;
            while (counter < listHistoryPosisi.size()){
                addHistory(listHistoryPosisi.get(counter),listHistoryTanggal.get(counter));
                counter++;
            }
        }else if (listHistoryPosisi.size() == 0){
            showInitialLocation(barang.getPosisi());
        }

        progressBar.setVisibility(View.GONE);
        btnTrack.setVisibility(View.VISIBLE);
    }

    public void showInitialLocation(String posisi){
        initialHistory.setText(posisi);
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "Barang tidak dapat ditemukan", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        btnTrack.setVisibility(View.VISIBLE);
    }

    void initUI(){
        initialHistory = findViewById(R.id.txtInitial);
        layoutHistory = findViewById(R.id.layout_history);
        txtPosisi = findViewById(R.id.txtPosisiSurat);
        txtResiId = findViewById(R.id.txtNoSurat);
        txtTanggal = findViewById(R.id.txtTglSurat);
        edtResiId = findViewById(R.id.edtTracking);
        cardView = findViewById(R.id.card_view);
        cardView.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progresTrack);
        progressBar.setVisibility(View.GONE);
    }

    void btnTrackClicked(){
        progressBar.setVisibility(View.VISIBLE);
        btnTrack.setVisibility(View.GONE);
    }

    void addHistory(String historyLocation,long historyTanggal){
        TextView txtHistoryLocation;
        TextView txtHistoryTanggal;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.history,null);
        txtHistoryLocation = rowView.findViewById(R.id.txtHistoryPosisi);
        txtHistoryLocation.setText(historyLocation);
        txtHistoryTanggal = rowView.findViewById(R.id.txtHistoryTanggal);
        txtHistoryTanggal.setText(TimeConverter.longToDate(historyTanggal));
        layoutHistory.addView(rowView,layoutHistory.getChildCount());
    }
}
