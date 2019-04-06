package com.example.trackingsurat.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.trackingsurat.listener.UploadListener;
import com.example.trackingsurat.model.Barang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class UpdatePresenter {
    private Context context;
    private UploadListener listener;
    private FirebaseFirestore db;

    public UpdatePresenter(Context context, UploadListener listener){
        this.context = context;
        this.listener = listener;
        db = FirebaseFirestore.getInstance();
    }

    public void updateBarang(final Barang barang, final String sebelum){
        db.collection("Barang").whereEqualTo("idResi",barang.getIdResi())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().size() == 1){
                    for (QueryDocumentSnapshot x: task.getResult()){
                        Barang barangs = x.toObject(Barang.class);
                        ArrayList<String> newListHistory = new ArrayList<>();
                        newListHistory.addAll(barangs.getHistory_posisi());
                        if (newListHistory.size() == 0){
                            newListHistory.add(sebelum);
                        }
                        ArrayList<Long> newListTanggal = new ArrayList<>();
                        newListTanggal.addAll(barangs.getHistory_tanggal());
                        newListHistory.add(barang.getPosisi());
                        Date currentTime = new Date();
                        newListTanggal.add(currentTime.getTime());
                        db.collection("Barang").document(x.getId()).update("posisi",barang.getPosisi()
                        ,"history_posisi",newListHistory,"history_tanggal", newListTanggal)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        listener.onSucces();
                                    }
                                });
                    }
                }else {
                    listener.onFailure("Tidak dapat mengupdate, ada yang salah");
                }
            }
        });
    }

}
