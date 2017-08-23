package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 20/07/17.
 */

public class SignUpRestResponse {
    @SerializedName("idUsuario")
    @Expose
    private Integer idUsuario;
    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("activo")
    @Expose
    private Boolean activo;
    @SerializedName("fechaRegistro")
    @Expose
    private Object fechaRegistro;
    @SerializedName("personaRest")
    @Expose
    private PersonaRest personaRest;
    @SerializedName("idPerfil")
    @Expose
    private Integer idPerfil;
    @SerializedName("facebook")
    @Expose
    private Boolean facebook;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Object getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Object fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public PersonaRest getPersonaRest() {
        return personaRest;
    }

    public void setPersonaRest(PersonaRest personaRest) {
        this.personaRest = personaRest;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Boolean getFacebook() {
        return facebook;
    }

    public void setFacebook(Boolean facebook) {
        this.facebook = facebook;
    }
}
