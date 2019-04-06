package com.example.trackingsurat.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trackingsurat.R;
import com.example.trackingsurat.listener.LoginListener;
import com.example.trackingsurat.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    EditText username;
    EditText password;
    Button btnLogin;
    LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString() != null && password.getText().toString() != null){
                    presenter.login(username.getText().toString(), password.getText().toString());
                }else {
                    Toast.makeText(LoginActivity.this, "Username dan Password tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void init(){
        presenter = new LoginPresenter(LoginActivity.this,LoginActivity.this);
        username = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public void onSucces() {
        SharedPreferences.Editor editor = getSharedPreferences("Preference", MODE_PRIVATE).edit();
        SharedPreferences preferences = getSharedPreferences("Preference", MODE_PRIVATE);
        editor.putBoolean("isLogin", true);
        editor.apply();
        Log.e("Preference", String.valueOf(preferences.getBoolean("isLogin",false)));
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void onFailure() {
        Toast.makeText(this,"Tidak dapat login",Toast.LENGTH_SHORT).show();
    }
}