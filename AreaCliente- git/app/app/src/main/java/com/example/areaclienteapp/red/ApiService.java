package com.example.areaclienteapp.red;

import com.example.areaclienteapp.modelo.CapturaPagoRequest;
import com.example.areaclienteapp.modelo.Conductor;
import com.example.areaclienteapp.modelo.DocumentoPoliza;
import com.example.areaclienteapp.modelo.LoginRequest;
import com.example.areaclienteapp.modelo.PagoPaypalRespuesta;
import com.example.areaclienteapp.modelo.Poliza;
import com.example.areaclienteapp.modelo.Recibo;
import com.example.areaclienteapp.modelo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interfaz para definir las llamadas al backend.
 */
public interface ApiService {

    @POST("auth/login")
    Call<Usuario> login(@Body LoginRequest loginRequest);

    @GET("usuarios/{id}")
    Call<Usuario> obtenerUsuario(@Path("id") int idUsuario);

    @PUT("usuarios/{id}")
    Call<Usuario> actualizarUsuario(@Path("id") int idUsuario, @Body Usuario usuario);

    @GET("polizas/usuario/{idUsuario}")
    Call<List<Poliza>> obtenerPolizas(@Path("idUsuario") int idUsuario);

    @GET("conductores/poliza/{idPoliza}")
    Call<List<Conductor>> obtenerConductores(@Path("idPoliza") int idPoliza);

    @POST("conductores/poliza/{idPoliza}")
    Call<Conductor> crearConductor(@Path("idPoliza") int idPoliza, @Body Conductor conductor);

    @DELETE("conductores/{idConductor}")
    Call<Void> eliminarConductor(@Path("idConductor") int idConductor);

    @GET("recibos/usuario/{idUsuario}")
    Call<List<Recibo>> obtenerRecibosUsuario(@Path("idUsuario") int idUsuario);

    @POST("pagos/paypal/crear/{idRecibo}")
    Call<PagoPaypalRespuesta> crearPagoPaypal(@Path("idRecibo") int idRecibo);

    @POST("pagos/paypal/capturar")
    Call<Recibo> capturarPagoPaypal(@Body CapturaPagoRequest request);

    @GET("documentos/poliza/{idPoliza}")
    Call<List<DocumentoPoliza>> obtenerDocumentosPoliza(@Path("idPoliza") int idPoliza);
}