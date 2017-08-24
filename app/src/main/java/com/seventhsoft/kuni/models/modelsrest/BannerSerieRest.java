package com.seventhsoft.kuni.models.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 24/08/17.
 */

public class BannerSerieRest {
    @SerializedName("idBanner")
    @Expose
    private Integer idBanner;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("impresiones")
    @Expose
    private Integer impresiones;
    @SerializedName("interacciones")
    @Expose
    private Integer interacciones;
    @SerializedName("ruta")
    @Expose
    private String ruta;
    @SerializedName("activo")
    @Expose
    private Boolean activo;
    @SerializedName("tipo")
    @Expose
    private Boolean tipo;
    @SerializedName("idCampana")
    @Expose
    private Integer idCampana;
    @SerializedName("fechaRegistro")
    @Expose
    private long fechaRegistro;
    @SerializedName("campana")
    @Expose
    private Object campana;
    @SerializedName("url")
    @Expose
    private String url;

    public Integer getIdBanner() {
        return idBanner;
    }

    public void setIdBanner(Integer idBanner) {
        this.idBanner = idBanner;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getImpresiones() {
        return impresiones;
    }

    public void setImpresiones(Integer impresiones) {
        this.impresiones = impresiones;
    }

    public Integer getInteracciones() {
        return interacciones;
    }

    public void setInteracciones(Integer interacciones) {
        this.interacciones = interacciones;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public Integer getIdCampana() {
        return idCampana;
    }

    public void setIdCampana(Integer idCampana) {
        this.idCampana = idCampana;
    }

    public long getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(long fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Object getCampana() {
        return campana;
    }

    public void setCampana(Object campana) {
        this.campana = campana;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
