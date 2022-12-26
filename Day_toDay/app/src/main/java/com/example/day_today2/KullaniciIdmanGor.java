package com.example.day_today2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class KullaniciIdmanGor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_idman_gor);


        Button idmanSorgulaButon = findViewById(R.id.idmanSorgulaButon);
        TextView egitmenNotText = findViewById(R.id.egitmenNotText);
        TextView idmanText = findViewById(R.id.idmanText);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();


        idmanSorgulaButon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DocumentReference docRef = firestore.collection("users").document(MainActivity.loggedID);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String egitmenNot = document.get("egitmenNot").toString();
                                String idman = document.get("idman").toString();

                                egitmenNotText.setText(egitmenNot.toString());
                                idmanText.setText(idman.toString());
                            } else {

                            }
                        } else {

                        }
                    }
                });

               
            }
        });


    }
}