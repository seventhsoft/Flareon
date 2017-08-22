package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by olibits on 22/08/17.
 */

public class PreguntaRest {
    @SerializedName("idPregunta")
    @Expose
    private Integer idPregunta;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("ruta")
    @Expose
    private String ruta;
    @SerializedName("clase")
    @Expose
    private String clase;
    @SerializedName("activo")
    @Expose
    private Boolean activo;
    @SerializedName("respuestaList")
    @Expose
    private List<RespuestaRest> respuestaList = null;

    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<RespuestaRest> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<RespuestaRest> respuestaList) {
        this.respuestaList = respuestaList;
    }
}
