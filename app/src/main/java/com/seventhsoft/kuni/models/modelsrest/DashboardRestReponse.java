package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by olibits on 8/08/17.
 */

public class DashboardRestReponse {
    @SerializedName("concurso")
    @Expose
    private Concurso concurso;
    @SerializedName("jugadorNivel")
    @Expose
    private JugadorNivel jugadorNivel;
    @SerializedName("niveles")
    @Expose
    private List<Nivel> niveles = null;

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }

    public JugadorNivel getJugadorNivel() {
        return jugadorNivel;
    }

    public void setJugadorNivel(JugadorNivel jugadorNivel) {
        this.jugadorNivel = jugadorNivel;
    }

    public List<Nivel> getNiveles() {
        return niveles;
    }

    public void setNiveles(List<Nivel> niveles) {
        this.niveles = niveles;
    }

}
