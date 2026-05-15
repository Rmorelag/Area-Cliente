package com.example.areaclienteapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.areaclienteapp.modelo.DocumentoPoliza;
import com.example.areaclienteapp.modelo.Poliza;
import com.example.areaclienteapp.red.ApiService;
import com.example.areaclienteapp.red.RetrofitCliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para mostrar los documentos de las pólizas.
 */
public class DocumentosActivity extends AppCompatActivity {

    private LinearLayout contenedorDocumentos;
    private int idUsuario;

    private static final String URL_BACKEND = "http://10.0.2.2:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos);

        contenedorDocumentos = findViewById(R.id.contenedorDocumentos);
        idUsuario = getIntent().getIntExtra("idUsuario", 0);

        cargarPolizas();
    }

    /**
     * Carga las pólizas del usuario para buscar sus documentos.
     */
    private void cargarPolizas() {

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        apiService.obtenerPolizas(idUsuario).enqueue(new Callback<List<Poliza>>() {
            @Override
            public void onResponse(Call<List<Poliza>> call, Response<List<Poliza>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Poliza> polizas = response.body();

                    if (polizas.isEmpty()) {
                        crearTexto("No tienes pólizas.");
                    } else {
                        for (Poliza poliza : polizas) {
                            cargarDocumentosPoliza(poliza);
                        }
                    }

                } else {
                    Toast.makeText(DocumentosActivity.this, "No se pudieron cargar las pólizas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Poliza>> call, Throwable t) {
                Toast.makeText(DocumentosActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Carga los documentos de una póliza.
     */
    private void cargarDocumentosPoliza(Poliza poliza) {

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        apiService.obtenerDocumentosPoliza(poliza.getIdPoliza()).enqueue(new Callback<List<DocumentoPoliza>>() {
            @Override
            public void onResponse(Call<List<DocumentoPoliza>> call, Response<List<DocumentoPoliza>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<DocumentoPoliza> documentos = response.body();

                    for (DocumentoPoliza documento : documentos) {
                        crearTarjetaDocumento(documento);
                    }

                } else {
                    Toast.makeText(DocumentosActivity.this, "No se pudieron cargar documentos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DocumentoPoliza>> call, Throwable t) {
                Toast.makeText(DocumentosActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Crea una tarjeta para mostrar un documento.
     */
    private void crearTarjetaDocumento(DocumentoPoliza documento) {

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

        String infoPoliza = obtenerInfoPoliza(documento);

        crearTextoEnTarjeta(tarjeta, "Vehículo / Póliza: " + infoPoliza, 17);
        crearTextoEnTarjeta(tarjeta, "Versión: " + documento.getVersion(), 15);
        crearTextoEnTarjeta(tarjeta, "Motivo: " + valorSeguro(documento.getMotivo()), 15);
        crearTextoEnTarjeta(tarjeta, "Fecha: " + valorSeguro(documento.getFechaGeneracion()), 15);

        Button botonDescargar = new Button(this);
        botonDescargar.setText("Descargar PDF");
        botonDescargar.setAllCaps(false);

        botonDescargar.setOnClickListener(v -> descargarDocumento(documento.getIdDocumento()));

        tarjeta.addView(botonDescargar);

        contenedorDocumentos.addView(tarjeta);
    }

    /**
     * Abre el PDF en el navegador.
     */
    private void descargarDocumento(Integer idDocumento) {

        String url = URL_BACKEND + "documentos/descargar/" + idDocumento;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Obtiene matrícula o número de póliza.
     */
    private String obtenerInfoPoliza(DocumentoPoliza documento) {

        Poliza poliza = documento.getPoliza();

        if (poliza == null) {
            return "No consta";
        }

        if (poliza.getMatricula() != null && !poliza.getMatricula().isEmpty()) {
            return poliza.getMatricula();
        }

        if (poliza.getNumeroPoliza() != null && !poliza.getNumeroPoliza().isEmpty()) {
            return poliza.getNumeroPoliza();
        }

        return "No consta";
    }

    /**
     * Crea un texto en pantalla.
     */
    private void crearTexto(String mensaje) {
        TextView texto = new TextView(this);
        texto.setText(mensaje);
        texto.setTextSize(17);
        texto.setTextColor(getResources().getColor(android.R.color.black));

        contenedorDocumentos.addView(texto);
    }

    /**
     * Crea un texto dentro de una tarjeta.
     */
    private void crearTextoEnTarjeta(LinearLayout tarjeta, String texto, int tamano) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setTextSize(tamano);
        textView.setTextColor(getResources().getColor(android.R.color.black));
        textView.setPadding(0, 3, 0, 3);

        tarjeta.addView(textView);
    }

    /**
     * Evita mostrar null.
     */
    private String valorSeguro(String valor) {
        if (valor == null || valor.isEmpty()) {
            return "No consta";
        }
        return valor;
    }
}