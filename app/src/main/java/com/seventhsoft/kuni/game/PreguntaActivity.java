package com.seventhsoft.kuni.game;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.RespuestaBean;
import com.seventhsoft.kuni.models.modelsrealm.Pregunta;
import com.seventhsoft.kuni.utils.ToolbarFragment;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
    private Fragment fragmentToolbar;

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
        Log.i(TAG, "OSE| " + "on create");

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

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "OSE| " + "on resume");

        /*setContentView(R.layout.activity_pregunta);
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

        }*/

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (preguntaBean != null) {
            gamePresenter.evaluatePregunta(preguntaBean, 4);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (preguntaBean != null) {
            gamePresenter.evaluatePregunta(preguntaBean, 4);
        }
    }

    public void setPregunta(final PreguntaBean pregunta) {
        this.preguntaBean = pregunta;
        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if (pregunta.getRespuestaList() != null) {
                    seleccionada = false;
                    btnRespuestaUno.setVisibility(View.VISIBLE);
                    btnRespuestaDos.setVisibility(View.VISIBLE);
                    btnRespuestaTres.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                    txtTiempo.setVisibility(View.VISIBLE);
                    ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorPregunta));
                    cardView.setBackground(colorDrawable);
                    Picasso.with(getApplicationContext()).cancelRequest(cardView);
                    Picasso.with(getApplicationContext()).load("http://images.juegakuni.com.mx")
                            .error(colorDrawable).placeholder(colorDrawable).
                            into(cardView);// + pregunta.getRuta()).into(cardView);
                    txtClase.setVisibility(View.GONE);
                    btnSiguientePregunta.setVisibility(View.GONE);
                    txtEtiquetaPregunta.setText(String.format(getApplicationContext()
                                    .getString(R.string.lbl_etiqueta_pregunta), preguntaBean.getNivelActual(),
                            preguntaBean.getSerieActual(), preguntaBean.getNumeroPregunta()+1));
                    txtDescripcion.setText(pregunta.getDescripcion());
                    btnRespuestaUno.setText(pregunta.getRespuestaList().get(0).getDescripcion());
                    btnRespuestaDos.setText(pregunta.getRespuestaList().get(1).getDescripcion());
                    btnRespuestaTres.setText(pregunta.getRespuestaList().get(2).getDescripcion());
                    btnRespuestaUno.setTextColor(getResources().getColor(R.color.monsoon));
                    btnRespuestaDos.setTextColor(getResources().getColor(R.color.monsoon));
                    btnRespuestaTres.setTextColor(getResources().getColor(R.color.monsoon));
                    clickListener();
                    timer();
                    /*new CountDownTimer(preguntaBean.getTiempo() * 1000, 1000) {
                        int contador = 0;

                        public void onTick(long millisUntilFinished) {
                            txtTiempo.setText(String.valueOf(millisUntilFinished / 1000));
                        }
                        public void onFinish() {
                            contador++;
                            txtTiempo.setText("0");
                            Log.i(TAG, "OSE| " + "on finish time, contador: " + contador);

                            if (!seleccionada && contador == 1) {
                                gamePresenter.evaluatePregunta(preguntaBean, 4);
                            }
                        }

                    }.start();*/

                }
            }
        });
    }

    public void timer() {
//build the Observable object
        final Observable<Integer> timerObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            int mSickTimer = preguntaBean.getTiempo();
            boolean contar = true;

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onStart();
                while (mSickTimer >= 0 && !seleccionada) {
                    subscriber.onNext(mSickTimer--);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        subscriber.onCompleted();
                    }
                }
                subscriber.onCompleted();
            }
        });
//Excute the observable by call *subscribe*,
// Key features here, you can control what thread excute timer and handle callback.
        timerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        if (!seleccionada) {
                            gamePresenter.evaluatePregunta(preguntaBean, 4);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        //error -> I will make it finish here
                    }

                    @Override
                    public void onNext(Integer i) {
                        txtTiempo.setText(String.valueOf(i));
                    }
                });
    }

    public void onFragmentInteraction() {
        transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.addToBackStack("clase");
        transaction.commit();
        fondo.setVisibility(View.GONE);
        setToolbar();
        gamePresenter.getPregunta();

    }

    public void setClase(final PreguntaBean pregunta, final RespuestaBean respuestaBean, final boolean bien,
                         final boolean nivel, final boolean serie, final boolean premio,
                         final String descripcionPremio) {

        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                btnRespuestaUno.setVisibility(View.GONE);
                btnRespuestaDos.setVisibility(View.GONE);
                btnRespuestaTres.setVisibility(View.GONE);
                txtTiempo.setVisibility(View.GONE);
                txtEtiquetaPregunta.setText(getString(R.string.lbl_respuesta_correcta));
                Picasso.with(getApplicationContext()).load("http://images.juegakuni.com.mx/images/clase/"+pregunta.getRuta())
                        .error(R.drawable.noclass).placeholder(R.drawable.noclass).
                        into(cardView);
                txtDescripcion.setText(respuestaBean.getDescripcion());
                txtClase.setVisibility(View.VISIBLE);
                btnSiguientePregunta.setVisibility(View.VISIBLE);
                txtClase.setText(pregunta.getClase());
                btnSiguientePregunta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!serie) {
                            if (bien)
                                gamePresenter.getPregunta();
                            else {
                                setTerminado(nivel, bien, premio, descripcionPremio);

                            }
                        } else {
                            setTerminado(nivel, bien, premio, descripcionPremio);
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
                afterColors(pregunta, respuestaBien, bien, nivel, serie, premio, desripcionPremio);

            }
        });
    }

    public void afterColors(final PreguntaBean pregunta, final RespuestaBean respuestaBean, final boolean bien,
                            final boolean nivel, final boolean serie, final boolean premio,
                            final String descripcionPremio) {
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
                        Log.i(TAG, "OSE| " + "Set clase");

                        setClase(pregunta, respuestaBean, bien, nivel, serie, premio, descripcionPremio);
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

    private void setTerminado(boolean nivel, boolean mal, boolean premio, String descripcionPremio) {
        fondo.setVisibility(View.VISIBLE);
        fragment = ClaseFragment.newInstance(nivel, mal, premio, descripcionPremio);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, "clase");
        transaction.addToBackStack("clase");
        transaction.commit();
        quitToolbar();

    }

    public void clickListener() {
        btnRespuestaUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionada = true;
                gamePresenter.evaluatePregunta(preguntaBean, 0);
            }
        });
        btnRespuestaDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionada = true;
                gamePresenter.evaluatePregunta(preguntaBean, 1);
            }
        });
        btnRespuestaTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionada = true;
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
    private void quitToolbar() {
        FragmentManager fm = this.getSupportFragmentManager();
        //if (fragment == null) {
        fm.beginTransaction()
                .remove(fragmentToolbar)
                .commit();
        //}
    }

    /**
     * Set the toolbar for the activity
     */
    private void setToolbar() {
        FragmentManager fm = this.getSupportFragmentManager();
        //if (fragment == null) {
        fragmentToolbar = ToolbarFragment.newInstance(1);
        fm.beginTransaction()
                .add(R.id.toolbar_fragment, fragmentToolbar)
                .commit();
        //}
    }
}
