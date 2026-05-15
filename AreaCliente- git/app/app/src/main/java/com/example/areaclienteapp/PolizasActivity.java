package com.example.areaclienteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.areaclienteapp.modelo.Poliza;
import com.example.areaclienteapp.red.ApiService;
import com.example.areaclienteapp.red.RetrofitCliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para mostrar las pólizas del usuario.
 */
public class PolizasActivity extends AppCompatActivity {

    private LinearLayout contenedorPolizas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polizas);

        contenedorPolizas = findViewById(R.id.contenedorPolizas);

        int idUsuario = getIntent().getIntExtra("idUsuario", 0);

        cargarPolizas(idUsuario);
    }

    /**
     * Carga las pólizas desde el backend.
     */
    private void cargarPolizas(int idUsuario) {

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        apiService.obtenerPolizas(idUsuario).enqueue(new Callback<List<Poliza>>() {
            @Override
            public void onResponse(Call<List<Poliza>> call, Response<List<Poliza>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Poliza> lista = response.body();

                    if (lista.isEmpty()) {
                        mostrarTexto("No tienes pólizas contratadas.");
                    } else {
                        for (Poliza poliza : lista) {
                            crearTarjetaPoliza(poliza);
                        }
                    }

                } else {
                    Toast.makeText(PolizasActivity.this, "No se pudieron cargar las pólizas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Poliza>> call, Throwable t) {
                Toast.makeText(PolizasActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Crea una tarjeta sencilla para mostrar una póliza.
     */
    private void crearTarjetaPoliza(Poliza poliza) {

        LinearLayout tarjeta = new LinearLayout(this);
        tarjeta.setOrientation(LinearLayout.VERTICAL);
        tarjeta.setBackgroundResource(R.drawable.fondo_tarjeta);
        tarjeta.setPadding(24, 20, 24, 20);

        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        parametros.setMargins(0, 0, 0, 24);
        tarjeta.setLayoutParams(parametros);
        tarjeta.setElevation(3);

        TextView txtNumero = new TextView(this);
        txtNumero.setText("Póliza: " + poliza.getNumeroPoliza());
        txtNumero.setTextSize(18);
        txtNumero.setTextColor(getResources().getColor(android.R.color.black));

        TextView txtMatricula = new TextView(this);
        txtMatricula.setText("Matrícula: " + poliza.getMatricula());
        txtMatricula.setTextSize(16);
        txtMatricula.setTextColor(getResources().getColor(android.R.color.black));

        TextView txtEstado = new TextView(this);
        txtEstado.setText("Estado: " + poliza.getEstado());
        txtEstado.setTextSize(16);
        txtEstado.setTextColor(getResources().getColor(android.R.color.black));

        tarjeta.addView(txtNumero);
        tarjeta.addView(txtMatricula);
        tarjeta.addView(txtEstado);

        tarjeta.setOnClickListener(v -> {
            Intent intent = new Intent(PolizasActivity.this, DetallePolizaActivity.class);
            intent.putExtra("idPoliza", poliza.getIdPoliza());
            intent.putExtra("numeroPoliza", poliza.getNumeroPoliza());
            intent.putExtra("matricula", poliza.getMatricula());
            intent.putExtra("estado", poliza.getEstado());
            intent.putExtra("fechaInicio", poliza.getFechaInicio());
            intent.putExtra("fechaFin", poliza.getFechaFin());
            startActivity(intent);
        });

        contenedorPolizas.addView(tarjeta);
    }

    /**
     * Muestra un texto sencillo en pantalla.
     */
    private void mostrarTexto(String mensaje) {
        TextView texto = new TextView(this);
        texto.setText(mensaje);
        texto.setTextSize(17);
        texto.setTextColor(getResources().getColor(android.R.color.black));

        contenedorPolizas.addView(texto);
    }
}