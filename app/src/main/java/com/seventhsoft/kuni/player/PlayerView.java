package com.seventhsoft.kuni.player;

/**
 * Created by olibits on 4/07/17.
 */

public interface PlayerView {

    void setEmailEmptyError();

    void setEmailError();

    void setPasswordError();

    void setLoginError();

    void setSignUpError();

    void setRecoverPassword();

    void setError();

    void setNameError();

    void setFirstNameError();

    void setPasswordRepeatError();

    void setPasswordSuccess();

    void setLoginSuccess();

    void setRecoverPasswordSuccess();

    void setSignUpSuccesss();

}
