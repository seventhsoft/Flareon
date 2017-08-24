package com.seventhsoft.kuni.models.modelsrealm;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by olibits on 12/08/17.
 */

public class Pregunta extends RealmObject{

    @PrimaryKey
    private int idPregunta;

    private String descripcion;
    private String ruta;
    private String clase;
    private Boolean activo;
    public RealmList<Respuesta> respuestaList = null;

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
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

    public RealmList<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(RealmList<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
    }
}
