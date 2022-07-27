package com.example.sherpa_vm.Procesos;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sherpa_vm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Administrador extends AppCompatActivity {
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
        setContentView(R.layout.activity_administrador);


        nom = findViewById(R.id.txtnombre);
        apell = findViewById(R.id.txtapellidos);
        corr = findViewById(R.id.txtcorreo);
        contra = findViewById(R.id.txtcontraseña);
        listausuarios = findViewById(R.id.lista);

        listar();

        listausuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userselect = (Usuarios) parent.getItemAtPosition(position);
                nom.setText(userselect.getNombre());
                apell.setText(userselect.getApellidos());
                corr.setText(userselect.getCorreo());
                contra.setText(userselect.getPassword());
            }
        });


    }




    private void listar () {
        db.collection("Usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=>" + document.getData());
                                Usuarios u = new Usuarios();
                                u.setNombre(document.get("Nombre").toString());
                                u.setApellidos(document.get("Apellidos").toString());
                                u.setCorreo(document.get("Correo").toString());
                                u.setPassword(document.get("Contraseña").toString());


                                listusuarios.add(u);

                                arrayAdapterUsuarios = new ArrayAdapter<Usuarios>(Administrador.this, android.R.layout.simple_list_item_1, listusuarios);
                                listausuarios.setAdapter(arrayAdapterUsuarios);
                            }
                        } else {
                            Log.d(TAG, "Error en datos", task.getException());
                        }
                    }
                });
    }

    private void inicializarFirebase () {
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Toast.makeText(this, "Base indicada", Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected (MenuItem item){

        String nombre = nom.getText().toString();
        String apellido = apell.getText().toString();
        String correo = corr.getText().toString();
        String contraseña = contra.getText().toString();

        switch (item.getItemId()) {
            case R.id.icon_add:
                if (nombre.equals("") || apellido.equals("") || correo.equals("") || contraseña.equals("")) {
                    validacion();
                } else {
                    Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
                    guardardatos();
                    limpiarcaja();
                }
                break;

            case R.id.icon_save:
                if (nombre.equals("") || apellido.equals("") || correo.equals("") || contraseña.equals("")) {
                    Toast.makeText(this, "Seleccione el usuario", Toast.LENGTH_SHORT).show();

                } else {
                    Usuarios u2 = new Usuarios();
                    u2.setUid(userselect.getUid());
                    u2.setNombre(userselect.getNombre());
                    u2.setApellidos(userselect.getApellidos());
                    u2.setCorreo(userselect.getCorreo());
                    u2.setPassword(userselect.getPassword());

                    CollectionReference userCol = db.collection("Usuarios");


                    Map<String, Object> user = new HashMap<>();
                    user.put("Nombre", nom.getText().toString());
                    user.put("Apellidos", apell.getText().toString());
                    user.put("Correo", corr.getText().toString());
                    user.put("Contraseña", contra.getText().toString());

                    userCol.document(corr.getText().toString()).set(user);

                    listusuarios.clear();

                    listar();
                    Toast.makeText(this, "Cambios Guardados", Toast.LENGTH_SHORT).show();


                }
                break;

            case R.id.icon_delete: {
                if (nombre.equals("") || apellido.equals("") || correo.equals("") || contraseña.equals("")) {
                    Toast.makeText(this, "Seleccione el usuario", Toast.LENGTH_SHORT).show();

                } else {

                    Usuarios u3 = new Usuarios();
                    u3.setUid(userselect.getUid());

                    CollectionReference userCol2 = db.collection("Usuarios");
                    userCol2.document(corr.getText().toString()).delete();

                    listusuarios.clear();
                    listar();
                    limpiarcaja();
                    Toast.makeText(this, "Usuario Eliminado", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.icon_clear: {
                listusuarios.clear();
                limpiarcaja();
                Toast.makeText(this, "Campos limpios", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.icon_actualizar:

                listusuarios.clear();
                listar();
                Toast.makeText(this, "Datos Actualizados", Toast.LENGTH_SHORT).show();


                break;
        }
        return true;
    }

    private void limpiarcaja () {
        nom.setText("");
        apell.setText("");
        corr.setText("");
        contra.setText("");
    }

    private void validacion () {
        String nombre = nom.getText().toString();
        String apellido = apell.getText().toString();
        String correo = corr.getText().toString();
        String contraseñ = contra.getText().toString();

        if (nombre.equals("")) {
            nom.setError("Dato requerido");
        } else if (apellido.equals("")) {
            apell.setError("Dato requerido");
        } else if (correo.equals("")) {
            corr.setError("Dato requqerido");
        } else if (contraseñ.equals("")) {
            contra.setError("Dato requerido");
        }
    }

    public void pres (View view){
        listar();
    }

    public void guardardatos () {
        CollectionReference persons = db.collection("Usuarios");


        Map<String, Object> user = new HashMap<>();
        user.put("Nombre", nom.getText().toString());
        user.put("Apellidos", apell.getText().toString());
        user.put("Correo", corr.getText().toString());
        user.put("Contraseña", contra.getText().toString());
        //String dt = nom.getText().toString()+apell.getText().toString()+corr.getText().toString();
        persons.document(corr.getText().toString()).set(user);
        listusuarios.clear();
        listar();
        limpiarcaja();
        Toast.makeText(this, "Datos Guardados", Toast.LENGTH_SHORT).show();
    }




}

