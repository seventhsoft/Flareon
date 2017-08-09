package com.seventhsoft.kuni.game;

import android.content.Context;

import com.seventhsoft.kuni.player.PlayerInteractor;
import com.seventhsoft.kuni.player.PlayerInteractorImpl;
import com.seventhsoft.kuni.player.PlayerView;

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

    public void getDashboard(){}

    public void setDashboard(){}
}
