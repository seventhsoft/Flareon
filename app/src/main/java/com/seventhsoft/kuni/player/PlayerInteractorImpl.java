package com.seventhsoft.kuni.player;

import android.util.Log;

import com.seventhsoft.kuni.model.modelrest.SignUpRestRequest;
import com.seventhsoft.kuni.services.RestServiceFactory;
import com.seventhsoft.kuni.services.TrackerService;

import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by olibits on 4/07/17.
 */

public class PlayerInteractorImpl implements PlayerInteractor {

    private PlayerPresenter playerPresenter;
    private String token;

    public PlayerInteractorImpl(PlayerPresenter playerPresenter) {
        this.playerPresenter = playerPresenter;
    }

    public void login(String email, String password) {

    }

    public void sendEmail(String email) {
    }

    public void signUp(String name, String firstName, String email, String password, Boolean facebook){

        SignUpRestRequest signUpRestRequest = new SignUpRestRequest();
        signUpRestRequest.setActivo(true);
        signUpRestRequest.setApellidoPaterno(firstName);
        signUpRestRequest.setCorreoUsuario(email);
        signUpRestRequest.setFacebook(facebook);
        signUpRestRequest.setIdPerfil(2);
        signUpRestRequest.setNombreUsuario(name);
        signUpRestRequest.setUsuario(email);
        signUpRestRequest.setPassword(password);


        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.signUp(signUpRestRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "OSE|" + e.getMessage() + "Error en el servicio obtener cuenta");
                    }

                    @Override
                    public void onNext(String response) {
                        //aqui haces algo con el response
                    }
                });
    }

}
