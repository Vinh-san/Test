package com.example.test;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Service {

    private Complete complete;

    public void setComplete(Complete complete) {
        this.complete = complete;
    }

    public void getAddItem(List<String> files, String path) {
        FirebaseFirestore.getInstance().collection("AAA").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    String sourcePath = documentSnapshot.getId().replace('\\', '/');
                    if (sourcePath.substring(0, sourcePath.lastIndexOf('/')).equals(path)) {
                        files.add(sourcePath);
                    }
                }
                complete.list(files.size());
            }
        });
    }

    public void createFolder(String folderName, String path) {
        String pathSource = path.replace('/', '\\') + "\\" + folderName;
        FirebaseFirestore.getInstance().collection("AAA").document(pathSource).set(new HashMap<>()).addOnSuccessListener(unused -> {
            Log.d("AAA", "Create");
            complete.add(folderName, 1, 0);
        }).addOnFailureListener(e -> complete.add(folderName, 0, 0));
    }
}