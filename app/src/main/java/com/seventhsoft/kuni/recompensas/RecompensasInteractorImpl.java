package com.seventhsoft.kuni.recompensas;

import android.util.Log;

import com.seventhsoft.kuni.game.ConcursoRepository;
import com.seventhsoft.kuni.models.modelsrest.RecompensasJugadorRestResponse;
import com.seventhsoft.kuni.player.PlayerRepositoryImpl;
import com.seventhsoft.kuni.services.RestServiceFactory;
import com.seventhsoft.kuni.services.TrackerService;

import java.util.List;
import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by olibits on 6/09/17.
 */

public class RecompensasInteractorImpl implements RecompensasInteractor {

    private RecompensasPresenter recompensasPresenter;
    private String token;
    private PlayerRepositoryImpl playerRepository;
    private ConcursoRepository concursoRepository;

    public RecompensasInteractorImpl(RecompensasPresenter recompensasPresenter) {
        this.recompensasPresenter = recompensasPresenter;
        this.playerRepository = new PlayerRepositoryImpl();
        this.concursoRepository = new ConcursoRepository();
    }

    public void getRecompensasJugador() {
        token = "bearer " + playerRepository.getToken();
        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.getRecompensasJugador()
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<List<RecompensasJugadorRestResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        recompensasPresenter.setRecompensasEmpty();
                        Log.e(TAG, "OSE| error recompensas jugador " + e);

                    }

                    @Override
                    public void onNext(List<RecompensasJugadorRestResponse> response) {
                        Log.i(TAG, "OSE| interactor set recompensas jugador ");
                        if (response.isEmpty()) {
                            recompensasPresenter.setRecompensasEmpty();

                        } else {
                            recompensasPresenter.setRecompensasJugador(response);
                        }
                    }
                });
    }

    public void getRecompensasConcurso() {
        token = "bearer " + playerRepository.getToken();
        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.getRecompensasConcurso(concursoRepository.getIdConcurso())
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<List<RecompensasJugadorRestResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        recompensasPresenter.setRecompensasEmpty();

                        Log.e(TAG, "OSE| error recompensas jugador " + e);

                    }

                    @Override
                    public void onNext(List<RecompensasJugadorRestResponse> response) {
                        Log.i(TAG, "OSE| interactor set recompensas jugador ");
                        if (response.isEmpty()) {
                            recompensasPresenter.setRecompensasEmpty();

                        } else {
                            recompensasPresenter.setRecompensasConcurso(response);
                        }
                    }
                });
    }
}
