package com.seventhsoft.kuni.game;

/**
 * Created by olibits on 9/08/17.
 */

public interface RepositoryRowView {

    void setNivel(String nivel);

    void setEstadoNivel(String estadoNivel);

    void setPremiosDisponibles(String supportText);

    void setSeries(int series, int serieActual);

    void setPremiosGanados(boolean premiosGanados);

    void setImage(String resourse);

    void setTextColor(boolean started);
}
