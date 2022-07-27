package com.example.sherpa_vm.Procesos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sherpa_vm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class LectorQR extends AppCompatActivity {
    EditText nom, apell, corr, contra, codi;
    TextView txtuser, txtmues;
    int aux = 0;
    Button button;
    public String user;
    EditText editText;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectorqr);

        txtmues = findViewById(R.id.txtmuestra1);
        txtuser = findViewById(R.id.lineauser2);
        Bundle re = getIntent().getExtras();
        String rec = re.getString("UsuarioOn");

        System.out.println("muestraesto```````````````` " + rec);

        user = rec;
        button = findViewById(R.id.button);
        editText = findViewById(R.id.txtarea);


        txtuser.setText("Codigos de " + rec + " =" + aux);

        System.out.println("Codigos de " + rec + " =" + aux);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrador = new IntentIntegrator(LectorQR.this);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrador.setPrompt("lector Funcionado");
                integrador.setCameraId(0);
                integrador.setBeepEnabled(true);
                integrador.setBarcodeImageEnabled(true);
                integrador.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lectura Cancelada", Toast.LENGTH_LONG).show();
            }
            aux = aux + 1;
            txtuser.setText("Codigos de " + user + " =" + aux);
            System.out.println("Codigos de " + user + " =" + aux);
            sumacodigos();
            editText.setText(result.getContents());

            Toast.makeText(this, "Lectura Correcto", Toast.LENGTH_LONG).show();


            Bundle envia = new Bundle();
            envia.putString("UsuarioOn", user);
            System.out.println("recibe el perro-------------------" + user);
            Intent m = new Intent(this, QRLector.class);
            m.putExtras(envia);

        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    public void sumacodigos() {
        DocumentReference docRef = db.collection("Codigos").document(user);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();


                    if (document.exists()) {
                        String nombre = document.get("Nombre").toString();
                        String apellido = document.get("Apellidos").toString();
                        String correo = document.get("Correo").toString();
                        String contra = document.get("Contraseña").toString();
                        String a = document.get("NCodigos").toString();
                        int codi = Integer.parseInt(a);
                        codi = codi + 1;
                        txtmues.setText("valores = " + codi);


                        CollectionReference cities = db.collection("Codigos");

                        Map<String, Object> data1 = new HashMap<>();
                        data1.put("Nombre", nombre);
                        data1.put("Apellidos",apellido);
                        data1.put("Correo", correo);
                        data1.put("Contraseña", contra);
                        data1.put("NCodigos", codi);

                        cities.document(user).set(data1);


                        Toast.makeText(LectorQR.this, "Escaneo Correcto", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(LectorQR.this, "Error en el escaneo", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


}