package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 8/08/17.
 */

public class JugadorNivel {
    @SerializedName("dNivel")
    @Expose
    private Integer nivel;
    @SerializedName("serieActual")
    @Expose
    private Integer serieActual;
    @SerializedName("idJugadorNivel")
    @Expose
    private Integer idJugadorNivel;

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getSerieActual() {
        return serieActual;
    }

    public void setSerieActual(Integer serieActual) {
        this.serieActual = serieActual;
    }

    public Integer getIdJugadorNivel() {
        return idJugadorNivel;
    }

    public void setIdJugadorNivel(Integer idJugadorNivel) {
        this.idJugadorNivel = idJugadorNivel;
    }

}
