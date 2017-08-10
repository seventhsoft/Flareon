package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 20/07/17.
 */

public class Persona {
    @SerializedName("idPersona")
    @Expose
    private Integer idPersona;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apaterno")
    @Expose
    private String apaterno;
    @SerializedName("amaterno")
    @Expose
    private String amaterno;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("organizacion")
    @Expose
    private Object organizacion;
    @SerializedName("telefono")
    @Expose
    private Object telefono;
    @SerializedName("activo")
    @Expose
    private Boolean activo;
    @SerializedName("fechaRegistro")
    @Expose
    private Object fechaRegistro;
    @SerializedName("personaPerfilList")
    @Expose
    private Object personaPerfilList;
    @SerializedName("jugadorList")
    @Expose
    private Object jugadorList;
    @SerializedName("patrocinadorList")
    @Expose
    private Object patrocinadorList;
    @SerializedName("usuarioList")
    @Expose
    private Object usuarioList;
    @SerializedName("anuncianteList")
    @Expose
    private Object anuncianteList;

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Object getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Object organizacion) {
        this.organizacion = organizacion;
    }

    public Object getTelefono() {
        return telefono;
    }

    public void setTelefono(Object telefono) {
        this.telefono = telefono;
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

    public Object getPersonaPerfilList() {
        return personaPerfilList;
    }

    public void setPersonaPerfilList(Object personaPerfilList) {
        this.personaPerfilList = personaPerfilList;
    }

    public Object getJugadorList() {
        return jugadorList;
    }

    public void setJugadorList(Object jugadorList) {
        this.jugadorList = jugadorList;
    }

    public Object getPatrocinadorList() {
        return patrocinadorList;
    }

    public void setPatrocinadorList(Object patrocinadorList) {
        this.patrocinadorList = patrocinadorList;
    }

    public Object getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(Object usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Object getAnuncianteList() {
        return anuncianteList;
    }

    public void setAnuncianteList(Object anuncianteList) {
        this.anuncianteList = anuncianteList;
    }

}
