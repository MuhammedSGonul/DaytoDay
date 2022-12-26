package com.example.day_today2;

import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {

    public static String loggedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);
        EditText emailText = findViewById(R.id.mailText);
        EditText passwordText = findViewById(R.id.passwordText);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (emailText.getText().toString().trim().length() > 0 && passwordText.getText().toString().trim().length() > 0) {

                    firestore.collection("users")
                            .whereEqualTo("email", emailText.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String sifre = document.get("sifre").toString();
                                            String uyelikTipi = document.get("uyelikTipi").toString();

                                            loggedID = document.getId();

                                            if (sifre.equalsIgnoreCase(passwordText.getText().toString())) {

                                                if (uyelikTipi.equalsIgnoreCase("Trainer")) {
                                                    startActivity(new Intent(MainActivity.this, TrainerEkrani.class));
                                                } else {
                                                    startActivity(new Intent(MainActivity.this, GenelBilgiler.class));
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

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, KayitEkrani.class));
            }
        });

    }
}