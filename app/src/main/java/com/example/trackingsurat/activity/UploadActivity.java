package com.example.trackingsurat.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trackingsurat.R;
import com.example.trackingsurat.listener.UploadListener;
import com.example.trackingsurat.model.Barang;
import com.example.trackingsurat.presenter.UploadPresenter;
import com.google.firebase.FirebaseApp;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UploadActivity extends AppCompatActivity implements UploadListener {

    Button btnUpload;
    EditText edtTanggal;
    EditText edtIdResi;
    EditText edtPosisi;
    UploadPresenter presenter;
    ProgressBar progressBar;
    ImageView imgDatePicker;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        init();
        FirebaseApp.initializeApp(this);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEditTxt()){
                    String date = edtTanggal.getText().toString();
                    Date parseDate;
                    Timestamp timestamp;
                    try {
                        parseDate = simpleDateFormat.parse(date);
                        presenter.isDuplicate(new Barang(edtIdResi.getText().toString()
                                ,parseDate.getTime()
                                ,edtPosisi.getText().toString()));
                        btnUpload.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        imgDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    void init (){
        btnUpload = findViewById(R.id.btnUpload);
        edtIdResi = findViewById(R.id.edtResiUpload);
        edtTanggal = findViewById(R.id.edtTanggalUpload);
        edtPosisi = findViewById(R.id.edtPoisisi);
        progressBar = findViewById(R.id.progresUpload);
        imgDatePicker = findViewById(R.id.imgDatePicker);
        presenter = new UploadPresenter(this,this);
        progressBar.setVisibility(View.GONE);
        simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);

    }

    boolean checkEditTxt(){
        boolean isEmpty = true;
        if (edtPosisi.getText().toString().length() == 0){
            edtPosisi.setError("Tidak Boleh Kosong");
            isEmpty = false;
        }else if (edtTanggal.getText().toString().length() == 0){
            edtPosisi.setError("Tidak Boleh Kosong");
            isEmpty = false;
        }else if (edtPosisi.getText().toString().length() == 0){
            edtPosisi.setError("Tidak Boleh Kosong");
            isEmpty = false;
        }
        return isEmpty;
    }

    @Override
    public void onSucces() {
        Toast.makeText(this,"Data telah berhasil di upload",Toast.LENGTH_SHORT).show();
        edtPosisi.setText("");
        edtIdResi.setText("");
        edtTanggal.setText("");
        progressBar.setVisibility(View.GONE);
        btnUpload.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(String string) {
        Toast.makeText(this,string, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        btnUpload.setVisibility(View.VISIBLE);
    }

    void showDateDialog(){
        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                edtTanggal.setText(simpleDateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
