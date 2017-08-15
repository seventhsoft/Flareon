package com.seventhsoft.kuni.game;

import android.content.Context;

import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;


/**
 * Created by olibits on 8/08/17.
 */

public class GamePresenterImpl implements GamePresenter {

    private MainView mainView;
    private GameInteractor gameInteractor;
    private RecyclerViewAdapter.RepositoryViewHolder holder;
    private DashboardRestReponse dashboardRestReponse;


    public GamePresenterImpl(MainView mainView, Context context) {
        this.mainView = mainView;
        this.gameInteractor = new GameInteractorImpl(this, context);

    }

    public void getDashboard(){
        gameInteractor.getDashboard();
    }

    public void setDashboard(){

    }
    public void setDashboard(DashboardRestReponse dashboardResponse, String fecha){
        this.dashboardRestReponse = dashboardResponse;
        mainView.setDashboard(fecha);
    }

    public void onBindRepositoryRowViewAtPosition(int position, RepositoryRowView rowView){
        rowView.setTitle("Nivel " + dashboardRestReponse.getNiveles().get(position).getNivel());
        //gameInteractor.getDashboard();
    }



}
