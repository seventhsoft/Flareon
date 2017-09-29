package com.seventhsoft.kuni.services;

import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;
import com.seventhsoft.kuni.models.modelsrest.LoginRestResponse;
import com.seventhsoft.kuni.models.modelsrest.RecompensasJugadorRestResponse;
import com.seventhsoft.kuni.models.modelsrest.RespuestaPreguntaRequest;
import com.seventhsoft.kuni.models.modelsrest.RespuestaPreguntaRestResponse;
import com.seventhsoft.kuni.models.modelsrest.RestorePasswordRequest;
import com.seventhsoft.kuni.models.modelsrest.SerieRestReponse;
import com.seventhsoft.kuni.models.modelsrest.UpdateUserRestRequest;
import com.seventhsoft.kuni.models.modelsrest.UserRestResponse;
import com.seventhsoft.kuni.models.modelsrest.SignUpRestRequest;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
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
     */
    @FormUrlEncoded
    @POST("/lfs/oauth/token")
    Observable<LoginRestResponse> logIn(@Field("username") String userName,
                                        @Field("password") String password,
                                        @Field("client_id") String clientId,
                                        @Field("grant_type") String grandType);

    /**
     * Recuperar contraseña
     */
    @POST("/lfs/usuarios/recuperar/password")
    Observable<Void> restorePassword(@Body RestorePasswordRequest email);

    /**
     * Get perfil
     */
    @GET("/lfs/usuarios/perfil")
    Observable<UserRestResponse> getPlayer();

    /**
     * Refresh token
     */
    @FormUrlEncoded
    @POST("/lfs/oauth/token")
    Observable<LoginRestResponse> refreshToken(@Field("refresh_token") String refreshToken,
                                               @Field("grant_type") String grandType);

    /**
     * Actualizar usuario
     */
    @PUT("/lfs/usuarios")
    Observable<Void> updatePlayer(@Body UpdateUserRestRequest updateRestRequest);

    /**
     * Cerrar sesión
     */
    @POST("/lfs/oauth/token/revokeById/{token}")
    Observable<Void> closeSesionAccess(@Path("token") String accessToken);

    @POST("/lfs/tokens/revokeRefreshToken/{refresh_token}")
    Observable<Void> closeSesionRefresh(@Path("refresh_token") String refreshToken);

    /**
     * Get consurso
     */
    @GET("lfs/jugador/concurso")
    Observable<DashboardRestReponse> getDashboard();

    /**
     * Get serie
     */
    @GET("/lfs/concurso/serie")
    Observable<SerieRestReponse> getSerie(@Query("idJugadorNivel") int idJugadorNivel);

    /**
     * respuesta pregunta
     */
    @POST("/lfs/concurso/serie/preguntarespuesta")
    Observable<RespuestaPreguntaRestResponse> respuestaPregunta(@Body RespuestaPreguntaRequest respuestaPreguntaRequest);

    /**
     * Get recompensas jugador
     */
    @GET("/lfs/recompensa/jugador")
    Observable<List<RecompensasJugadorRestResponse>> getRecompensasJugador();

    /**
     * Get recompensas concurso
     */
    @GET("/lfs/recompensa/concurso/{idConcurso}")
    Observable<List<RecompensasJugadorRestResponse>> getRecompensasConcurso(@Path("idConcurso") int idConcurso);
}
