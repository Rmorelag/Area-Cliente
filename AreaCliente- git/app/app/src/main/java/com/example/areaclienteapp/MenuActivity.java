package com.example.areaclienteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Pantalla principal después del login.
 */
public class MenuActivity extends AppCompatActivity {

    private TextView txtBienvenida;
    private LinearLayout btnPerfil;
    private LinearLayout btnPolizas;
    private LinearLayout btnRecibos;
    private LinearLayout btnDocumentos;
    private LinearLayout btnSalir;

    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtBienvenida = findViewById(R.id.txtBienvenida);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnPolizas = findViewById(R.id.btnPolizas);
        btnRecibos = findViewById(R.id.btnRecibos);
        btnDocumentos = findViewById(R.id.btnDocumentos);
        btnSalir = findViewById(R.id.btnSalir);

        String nombre = getIntent().getStringExtra("nombre");
        idUsuario = getIntent().getIntExtra("idUsuario", 0);

        txtBienvenida.setText("Hola, " + nombre);

        btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);
            intent.putExtra("idUsuario", idUsuario);
            startActivity(intent);
        });

        btnPolizas.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, PolizasActivity.class);
            intent.putExtra("idUsuario", idUsuario);
            startActivity(intent);
        });

        btnRecibos.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, RecibosActivity.class);
            intent.putExtra("idUsuario", idUsuario);
            startActivity(intent);
        });

        btnDocumentos.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, DocumentosActivity.class);
            intent.putExtra("idUsuario", idUsuario);
            startActivity(intent);
        });

        btnSalir.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}