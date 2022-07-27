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


public class AdministradorCodigos extends AppCompatActivity    {
    private FirebaseAnalytics mFirebaseAnalytics;
    private List<Codigos> listCodigos = new ArrayList<>();

    ArrayAdapter<Codigos> codigosArrayAdapter;
    String TAG = "Firebase";

    EditText nom, apell, corr, contra,codi;
    ListView listausuarios;
    Codigos userselect;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administradorcodigos);




        nom = findViewById(R.id.txtnombre);
        apell = findViewById(R.id.txtapellidos);
        corr = findViewById(R.id.txtcorreo);
        contra = findViewById(R.id.txtcontraseña);
        codi= findViewById(R.id.txtcodigo);
        listausuarios = findViewById(R.id.lista);

        listar();

        listausuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userselect = (Codigos) parent.getItemAtPosition(position);
                nom.setText(userselect.getNombre());
                apell.setText(userselect.getApellidos());
                corr.setText(userselect.getCorreo());
                contra.setText(userselect.getContraseña());
                codi.setText(userselect.getNCodigos());
            }
        });


    }




    private void listar () {
        db.collection("Codigos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=>" + document.getData());
                                Codigos u = new Codigos();
                                u.setNombre(document.get("Nombre").toString()+"est");
                                u.setApellidos(document.get("Apellidos").toString());
                                u.setCorreo(document.get("Correo").toString());
                                u.setContraseña(document.get("Contraseña").toString());
                                u.setNCodigos(document.get("NCodigos").toString());

                                listCodigos.add(u);

                                codigosArrayAdapter= new ArrayAdapter<Codigos>(AdministradorCodigos.this, android.R.layout.simple_list_item_1, listCodigos);
                                listausuarios.setAdapter(codigosArrayAdapter);
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
        String ncodigos  = codi.getText().toString();

        switch (item.getItemId()) {
            case R.id.icon_add:
                if (nombre.equals("") || apellido.equals("") || correo.equals("") || contraseña.equals("") || ncodigos.equals("")) {
                    validacion();
                } else {

                    guardardatos();
                    limpiarcaja();
                    Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.icon_save:
                if (nombre.equals("") || apellido.equals("") || correo.equals("") || contraseña.equals("") || ncodigos.equals("")) {
                    Toast.makeText(this, "Seleccione el usuario", Toast.LENGTH_SHORT).show();

                } else {
                    Codigos u2 = new Codigos();
                    u2.setUid(userselect.getUid());
                    u2.setNombre(userselect.getNombre());
                    u2.setApellidos(userselect.getApellidos());
                    u2.setCorreo(userselect.getCorreo());
                    u2.setContraseña(userselect.getContraseña());
                    u2.setNCodigos(userselect.getNCodigos());

                    CollectionReference userCol = db.collection("Codigos");


                    Map<String, Object> user = new HashMap<>();
                    user.put("Nombre", nom.getText().toString());
                    user.put("Apellidos", apell.getText().toString());
                    user.put("Correo", corr.getText().toString());
                    user.put("Contraseña", contra.getText().toString());
                    user.put("NCodigos", codi.getText().toString());

                    userCol.document(corr.getText().toString()).set(user);

                    listCodigos.clear();

                    listar();
                    Toast.makeText(this, "Cambios Guardados", Toast.LENGTH_SHORT).show();


                }
                break;

            case R.id.icon_delete: {
                if (nombre.equals("") || apellido.equals("") || correo.equals("") || contraseña.equals("")) {
                    Toast.makeText(this, "Seleccione el usuario", Toast.LENGTH_SHORT).show();

                } else {

                    Codigos u3 = new Codigos();
                    u3.setUid(userselect.getUid());

                    CollectionReference userCol2 = db.collection("Codigos");
                    userCol2.document(corr.getText().toString()).delete();

                    listCodigos.clear();
                    listar();
                    limpiarcaja();
                    Toast.makeText(this, "Usuario Eliminado", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.icon_clear: {
                listCodigos.clear();
                limpiarcaja();
                Toast.makeText(this, "Campos limpios", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.icon_actualizar:

                listCodigos.clear();
                listar();
                Toast.makeText(this, "Datos Actualizados", Toast.LENGTH_SHORT).show();


                break;
            case R.id.icon_buscar:


                break;
        }
        return true;
    }

    private void limpiarcaja () {
        nom.setText("");
        apell.setText("");
        corr.setText("");
        contra.setText("");
        codi.setText("");
    }

    private void validacion () {
        String nombre = nom.getText().toString();
        String apellido = apell.getText().toString();
        String correo = corr.getText().toString();
        String contraseñ = contra.getText().toString();
        String ncodigos= codi.getText().toString();

        if (nombre.equals("")) {
            nom.setError("Dato requerido");
        } else if (apellido.equals("")) {
            apell.setError("Dato requerido");
        } else if (correo.equals("")) {
            corr.setError("Dato requqerido");
        } else if (contraseñ.equals("")) {
            contra.setError("Dato requerido");
        }else if (ncodigos.equals("")) {
            codi.setError("Dato requerido");
        }
    }

    public void pres (View view){
        listar();
    }

    public void guardardatos () {
        CollectionReference persons = db.collection("Codigos");


        Map<String, Object> user = new HashMap<>();
        user.put("Nombre", nom.getText().toString());
        user.put("Apellidos", apell.getText().toString());
        user.put("Correo", corr.getText().toString());
        user.put("Contraseña", contra.getText().toString());
        user.put("NCodigos", codi.getText().toString());
        //String dt = nom.getText().toString()+apell.getText().toString()+corr.getText().toString();
        persons.document(corr.getText().toString()).set(user);
        listCodigos.clear();
        listar();
        limpiarcaja();
        Toast.makeText(this, "Datos Guardados", Toast.LENGTH_SHORT).show();
    }
}

