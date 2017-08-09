package com.seventhsoft.kuni.player;

import com.seventhsoft.kuni.model.UserBean;

/**
 * Created by olibits on 4/07/17.
 */

public interface PlayerView {

    void setEmailEmptyError();

    void setEmailError();

    void setPasswordError();

    void setPasswordFormatError();

    void setPasswordRepeatError();

    void setLoginError();

    void setSignUpError();

    void setRecoverPassword();

    void setError();

    void setNameError();

    void setFirstNameError();

    void setPasswordSuccess();

    void setLoginSuccess();

    void setRecoverPasswordSuccess();

    void setSignUpSuccesss();

    void setPlayer(UserBean userBean);

}
