package com.seventhsoft.kuni.services;

import com.seventhsoft.kuni.model.modelsrest.LoginRestRequest;
import com.seventhsoft.kuni.model.modelsrest.LoginRestResponse;
import com.seventhsoft.kuni.model.modelsrest.SignUpRestRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by olimpia on 3/04/17.
 */

public interface TrackerService {

    String SERVICE_ENDPOINT = "http://api.juegakuni.com.mx";

    /**
     * Crear una cuenta
     * @param signUpRestRequest
     * @return
     */
    @POST("/lfs/usuarios")
    Observable<String> signUp(@Body SignUpRestRequest signUpRestRequest);

    /**
     * Iniciar sesión
     * @param loginRestRequest
     * @return
     */
    @POST("/lfs/usuarios")
    Observable<LoginRestResponse> logIn(@Body LoginRestRequest loginRestRequest);

}
