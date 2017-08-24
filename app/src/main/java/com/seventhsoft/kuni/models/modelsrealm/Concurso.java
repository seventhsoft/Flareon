package com.seventhsoft.kuni.models.modelsrealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by olibits on 22/08/17.
 */

public class Concurso extends RealmObject {

    @PrimaryKey
    private int idConcurso;

    private int idConcursoRest;
    private long fechaInicio;
    private long fechaFin;
    private boolean activo;
    private int idJugadorNivel;
    private int serieActual;
    private int dNivel;

    public int getIdConcurso() {
        return idConcurso;
    }

    public void setIdConcurso(int idConcurso) {
        this.idConcurso = idConcurso;
    }

    public int getIdConcursoRest() {
        return idConcursoRest;
    }

    public void setIdConcursoRest(int idConcursoRest) {
        this.idConcursoRest = idConcursoRest;
    }

    public long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public long getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(long fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getIdJugadorNivel() {
        return idJugadorNivel;
    }

    public void setIdJugadorNivel(int idJugadorNivel) {
        this.idJugadorNivel = idJugadorNivel;
    }

    public int getSerieActual() {
        return serieActual;
    }

    public void setSerieActual(int serieActual) {
        this.serieActual = serieActual;
    }

    public int getdNivel() {
        return dNivel;
    }

    public void setdNivel(int dNivel) {
        this.dNivel = dNivel;
    }
}
