package com.seventhsoft.kuni.game;

import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;

/**
 * Created by olibits on 8/08/17.
 */

public interface GameInteractor {

    void getDashboard();

    void getSerie(DashboardRestReponse dashboardResponse);

    void getPregunta();

    void evaluarPregunta(int idRespuesta, final boolean correcta);
}
