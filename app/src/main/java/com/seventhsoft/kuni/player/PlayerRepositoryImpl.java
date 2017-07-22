package com.seventhsoft.kuni.player;

import com.seventhsoft.kuni.model.modelsrealm.Usuario;

import io.realm.Realm;

/**
 * Created by olibits on 21/07/17.
 */

public class PlayerRepositoryImpl implements  PlayerRepository{
    private static Realm realm;

    public void setUser(String name, String firstName, String email, String password, Boolean facebook){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Usuario user = bgRealm.createObject(Usuario.class);
                user.setIdUser(1);
                user.setEmail("john@corporation.com");
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
            }
        });

    }
    public void getUser(){}


}
