package com.example.day_today2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class TrainerEkrani extends AppCompatActivity {

    String cagrilanID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ekrani);

        Button bilgiGetirButon = findViewById(R.id.sporcuBilgilerButon);

        TextView kullaniciBilgi = findViewById(R.id.kullaniciBilgiText);

        EditText isimText = findViewById(R.id.isimText);
        EditText soyisimText = findViewById(R.id.soyisimText);

        EditText egitmenNotText = findViewById(R.id.egitmenNotText);
        EditText idmanText = findViewById(R.id.idmanText);
        Button notEkleButon = findViewById(R.id.notEkleButon);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();

        bilgiGetirButon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isimText.getText().toString().trim().length() > 0 && soyisimText.getText().toString().trim().length() > 0) {

                    firestore.collection("users")
                            .whereEqualTo("isim", isimText.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String uyelikTipi = document.get("uyelikTipi").toString();
                                            String soyisim = document.get("soyisim").toString();
                                            String telefon = document.get("telefon").toString();
                                            String egitmenNot = document.get("egitmenNot").toString();

                                            cagrilanID = document.getId();

                                            if (soyisim.equalsIgnoreCase(soyisimText.getText().toString())) {

                                                if (!uyelikTipi.equalsIgnoreCase("trainer")) {
                                                    kullaniciBilgi.setText("Uyelik Tipi : " + uyelikTipi + "\n" + "İsim : " + isimText.getText().toString()
                                                    + "\n" + "Soyisim : " + soyisim + "\n" + "Telefon Numarası : " + telefon + "\n" + "Egitmen Notu : " + egitmenNot);
                                                }
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Lütfen bilgilerinizi doğru giriniz!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen bilgilerinizi giriniz!", Toast.LENGTH_LONG).show();
                }
            }
        });

        notEkleButon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (egitmenNotText.getText().toString().trim().length() > 0) {
                    firestore.collection("users").document(cagrilanID)
                            .update("egitmenNot", egitmenNotText.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Başarıyla not eklediniz!", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Sunucu Hatası!", Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Eğitmen notu eklenemedi!", Toast.LENGTH_LONG).show();
                }
                if (egitmenNotText.getText().toString().trim().length() > 0) {
                    firestore.collection("users").document(cagrilanID)
                            .update("idman", idmanText.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Başarıyla idman eklediniz!", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Sunucu Hatası!", Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "İdman eklenemedi!", Toast.LENGTH_LONG).show();
                }
            }
        });









    }
}