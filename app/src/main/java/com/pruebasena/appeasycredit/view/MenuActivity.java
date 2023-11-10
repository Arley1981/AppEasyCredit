package com.pruebasena.appeasycredit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.pruebasena.appeasycredit.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Configurar el botón "Crear Usuario"
        Button btnCrearUsuario = findViewById(R.id.btnCrearUsuario);
        btnCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en "Crear Usuario", abrir UsuarioActivity
                Intent intent = new Intent(MenuActivity.this, UsuarioActivity.class);
                startActivity(intent);
            }
        });

        // Configurar el botón "Registrar Cliente"
        Button btnRegistrarCliente = findViewById(R.id.btnRegistrarCliente);
        btnRegistrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en "Registrar Cliente", abrir ClienteActivity
                Intent intent = new Intent(MenuActivity.this, ClienteActivity.class);
                startActivity(intent);
            }
        });
    }
}