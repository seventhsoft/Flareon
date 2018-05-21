package com.seventhsoft.kuni.game;

import android.util.Log;

import com.seventhsoft.kuni.models.ConcursoBean;
import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.RespuestaBean;
import com.seventhsoft.kuni.models.SerieBean;
import com.seventhsoft.kuni.models.modelsrealm.Concurso;
import com.seventhsoft.kuni.models.modelsrealm.Pregunta;
import com.seventhsoft.kuni.models.modelsrealm.Respuesta;
import com.seventhsoft.kuni.models.modelsrealm.Serie;
import com.seventhsoft.kuni.models.modelsrest.RespuestaPreguntaRequest;
import com.seventhsoft.kuni.models.modelsrest.RespuestaPreguntaRestResponse;
import com.seventhsoft.kuni.models.modelsrest.RespuestaRest;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by olibits on 22/08/17.
 */


public class ConcursoRepository {

    private static Realm realm;
    private int contador = 0;


    public void saveConcurso(final ConcursoBean concursoBean) {
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Concurso concurso = realm.createObject(Concurso.class, setIdConcurso());
                    concurso.setIdConcursoRest(concursoBean.getIdConsurso());
                    concurso.setFechaInicio(concursoBean.getFechaInicio());
                    concurso.setFechaFin(concursoBean.getFechaFin());
                    concurso.setActivo(concursoBean.isActivo());
                    concurso.setIdJugadorNivel(concursoBean.getIdJugadorNivel());
                    concurso.setSerieActual(concursoBean.getSerieActual());
                    concurso.setdNivel(concursoBean.getdNivel());
                    Log.e(TAG, "OSE| Save concurso idConcurso: " + concurso.getIdConcursoRest() + "idJugadorNivel: " + concurso.getIdJugadorNivel() + " serie actual: " + concurso.getSerieActual() + " nivelActual: " + concurso.getdNivel());

                    //Log.i(TAG, "OSE| Usuario Realm: " + user.getName() + " " + user.getEmail() + " " + user.getTokenAccess() + " " + user.getRefreshToken());
                }
            });
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error guardar concurso" + e);

        }
    }

    public boolean isConcurso() {
        boolean existe = false;
        try {
            realm = Realm.getDefaultInstance();
            Concurso concurso = realm.where(Concurso.class)
                    .equalTo("idConcurso", setIdConcurso() - 1).findFirst();
            if (concurso != null) {
                existe = true;
            }
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error traer pregunta " + e);
            existe = false;

        }
        return existe;
    }

    public boolean isSerie() {
        boolean existe = false;
        try {
            realm = Realm.getDefaultInstance();
            Serie serie = realm.where(Serie.class)
                    .equalTo("idSerie", setIdSerie() - 1).findFirst();
            if (serie != null) {
                existe = true;
            }
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error traer pregunta " + e);
            existe = false;

        }
        return existe;
    }

    public int getIdJugadorNivel() {
        int idJugadorNivel = 0;
        try {
            realm = Realm.getDefaultInstance();

            Concurso concurso = realm.where(Concurso.class)
                    .equalTo("idConcurso", setIdConcurso() - 1).findFirst();
            idJugadorNivel = concurso.getIdJugadorNivel();
            Log.i(TAG, "OSE| get id jugador: " + concurso.getIdJugadorNivel());

            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error traer pregunta " + e);
        }
        return idJugadorNivel;


    }

    public void updateConcurso(final RespuestaPreguntaRestResponse response) {
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Concurso concurso = realm.where(Concurso.class).
                            equalTo("idConcurso", setIdConcurso() - 1).findFirst();
                    concurso.setIdJugadorNivel(response.getJugadorNivel().getIdJugadorNivel());
                    concurso.setSerieActual(response.getJugadorNivel().getSerieActual());
                    concurso.setdNivel(response.getJugadorNivel().getNivel());

                    Log.i(TAG, "OSE| Actualizar concurso: " + concurso.getIdJugadorNivel() + " serie acrual: " + concurso.getSerieActual() + " nivel actual: " + concurso.getdNivel());
                }
            });
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error guardar concurso" + e);

        }

    }

    public void saveSerie(final SerieBean serieBean) {
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Serie serie = realm.createObject(Serie.class, setIdSerie());
                    //serie.setBannerSerie(serieBean.getBannerSerie());
                    serie.setTiempoPregunta(serieBean.getTiempoPregunta());
                    serie.setContador(0);
                    for (int i = 0; i < serieBean.getPreguntas().size(); i++) {
                        Pregunta pregunta = realm.createObject(Pregunta.class, setIdPregunta());

                        pregunta.setActivo(serieBean.getPreguntas().get(i).getActivo());
                        pregunta.setDescripcion(serieBean.getPreguntas().get(i).getDescripcion());
                        pregunta.setRuta(serieBean.getPreguntas().get(i).getRuta());
                        pregunta.setClase(serieBean.getPreguntas().get(i).getClase());
                        List<RespuestaBean> respuestasBean = serieBean.getPreguntas().get(i).getRespuestaList();

                        for (int j = 0; j < respuestasBean.size(); j++) {
                            Respuesta respuesta = realm.createObject(Respuesta.class, setIdRespuesta());
                            //Log.i(TAG, "OSE| respuesta id " + respuestasBean.get(j).getIdRespuesta());

                            respuesta.setIdRespuestaRest(respuestasBean.get(j).getIdRespuesta());
                            respuesta.setActivo(respuestasBean.get(j).getActivo());
                            respuesta.setDescripcion(respuestasBean.get(j).getDescripcion());
                            respuesta.setCorrecta(respuestasBean.get(j).getCorrecta());
                            respuesta.setOrden(respuestasBean.get(j).getOrden());
                            pregunta.respuestaList.add(j, respuesta);
                        }

                        serie.preguntas.add(i, pregunta);
                        Log.e(TAG, "OSE| Se guardo la serie " + i);


                    }
                }
            });
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error guardar serie " + e);

        }
    }

    public void deleteSerie() {
        try {
            realm = Realm.getDefaultInstance();
            final RealmResults<Serie> results = realm.where(Serie.class).findAll();
            Log.i(TAG, "OSE | size series" + results.size());

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
    public void deleteConcurso() {
        try {
            realm = Realm.getDefaultInstance();
            final RealmResults<Concurso> results = realm.where(Concurso.class).findAll();
            Log.i(TAG, "OSE | size concurso" + results.size());

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

    public PreguntaBean getPregunta() {
        final PreguntaBean preguntaBean = new PreguntaBean();
        try {
            realm = Realm.getDefaultInstance();
            final Serie serie = realm.where(Serie.class)
                    .equalTo("idSerie", setIdSerie() - 1).findFirst();
            Log.i(TAG, "OSE| id serie" + serie.getIdSerie());

            if (serie.getContador() < 6) {
                Pregunta pregunta = serie.getPreguntas().get(serie.getContador());
                contador = serie.getContador();
                Log.i(TAG, "OSE| contador serie" + contador);
                Concurso concurso = realm.where(Concurso.class)
                        .equalTo("idConcurso", setIdConcurso() - 1).findFirst();
                preguntaBean.setTiempo(serie.getTiempoPregunta());
                preguntaBean.setNivelActual(concurso.getdNivel());
                preguntaBean.setSerieActual(concurso.getSerieActual());
                preguntaBean.setNumeroPregunta(serie.getContador());
                preguntaBean.setClase(pregunta.getClase());
                preguntaBean.setDescripcion(pregunta.getDescripcion());
                preguntaBean.setRuta(pregunta.getRuta());
                RealmList<Respuesta> respuestas = pregunta.getRespuestaList();
                List<RespuestaBean> respuestasBeen = new ArrayList<>();
                for (int i = 0; i < respuestas.size(); i++) {
                    RespuestaBean respuestaBean = new RespuestaBean();
                    respuestaBean.setIdRespuesta(respuestas.get(i).getIdRespuestaRest());
                    respuestaBean.setActivo(respuestas.get(i).getActivo());
                    respuestaBean.setDescripcion(respuestas.get(i).getDescripcion());
                    respuestaBean.setCorrecta(respuestas.get(i).getCorrecta());
                    respuestaBean.setOrden(respuestas.get(i).getOrden());
                    respuestasBeen.add(i, respuestaBean);
                }
                preguntaBean.setRespuestaList(respuestasBeen);
            }
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    int cont = contador;
                    serie.setContador(cont + 1);

                }
            });
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error traer pregunta " + e);
        }
        return preguntaBean;

    }

    public RespuestaPreguntaRequest getRespuestaPregunta(final int idRespuesta) {
        final RespuestaPreguntaRequest respuestaPreguntaRequest = new RespuestaPreguntaRequest();
        try {
            realm = Realm.getDefaultInstance();
            Concurso concurso = realm.where(Concurso.class)
                    .equalTo("idConcurso", setIdConcurso() - 1).findFirst();
            respuestaPreguntaRequest.setIdConcurso(concurso.getIdConcursoRest());
            respuestaPreguntaRequest.setIdJugadorNivel(concurso.getIdJugadorNivel());
            respuestaPreguntaRequest.setIdRespuesta(idRespuesta);
            respuestaPreguntaRequest.setSerie(concurso.getSerieActual());
            respuestaPreguntaRequest.setNivelActual(concurso.getdNivel());
            Serie serie = realm.where(Serie.class).equalTo("idSerie", setIdSerie() - 1).findFirst();
            if (serie.getContador() == 6) {
                respuestaPreguntaRequest.setPerfecta(1);
            } else {
                respuestaPreguntaRequest.setPerfecta(0);

            }
            Log.i(TAG, "OSE| Respuesta concurso idJugadorNivel: " + concurso.getIdJugadorNivel() + " serie actual: " + concurso.getSerieActual() + " nivelActual: " + concurso.getdNivel());

            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error traer pregunta " + e);

        }
        return respuestaPreguntaRequest;
    }

    public int getIdConcurso() {
        int idConcurso = 0;
        try {
            realm = Realm.getDefaultInstance();
            Concurso concurso = realm.where(Concurso.class)
                    .equalTo("idConcurso", setIdConcurso() - 1).findFirst();
            idConcurso = concurso.getIdConcursoRest();
            Log.i(TAG, "OSE| idConcurso: " + concurso.getIdConcursoRest());
            realm.close(); // Remember to close Realm when done.
        } catch (Exception e) {
            Log.e(TAG, "OSE| Error traer idConcurso " + e);
        }
        return idConcurso;
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

    private int setIdSerie() {
        realm = Realm.getDefaultInstance();
        //RealmQuery<Usuario> query = realm.where(Usuario.class);
        int idSerie = 0;
        if (realm.where(Serie.class).max("idSerie") != null) {
            idSerie = realm.where(Serie.class).max("idSerie").intValue();
        }
        realm.close(); // Remember to close Realm when done.
        return idSerie + 1;
    }

    private int setIdPregunta() {
        realm = Realm.getDefaultInstance();
        //RealmQuery<Usuario> query = realm.where(Usuario.class);
        int idPregunta = 0;
        if (realm.where(Pregunta.class).max("idPregunta") != null) {
            idPregunta = realm.where(Pregunta.class).max("idPregunta").intValue();
        }
        realm.close(); // Remember to close Realm when done.
        return idPregunta + 1;
    }

    private int setIdRespuesta() {
        realm = Realm.getDefaultInstance();
        //RealmQuery<Usuario> query = realm.where(Usuario.class);
        int idRespuesta = 0;
        if (realm.where(Respuesta.class).max("idRespuesta") != null) {
            idRespuesta = realm.where(Respuesta.class).max("idRespuesta").intValue();
        }
        realm.close(); // Remember to close Realm when done.
        return idRespuesta + 1;
    }

}
