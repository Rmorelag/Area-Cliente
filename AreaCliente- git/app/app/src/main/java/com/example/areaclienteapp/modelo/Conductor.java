package com.example.areaclienteapp.modelo;

/**
 * Clase para guardar los datos de un conductor.
 */
public class Conductor {

    private Integer idConductor;
    private String nombre;
    private String apellidos;
    private String dni;
    private String fechaNacimiento;
    private String fechaCarnet;
    private String tipoConductor;

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaCarnet() {
        return fechaCarnet;
    }

    public void setFechaCarnet(String fechaCarnet) {
        this.fechaCarnet = fechaCarnet;
    }

    public String getTipoConductor() {
        return tipoConductor;
    }

    public void setTipoConductor(String tipoConductor) {
        this.tipoConductor = tipoConductor;
    }
}