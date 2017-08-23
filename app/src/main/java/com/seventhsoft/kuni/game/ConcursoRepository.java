package com.seventhsoft.kuni.game;

import android.util.Log;
import com.seventhsoft.kuni.models.ConcursoBean;
import com.seventhsoft.kuni.models.modelsrealm.Concurso;
import io.realm.Realm;
import static android.content.ContentValues.TAG;

/**
 * Created by olibits on 22/08/17.
 */


public class ConcursoRepository {

    private static Realm realm;

    public void saveConcurso(final ConcursoBean concursoBean) {
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Concurso concurso = realm.createObject(Concurso.class, setIdConcurso());

                    concurso.setFechaInicio(concursoBean.getFechaInicio());
                    concurso.setFechaFin(concursoBean.getFechaFin());
                    concurso.setActivo(concursoBean.isActivo());
                    concurso.setIdJugadorNivel(concursoBean.getIdJugadorNivel());
                    concurso.setSerieActual(concursoBean.getSerieActual());
                    concurso.setdNivel(concursoBean.getdNivel());



                    //Log.i(TAG, "OSE| Usuario Realm: " + user.getName() + " " + user.getEmail() + " " + user.getTokenAccess() + " " + user.getRefreshToken());
                }
            });
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error guardar usuario token " + e);

        }
    }

    public void updateConcurso() {

    }

    public void saveSerie() {
    }

    public void deleteSerie() {

    }

    public void getPregunta() {

    }

    private int setIdConcurso() {
        realm = Realm.getDefaultInstance();
        //RealmQuery<Usuario> query = realm.where(Usuario.class);
        int idConcurso = 0;
        if (realm.where(Concurso.class).max("idConcurso") != null) {
            idConcurso = realm.where(Concurso.class).max("idConcurso").intValue();
        }
        realm.close(); // Remember to close Realm when done.
        return idConcurso + 1;
    }


}
