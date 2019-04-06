package com.example.trackingsurat.presenter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.trackingsurat.listener.TrackingListener;
import com.example.trackingsurat.model.Barang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class TrackingPresenter {
    private Context context;
    private TrackingListener listener;
    private FirebaseFirestore db;

    public TrackingPresenter(Context context, TrackingListener listener){
        this.context = context;
        this.listener = listener;
        db = FirebaseFirestore.getInstance();
    }

    public void track(final String resId){
        db.collection("Barang")
                .whereEqualTo("idResi",resId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().size() >= 1){
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            Barang barang = documentSnapshot.toObject(Barang.class);
                            try {
                                listener.onSucces(barang);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }else
                        listener.onFailure();

                }else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()){
//                            for (QueryDocumentSnapshot document: task.getResult()){
//                                Barang barang = document.toObject(Barang.class);
//                                if (barang !=null){
//                                    Log.e(TAG,barang.getIdResi());
//                                }
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                                    if (Objects.equals(barang.getIdResi(), resId)){
//                                        listener.onSucces(barang);
//                                    }
//                                }
//                            }
//                        }else {
//                            Log.e(TAG, "Error Getting data");
//                            listener.onFailure();
//                        }
//                    }
//                });
    }
}
