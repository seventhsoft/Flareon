package com.seventhsoft.kuni.game;

import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.RespuestaBean;
import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;

/**
 * Created by olibits on 8/08/17.
 */

public interface GamePresenter {

    void getDashboard();

    void setDashboard(DashboardRestReponse dashboardResponse, String fecha);

    void onBindRepositoryRowViewAtPosition(int position, RepositoryRowView repositoryRowView);

    void getPregunta();

    void setPreguntaView(int position);


    void setPregunta(PreguntaBean pregunta);

    void evaluatePregunta(RespuestaBean respuesta, int position);

    void actualizarSerie();

    void setSuccessSerie();
    void setSuccessPregunta();


    void setFail();
}
