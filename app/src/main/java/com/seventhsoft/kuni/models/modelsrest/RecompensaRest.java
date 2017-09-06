package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 24/08/17.
 */

public class RecompensaRest {

    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("cantidad")
    @Expose
    private Integer cantidad;
    @SerializedName("activo")
    @Expose
    private Boolean activo;
    @SerializedName("redimido")
    @Expose
    private Boolean redimido;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getRedimido() {
        return redimido;
    }

    public void setRedimido(Boolean redimido) {
        this.redimido = redimido;
    }
}
