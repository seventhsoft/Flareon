package com.seventhsoft.kuni.player;

import android.content.Context;
import android.util.Log;

import com.seventhsoft.kuni.models.UserBean;
import com.seventhsoft.kuni.models.modelsrest.LoginRestRequest;
import com.seventhsoft.kuni.models.modelsrest.LoginRestResponse;
import com.seventhsoft.kuni.models.modelsrest.PersonaRest;
import com.seventhsoft.kuni.models.modelsrest.RestorePasswordRequest;
import com.seventhsoft.kuni.models.modelsrest.UpdateUserRestRequest;
import com.seventhsoft.kuni.models.modelsrest.UserRestResponse;
import com.seventhsoft.kuni.models.modelsrest.SignUpRestRequest;
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
    private PlayerRepositoryImpl playerRepository;
    private SesionPreference sesionPreference;


    public PlayerInteractorImpl(PlayerPresenter playerPresenter, Context context) {
        this.playerPresenter = playerPresenter;
        this.playerRepository = new PlayerRepositoryImpl();
        sesionPreference = SesionPreference.getInstance(context);
    }

    public void login(String email, String password) {
        LoginRestRequest loginRestRequest = new LoginRestRequest();
        loginRestRequest.setUsername(email);
        loginRestRequest.setPassword(password);
        loginRestRequest.setClientId("mobileClient");
        loginRestRequest.setGrantType("password");

        token = "Basic bW9iaWxlQ2xpZW50OnNlY3JldE1vYmlsZQ==";

        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.logIn(email, password, "mobileClient", "password")
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<LoginRestResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        playerPresenter.onLoginFaiure();
                        Log.e(TAG, "OSE|" + e.getMessage() + "Error en el servicio iniciar sesion");
                        sesionPreference.saveData("statusSesion", false);

                    }

                    @Override
                    public void onNext(LoginRestResponse response) {
                        UserBean userBean = new UserBean();
                        userBean.setTokenAccess(response.getAccessToken());
                        userBean.setRefreshToken(response.getRefreshToken());
                        Log.i(TAG, "OSE| Access token " + response.getAccessToken());
                        Log.i(TAG, "OSE| Refresh token " + response.getRefreshToken());

                        /*
                         * Access token 04046649-ad32-44d4-b454-e9ff5b329616
                         * Refresh token 10cb13d5-67e7-424e-987b-73dcbb1a6e85
                         */

                        sesionPreference.saveData("statusSesion", true);
                        playerRepository.saveUser(userBean);
                        playerPresenter.onLoginSuccess();
                    }
                });
    }


    public void sendEmail(String email) {
        token = "Basic bW9iaWxlQ2xpZW50OnNlY3JldE1vYmlsZQ==";

        RestorePasswordRequest restorePasswordRequest = new RestorePasswordRequest();
        restorePasswordRequest.setUsuario(email);
        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.restorePassword(restorePasswordRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        playerPresenter.onRecoverPasswordFailure();

                        Log.e(TAG, "OSE|" + e.getMessage() + "Error en el servicio recuperar contaseña");
                    }

                    @Override
                    public void onNext(Void response) {
                        playerPresenter.onRecoverPasswordSuccess();
                        Log.i(TAG, "OSE|" + "Servicio recuperar contaseña");

                    }
                });
    }

    public void signUp(String name, String firstName, final String email, String password, final Boolean facebook) {

        SignUpRestRequest signUpRestRequest = new SignUpRestRequest();
        if (facebook) {
            signUpRestRequest.setActivo(true);

        } else {
            signUpRestRequest.setActivo(false);

        }
        signUpRestRequest.setApellidoPaterno(firstName);
        signUpRestRequest.setCorreoUsuario(email);
        signUpRestRequest.setFacebook(facebook);
        signUpRestRequest.setIdPerfil(2);
        signUpRestRequest.setNombreUsuario(name);
        signUpRestRequest.setUsuario(email);
        signUpRestRequest.setPassword(password);

        token = "Basic bW9iaWxlQ2xpZW50OnNlY3JldE1vYmlsZQ==";

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
                        if (facebook) {
                            login(email, "");
                        } else {
                            playerPresenter.onSignUpSuccesss();
                        }
                        Log.e(TAG, "OSE|" + e.getMessage() + "Error en el servicio crear cuenta");
                    }

                    @Override
                    public void onNext(String response) {
                        if (facebook) {
                            login(email, "");
                        } else {
                            playerPresenter.onSignUpSuccesss();
                        }
                    }
                });
    }

    public void closeSesion() {

        token = playerRepository.getToken();

        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.closeSesionAccess(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void response) {
                        revoqueToken();
                    }
                });

    }

    public void revoqueToken() {

        token = playerRepository.getRefreshToken();
        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.closeSesionRefresh(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void response) {
                        sesionPreference.saveData("statusSesion", false);
                        playerRepository.deletePlayers();
                        playerPresenter.onSesionClosed();

                    }
                });

    }

    public void refreshToken() {
        token = "Basic bW9iaWxlQ2xpZW50OnNlY3JldE1vYmlsZQ==";

        String tokenRefresh = playerRepository.getRefreshToken();
        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.refreshToken(tokenRefresh, "refresh_token")
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<LoginRestResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "OSE|" + e + "Error en el servicio refresh token");

                    }

                    @Override
                    public void onNext(LoginRestResponse response) {

                        playerRepository.updateToken(response.getAccessToken(),response.getRefreshToken());
                    }
                });
    }

    public void getPlayer() {

        token = "bearer " + playerRepository.getToken();

        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.getPlayer()
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<UserRestResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e(TAG, "OSE|" + e.getMessage() + "Error en el servicio obtener usuario");
                    }

                    @Override
                    public void onNext(UserRestResponse response) {
                        PersonaRest personaRest = new PersonaRest();
                        personaRest = response.getPersonaRest();
                        UserBean userBean = new UserBean();
                        userBean.setName(personaRest.getNombre());
                        userBean.setEmail(personaRest.getCorreo());
                        String[] parts = personaRest.getApaterno().split("null");
                        String part = parts[0]; // 004

                        userBean.setFirstName(part);
                        //userBean.setPassword();
                        //userBean.setEmail();

                        playerPresenter.onGetPlayer(userBean);

                    }
                });
    }

    public void updatePlayerName(UserBean userBean, String apellidos, String nombre) {
        UpdateUserRestRequest updateRestRequest = new UpdateUserRestRequest();
        if (!userBean.getName().equals(nombre)) {
            updateRestRequest.setNombre(nombre);
        }
        if (!userBean.getFirstName().equals(apellidos)) {
            updateRestRequest.setApellidos(apellidos);
        }
        updatePlayer(updateRestRequest);

    }

    public void updatePlayerPassword(String contraseñaActual, String contraseñaNueva) {
        UpdateUserRestRequest updateRestRequest = new UpdateUserRestRequest();
        updateRestRequest.setPasswordAnterior(contraseñaActual);
        updateRestRequest.setPassword(contraseñaNueva);
        updatePlayer(updateRestRequest);
    }

    private void updatePlayer(UpdateUserRestRequest updateRestRequest) {
        token = "bearer " + playerRepository.getToken();

        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.updatePlayer(updateRestRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e(TAG, "OSE|" + e.getMessage() + "Error en el servicio actualizar usuario");
                    }

                    @Override
                    public void onNext(Void response) {
                        //refreshToken();

                        /*PersonaRest persona = new PersonaRest();
                        persona = response.getPersonaRest();
                        UserBean userBeUserRestResponse responsean = new UserBean();
                        userBean.setName(persona.getNombre());
                        userBean.setEmail(persona.getCorreo());
                        userBean.setFirstName(persona.getApaterno());
                        //userBean.setPassword();
                        //userBean.setEmail();
*/
                        //playerPresenter.onGetPlayer(userBean);

                    }
                });
    }

    /**
     * Repository to iteractor
     */

    public void onSaveSuccess() {
    }

    public void onSaveError() {
    }

    public void onGetError() {
    }

    public void setUser(UserBean userBean) {

    }
}
