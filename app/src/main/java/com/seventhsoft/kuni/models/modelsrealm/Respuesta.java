package com.seventhsoft.kuni.models.modelsrealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by olibits on 22/08/17.
 */

public class Respuesta extends RealmObject {

    @PrimaryKey
    private Integer idRespuesta;

    private Integer idRespuestaRest;
    private String descripcion;
    private Integer orden;
    private Boolean correcta;
    private Boolean activo;

    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getCorrecta() {
        return correcta;
    }

    public void setCorrecta(Boolean correcta) {
        this.correcta = correcta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
