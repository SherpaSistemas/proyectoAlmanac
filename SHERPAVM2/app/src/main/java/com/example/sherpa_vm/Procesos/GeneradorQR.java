package com.example.sherpa_vm.Procesos;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sherpa_vm.R;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class GeneradorQR extends AppCompatActivity {
TextView txtuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generador_qr);






        EditText edittext= findViewById(R.id.edittext);


        Button boton= findViewById(R.id.button);
        ImageView viewIm = findViewById(R.id.imageview);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BarcodeEncoder bardcodeencoder = new BarcodeEncoder();



                    Bitmap bitmap = bardcodeencoder.encodeBitmap(edittext.getText().toString(), BarcodeFormat.QR_CODE, 300,350);

                    viewIm.setImageBitmap(bitmap);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }



            }
        });

    }
}