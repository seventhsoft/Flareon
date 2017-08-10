package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 9/08/17.
 */

public class Concurso {

    @SerializedName("idConcurso")
    @Expose
    private Integer idConcurso;
    @SerializedName("fechaInicio")
    @Expose
    private long fechaInicio;
    @SerializedName("fechaFin")
    @Expose
    private long fechaFin;
    @SerializedName("activo")
    @Expose
    private Boolean activo;

    public Integer getIdConcurso() {
        return idConcurso;
    }

    public void setIdConcurso(Integer idConcurso) {
        this.idConcurso = idConcurso;
    }

    public long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Integer fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public long getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Integer fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
