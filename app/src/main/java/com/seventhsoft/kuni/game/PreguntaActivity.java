package com.seventhsoft.kuni.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.PreguntaBean;

public class PreguntaActivity extends AppCompatActivity implements PreguntaView {

    private GamePresenter gamePresenter;
    private TextView txtDescripcion;
    private Button btnRespuestaUno;
    private Button btnRespuestaDos;
    private Button btnRespuestaTres;
    private PreguntaBean preguntaBean;
    private LinearLayout transiciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);
        gamePresenter = new GamePresenterImpl(this, this);
        btnRespuestaUno = (Button) findViewById(R.id.btnRespuestaUno);
        btnRespuestaDos = (Button) findViewById(R.id.btnRespuestaDos);
        btnRespuestaTres = (Button) findViewById(R.id.btnRespuestaTres);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        transiciones = (LinearLayout) findViewById(R.id.transicion);
        if (getIntent().hasExtra("position")) {
            gamePresenter.getPregunta();

        }
    }

    public void setPregunta(final PreguntaBean pregunta) {
        this.preguntaBean = pregunta;
        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if (pregunta != null) {

                    txtDescripcion.setText(pregunta.getDescripcion());

                    btnRespuestaUno.setText(pregunta.getRespuestaList().get(0).getDescripcion());
                    btnRespuestaDos.setText(pregunta.getRespuestaList().get(1).getDescripcion());
                    btnRespuestaTres.setText(pregunta.getRespuestaList().get(2).getDescripcion());
                    clickListener();
                }
            }
        });
    }

    public void setClase(boolean bien, boolean perfecta) {
        txtDescripcion.setText(preguntaBean.getClase());
        if(bien && perfecta){
            transiciones.setBackground(getDrawable(R.drawable.));
        }else if (bien){

        }else{

        }
        gamePresenter.getPregunta();


    }


    public void clickListener() {
        btnRespuestaUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePresenter.evaluatePregunta(preguntaBean.getRespuestaList().get(0), 0);
            }
        });
        btnRespuestaDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePresenter.evaluatePregunta(preguntaBean.getRespuestaList().get(1), 1);

            }
        });
        btnRespuestaTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePresenter.evaluatePregunta(preguntaBean.getRespuestaList().get(2), 2);

            }
        });
    }


    public void changeColorButton(int position, boolean bien) {
        if (bien) {
            switch (position) {
                case 0:
                    btnRespuestaUno.setBackgroundColor(getResources().getColor(R.color.verde));
                    break;
                case 1:
                    btnRespuestaDos.setBackgroundColor(getResources().getColor(R.color.verde));
                    break;
                case 2:
                    btnRespuestaTres.setBackgroundColor(getResources().getColor(R.color.verde));
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    btnRespuestaUno.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                    break;
                case 1:
                    btnRespuestaDos.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                    break;
                case 2:
                    btnRespuestaTres.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                    break;
            }
        }
    }

    public void setCorrecta() {
    }

    public void setIncorrecta() {
    }

    public void setNivelUp() {
    }

    public void setSerieUp() {
    }
}
