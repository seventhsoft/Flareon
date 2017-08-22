package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by olibits on 22/08/17.
 */

public class SerieRestReponse {
    @SerializedName("tiempoPregunta")
    @Expose
    private Integer tiempoPregunta;
    @SerializedName("bannerSerie")
    @Expose
    private Object bannerSerie;
    @SerializedName("preguntas")
    @Expose
    private List<PreguntaRest> preguntas = null;

    public Integer getTiempoPregunta() {
        return tiempoPregunta;
    }

    public void setTiempoPregunta(Integer tiempoPregunta) {
        this.tiempoPregunta = tiempoPregunta;
    }

    public Object getBannerSerie() {
        return bannerSerie;
    }

    public void setBannerSerie(Object bannerSerie) {
        this.bannerSerie = bannerSerie;
    }

    public List<PreguntaRest> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaRest> preguntas) {
        this.preguntas = preguntas;
    }

}
