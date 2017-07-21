package com.seventhsoft.kuni.player;

import android.text.TextUtils;

/**
 * Created by olibits on 4/07/17.
 */

public class PlayerPresenterImpl implements PlayerPresenter {

    private PlayerView playerView;
    private PlayerInteractor playerInteractor;

    public PlayerPresenterImpl(PlayerView playerView) {
        this.playerView = playerView;
        this.playerInteractor = new PlayerInteractorImpl(this);

    }

    public void validateCredentials(String email, String password) {
        boolean error = false;
        if (playerView != null) {
            if (validateEmailFormat(email))
                error = true;
            if (password.isEmpty()) {
                error = true;
                playerView.setPasswordError();
            }
            if (!error) {
                playerInteractor.login(email, password);
            }
        }
    }

    public void validateEmail(String email) {
        if (validateEmailFormat(email)) {
            playerInteractor.sendEmail(email);
        }
    }

    private Boolean validateEmailFormat(String email) {
        Boolean error = false;
        if (playerView != null) {
            //view
            if (TextUtils.isEmpty(email)) {
                error = true;
                playerView.setEmailEmptyError();
            } else if (!email.contains("@")) {
                error = true;
                playerView.setEmailError();
            } else if (!email.contains(".")) {
                error = true;
                playerView.setEmailError();
            }
        }
        return error;
    }

    public void validateSignUp(String name, String firstName, String email, String password, Boolean facebook) {
        boolean error = false;
        if (playerView != null) {
            if (validateEmailFormat(email))
                error = true;
            if (name.isEmpty()) {
                error = true;
                playerView.setPasswordError();
            }
            if (firstName.isEmpty()) {
                error = true;
                playerView.setFirstNameError();
            }
            if (!error) {
                playerInteractor.signUp(name, firstName, email, password, facebook);
            }
        }
    }

    public void validatePassword(String contraseñaNueva, CharSequence contraseñaNuevaRepetida) {

        Boolean error = false;
        if (TextUtils.isEmpty(contraseñaNueva)) {
            error = true;
            playerView.setPasswordError();
        } else if (contraseñaNueva.length() < 8 || contraseñaNueva.length() > 15) {
            error = true;
            playerView.setPasswordError();
        } else if (!contraseñaNueva.equals(contraseñaNuevaRepetida.toString())) {
            error = true;
            playerView.setPasswordRepeatError();
        }
        if (!error) {
            playerView.setPasswordSuccess();
        }

    }


    public void onLoginFaiure() {
    }

    public void onSignUpFailure() {
    }

    public void onRecoverPasswordFailure() {
    }

    public void onFailure() {
        if (playerView != null) {
            playerView.setError();
        }
    }

    public void onLoginSuccess() {
    }

    public void onRecoverPasswordSuccess() {
    }

    public void onSignUpSuccesss() {
    }
}
