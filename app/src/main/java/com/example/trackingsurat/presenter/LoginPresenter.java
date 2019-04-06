package com.example.trackingsurat.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.trackingsurat.listener.LoginListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginPresenter {
    private Context context;
    private FirebaseFirestore db;
    private LoginListener loginListener;

    public LoginPresenter(Context context, LoginListener loginListener){
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
        this.loginListener = loginListener;
    }

    public void login(String username, String password){
        db.collection("User").whereEqualTo("username",username).whereEqualTo("password", password)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().size() != 0){
                        for (QueryDocumentSnapshot x: task.getResult()){
                            loginListener.onSucces();
                        }
                    }else {
                        loginListener.onFailure();
                    }
                }
            }
        });
    }

}