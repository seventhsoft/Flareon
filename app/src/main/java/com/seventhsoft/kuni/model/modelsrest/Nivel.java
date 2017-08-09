package com.seventhsoft.kuni.model.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 8/08/17.
 */

public class Nivel {
    @SerializedName("nivel")
    @Expose
    private Integer nivel;
    @SerializedName("series")
    @Expose
    private Integer series;
    @SerializedName("seriesJugador")
    @Expose
    private Integer seriesJugador;
    @SerializedName("tieneRecompensa")
    @Expose
    private Boolean tieneRecompensa;
    @SerializedName("recompensasDisponibles")
    @Expose
    private Integer recompensasDisponibles;

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getSeriesJugador() {
        return seriesJugador;
    }

    public void setSeriesJugador(Integer seriesJugador) {
        this.seriesJugador = seriesJugador;
    }

    public Boolean getTieneRecompensa() {
        return tieneRecompensa;
    }

    public void setTieneRecompensa(Boolean tieneRecompensa) {
        this.tieneRecompensa = tieneRecompensa;
    }

    public Integer getRecompensasDisponibles() {
        return recompensasDisponibles;
    }

    public void setRecompensasDisponibles(Integer recompensasDisponibles) {
        this.recompensasDisponibles = recompensasDisponibles;
    }
}
