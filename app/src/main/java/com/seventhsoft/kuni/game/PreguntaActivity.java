package com.seventhsoft.kuni.game;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.RespuestaBean;
import com.seventhsoft.kuni.player.DialogoFragment;
import com.seventhsoft.kuni.utils.ProgressFragment;
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

public class PreguntaActivity extends AppCompatActivity implements PreguntaView, ResultadoFragment.OnFragmentInteractionListener, ClaseFragment.OnFragmentClaseInteractionListener {

    private GamePresenter gamePresenter;
    private TextView txtDescripcion;
    private TextView txtClase;
    private TextView txtDescripcionClase;
    private TextView txtEtiquetaPregunta;
    private TextView txtTiempo;
    private FrameLayout fondo;
    private Fragment fragmentToolbar;

    private RelativeLayout relative;
    private LinearLayout imageClase;
    private Button btnRespuestaUno;
    private Button btnRespuestaDos;
    private Button btnRespuestaTres;
    private Button btnSiguientePregunta;

    private PreguntaBean preguntaBean;
    private LinearLayout transiciones;
    private Fragment fragmentClase;
    private Fragment fragmentTerminado;

    private FragmentTransaction transaction;
    private ImageView cardView;
    private boolean seleccionada;
    private boolean destroyed;

    private Fragment fragmentProgress;


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
        //relative = (RelativeLayout) findViewById(R.id.relative);
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
                            preguntaBean.getSerieActual(), preguntaBean.getNumeroPregunta() + 1));
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
        transaction.remove(fragmentTerminado);
        transaction.addToBackStack("terminado");
        transaction.commitAllowingStateLoss();
        fondo.setVisibility(View.GONE);
        setToolbar();
        gamePresenter.getPregunta();

    }

    public void setClase(final PreguntaBean pregunta, final RespuestaBean respuestaBean, final boolean bien,
                         final boolean nivel, final boolean serie, final boolean premio,
                         final String descripcionPremio) {

        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {

                String ruta = "http://images.juegakuni.com.mx/images/clase/" + pregunta.getRuta();
                setClaseFragment(pregunta.getClase(), respuestaBean.getDescripcion(),
                        ruta, bien, nivel, serie,
                        premio, descripcionPremio);

                /*btnRespuestaUno.setVisibility(View.GONE);
                btnRespuestaDos.setVisibility(View.GONE);
                btnRespuestaTres.setVisibility(View.GONE);
                txtTiempo.setVisibility(View.GONE);
                int height = getResources().getDisplayMetrics().heightPixels + 225;
                Log.i(TAG, "OSE| " + "height" + height);
                Log.i(TAG, "OSE| " + "height" + convertPixelsToDp(height, getApplicationContext()));

                //relative.getLayoutParams().height = ((int) convertPixelsToDp(height, getApplicationContext()));

                txtEtiquetaPregunta.setText(getString(R.string.lbl_respuesta_correcta));
                Picasso.with(getApplicationContext()).load("http://images.juegakuni.com.mx/images/clase/" + pregunta.getRuta())
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
            }*/
            }
        });
    }

    private void setClaseFragment(String clase, String respuesta, String imagen, boolean bien,
                                  boolean nivel, boolean serie, boolean premio,
                                  String descripcionPremio) {
        fondo.setVisibility(View.VISIBLE);
        showProgress();
        fragmentClase = ClaseFragment.newInstance(clase, respuesta, imagen, bien, nivel, serie,
                premio, descripcionPremio);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragmentClase, "clase");
        transaction.addToBackStack("clase");
        transaction.commitAllowingStateLoss();
        //quitToolbar();

    }

    @Override
    public void onFragmentClaseInteraction(boolean correcta, boolean nivel, boolean serie, boolean premio, String descripcionPremio) {
        transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.remove(fragmentClase);
        transaction.addToBackStack("clase");
        transaction.commitAllowingStateLoss();
        fondo.setVisibility(View.GONE);
        //setToolbar();
        if (!serie) {
            if (correcta)
                gamePresenter.getPregunta();
            else {
                setTerminado(nivel, correcta, premio, descripcionPremio);
            }
        } else {
            setTerminado(nivel, correcta, premio, descripcionPremio);
        }
    }

    @Override
    public void setResultado(final PreguntaBean pregunta, final RespuestaBean respuestaBean, final int pressed, final int correcta,
                             final RespuestaBean respuestaBien, final boolean bien, final boolean nivel, final boolean serie,
                             final boolean premio, final String desripcionPremio) {
        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                SpannableString spanString;
                if (bien) {
                    switch (correcta) {
                        case 0:
                            btnRespuestaUno.setTextColor(getResources().getColor(R.color.verde));
                            spanString = new SpannableString(btnRespuestaUno.getText());
                            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                            btnRespuestaUno.setText(spanString);
                            break;
                        case 1:
                            btnRespuestaDos.setTextColor(getResources().getColor(R.color.verde));
                            spanString = new SpannableString(btnRespuestaDos.getText());
                            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                            btnRespuestaDos.setText(spanString);
                            break;
                        case 2:
                            btnRespuestaTres.setTextColor(getResources().getColor(R.color.verde));
                            spanString = new SpannableString(btnRespuestaTres.getText());
                            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                            btnRespuestaTres.setText(spanString);
                            break;
                    }
                } else {
                    switch (pressed) {
                        case 0:
                            btnRespuestaUno.setTextColor(getResources().getColor(R.color.primary_dark));
                            spanString = new SpannableString(btnRespuestaUno.getText());
                            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                            btnRespuestaUno.setText(spanString);
                            break;
                        case 1:
                            btnRespuestaDos.setTextColor(getResources().getColor(R.color.primary_dark));
                            spanString = new SpannableString(btnRespuestaDos.getText());
                            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                            btnRespuestaDos.setText(spanString);
                            break;
                        case 2:
                            btnRespuestaTres.setTextColor(getResources().getColor(R.color.primary_dark));
                            spanString = new SpannableString(btnRespuestaTres.getText());
                            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                            btnRespuestaTres.setText(spanString);
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
        fragmentTerminado = ResultadoFragment.newInstance(nivel, mal, premio, descripcionPremio);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragmentTerminado, "terminado");
        transaction.addToBackStack("terminado");
        transaction.remove(fragmentToolbar);
        transaction.commitAllowingStateLoss();
        //quitToolbar();

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
        fragmentToolbar = ToolbarFragment.newInstance(1);
        fm.beginTransaction()
                .remove(fragmentToolbar)
                .commitAllowingStateLoss();
        /*fondo.setVisibility(View.GONE);
        FragmentManager fm = this.getSupportFragmentManager();
        //if (fragment == null) {
        fm.beginTransaction()
                .remove(fragmentToolbar)
                .commitAllowingStateLoss();
        //}*/
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
                .commitAllowingStateLoss();
        //}
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / metrics.DENSITY_DEFAULT);
        return dp;
    }

    public void hideProgress() {
        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                fondo.setVisibility(View.GONE);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(fragmentProgress);
                transaction.addToBackStack("progress");
                transaction.commitAllowingStateLoss();
            }
        });
    }

    //view

    public void showProgress() {
        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                fondo.setVisibility(View.VISIBLE);

                fragmentProgress = ProgressFragment.newInstance();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, fragmentProgress, "progress");
                transaction.addToBackStack("progress");
                transaction.commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onNoConnection() {
        PreguntaActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if (!destroyed) {
                    DialogFragment newFragment = DialogoFragment.newInstance(3);
                    newFragment.show(getSupportFragmentManager(), "dialogo");
                }
                //Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.no_conexion), Toast.LENGTH_LONG);
                //toast.show();

               /* FragmentTransaction transactionFragment = getSupportFragmentManager().beginTransaction();
                DialogFragment newFragment = DialogoFragment.newInstance(3);
                transactionFragment.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transactionFragment.add(android.R.id.content, newFragment).addToBackStack("dialogo").commitAllowingStateLoss();*/
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        //gamePresenter.evaluatePregunta(preguntaBean, 4);
    }

    @Override
    protected void onPause() {
        super.onPause();
        destroyed = true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
        /*if (preguntaBean != null) {
            seleccionada = true;
            gamePresenter.evaluatePregunta(preguntaBean, 5);
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (preguntaBean != null && preguntaBean.getRespuestaList() != null) {
            seleccionada = true;
            destroyed = true;
            gamePresenter.evaluatePregunta(preguntaBean, 5);
        }

    }

}
