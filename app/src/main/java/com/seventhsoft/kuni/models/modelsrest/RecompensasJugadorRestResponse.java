package com.seventhsoft.kuni.models.modelsrest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 6/09/17.
 */

public class RecompensasJugadorRestResponse implements Parcelable {
    @SerializedName("idRecompensa")
    @Expose
    private Integer idRecompensa;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("cantidad")
    @Expose
    private Integer cantidad;
    @SerializedName("vigencia")
    @Expose
    private long vigencia;
    @SerializedName("activo")
    @Expose
    private Boolean activo;
    @SerializedName("redimido")
    @Expose
    private Boolean redimido;

    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("organizacion")
    @Expose
    private String organizacion;

    public static final Parcelable.Creator<RecompensasJugadorRestResponse> CREATOR
            = new Parcelable.Creator<RecompensasJugadorRestResponse>() {
        public RecompensasJugadorRestResponse createFromParcel(Parcel in) {
            return new RecompensasJugadorRestResponse(in);
        }

        public RecompensasJugadorRestResponse[] newArray(int size) {
            return new RecompensasJugadorRestResponse[size];
        }
    };

    private RecompensasJugadorRestResponse(Parcel in) {
        descripcion = in.readString();
        cantidad = in.readInt();
        vigencia = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(descripcion);
        parcel.writeInt(cantidad);
        parcel.writeLong(vigencia);
    }

    public Integer getIdRecompensa() {
        return idRecompensa;
    }

    public void setIdRecompensa(Integer idRecompensa) {
        this.idRecompensa = idRecompensa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public long getVigencia() {
        return vigencia;
    }

    public void setVigencia(long vigencia) {
        this.vigencia = vigencia;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getRedimido() {
        return redimido;
    }

    public void setRedimido(Boolean redimido) {
        this.redimido = redimido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }
}
