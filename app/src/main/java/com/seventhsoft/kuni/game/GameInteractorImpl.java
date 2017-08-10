package com.seventhsoft.kuni.game;

import android.content.Context;

import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;
import com.seventhsoft.kuni.player.PlayerRepositoryImpl;
import com.seventhsoft.kuni.services.RestServiceFactory;
import com.seventhsoft.kuni.services.TrackerService;

import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by olibits on 8/08/17.
 */

public class GameInteractorImpl implements GameInteractor{

    private GamePresenter gamePresenter;
    private PlayerRepositoryImpl playerRepository;
    private String token;


    public GameInteractorImpl(GamePresenter gamePresenter, Context context) {
        this.gamePresenter = gamePresenter;
        this.playerRepository = new PlayerRepositoryImpl();
    }

    public void getDashboard(){

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

                    }
                });
    }
}
