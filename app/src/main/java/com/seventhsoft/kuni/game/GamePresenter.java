package com.seventhsoft.kuni.game;

import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.RespuestaBean;
import com.seventhsoft.kuni.models.modelsrealm.Pregunta;
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

    void evaluatePregunta(PreguntaBean pregunta, int position);

    void actualizarSerie();

    void setSuccessSerie();

    void setSuccessPregunta(PreguntaBean pregunta, RespuestaBean respuestaBean);

    void setClase(PreguntaBean pregunta, RespuestaBean respuestaBean, boolean nivel, boolean serie,
                  boolean premio, String desripcionPremio);

    void setFail();
}
