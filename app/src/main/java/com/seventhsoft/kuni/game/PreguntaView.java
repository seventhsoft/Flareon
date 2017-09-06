package com.seventhsoft.kuni.game;

import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.RespuestaBean;

/**
 * Created by olibits on 24/08/17.
 */

public interface PreguntaView {

    void setPregunta(PreguntaBean pregunta);
    void changeColorButton(int position, boolean bien);

    void setClase(final PreguntaBean pregunta, final RespuestaBean respuestaBean,
                  final boolean nivel, final boolean serie, final  boolean premio, final String descripcionPremio);
    void setCorrecta();
    void setIncorrecta();
    void setNivelUp();
    void setSerieUp();
}
