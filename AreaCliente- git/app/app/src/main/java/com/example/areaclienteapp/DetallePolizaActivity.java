package com.example.areaclienteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.areaclienteapp.modelo.Conductor;
import com.example.areaclienteapp.red.ApiService;
import com.example.areaclienteapp.red.RetrofitCliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para ver el detalle de una póliza.
 */
public class DetallePolizaActivity extends AppCompatActivity {

    private LinearLayout contenedorDetalle;
    private int idPoliza;
    private String estadoPoliza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_poliza);

        contenedorDetalle = findViewById(R.id.contenedorDetalle);
        idPoliza = getIntent().getIntExtra("idPoliza", 0);
        estadoPoliza = getIntent().getStringExtra("estado");
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarPantalla();
    }

    /**
     * Recarga toda la pantalla.
     */
    private void cargarPantalla() {
        contenedorDetalle.removeAllViews();
        mostrarDatosPoliza();
        cargarConductores();
    }

    /**
     * Muestra los datos recibidos de la póliza.
     */
    private void mostrarDatosPoliza() {

        String numeroPoliza = getIntent().getStringExtra("numeroPoliza");
        String matricula = getIntent().getStringExtra("matricula");
        String fechaInicio = getIntent().getStringExtra("fechaInicio");
        String fechaFin = getIntent().getStringExtra("fechaFin");

        crearTexto("Póliza: " + valorSeguro(numeroPoliza), 18);
        crearTexto("Matrícula: " + valorSeguro(matricula), 16);
        crearTexto("Estado: " + valorSeguro(estadoPoliza), 16);
        crearTexto("Fecha inicio: " + valorSeguro(fechaInicio), 16);
        crearTexto("Fecha fin: " + valorSeguro(fechaFin), 16);

        crearSeparador();
        crearTexto("Conductores", 20);

        if (!"anulada".equalsIgnoreCase(estadoPoliza)) {
            crearBotonAgregar();
        }
    }

    /**
     * Crea botón para añadir conductor.
     */
    private void crearBotonAgregar() {

        TextView boton = new TextView(this);
        boton.setText("+ Añadir conductor");
        boton.setTextSize(18);
        boton.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        boton.setPadding(0, 12, 0, 20);

        boton.setOnClickListener(v -> {
            Intent intent = new Intent(DetallePolizaActivity.this, NuevoConductorActivity.class);
            intent.putExtra("idPoliza", idPoliza);
            startActivity(intent);
        });

        contenedorDetalle.addView(boton);
    }

    /**
     * Carga los conductores de la póliza.
     */
    private void cargarConductores() {

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        apiService.obtenerConductores(idPoliza).enqueue(new Callback<List<Conductor>>() {
            @Override
            public void onResponse(Call<List<Conductor>> call, Response<List<Conductor>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Conductor> lista = response.body();

                    if (lista.isEmpty()) {
                        crearTexto("No constan conductores.", 16);
                    } else {
                        for (Conductor conductor : lista) {
                            crearTarjetaConductor(conductor);
                        }
                    }

                } else {
                    Toast.makeText(DetallePolizaActivity.this, "No se pudieron cargar los conductores", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Conductor>> call, Throwable t) {
                Toast.makeText(DetallePolizaActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Crea una tarjeta para mostrar un conductor.
     */
    private void crearTarjetaConductor(Conductor conductor) {

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

        crearTextoEnTarjeta(tarjeta, "Nombre: " + valorSeguro(conductor.getNombre()) + " " + valorSeguro(conductor.getApellidos()), 17);
        crearTextoEnTarjeta(tarjeta, "DNI: " + valorSeguro(conductor.getDni()), 15);
        crearTextoEnTarjeta(tarjeta, "Tipo: " + traducirTipo(conductor.getTipoConductor()), 15);
        crearTextoEnTarjeta(tarjeta, "Fecha carnet: " + valorSeguro(conductor.getFechaCarnet()), 15);

        if (!"T".equalsIgnoreCase(conductor.getTipoConductor())
                && !"anulada".equalsIgnoreCase(estadoPoliza)) {

            TextView botonEliminar = new TextView(this);
            botonEliminar.setText("- Eliminar conductor");
            botonEliminar.setTextSize(16);
            botonEliminar.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            botonEliminar.setPadding(0, 16, 0, 0);

            botonEliminar.setOnClickListener(v -> eliminarConductor(conductor.getIdConductor()));

            tarjeta.addView(botonEliminar);
        }

        contenedorDetalle.addView(tarjeta);
    }

    /**
     * Elimina un conductor de la póliza.
     */
    private void eliminarConductor(Integer idConductor) {

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        apiService.eliminarConductor(idConductor).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(DetallePolizaActivity.this, "Conductor eliminado", Toast.LENGTH_SHORT).show();
                    cargarPantalla();
                } else {
                    Toast.makeText(DetallePolizaActivity.this, "No se pudo eliminar el conductor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DetallePolizaActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Crea un texto dentro de la pantalla.
     */
    private void crearTexto(String texto, int tamano) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setTextSize(tamano);
        textView.setTextColor(getResources().getColor(android.R.color.black));
        textView.setPadding(0, 4, 0, 8);

        contenedorDetalle.addView(textView);
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
     * Crea un pequeño espacio entre bloques.
     */
    private void crearSeparador() {
        TextView separador = new TextView(this);
        separador.setText("");
        separador.setPadding(0, 12, 0, 12);

        contenedorDetalle.addView(separador);
    }

    /**
     * Evita mostrar null en pantalla.
     */
    private String valorSeguro(String valor) {
        if (valor == null || valor.isEmpty()) {
            return "No consta";
        }
        return valor;
    }

    /**
     * Traduce el tipo de conductor.
     */
    private String traducirTipo(String tipo) {
        if ("T".equalsIgnoreCase(tipo)) {
            return "Tomador";
        } else if ("P".equalsIgnoreCase(tipo)) {
            return "Principal";
        } else if ("O".equalsIgnoreCase(tipo)) {
            return "Ocasional";
        } else {
            return "No consta";
        }
    }
}