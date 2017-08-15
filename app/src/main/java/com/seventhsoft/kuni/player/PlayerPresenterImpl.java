package com.seventhsoft.kuni.player;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.seventhsoft.kuni.game.MainView;
import com.seventhsoft.kuni.models.UserBean;

import static android.content.ContentValues.TAG;

/**
 * Created by olibits on 4/07/17.
 */

public class PlayerPresenterImpl implements PlayerPresenter {

    private PlayerView playerView;
    private PlayerInteractor playerInteractor;
    private MainView mainView;

    public PlayerPresenterImpl(PlayerView playerView, Context context) {
        this.playerView = playerView;
        this.playerInteractor = new PlayerInteractorImpl(this, context);

    }

    public PlayerPresenterImpl(MainView mainView, Context context) {
        this.playerInteractor = new PlayerInteractorImpl(this, context);
        this.mainView = mainView;
    }

    public void loginFacebook(String name, String email) {
        playerInteractor.signUp(name, "", email, "", true);
    }

    public void validateCredentials(String email, String password) {
        /*boolean error = false;
        if (playerView != null) {
            if (validateEmailFormat(email))
                error = true;
            if (password.isEmpty()) {
                error = true;
                playerView.setPasswordError();
            }
            if (!error) {*/
        playerInteractor.login(email, password);
        //}
        //}
    }

    public void validateEmail(String email) {
        if (!validateEmailFormat(email)) {
            playerInteractor.sendEmail(email);
            Log.i(TAG, "OSE|" + "correo vaido");

        } else {
            Log.i(TAG, "OSE|" + "correo no valido");

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

    public void validateSignUp(String name, String firstName, String email, String password, String passwordRepeat, Boolean facebook) {
        boolean error = false;
        if (playerView != null) {
            if (validateEmailFormat(email))
                error = true;
            if (name.isEmpty()) {
                error = true;
                playerView.setNameError();
            }
            if (firstName.isEmpty()) {
                error = true;
                playerView.setFirstNameError();
            }
            if (password.isEmpty()) {
                error = true;
                playerView.setPasswordError();
            }
            if (password.length() < 8 || password.length() > 15) {
                error = true;
                playerView.setPasswordFormatError();
            }
            if (!password.equals(passwordRepeat)) {
                error = true;
                playerView.setPasswordRepeatError();
            }
            if (!error) {
                playerInteractor.signUp(name, firstName, email, password, facebook);
            }
        }
    }

    public void validatePasswordUpdate(String password, String passwordNew, String passwordRepeat) {

        Boolean error = false;
        if (TextUtils.isEmpty(password)) {
            error = true;
            playerView.setPasswordError();
        } else if (passwordNew.length() < 4 || passwordNew.length() > 20) {
            error = true;
            playerView.setPasswordError();
        } else if (!passwordNew.equals(passwordRepeat.toString())) {
            error = true;
            playerView.setPasswordRepeatError();
        }
        if (!error) {
            playerInteractor.updatePlayerPassword(password,passwordNew);
        }

    }

    public void updatePlayerNames(UserBean userBean, String apellidos, String nombre){
        boolean error = false;
        if (playerView != null) {
            if (nombre.isEmpty()) {
                error = true;
                playerView.setNameError();
            }
            if (apellidos.isEmpty()) {
                error = true;
                playerView.setFirstNameError();
            }
            if (!error) {
                playerInteractor.updatePlayerName(userBean, apellidos, nombre);
            }
        }
    }


    public void onLoginFaiure() {
        if (playerView != null) {
            playerView.setLoginError();
        }
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
        if (playerView != null) {
            playerView.setLoginSuccess();
        }
    }

    public void onRecoverPasswordSuccess() {
    }

    public void onSignUpSuccesss() {
        if (playerView != null) {
            playerView.setSignUpSuccesss();
        }
    }

    public void closeSesion() {

        playerInteractor.closeSesion();
    }

    public void onSesionClosed() {
        if (mainView != null) {
            mainView.onSesionClosed();
        }
    }

    public void getPlayer() {
        playerInteractor.getPlayer();
    }

    public void onGetPlayer(UserBean userBean) {
        if (playerView != null) {
            playerView.setPlayer(userBean);
        }
    }

}
