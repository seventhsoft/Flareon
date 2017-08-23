package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 20/07/17.
 */

public class UserRestResponse {

    @SerializedName("idUsuario")
    @Expose
    private Integer idUsuario;
    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("activo")
    @Expose
    private Boolean activo;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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
