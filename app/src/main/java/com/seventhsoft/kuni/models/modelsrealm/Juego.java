package com.seventhsoft.kuni.models.modelsrealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by olibits on 12/08/17.
 */

public class Juego extends RealmObject{

    @PrimaryKey
    int idNivel;

    int niveles;

    Serie serie;

}
