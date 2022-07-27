package com.example.sherpa_vm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sherpa_vm.Procesos.AdministradorCodigos;
import com.example.sherpa_vm.Procesos.Usuarios;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private List<Usuarios> listusuarios = new ArrayList<>();
    ArrayAdapter<Usuarios> arrayAdapterUsuarios;
    String TAG = "Firebase";

    EditText nom, apell, corr, contra;
    ListView listausuarios;
    Usuarios userselect;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent a = new Intent(this, AdministradorCodigos.class);
        startActivity(a);

    }
}