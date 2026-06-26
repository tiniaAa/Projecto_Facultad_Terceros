package com.utn.prog3.tp_Final.Exception;

import java.util.Date;

public class ErrorDTO {
    
    private String mensaje;
    private int estado;
    private Date fecha;

    // Constructor vacío
    public ErrorDTO() {}

    // Constructor con parámetros para llenarlo rápido
    public ErrorDTO(String mensaje, int estado, Date fecha) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}