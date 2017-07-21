package com.seventhsoft.kuni.services;

import com.seventhsoft.kuni.model.modelrest.SignUpRestRequest;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by olimpia on 3/04/17.
 */

public interface TrackerService {

    String SERVICE_ENDPOINT = "api.juegakuni.com.mx";

    @POST("/fls/usuarios")
    Observable<String> signUp(@Body SignUpRestRequest signUpRestRequest);

}
