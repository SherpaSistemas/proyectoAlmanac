package com.example.sherpa_vm.Procesos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sherpa_vm.R;

public class Menu extends AppCompatActivity {
TextView txtuser,usertxt;

public String reci;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtuser=findViewById(R.id.lineauser);

        Bundle recibe = getIntent().getExtras();
        reci=  recibe.getString("UsuarioOnline");
        txtuser.setText(reci);
        System.out.println("muestraesto```````````````` "+reci);




    }

    public void irqrlector(View view){

        Bundle envia = new Bundle();
        envia.putString("UsuarioOn",reci);
        System.out.println("recibe el perro-------------------"+reci);
        Intent m= new Intent(this, LectorQR.class);
        m.putExtras(envia);
        startActivity(m);

    }
    public void irqrgenerador(View view){
        Intent e= new Intent(this, GeneradorQR.class);
        startActivity(e);
    }

    public void iradministrador(View view){
        Intent e= new Intent(this, Administrador.class);
        startActivity(e);
    }

    public void irsalir(View view){
        Intent w= new Intent(this, Login.class);
        startActivity(w);
    }
    public void iradmincodigos(View view){
        Intent q= new Intent(this, QRLector.class);
        startActivity(q);
    }
    public void iradministracioncodigos(View view){
        Intent q= new Intent(this, AdministradorCodigos.class);
        startActivity(q);
    }

}