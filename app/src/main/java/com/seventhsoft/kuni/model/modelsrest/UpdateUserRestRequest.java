package com.seventhsoft.kuni.model.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 30/07/17.
 */

public class UpdateUserRestRequest {
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apaterno")
    @Expose
    private String apellidos;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("passwordAnterior")
    @Expose
    private String passwordAnterior;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAnterior() {
        return passwordAnterior;
    }

    public void setPasswordAnterior(String passwordAnterior) {
        this.passwordAnterior = passwordAnterior;
    }
}
