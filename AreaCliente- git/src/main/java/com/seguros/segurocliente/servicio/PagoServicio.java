package com.seguros.segurocliente.servicio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seguros.segurocliente.modelo.Poliza;
import com.seguros.segurocliente.modelo.Recibo;
import com.seguros.segurocliente.repositorio.ReciboRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para gestionar pagos de recibos con PayPal.
 */
@Service
@RequiredArgsConstructor
public class PagoServicio {

    private final ReciboRepositorio reciboRepositorio;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${paypal.client-id}")
    private String paypalClientId;

    @Value("${paypal.client-secret}")
    private String paypalClientSecret;

    @Value("${paypal.base-url}")
    private String paypalBaseUrl;

    @Value("${paypal.return-url}")
    private String paypalReturnUrl;

    @Value("${paypal.cancel-url}")
    private String paypalCancelUrl;

    /**
     * Crea una orden en PayPal para pagar un recibo.
     * @param idRecibo id del recibo
     * @return datos de la orden creada
     */
    public Map<String, String> crearOrdenPaypal(Integer idRecibo) {
        Recibo recibo = obtenerReciboValidoParaPago(idRecibo);

        try {
            String token = obtenerAccessToken();
            String importe = formatearImporte(recibo.getImporte());

            String body = """
                    {
                      "intent": "CAPTURE",
                      "purchase_units": [
                        {
                          "reference_id": "RECIBO_%d",
                          "description": "Pago del recibo %d",
                          "amount": {
                            "currency_code": "EUR",
                            "value": "%s"
                          }
                        }
                      ],
                      "application_context": {
                        "brand_name": "Seguro Cliente",
                        "landing_page": "LOGIN",
                        "user_action": "PAY_NOW",
                        "return_url": "%s",
                        "cancel_url": "%s"
                      }
                    }
                    """.formatted(
                    recibo.getIdRecibo(),
                    recibo.getIdRecibo(),
                    importe,
                    paypalReturnUrl,
                    paypalCancelUrl
            );

            URL url = URI.create(paypalBaseUrl + "/v2/checkout/orders").toURL();
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Authorization", "Bearer " + token);

            try (OutputStream os = conexion.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }

            int codigo = conexion.getResponseCode();
            String respuesta = leerRespuesta(conexion, codigo);

            if (codigo != 201) {
                throw new RuntimeException("PayPal no creó la orden. Respuesta: " + respuesta);
            }

            JsonNode json = objectMapper.readTree(respuesta);

            String orderId = json.path("id").asText();
            String approveLink = "";

            for (JsonNode link : json.path("links")) {
                if ("approve".equalsIgnoreCase(link.path("rel").asText())) {
                    approveLink = link.path("href").asText();
                    break;
                }
            }

            Map<String, String> resultado = new HashMap<>();
            resultado.put("mensaje", "Orden PayPal creada correctamente");
            resultado.put("idRecibo", recibo.getIdRecibo().toString());
            resultado.put("orderId", orderId);
            resultado.put("approveLink", approveLink);

            return resultado;

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la orden PayPal: " + e.getMessage(), e);
        }
    }

    /**
     * Captura una orden de PayPal y marca el recibo como pagado.
     * @param idRecibo id del recibo
     * @param orderId id de la orden de PayPal
     * @return recibo actualizado
     */
    public Recibo capturarOrdenPaypal(Integer idRecibo, String orderId) {
        Recibo recibo = obtenerReciboValidoParaPago(idRecibo);

        try {
            String token = obtenerAccessToken();

            URL url = URI.create(paypalBaseUrl + "/v2/checkout/orders/" + orderId + "/capture").toURL();
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Authorization", "Bearer " + token);

            try (OutputStream os = conexion.getOutputStream()) {
                os.write("{}".getBytes(StandardCharsets.UTF_8));
            }

            int codigo = conexion.getResponseCode();
            String respuesta = leerRespuesta(conexion, codigo);

            if (codigo != 201) {
                throw new RuntimeException("PayPal no capturó la orden. Respuesta: " + respuesta);
            }

            JsonNode json = objectMapper.readTree(respuesta);
            String estadoPaypal = json.path("status").asText();

            if (!"COMPLETED".equalsIgnoreCase(estadoPaypal)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La orden de PayPal no se ha completado correctamente."
                );
            }

            recibo.setEstado("pagado");
            return reciboRepositorio.save(recibo);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al capturar la orden PayPal: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene el token de PayPal.
     * @return token de acceso
     */
    private String obtenerAccessToken() {
        try {
            String credenciales = paypalClientId + ":" + paypalClientSecret;
            String credencialesBase64 = Base64.getEncoder()
                    .encodeToString(credenciales.getBytes(StandardCharsets.UTF_8));

            URL url = URI.create(paypalBaseUrl + "/v1/oauth2/token").toURL();
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            conexion.setRequestProperty("Authorization", "Basic " + credencialesBase64);
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String body = "grant_type=client_credentials";

            try (OutputStream os = conexion.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }

            int codigo = conexion.getResponseCode();
            String respuesta = leerRespuesta(conexion, codigo);

            if (codigo != 200) {
                throw new RuntimeException("PayPal no devolvió token. Respuesta: " + respuesta);
            }

            JsonNode json = objectMapper.readTree(respuesta);
            return json.path("access_token").asText();

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener token PayPal: " + e.getMessage(), e);
        }
    }

    /**
     * Lee la respuesta de PayPal.
     * @param conexion conexión HTTP
     * @param codigo código de respuesta
     * @return respuesta en texto
     */
    private String leerRespuesta(HttpURLConnection conexion, int codigo) {
        try {
            if (codigo >= 200 && codigo < 300) {
                return new String(conexion.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            } else {
                return new String(conexion.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo respuesta de PayPal");
        }
    }

    /**
     * Comprueba que un recibo pueda pagarse.
     * @param idRecibo id del recibo
     * @return recibo si se puede pagar
     */
    private Recibo obtenerReciboValidoParaPago(Integer idRecibo) {
        Recibo recibo = reciboRepositorio.findById(idRecibo)
                .orElseThrow(() -> new RuntimeException("Recibo no encontrado"));

        Poliza poliza = recibo.getPoliza();

        if ("anulada".equalsIgnoreCase(poliza.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No se puede pagar un recibo de una póliza anulada. Contacte con nosotros en el teléfono 900 123 123."
            );
        }

        if (!"pendiente".equalsIgnoreCase(recibo.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El recibo no está pendiente de pago."
            );
        }

        return recibo;
    }

    /**
     * Da formato al importe para enviarlo a PayPal.
     * @param importe importe del recibo
     * @return importe con dos decimales
     */
    private String formatearImporte(Double importe) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setGroupingUsed(false);
        return decimalFormat.format(importe).replace(",", ".");
    }
}