package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 24/08/17.
 */

public class RespuestaPreguntaRestResponse {
    @SerializedName("jugadorNivel")
    @Expose
    private JugadorNivel jugadorNivel;
    @SerializedName("recompensa")
    @Expose
    private RecompensaRest recompensa;

    public JugadorNivel getJugadorNivel() {
        return jugadorNivel;
    }

    public void setJugadorNivel(JugadorNivel jugadorNivel) {
        this.jugadorNivel = jugadorNivel;
    }

    public RecompensaRest getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(RecompensaRest recompensa) {
        this.recompensa = recompensa;
    }
}
