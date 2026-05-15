package com.example.areaclienteapp.modelo;

/**
 * Clase para guardar la respuesta al crear un pago con PayPal.
 */
public class PagoPaypalRespuesta {

    private String mensaje;
    private String idRecibo;
    private String orderId;
    private String approveLink;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(String idRecibo) {
        this.idRecibo = idRecibo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getApproveLink() {
        return approveLink;
    }

    public void setApproveLink(String approveLink) {
        this.approveLink = approveLink;
    }
}