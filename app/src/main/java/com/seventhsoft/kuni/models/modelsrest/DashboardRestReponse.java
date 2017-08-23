package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by olibits on 8/08/17.
 */

public class DashboardRestReponse {
    @SerializedName("concursoRest")
    @Expose
    private ConcursoRest concursoRest;
    @SerializedName("jugadorNivel")
    @Expose
    private JugadorNivel jugadorNivel;
    @SerializedName("niveles")
    @Expose
    private List<NivelRest> niveles = null;

    public ConcursoRest getConcursoRest() {
        return concursoRest;
    }

    public void setConcursoRest(ConcursoRest concursoRest) {
        this.concursoRest = concursoRest;
    }

    public JugadorNivel getJugadorNivel() {
        return jugadorNivel;
    }

    public void setJugadorNivel(JugadorNivel jugadorNivel) {
        this.jugadorNivel = jugadorNivel;
    }

    public List<NivelRest> getNiveles() {
        return niveles;
    }

    public void setNiveles(List<NivelRest> niveles) {
        this.niveles = niveles;
    }

}
