package com.seventhsoft.kuni.game;

import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;

/**
 * Created by olibits on 29/07/17.
 */

public interface MainView {

    void onSesionClosed();

    void setDashboard(String fecha);

}
