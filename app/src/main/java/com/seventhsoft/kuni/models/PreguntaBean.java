package com.seventhsoft.kuni.models;

import com.seventhsoft.kuni.models.modelsrealm.Respuesta;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by olibits on 23/08/17.
 */

public class PreguntaBean {

    private int idPregunta;

    private String descripcion;
    private String ruta;
    private String clase;
    private Boolean activo;
    private List<RespuestaBean> respuestaList = null;


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

    public List<RespuestaBean> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<RespuestaBean> respuestaList) {
        this.respuestaList = respuestaList;
    }
}
