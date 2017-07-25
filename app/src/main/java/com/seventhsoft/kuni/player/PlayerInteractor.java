package com.seventhsoft.kuni.player;

import com.seventhsoft.kuni.model.UserBean;

/**
 * Created by olibits on 4/07/17.
 */

public interface PlayerInteractor {

    /**
     * Presenter to interactor
     * @param email
     * @param password
     */

    void login(String email, String password);

    void sendEmail(String email);

    void signUp(String name, String firstName, String email, String password, Boolean facebook);

    /**
     * Repository to iteractor
     */

    void onSaveSuccess();

    void onSaveError();

    void onGetError();

    void setUser(UserBean userBean);
}
