package com.seventhsoft.kuni.player;

/**
 * Created by olibits on 4/07/17.
 */

public class PlayerInteractorImpl implements PlayerInteractor {

    private PlayerPresenter playerPresenter;

    public PlayerInteractorImpl(PlayerPresenter playerPresenter) {
        this.playerPresenter = playerPresenter;
    }

    public void login(String email, String password) {

    }

    public void sendEmail(String email) {
    }

    public void signUp(String name, String firstName, String email, String password){

    }

}
