package com.seventhsoft.kuni.game;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.RespuestaBean;
import com.seventhsoft.kuni.models.modelsrealm.Pregunta;
import com.seventhsoft.kuni.utils.ToolbarFragment;

public class PreguntaActivity extends AppCompatActivity implements PreguntaView, ClaseFragment.OnFragmentInteractionListener {

    private GamePresenter gamePresenter;
    private TextView txtDescripcion;
    private TextView txtClase;
    private TextView txtDescripcionClase;

    private FrameLayout fondo;

    private LinearLayout imageClase;
    private Button btnRespuestaUno;
    private Button btnRespuestaDos;
    private Button btnRespuestaTres;
    private Button btnSiguientePregunta;

    private PreguntaBean preguntaBean;
    private LinearLayout transiciones;
    private Fragment fragment;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);
        setToolbar();
        gamePresenter = new GamePresenterImpl(this, this);
        btnRespuestaUno = (Button) findViewById(R.id.btnRespuestaUno);
        btnRespuestaDos = (Button) findViewById(R.id.btnRespuestaDos);
        btnRespuestaTres = (Button) findViewById(R.id.btnRespuestaTres);
        btnSiguientePregunta = (Button) findViewById(R.id.btnSiguientePregunta);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        txtClase = (TextView) findViewById(R.id.txtClase);
        //imageClase = (LinearLayout) findViewById(R.id.imageClase);
        fondo = (FrameLayout) findViewById(R.id.fragment_container);

        if (getIntent().hasExtra("position")) {
            gamePresenter.getPregunta();

        }
    }

    public void setPregunta(final PreguntaBean pregunta) {
        this.preguntaBean = pregunta;
        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if (pregunta.getRespuestaList() != null) {
                    btnRespuestaUno.setVisibility(View.VISIBLE);
                    btnRespuestaDos.setVisibility(View.VISIBLE);
                    btnRespuestaTres.setVisibility(View.VISIBLE);
                    txtClase.setVisibility(View.GONE);
                    btnSiguientePregunta.setVisibility(View.GONE);
                    txtDescripcion.setText(pregunta.getDescripcion());
                    btnRespuestaUno.setText(pregunta.getRespuestaList().get(0).getDescripcion());
                    btnRespuestaDos.setText(pregunta.getRespuestaList().get(1).getDescripcion());
                    btnRespuestaTres.setText(pregunta.getRespuestaList().get(2).getDescripcion());
                    clickListener();
                }
            }
        });
    }

    public void onFragmentInteraction() {
        transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.addToBackStack("clase");
        transaction.commit();
        fondo.setVisibility(View.GONE);
        gamePresenter.getPregunta();

    }

    public void setClase(final PreguntaBean pregunta, final RespuestaBean respuestaBean,
                         final boolean nivel, final boolean serie, final boolean premio, final String descripcionPremio) {
        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                btnRespuestaUno.setVisibility(View.GONE);
                btnRespuestaDos.setVisibility(View.GONE);
                btnRespuestaTres.setVisibility(View.GONE);
                txtClase.setVisibility(View.VISIBLE);
                btnSiguientePregunta.setVisibility(View.VISIBLE);
                txtClase.setText(pregunta.getClase());
                btnSiguientePregunta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!serie) {
                            gamePresenter.getPregunta();
                        } else {

                            setTerminado(nivel, premio, descripcionPremio);
                        }
                    }
                });
            }
        });

    }

    private void setTerminado(boolean nivel, boolean premio, String descripcionPremio) {
        fondo.setVisibility(View.VISIBLE);
        fragment = ClaseFragment.newInstance(nivel, premio, descripcionPremio);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, "clase");
        transaction.addToBackStack("clase");
        transaction.commit();

    }



    public void clickListener() {
        btnRespuestaUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePresenter.evaluatePregunta(preguntaBean, 0);
            }
        });
        btnRespuestaDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePresenter.evaluatePregunta(preguntaBean, 1);

            }
        });
        btnRespuestaTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePresenter.evaluatePregunta(preguntaBean, 2);

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
        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                transiciones.setVisibility(View.VISIBLE);
                transiciones.setBackground(getResources().getDrawable(R.drawable.bg_serie_complete));
            }
        });
    }

    /**
     * Set the toolbar for the activity
     */
    private void setToolbar() {
        FragmentManager fm = this.getSupportFragmentManager();
        Fragment fragment;
        //if (fragment == null) {
        fragment = ToolbarFragment.newInstance(1);
        fm.beginTransaction()
                .add(R.id.toolbar_fragment, fragment)
                .commit();
        //}
    }
}
