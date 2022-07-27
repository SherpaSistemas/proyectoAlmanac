package com.example.sherpa_vm.Procesos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sherpa_vm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText user, pass;
    Button btningresar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public String nombredato ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user=findViewById(R.id.txt_nombrePersona);
        pass=findViewById(R.id.txt_passwordPersona);
        btningresar=findViewById(R.id.btningresar);





        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                entrar();

            }
        });



    }
    public void entrar( ){
        String usuario= user.getText().toString();
        String contraseña= pass.getText().toString();
        {
            DocumentReference docRef = db.collection("Codigos").document(usuario);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()  ) {
                            String contrase  = document.get("Contraseña").toString();
                            if (contraseña.equals(contrase)) {
                                Bundle envia = new Bundle();
                                envia.putString("UsuarioOnline", user.getText().toString());
                                Intent m = new Intent(Login.this, Menu.class);
                                m.putExtras(envia);
                                startActivity(m);
                                Toast.makeText(Login.this, "Bienvenido " + usuario, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Login.this, "no se encontro el usuario", Toast.LENGTH_SHORT).show();

                            }
                            } else {

                            Toast.makeText(Login.this, "no se encontro el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

    }



}
