package com.seventhsoft.kuni.player;

import com.seventhsoft.kuni.model.UserBean;
import com.seventhsoft.kuni.model.modelsrealm.Usuario;

import io.realm.Realm;

/**
 * Created by olibits on 21/07/17.
 */

public class PlayerRepositoryImpl implements  PlayerRepository{

    private static Realm realm;

    public void saveUser(final UserBean userBean){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                Usuario user = bgRealm.createObject(Usuario.class);

                user.setIdUser(setIdUser());
                user.setEmail(userBean.getEmail());
                user.setPassword(userBean.getPassword());
                user.setFirstName(userBean.getFirstName());
                user.setName(userBean.getName());
                user.setTime(userBean.getTime());
                user.setFacebook(userBean.getFacebook());

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

    /**
     * Gets the last id of the table
     * @return id for an user
     */
    private int setIdUser() {
        realm = Realm.getDefaultInstance();
        //RealmQuery<Usuario> query = realm.where(Usuario.class);
        int idUser;
        if (realm.where(Usuario.class).max("idUser") == null) {
            idUser = 0;
        } else {
            idUser = realm.where(Usuario.class).max("idUser").intValue();
        }
        realm.close(); // Remember to close Realm when done.
        return idUser + 1;
    }


    public void getUser(){}




}
