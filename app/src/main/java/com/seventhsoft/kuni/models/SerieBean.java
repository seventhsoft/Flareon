package com.seventhsoft.kuni.models;

import com.seventhsoft.kuni.models.modelsrealm.Pregunta;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by olibits on 23/08/17.
 */

public class SerieBean {

    private int idSerie;

    private Integer tiempoPregunta;
    private String bannerSerie;
    private List<PreguntaBean> preguntas = null;

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public Integer getTiempoPregunta() {
        return tiempoPregunta;
    }

    public void setTiempoPregunta(Integer tiempoPregunta) {
        this.tiempoPregunta = tiempoPregunta;
    }

    public String getBannerSerie() {
        return bannerSerie;
    }

    public void setBannerSerie(String bannerSerie) {
        this.bannerSerie = bannerSerie;
    }

    public List<PreguntaBean> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaBean> preguntas) {
        this.preguntas = preguntas;
    }
}
