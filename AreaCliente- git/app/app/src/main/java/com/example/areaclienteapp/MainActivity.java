package com.example.areaclienteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.areaclienteapp.modelo.LoginRequest;
import com.example.areaclienteapp.modelo.Usuario;
import com.example.areaclienteapp.red.ApiService;
import com.example.areaclienteapp.red.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla de login de la aplicación.
 */
public class MainActivity extends AppCompatActivity {

    private EditText edtDni;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtDni = findViewById(R.id.edtDni);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> hacerLogin());
    }

    /**
     * Envía el DNI y la contraseña al backend.
     */
    private void hacerLogin() {

        String dni = edtDni.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (dni.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest datosLogin = new LoginRequest(dni, password);

        ApiService apiService = RetrofitCliente.getCliente().create(ApiService.class);

        apiService.login(datosLogin).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();

                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("nombre", usuario.getNombre());
                    intent.putExtra("idUsuario", usuario.getIdUsuario());
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}