package com.example.areaclienteapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.areaclienteapp.modelo.Conductor;
import com.example.areaclienteapp.red.ApiService;
import com.example.areaclienteapp.red.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para añadir un nuevo conductor.
 */
public class NuevoConductorActivity extends AppCompatActivity {

    private int idPoliza;

    private EditText edtNombre;
    private EditText edtApellidos;
    private EditText edtDni;
    private EditText edtFechaNacimiento;
    private EditText edtFechaCarnet;
    private EditText edtTipoConductor;
    private Button btnGuardarConductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_conductor);

        idPoliza = getIntent().getIntExtra("idPoliza", 0);

        edtNombre = findViewById(R.id.edtNombre);
        edtApellidos = findViewById(R.id.edtApellidos);
        edtDni = findViewById(R.id.edtDni);
        edtFechaNacimiento = findViewById(R.id.edtFechaNacimiento);
        edtFechaCarnet = findViewById(R.id.edtFechaCarnet);
        edtTipoConductor = findViewById(R.id.edtTipoConductor);
        btnGuardarConductor = findViewById(R.id.btnGuardarConductor);

        btnGuardarConductor.setOnClickListener(v -> guardarConductor());
    }

    /**
     * Guarda el nuevo conductor en el backend.
     */
    private void guardarConductor() {

        String nombre = edtNombre.getText().toString().trim();
        String apellidos = edtApellidos.getText().toString().trim();
        String dni = edtDni.getText().toString().trim();
        String fechaNacimiento = edtFechaNacimiento.getText().toString().trim();
        String fechaCarnet = edtFechaCarnet.getText().toString().trim();
        String tipo = edtTipoConductor.getText().toString().trim().toUpperCase();

        if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || tipo.isEmpty()) {
            Toast.makeText(this, "Rellena los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!tipo.equals("P") && !tipo.equals("O")) {
            Toast.makeText(this, "El tipo debe ser P u O", Toast.LENGTH_SHORT).show();
            return;
        }

        Conductor conductor = new Conductor();
        conductor.setNombre(nombre);
        conductor.setApellidos(apellidos);
        conductor.setDni(dni);
        conductor.setFechaNacimiento(fechaNacimiento);
        conductor.setFechaCarnet(fechaCarnet);
        conductor.setTipoConductor(tipo);

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        apiService.crearConductor(idPoliza, conductor).enqueue(new Callback<Conductor>() {
            @Override
            public void onResponse(Call<Conductor> call, Response<Conductor> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(NuevoConductorActivity.this, "Conductor guardado", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NuevoConductorActivity.this, "No se pudo guardar el conductor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Conductor> call, Throwable t) {
                Toast.makeText(NuevoConductorActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}