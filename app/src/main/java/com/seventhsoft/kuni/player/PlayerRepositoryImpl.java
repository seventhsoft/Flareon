package com.seventhsoft.kuni.player;

import android.util.Log;

import com.seventhsoft.kuni.model.UserBean;
import com.seventhsoft.kuni.model.modelsrealm.Usuario;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

import static android.content.ContentValues.TAG;

/**
 * Created by olibits on 21/07/17.
 */

public class PlayerRepositoryImpl implements PlayerRepository {

    private static Realm realm;
    private PlayerInteractor playerInteractor;

    public PlayerRepositoryImpl(PlayerInteractor playerInteractor) {
        this.playerInteractor = playerInteractor;


    }

    public void saveUser(final UserBean userBean) {
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Usuario user = realm.createObject(Usuario.class, setIdUser());
                    user.setEmail(userBean.getEmail());
                    user.setPassword(userBean.getPassword());
                    user.setFirstName(userBean.getFirstName());
                    user.setName(userBean.getName());
                    user.setTime(userBean.getTime());
                    user.setFacebook(userBean.getFacebook());
                    user.setTokenAccess(userBean.getTokenAccess());
                    user.setRefreshToken(userBean.getRefreshToken());
                    Log.i(TAG, "OSE| Usuario Realm: " + user.getName() +" " + user.getEmail()+" " + user.getTokenAccess() + " " +user.getRefreshToken());
                }
            });
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error guardar usuario token " + e);

        }
    }

    /**
     * Gets the last id of the table
     *
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

    public String obtenerIdToken() {
        String idToken = "";
        try {
            realm = Realm.getDefaultInstance();
            if (realm.where(Usuario.class).findFirst() == null) {
                idToken = "";
            } else {
                idToken = realm.where(Usuario.class).findFirst().getTokenAccess();
            }
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error obtener usuario token " + e);
        }
        return idToken;

    }

    public void getUser() {

    }


    public String getToken() {

        String idToken = "";
        try {
            realm = Realm.getDefaultInstance();
            if (realm.where(Usuario.class).findFirst() == null) {
                idToken = "";
            } else {
                idToken = realm.where(Usuario.class).findFirst().getTokenAccess();
            }
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error obtener usuario token " + e);
        }
        return idToken;
    }

    public String getRefreshToken() {
        String idToken = "";
        try {
            realm = Realm.getDefaultInstance();
            if (realm.where(Usuario.class).findFirst() == null) {
                idToken = "";
            } else {
                idToken = realm.where(Usuario.class).findFirst().getRefreshToken();
            }
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error obtener usuario refresh token " + e);
        }
        return idToken;
    }


    public void updateToken(final String accessToken, final String refreshToken){
        try {
            realm = Realm.getDefaultInstance();
            final Usuario geoLimite = realm.where(Usuario.class).findFirst();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    geoLimite.setTokenAccess(accessToken);
                    geoLimite.setRefreshToken(refreshToken);
                }
            });
            realm.close();

        }
        catch (RealmException exception){
            Log.e(TAG, "OSE| " + exception);
        }

    }
    public void deletePlayers() {
        try {
            realm = Realm.getDefaultInstance();
            final RealmResults<Usuario> results = realm.where(Usuario.class).findAll();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.deleteAllFromRealm();
                    //realm.delete(Usuario.class);
                }
            });
            realm.close(); // Remember to close Realm when done.

        } catch (Exception e) {
            Log.e(TAG, "OSE | Error realm" + e);
        }
    }

}
