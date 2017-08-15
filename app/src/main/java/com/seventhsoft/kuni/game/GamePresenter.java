package com.seventhsoft.kuni.game;

import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;

/**
 * Created by olibits on 8/08/17.
 */

public interface GamePresenter {

    void getDashboard();

    void setDashboard(DashboardRestReponse dashboardResponse, String fecha);

    void onBindRepositoryRowViewAtPosition(int position,RepositoryRowView repositoryRowView);

}
