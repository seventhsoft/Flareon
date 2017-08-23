package com.seventhsoft.kuni.models;

import com.seventhsoft.kuni.models.modelsrealm.Respuesta;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by olibits on 23/08/17.
 */

public class PreguntaBean {

    private int idPregunta;

    private String descripcion;
    private String ruta;
    private String clase;
    private Boolean activo;
    private List<RespuestaBean> respuestaList = null;


}
