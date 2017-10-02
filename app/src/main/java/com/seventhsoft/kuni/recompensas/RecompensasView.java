package com.seventhsoft.kuni.recompensas;

import com.seventhsoft.kuni.models.modelsrest.RecompensasJugadorRestResponse;

/**
 * Created by olibits on 7/09/17.
 */

public interface RecompensasView {

    void onRecompensasJugadorSuccess();

    void onRecompensasConcursoSuccess();

    void onRecompensaSuccess(RecompensasJugadorRestResponse recompensasJugadorRestResponse);

    void onRecompensasEmpty();
}
