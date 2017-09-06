package com.seventhsoft.kuni.game;

import android.content.Context;
import android.util.Log;

import com.seventhsoft.kuni.models.ConcursoBean;
import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.RespuestaBean;
import com.seventhsoft.kuni.models.SerieBean;
import com.seventhsoft.kuni.models.modelsrealm.Pregunta;
import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;
import com.seventhsoft.kuni.models.modelsrest.RespuestaPreguntaRequest;
import com.seventhsoft.kuni.models.modelsrest.RespuestaPreguntaRestResponse;
import com.seventhsoft.kuni.models.modelsrest.RespuestaRest;
import com.seventhsoft.kuni.models.modelsrest.SerieRestReponse;
import com.seventhsoft.kuni.player.PlayerRepositoryImpl;
import com.seventhsoft.kuni.services.RestServiceFactory;
import com.seventhsoft.kuni.services.TrackerService;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by olibits on 8/08/17.
 */

public class GameInteractorImpl implements GameInteractor {

    private GamePresenter gamePresenter;
    private PlayerRepositoryImpl playerRepository;
    private ConcursoRepository concursoRepository;
    private String token;


    public GameInteractorImpl(GamePresenter gamePresenter, Context context) {
        this.gamePresenter = gamePresenter;
        this.playerRepository = new PlayerRepositoryImpl();
        this.concursoRepository = new ConcursoRepository();
    }

    public void getDashboard() {

        token = "bearer " + playerRepository.getToken();
        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.getDashboard()
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<DashboardRestReponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DashboardRestReponse response) {
                        Log.i(TAG, "OSE| interactor set dash ");
                        String fecha = "8 días";//getDias(response.getConcursoRest().getFechaInicio(),
                        //response.getConcursoRest().getFechaFin());
                        gamePresenter.setDashboard(response, fecha);
                    }
                });
    }


    public void saveDashBoard(final DashboardRestReponse dashboardRestReponse) {

    }

    public void getSerie(final DashboardRestReponse dashboardRestReponse) {
        Log.i(TAG, "OSE| getserie " + dashboardRestReponse);
        Log.i(TAG, "OSE| jugador id " + dashboardRestReponse.getJugadorNivel().getIdJugadorNivel());

        token = "bearer " + playerRepository.getToken();
        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.getSerie(dashboardRestReponse.getJugadorNivel().getIdJugadorNivel())
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<SerieRestReponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "OSE| getserie  error" + e);

                    }

                    @Override
                    public void onNext(SerieRestReponse response) {

                        ConcursoBean concursoBean = new ConcursoBean();
                        concursoBean.setIdConsurso(dashboardRestReponse.getConcursoRest().getIdConcurso());
                        concursoBean.setFechaInicio(dashboardRestReponse.getConcursoRest().getFechaInicio());
                        concursoBean.setFechaFin(dashboardRestReponse.getConcursoRest().getFechaFin());
                        concursoBean.setActivo(dashboardRestReponse.getConcursoRest().getActivo());
                        concursoBean.setIdJugadorNivel(dashboardRestReponse.getJugadorNivel().getIdJugadorNivel());
                        concursoBean.setSerieActual(dashboardRestReponse.getJugadorNivel().getSerieActual());
                        concursoBean.setdNivel(dashboardRestReponse.getJugadorNivel().getNivel());
                        Log.i(TAG, "OSE| concursobean " + concursoBean);

                        concursoRepository.saveConcurso(concursoBean);

                        SerieBean serieBean = new SerieBean();
                        serieBean.setTiempoPregunta(response.getTiempoPregunta());
                        //serieBean.setBannerSerie(response.getBannerSerie());
                        List<PreguntaBean> preguntas = new ArrayList<PreguntaBean>();

                        for (int i = 0; i < response.getPreguntas().size(); i++) {
                            PreguntaBean preguntaBean = new PreguntaBean();
                            preguntaBean.setActivo(response.getPreguntas().get(i).getActivo());
                            preguntaBean.setDescripcion(response.getPreguntas().get(i).getDescripcion());
                            preguntaBean.setRuta(response.getPreguntas().get(i).getRuta());
                            preguntaBean.setClase(response.getPreguntas().get(i).getClase());

                            List<RespuestaRest> respuestasRest = response.getPreguntas().get(i).getRespuestaList();
                            //Log.i(TAG, "OSE| size respuestas " + respuestasRest.size() + i);

                            List<RespuestaBean> respuestas = new ArrayList<RespuestaBean>();
                            for (int j = 0; j < respuestasRest.size(); j++) {
                                RespuestaBean respuestaBean = new RespuestaBean();
                                respuestaBean.setIdRespuesta(respuestasRest.get(j).getIdRespuesta());
                                respuestaBean.setActivo(respuestasRest.get(j).getActivo());
                                respuestaBean.setDescripcion(respuestasRest.get(j).getDescripcion());
                                respuestaBean.setCorrecta(respuestasRest.get(j).getCorrecta());
                                respuestaBean.setOrden(respuestasRest.get(j).getOrden());
                                Log.i(TAG, "OSE|  for j" + j);

                                respuestas.add(j, respuestaBean);
                            }
                            //Log.i(TAG, "OSE| despues for j" + i);

                            preguntaBean.setRespuestaList(respuestas);
                            preguntas.add(i, preguntaBean);

                        }

                        serieBean.setPreguntas(preguntas);
                        Log.i(TAG, "OSE| concursobean " + serieBean);

                        concursoRepository.saveSerie(serieBean);


                    }
                });
    }

    public void actualizarSerie() {
        int idJugadorNivel = concursoRepository.getIdJugadorNivel();
        Log.i(TAG, "OSE| idJugadorNivel" + idJugadorNivel);

        concursoRepository.deleteSerie();
        token = "bearer " + playerRepository.getToken();
        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.getSerie(idJugadorNivel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<SerieRestReponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "OSE| getserie  error" + e);

                    }

                    @Override
                    public void onNext(SerieRestReponse response) {

                        SerieBean serieBean = new SerieBean();
                        serieBean.setTiempoPregunta(response.getTiempoPregunta());
                        //serieBean.setBannerSerie(response.getBannerSerie());
                        List<PreguntaBean> preguntas = new ArrayList<PreguntaBean>();

                        for (int i = 0; i < response.getPreguntas().size(); i++) {
                            PreguntaBean preguntaBean = new PreguntaBean();
                            preguntaBean.setActivo(response.getPreguntas().get(i).getActivo());
                            preguntaBean.setDescripcion(response.getPreguntas().get(i).getDescripcion());
                            preguntaBean.setRuta(response.getPreguntas().get(i).getRuta());
                            preguntaBean.setClase(response.getPreguntas().get(i).getClase());

                            List<RespuestaRest> respuestasRest = response.getPreguntas().get(i).getRespuestaList();
                            Log.i(TAG, "OSE| size respuestas " + respuestasRest.size() + i);

                            List<RespuestaBean> respuestas = new ArrayList<RespuestaBean>();
                            for (int j = 0; j < respuestasRest.size(); j++) {
                                RespuestaBean respuestaBean = new RespuestaBean();
                                respuestaBean.setIdRespuesta(respuestasRest.get(j).getIdRespuesta());
                                respuestaBean.setActivo(respuestasRest.get(j).getActivo());
                                respuestaBean.setDescripcion(respuestasRest.get(j).getDescripcion());
                                respuestaBean.setCorrecta(respuestasRest.get(j).getCorrecta());
                                respuestaBean.setOrden(respuestasRest.get(j).getOrden());
                               // Log.i(TAG, "OSE|  for j" + j);

                                respuestas.add(j, respuestaBean);
                            }
                            //Log.i(TAG, "OSE| despues for j" + i);

                            preguntaBean.setRespuestaList(respuestas);
                            preguntas.add(i, preguntaBean);

                        }

                        serieBean.setPreguntas(preguntas);
                        Log.i(TAG, "OSE| concursobean " + serieBean);

                        concursoRepository.saveSerie(serieBean);

                    }
                });

    }

    public void evaluarPregunta(final PreguntaBean pregunta, final int position) {
        final RespuestaBean respuestaBean = pregunta.getRespuestaList().get(position);
        final RespuestaPreguntaRequest respuestaPreguntaRequest = concursoRepository.getRespuestaPregunta(respuestaBean.getIdRespuesta());
        token = "bearer " + playerRepository.getToken();
        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.respuestaPregunta(respuestaPreguntaRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<RespuestaPreguntaRestResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //actualizarSerie();
                        //gamePresenter.setSuccessPregunta(pregunta, pregunta.getRespuestaList().get(position));

                    }

                    @Override
                    public void onNext(RespuestaPreguntaRestResponse response) {
                        boolean nivel = false;
                        boolean serie = false;
                        boolean recompensa = false;
                        String descripcionRecompensa = "";
                        if (response.getJugadorNivel().getRecompensaGanada() != null && !response.getJugadorNivel().getRecompensaGanada().isEmpty()) {
                            recompensa = true;
                            descripcionRecompensa = response.getRecompensa().getDescripcion();
                        }
                        if (respuestaBean.getCorrecta()) {
                            if (respuestaPreguntaRequest.getPerfecta() == 1 && respuestaPreguntaRequest.getNivelActual() != response.getJugadorNivel().getNivel() ) {
                                Log.i(TAG, "OSE| nivel up ");

                                concursoRepository.updateConcurso(response);
                                actualizarSerie();
                                //gamePresenter.setSuccessSerie();
                                nivel = true;
                                serie = true;
                            } else if (respuestaPreguntaRequest.getPerfecta() == 1) {
                                Log.i(TAG, "OSE| serie ");
                                concursoRepository.updateConcurso(response);
                                actualizarSerie();
                                serie = true;
                            }
                        } else {
                            Log.i(TAG, "OSE| mal");
                            actualizarSerie();
                        }
                        gamePresenter.setClase(pregunta, pregunta.getRespuestaList().get(position),
                                nivel, serie, recompensa, descripcionRecompensa);

                    }
                });
    }

    public void getPregunta() {
        PreguntaBean preguntaBean = concursoRepository.getPregunta();
        if (preguntaBean != null)
            gamePresenter.setPregunta(preguntaBean);
    }

    private String getDias(long inicio, long fin) {
        // write your code here
        //long inicio = Long.parseLong(start);
        //long fin = Long.parseLong("1501545599000");

        try {
            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, new Locale("es", "MX"));

            Date date = new Date();

            String fechaInicio = String.valueOf(df.format(date));
            String fechaFin = String.valueOf(df.format(fin));
            System.out.println(fechaInicio);
            System.out.println(fechaFin);
            try {
                Date dateInicio = df.parse(fechaInicio);
                System.out.println("Parsed date: " + dateInicio.toString());
                Date dateFin = df.parse(fechaFin);
                System.out.println("Parsed date: " + dateFin.toString());

                Map<TimeUnit, Long> dias = computeDiff(dateInicio, dateFin);
                System.out.println(dias);

                System.out.println(dias.get(TimeUnit.DAYS));
                return String.valueOf(dias.get(TimeUnit.DAYS));
            } catch (ParseException pe) {
                System.out.println("ERROR: could not parse date in string ");
                return null;

            }

            //Date date = DateFormat.getDateInstance(DateFormat.DEFAULT).parse(String.valueOf(1498885201));
            //System.out.println(date);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    private static Map<TimeUnit, Long> computeDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);
        Map<TimeUnit, Long> result = new LinkedHashMap<TimeUnit, Long>();
        long milliesRest = diffInMillies;
        for (TimeUnit unit : units) {
            long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;
            result.put(unit, diff);
        }
        return result;
    }


}
