package com.example.areaclienteapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.areaclienteapp.modelo.CapturaPagoRequest;
import com.example.areaclienteapp.modelo.PagoPaypalRespuesta;
import com.example.areaclienteapp.modelo.Poliza;
import com.example.areaclienteapp.modelo.Recibo;
import com.example.areaclienteapp.red.ApiService;
import com.example.areaclienteapp.red.RetrofitCliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecibosActivity extends AppCompatActivity {

    private LinearLayout contenedorRecibos;
    private int idUsuario;
    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibos);

        contenedorRecibos = findViewById(R.id.contenedorRecibos);
        idUsuario = getIntent().getIntExtra("idUsuario", 0);
        preferencias = getSharedPreferences("pago_paypal", MODE_PRIVATE);

        revisarVueltaPaypal(getIntent());
        cargarRecibos();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        revisarVueltaPaypal(intent);
    }

    private void revisarVueltaPaypal(Intent intent) {

        Uri uri = intent.getData();

        if (uri != null && "areaclienteapp".equals(uri.getScheme())) {

            String resultado = uri.getLastPathSegment();

            if ("exito".equalsIgnoreCase(resultado)) {

                int idRecibo = preferencias.getInt("idRecibo", 0);
                String orderId = preferencias.getString("orderId", "");

                if (idRecibo != 0 && orderId != null && !orderId.isEmpty()) {
                    capturarPago(idRecibo, orderId);
                }

            } else if ("cancelado".equalsIgnoreCase(resultado)) {
                Toast.makeText(this, "Pago cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cargarRecibos() {

        contenedorRecibos.removeAllViews();

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        apiService.obtenerRecibosUsuario(idUsuario).enqueue(new Callback<List<Recibo>>() {
            @Override
            public void onResponse(Call<List<Recibo>> call, Response<List<Recibo>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Recibo> lista = response.body();

                    if (lista.isEmpty()) {
                        crearTexto("No tienes recibos.");
                    } else {
                        for (Recibo recibo : lista) {
                            crearTarjetaRecibo(recibo);
                        }
                    }

                } else {
                    Toast.makeText(RecibosActivity.this, "No se pudieron cargar los recibos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Recibo>> call, Throwable t) {
                Toast.makeText(RecibosActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearTarjetaRecibo(Recibo recibo) {

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

        String infoPoliza = obtenerInfoPoliza(recibo);

        crearTextoEnTarjeta(tarjeta, "Vehículo / Póliza: " + infoPoliza, 17);
        crearTextoEnTarjeta(tarjeta, "Importe: " + mostrarImporte(recibo.getImporte()), 15);

        // 🔥 ESTADO CON COLOR
        TextView estado = new TextView(this);
        estado.setText("Estado: " + valorSeguro(recibo.getEstado()));
        estado.setTextSize(15);
        estado.setPadding(0, 3, 0, 3);

        if ("pagado".equalsIgnoreCase(recibo.getEstado())) {
            estado.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            estado.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        tarjeta.addView(estado);

        crearTextoEnTarjeta(tarjeta, "Vencimiento: " + valorSeguro(recibo.getFechaVencimiento()), 15);

        // Botón PayPal solo si está pendiente
        if ("pendiente".equalsIgnoreCase(recibo.getEstado())) {

            Button botonPagar = new Button(this);
            botonPagar.setText("Pagar con PayPal");
            botonPagar.setAllCaps(false);

            botonPagar.setOnClickListener(v -> crearPagoPaypal(recibo.getIdRecibo()));

            tarjeta.addView(botonPagar);
        }

        contenedorRecibos.addView(tarjeta);
    }

    /**
     * 🔹 Obtiene matrícula o número de póliza
     */
    private String obtenerInfoPoliza(Recibo recibo) {

        Poliza poliza = recibo.getPoliza();

        if (poliza == null) return "No consta";

        if (poliza.getMatricula() != null && !poliza.getMatricula().isEmpty()) {
            return poliza.getMatricula();
        }

        if (poliza.getNumeroPoliza() != null && !poliza.getNumeroPoliza().isEmpty()) {
            return poliza.getNumeroPoliza();
        }

        return "No consta";
    }

    private void crearPagoPaypal(Integer idRecibo) {

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        apiService.crearPagoPaypal(idRecibo).enqueue(new Callback<PagoPaypalRespuesta>() {
            @Override
            public void onResponse(Call<PagoPaypalRespuesta> call, Response<PagoPaypalRespuesta> response) {

                if (response.isSuccessful() && response.body() != null) {

                    PagoPaypalRespuesta pago = response.body();

                    preferencias.edit()
                            .putInt("idRecibo", idRecibo)
                            .putString("orderId", pago.getOrderId())
                            .apply();

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pago.getApproveLink()));
                    startActivity(intent);

                } else {
                    Toast.makeText(RecibosActivity.this, "No se pudo crear el pago", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PagoPaypalRespuesta> call, Throwable t) {
                Toast.makeText(RecibosActivity.this, "Error de conexión con PayPal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void capturarPago(Integer idRecibo, String orderId) {

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        CapturaPagoRequest request = new CapturaPagoRequest(idRecibo, orderId);

        apiService.capturarPagoPaypal(request).enqueue(new Callback<Recibo>() {
            @Override
            public void onResponse(Call<Recibo> call, Response<Recibo> response) {

                if (response.isSuccessful()) {

                    preferencias.edit().clear().apply();

                    Toast.makeText(RecibosActivity.this, "Pago realizado correctamente", Toast.LENGTH_SHORT).show();

                    cargarRecibos();

                } else {
                    Toast.makeText(RecibosActivity.this, "No se pudo confirmar el pago", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recibo> call, Throwable t) {
                Toast.makeText(RecibosActivity.this, "Error al confirmar el pago", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearTexto(String mensaje) {
        TextView texto = new TextView(this);
        texto.setText(mensaje);
        texto.setTextSize(17);
        texto.setTextColor(getResources().getColor(android.R.color.black));
        contenedorRecibos.addView(texto);
    }

    private void crearTextoEnTarjeta(LinearLayout tarjeta, String texto, int tamano) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setTextSize(tamano);
        textView.setTextColor(getResources().getColor(android.R.color.black));
        textView.setPadding(0, 3, 0, 3);
        tarjeta.addView(textView);
    }

    private String valorSeguro(String valor) {
        if (valor == null || valor.isEmpty()) return "No consta";
        return valor;
    }

    private String mostrarImporte(Double importe) {
        if (importe == null) return "No consta";
        return importe + " €";
    }
}