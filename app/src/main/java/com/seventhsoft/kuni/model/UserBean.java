package com.seventhsoft.kuni.model;

/**
 * Created by olibits on 24/07/17.
 */

public class UserBean {

    private int idUser = 0;
    private String email = "";
    private String password = "";
    private String name = "";
    private String firstName = "";
    private int time = 0;
    private Boolean facebook = false;
    private String tokenAccess = "";
    private String refreshToken = "";

    private Boolean nombreEditado = false;
    private Boolean apellidoEditado = false;
    private Boolean contraseñaEditada = false;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Boolean getFacebook() {
        return facebook;
    }

    public void setFacebook(Boolean facebook) {
        this.facebook = facebook;
    }

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Boolean getNombreEditado() {
        return nombreEditado;
    }

    public void setNombreEditado(Boolean nombreEditado) {
        this.nombreEditado = nombreEditado;
    }

    public Boolean getApellidoEditado() {
        return apellidoEditado;
    }

    public void setApellidoEditado(Boolean apellidoEditado) {
        this.apellidoEditado = apellidoEditado;
    }

    public Boolean getContraseñaEditada() {
        return contraseñaEditada;
    }

    public void setContraseñaEditada(Boolean contraseñaEditada) {
        this.contraseñaEditada = contraseñaEditada;
    }
}
