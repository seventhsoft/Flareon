package com.seventhsoft.kuni.model.modelsrest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by olibits on 29/07/17.
 */

public class RestorePasswordRequest {


    @SerializedName("usuario")
    @Expose
    private String usuario;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
