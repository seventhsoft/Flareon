package com.seventhsoft.kuni.models.modelsrealm;


import io.realm.annotations.RealmModule;

@RealmModule(classes = {Usuario.class, Juego.class, Serie.class, Pregunta.class, Concurso.class, Respuesta.class})
public class KuniFieldsModule {
}