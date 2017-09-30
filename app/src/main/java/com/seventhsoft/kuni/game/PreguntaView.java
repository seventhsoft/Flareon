package com.seventhsoft.kuni.game;

import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.RespuestaBean;

/**
 * Created by olibits on 24/08/17.
 */

public interface PreguntaView {

    void setPregunta(PreguntaBean pregunta);

    //void changeColorButton(int bien, int pressed, boolean correcta);

    void setClase(PreguntaBean pregunta, RespuestaBean respuestaBean, boolean nivel, boolean serie,
                  boolean premio, String desripcionPremio);

    void setResultado(PreguntaBean pregunta, RespuestaBean respuestaBean, int pressed, int correcta,
                      RespuestaBean respuestaBien, boolean bien, boolean nivel, boolean serie,
                      boolean premio, String desripcionPremio);

    void setCorrecta();

    void setIncorrecta();

    void setNivelUp();

    void setSerieUp();
}
