package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 23/08/17.
 */

public class RespuestaPreguntaRequest {
    @SerializedName("idJugadorNivel")
    @Expose
    private Integer idJugadorNivel;
    @SerializedName("idRespuesta")
    @Expose
    private Integer idRespuesta;
    @SerializedName("serie")
    @Expose
    private Integer serie;
    @SerializedName("perfecta")
    @Expose
    private Integer perfecta;
    @SerializedName("idConcurso")
    @Expose
    private Integer idConcurso;

    public Integer getIdJugadorNivel() {
        return idJugadorNivel;
    }

    public void setIdJugadorNivel(Integer idJugadorNivel) {
        this.idJugadorNivel = idJugadorNivel;
    }

    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public Integer getPerfecta() {
        return perfecta;
    }

    public void setPerfecta(Integer perfecta) {
        this.perfecta = perfecta;
    }

    public Integer getIdConcurso() {
        return idConcurso;
    }

    public void setIdConcurso(Integer idConcurso) {
        this.idConcurso = idConcurso;
    }
}
