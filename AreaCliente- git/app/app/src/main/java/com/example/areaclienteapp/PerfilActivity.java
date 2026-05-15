package com.example.areaclienteapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.areaclienteapp.modelo.Usuario;
import com.example.areaclienteapp.red.ApiService;
import com.example.areaclienteapp.red.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla de perfil del usuario.
 */
public class PerfilActivity extends AppCompatActivity {

    private EditText txtNombre, txtEmail, txtTelefono, txtIban;
    private Button btnGuardar;

    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        txtNombre = findViewById(R.id.txtNombre);
        txtEmail = findViewById(R.id.txtEmail);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtIban = findViewById(R.id.txtIban);
        btnGuardar = findViewById(R.id.btnGuardar);

        idUsuario = getIntent().getIntExtra("idUsuario", 0);

        cargarUsuario();

        btnGuardar.setOnClickListener(v -> actualizarUsuario());
    }

    private void cargarUsuario() {

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        apiService.obtenerUsuario(idUsuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Usuario u = response.body();

                    txtNombre.setText(u.getNombre() + " " + u.getApellidos());
                    txtEmail.setText(u.getEmail());
                    txtTelefono.setText(u.getTelefono());
                    txtIban.setText(u.getIban());

                    txtNombre.setEnabled(false); // solo lectura
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(PerfilActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarUsuario() {

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        Usuario usuario = new Usuario();
        usuario.setEmail(txtEmail.getText().toString());
        usuario.setTelefono(txtTelefono.getText().toString());
        usuario.setIban(txtIban.getText().toString());

        apiService.actualizarUsuario(idUsuario, usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(PerfilActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PerfilActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(PerfilActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}