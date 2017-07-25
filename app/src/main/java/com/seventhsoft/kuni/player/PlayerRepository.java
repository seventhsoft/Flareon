package com.seventhsoft.kuni.player;

import com.seventhsoft.kuni.model.UserBean;

/**
 * Created by olibits on 21/07/17.
 */

public interface PlayerRepository {

    void saveUser(UserBean userBean);

    void getUser();
}
