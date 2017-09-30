package com.seventhsoft.kuni.game;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.RespuestaBean;
import com.seventhsoft.kuni.models.modelsrealm.Pregunta;
import com.seventhsoft.kuni.utils.ToolbarFragment;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class PreguntaActivity extends AppCompatActivity implements PreguntaView, ClaseFragment.OnFragmentInteractionListener {

    private GamePresenter gamePresenter;
    private TextView txtDescripcion;
    private TextView txtClase;
    private TextView txtDescripcionClase;
    private TextView txtEtiquetaPregunta;
    private TextView txtTiempo;
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
    private ImageView cardView;
    private boolean seleccionada;

    private Subscription subscription = null;


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
        txtEtiquetaPregunta = (TextView) findViewById(R.id.txtEtiquetaPregunta);
        txtClase = (TextView) findViewById(R.id.txtClase);
        txtTiempo = (TextView) findViewById(R.id.txtTiempo);
        //imageClase = (LinearLayout) findViewById(R.id.imageClase);
        fondo = (FrameLayout) findViewById(R.id.fragment_container);
        cardView = (ImageView) findViewById(R.id.imageView);

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
                    txtTiempo.setVisibility(View.VISIBLE);
                    cardView.setBackgroundColor(getResources().getColor(R.color.colorPregunta));
                    txtClase.setVisibility(View.GONE);
                    btnSiguientePregunta.setVisibility(View.GONE);
                    txtEtiquetaPregunta.setText(String.format(getApplicationContext()
                                    .getString(R.string.lbl_etiqueta_pregunta), preguntaBean.getNivelActual(),
                            preguntaBean.getSerieActual(), preguntaBean.getNumeroPregunta()));
                    txtDescripcion.setText(pregunta.getDescripcion());
                    btnRespuestaUno.setText(pregunta.getRespuestaList().get(0).getDescripcion());
                    btnRespuestaDos.setText(pregunta.getRespuestaList().get(1).getDescripcion());
                    btnRespuestaTres.setText(pregunta.getRespuestaList().get(2).getDescripcion());
                    btnRespuestaUno.setTextColor(getResources().getColor(R.color.monsoon));
                    btnRespuestaDos.setTextColor(getResources().getColor(R.color.monsoon));
                    btnRespuestaTres.setTextColor(getResources().getColor(R.color.monsoon));
                    new CountDownTimer(preguntaBean.getTiempo() * 1000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            txtTiempo.setText(String.valueOf(millisUntilFinished / 1000));
                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            if(!seleccionada){

                            }
                        }


                    }.start();
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

    public void setClase(final PreguntaBean pregunta, final RespuestaBean respuestaBean, final boolean nivel,
                         final boolean serie, final boolean premio, final String descripcionPremio) {

        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {

                btnRespuestaUno.setVisibility(View.GONE);
                btnRespuestaDos.setVisibility(View.GONE);
                btnRespuestaTres.setVisibility(View.GONE);
                txtTiempo.setVisibility(View.GONE);
                cardView.setBackgroundResource(R.drawable.noclass);
                txtEtiquetaPregunta.setText(getString(R.string.lbl_respuesta_correcta));
                Glide.with(getApplicationContext()).load("http://images.juegakuni.com.mx/images/clase/" + pregunta.getRuta()).into(cardView);
                txtDescripcion.setText(respuestaBean.getDescripcion());

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


    @Override
    public void setResultado(final PreguntaBean pregunta, final RespuestaBean respuestaBean, final int pressed, final int correcta,
                             final RespuestaBean respuestaBien, final boolean bien, final boolean nivel, final boolean serie,
                             final boolean premio, final String desripcionPremio) {
        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if (bien) {
                    switch (correcta) {
                        case 0:
                            btnRespuestaUno.setTextColor(getResources().getColor(R.color.verde));
                            break;
                        case 1:
                            btnRespuestaDos.setTextColor(getResources().getColor(R.color.verde));
                            break;
                        case 2:
                            btnRespuestaTres.setTextColor(getResources().getColor(R.color.verde));
                            break;
                    }
                } else {
                    switch (pressed) {
                        case 0:
                            btnRespuestaUno.setTextColor(getResources().getColor(R.color.primary_dark));

                            break;
                        case 1:
                            btnRespuestaDos.setTextColor(getResources().getColor(R.color.primary_dark));
                            break;
                        case 2:
                            btnRespuestaTres.setTextColor(getResources().getColor(R.color.primary_dark));
                            break;
                    }
                    switch (correcta) {
                        case 0:
                            btnRespuestaUno.setTextColor(getResources().getColor(R.color.verde));
                            break;
                        case 1:
                            btnRespuestaDos.setTextColor(getResources().getColor(R.color.verde));
                            break;
                        case 2:
                            btnRespuestaTres.setTextColor(getResources().getColor(R.color.verde));
                            break;
                    }
                }
                afterColors(pregunta, respuestaBien, nivel, serie, premio, desripcionPremio);

            }
        });
    }

    public void afterColors(final PreguntaBean pregunta, final RespuestaBean respuestaBean, final boolean nivel,
                            final boolean serie, final boolean premio, final String descripcionPremio) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            return;
        }
        subscription = Observable
                .timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onNext(Long t) {
                        setClase(pregunta, respuestaBean, nivel, serie, premio, descripcionPremio);
                        //Intent intent = new Intent(getApplicationContext(), Login.class);
                        //startActivity(intent);

                        subscription.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "OSE| " + "Error Splash" + e.getMessage());

                    }

                    @Override
                    public void onCompleted() {
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
        seleccionada = true;
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


    public void changeColorButton(PreguntaBean pregunta, RespuestaBean respuestaBean, int pressed, int correcta,
                                  RespuestaBean respuestaBien, boolean bien, boolean nivel, boolean serie,
                                  boolean premio, String desripcionPremio) {
        if (bien) {
            switch (correcta) {
                case 0:
                    btnRespuestaUno.setTextColor(getResources().getColor(R.color.verde));
                    break;
                case 1:
                    btnRespuestaDos.setTextColor(getResources().getColor(R.color.verde));
                    break;
                case 2:
                    btnRespuestaTres.setTextColor(getResources().getColor(R.color.verde));
                    break;
            }
        } else {
            switch (pressed) {
                case 0:
                    btnRespuestaUno.setTextColor(getResources().getColor(R.color.primary_dark));
                    break;
                case 1:
                    btnRespuestaDos.setTextColor(getResources().getColor(R.color.primary_dark));
                    break;
                case 2:
                    btnRespuestaTres.setTextColor(getResources().getColor(R.color.primary_dark));
                    break;
            }
            switch (correcta) {
                case 0:
                    btnRespuestaUno.setTextColor(getResources().getColor(R.color.verde));
                    break;
                case 1:
                    btnRespuestaDos.setTextColor(getResources().getColor(R.color.verde));
                    break;
                case 2:
                    btnRespuestaTres.setTextColor(getResources().getColor(R.color.verde));
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
