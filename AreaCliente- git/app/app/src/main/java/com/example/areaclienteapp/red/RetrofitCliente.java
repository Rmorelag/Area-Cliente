package com.example.areaclienteapp.red;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase que crea la conexión con el backend.
 */
public class RetrofitCliente {

    private static Retrofit retrofit;

    private static final String BASE_URL = "http://10.0.2.2:8080/";

    public static Retrofit getCliente() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}