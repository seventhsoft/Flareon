package com.seventhsoft.kuni.services;

import com.seventhsoft.kuni.model.modelsrest.LoginRestRequest;
import com.seventhsoft.kuni.model.modelsrest.LoginRestResponse;
import com.seventhsoft.kuni.model.modelsrest.SignUpRestRequest;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by olimpia on 3/04/17.
 */

public interface TrackerService {

    String SERVICE_ENDPOINT = "http://api.juegakuni.com.mx";

    /**
     * Crear una cuenta
     *
     * @param signUpRestRequest
     * @return
     */
    @POST("/lfs/usuarios")
    Observable<String> signUp(@Body SignUpRestRequest signUpRestRequest);

    /**
     * Iniciar sesión
     *
     */
    @FormUrlEncoded
    @POST("/lfs/oauth/token")
    Observable<LoginRestResponse> logIn(@Field("username") String userName,
                                        @Field("password") String password,
                                        @Field("client_id") String clientId,
                                        @Field("grant_type") String grandType);

}
