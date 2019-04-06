package com.example.trackingsurat.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.trackingsurat.listener.UploadListener;
import com.example.trackingsurat.model.Barang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UploadPresenter {
    private Context activityUpload;
    private FirebaseFirestore db;
    private UploadListener listener;

    public UploadPresenter(Context activityCompat, UploadListener listener){
        this.activityUpload = activityCompat;
        this.listener= listener;
        FirebaseApp.initializeApp(activityCompat);
        db = FirebaseFirestore.getInstance();
    }

    public void uploadData(Barang barang){
            db.collection("Barang").document().set(barang)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            listener.onSucces();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    listener.onFailure("Tidak bisa mengupload");
                }
            });

    }

    public boolean isDuplicate(final Barang id){
        final boolean[] isDuplicate = {false};
        db.collection("Barang").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult().size() == 1){
                                uploadData(id);
                            }else {

                            for (QueryDocumentSnapshot x : task.getResult()){
                                if (task.getResult().size() ==1){
                                    uploadData(id);
                                }
                                Barang barang = x.toObject(Barang.class);
                                if (barang.getIdResi().equals(id.getIdResi()))
                                    isDuplicate[0] = true;
                            }
                            if (isDuplicate[0]){
                                listener.onFailure("Data telah ada, tidak bisa upload lagi");
                            }else {
                                uploadData(id);
                            }
                            }
                        }
                    }
                });
        return isDuplicate[0];
    }
}
