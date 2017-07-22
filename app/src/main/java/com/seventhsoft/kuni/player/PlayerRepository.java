package com.seventhsoft.kuni.player;

/**
 * Created by olibits on 21/07/17.
 */

public interface PlayerRepository {

    void setUser(String name, String firstName, String email, String password, Boolean facebook);

    void getUser();
}
