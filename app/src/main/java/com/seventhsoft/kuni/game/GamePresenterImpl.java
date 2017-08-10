package com.seventhsoft.kuni.game;

import android.content.Context;


/**
 * Created by olibits on 8/08/17.
 */

public class GamePresenterImpl implements GamePresenter {

    private MainView mainView;
    private GameInteractor gameInteractor;


    public GamePresenterImpl(MainView mainView, Context context) {
        this.mainView = mainView;
        this.gameInteractor = new GameInteractorImpl(this, context);

    }

    public void getDashboard(){
        gameInteractor.getDashboard();
    }

    public void setDashboard(){

    }

    public void onBindRepositoryRowViewAtPosition(int position, RecyclerViewAdapter.RepositoryViewHolder holder){}

    public void getRepositoriesRowsCount(){

    }
}
