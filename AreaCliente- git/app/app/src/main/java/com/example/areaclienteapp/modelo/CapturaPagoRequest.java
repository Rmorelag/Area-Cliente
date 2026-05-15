package com.example.areaclienteapp.modelo;

/**
 * Clase para enviar los datos necesarios al capturar un pago.
 */
public class CapturaPagoRequest {

    private Integer idRecibo;
    private String orderId;

    public CapturaPagoRequest(Integer idRecibo, String orderId) {
        this.idRecibo = idRecibo;
        this.orderId = orderId;
    }

    public Integer getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(Integer idRecibo) {
        this.idRecibo = idRecibo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}