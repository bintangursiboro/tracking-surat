package com.example.trackingsurat.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trackingsurat.R;
import com.example.trackingsurat.listener.UploadListener;
import com.example.trackingsurat.model.Barang;
import com.example.trackingsurat.presenter.UpdatePresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateBarangActivity extends AppCompatActivity implements UploadListener {

    Intent intent;
    EditText edtResiIdUpdate;
    EditText edtTanggalUpdate;
    EditText edtPosisiUpdate;
    ProgressBar progressBar;
    Button btnUpdate;
    UpdatePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_barang);
        //Inisialisasi semmua UI
        init();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdate.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                String date = edtTanggalUpdate.getText().toString();
                Date parseDate;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                try {
                parseDate = simpleDateFormat.parse(date);
                    presenter.updateBarang(new Barang(edtResiIdUpdate.getText().toString(),parseDate.getTime(),edtPosisiUpdate.getText().toString()),intent.getStringExtra("posisi"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void init(){
        intent = getIntent();
        btnUpdate = findViewById(R.id.btnUpdate);
        progressBar = findViewById(R.id.progresUpdate);
        progressBar.setVisibility(View.GONE);
        edtPosisiUpdate = findViewById(R.id.edtPoisisiUpdate);
        edtResiIdUpdate = findViewById(R.id.edtResiUpdate);
        edtTanggalUpdate = findViewById(R.id.edtTanggalUpdate);
        presenter = new UpdatePresenter(this,this);
        String idResi = intent.getStringExtra("idResi");
        String tanggal = intent.getStringExtra("tanggal");
        String posisi =intent.getStringExtra("posisi");
        edtTanggalUpdate.setText(tanggal);
        edtResiIdUpdate.setText(idResi);
        edtPosisiUpdate.setText(posisi);
        edtResiIdUpdate.setEnabled(false);
        edtTanggalUpdate.setEnabled(false);
    }

    @Override
    public void onSucces() {
        btnUpdate.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,"Berhasil mengupdate data",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(UpdateBarangActivity.this,MainActivity.class));
    }

    @Override
    public void onFailure(String string) {
        btnUpdate.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
    }
}
