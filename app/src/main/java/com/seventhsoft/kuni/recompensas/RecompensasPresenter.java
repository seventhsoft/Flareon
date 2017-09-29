package com.seventhsoft.kuni.recompensas;

import com.seventhsoft.kuni.models.modelsrest.RecompensasJugadorRestResponse;

import java.util.List;

/**
 * Created by olibits on 6/09/17.
 */

public interface RecompensasPresenter {

    void onBindRepositoryRowViewAtPosition(int position, ListRowView listRowView);

    void onBindRepositoryRowViewConcurso(int position, ListRowView listRowView);

    void getRecompensasJugador();

    void setRecompensasJugador(List<RecompensasJugadorRestResponse> recompensasJugador);

    void getRecompensasConcurso();

    void setRecompensasConcurso(List<RecompensasJugadorRestResponse> recompensasConcurso);

    int setSizeJugador();

    int setSizeConcurso();

    void getRecompensa(int position);
}
