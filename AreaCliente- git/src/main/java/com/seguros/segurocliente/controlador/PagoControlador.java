package com.seguros.segurocliente.controlador;

import com.seguros.segurocliente.modelo.Recibo;
import com.seguros.segurocliente.servicio.PagoServicio;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador para los pagos con PayPal.
 */
@RestController
@RequestMapping("/pagos/paypal")
@RequiredArgsConstructor
public class PagoControlador {

    private final PagoServicio pagoServicio;

    /**
     * Crea una orden de pago en PayPal.
     * @param idRecibo id del recibo que se quiere pagar
     * @return datos de la orden creada
     */
    @PostMapping("/crear/{idRecibo}")
    public Map<String, String> crearOrdenPaypal(@PathVariable Integer idRecibo) {
        return pagoServicio.crearOrdenPaypal(idRecibo);
    }

    /**
     * Captura una orden de PayPal ya aprobada.
     * @param request datos de la captura
     * @return recibo actualizado
     */
    @PostMapping("/capturar")
    public Recibo capturarOrdenPaypal(@RequestBody CapturaPagoRequest request) {
        return pagoServicio.capturarOrdenPaypal(request.getIdRecibo(), request.getOrderId());
    }

    /**
     * Página sencilla para cuando PayPal aprueba el pago.
     * @return mensaje de pago aprobado
     */
    @GetMapping("/exito")
    public String exitoPago() {
        return "Pago aprobado en PayPal. Más adelante Flutter volverá aquí.";
    }

    /**
     * Página sencilla para cuando el usuario cancela el pago.
     * @return mensaje de pago cancelado
     */
    @GetMapping("/cancelado")
    public String pagoCancelado() {
        return "El usuario ha cancelado el pago en PayPal.";
    }

    /**
     * Clase para recibir los datos necesarios para capturar un pago.
     */
    @Data
    public static class CapturaPagoRequest {

        private Integer idRecibo;

        private String orderId;
    }
}