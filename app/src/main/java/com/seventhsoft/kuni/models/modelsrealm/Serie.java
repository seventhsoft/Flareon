package com.seventhsoft.kuni.models.modelsrealm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.seventhsoft.kuni.models.modelsrest.PreguntaRest;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by olibits on 12/08/17.
 */

public class Serie extends RealmObject {

    @PrimaryKey
    private int idSerie;

    private Integer tiempoPregunta;
    //private String bannerSerie;
    public RealmList<Pregunta> preguntas = null;
    private int contador;


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

    /*public String getBannerSerie() {
        return bannerSerie;
    }

    public void setBannerSerie(String bannerSerie) {
        this.bannerSerie = bannerSerie;
    }
*/
    public RealmList<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(RealmList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
}
