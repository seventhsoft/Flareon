package com.seventhsoft.kuni.game;

import android.content.Context;

import com.seventhsoft.kuni.player.PlayerRepository;

/**
 * Created by olibits on 8/08/17.
 */

public class GameInteractorImpl implements GameInteractor{

    private GamePresenter gamePresenter;
    private PlayerRepository playerRepository;


    public GameInteractorImpl(GamePresenter gamePresenter, Context context) {
        this.gamePresenter = gamePresenter;
    }

    public void getDashboard(){

    }
}
