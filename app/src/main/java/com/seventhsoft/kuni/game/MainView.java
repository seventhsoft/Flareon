package com.seventhsoft.kuni.game;

import com.seventhsoft.kuni.models.PreguntaBean;

/**
 * Created by olibits on 29/07/17.
 */

public interface MainView {

    void onSesionClosed();

    void setDashboard(long fin, long inicio);

    void setFragmentPregunta(int position);

    void onNoConnection();

}
