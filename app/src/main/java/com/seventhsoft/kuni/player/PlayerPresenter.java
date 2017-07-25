package com.seventhsoft.kuni.player;

/**
 * Created by olibits on 4/07/17.
 */

public interface PlayerPresenter {

    void validateCredentials(String email, String password);

    void validateEmail(String email);

    void validateSignUp(String name, String firstName, String email, String password, String passwordRepeat, Boolean facebook);

    void loginFacebook(String name, String userName);

    void validatePassword(String password, CharSequence passwordRepeat);

    void onLoginFaiure();

    void onSignUpFailure();

    void onRecoverPasswordFailure();

    void onFailure();

    void onLoginSuccess();

    void onRecoverPasswordSuccess();

    void onSignUpSuccesss();
}
