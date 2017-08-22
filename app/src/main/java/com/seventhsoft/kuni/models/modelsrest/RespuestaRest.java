package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 22/08/17.
 */

public class RespuestaRest {

    @SerializedName("idRespuesta")
    @Expose
    private Integer idRespuesta;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("orden")
    @Expose
    private Integer orden;
    @SerializedName("correcta")
    @Expose
    private Boolean correcta;
    @SerializedName("activo")
    @Expose
    private Boolean activo;

    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getCorrecta() {
        return correcta;
    }

    public void setCorrecta(Boolean correcta) {
        this.correcta = correcta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
