package com.seventhsoft.kuni.player;

/**
 * Created by olibits on 4/07/17.
 */

public interface PlayerInteractor {

    void login(String email, String password);

    void sendEmail(String email);

    void signUp(String name, String firstName, String email, String password);
}
