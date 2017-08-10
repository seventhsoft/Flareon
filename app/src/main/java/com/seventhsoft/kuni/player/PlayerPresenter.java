package com.seventhsoft.kuni.player;

import com.seventhsoft.kuni.models.UserBean;

/**
 * Created by olibits on 4/07/17.
 */

public interface PlayerPresenter {

    void validateCredentials(String email, String password);

    void validateEmail(String email);

    void validateSignUp(String name, String firstName, String email, String password, String passwordRepeat, Boolean facebook);

    void loginFacebook(String name, String userName);

    void validatePasswordUpdate(String password, String nueva, String passwordRepeat);

    void updatePlayerNames(UserBean userBean, String apellidos, String nombre);

    void onLoginFaiure();

    void onSignUpFailure();

    void onRecoverPasswordFailure();

    void onFailure();

    void onLoginSuccess();

    void onRecoverPasswordSuccess();

    void onSignUpSuccesss();

    void closeSesion();

    void onSesionClosed();

    void getPlayer();

    void onGetPlayer(UserBean userBean);
}
