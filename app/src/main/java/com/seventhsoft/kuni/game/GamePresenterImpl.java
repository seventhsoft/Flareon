package com.seventhsoft.kuni.game;

import android.content.Context;
import android.util.Log;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.models.RespuestaBean;
import com.seventhsoft.kuni.models.modelsrealm.Pregunta;
import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;

import static android.content.ContentValues.TAG;


/**
 * Created by olibits on 8/08/17.
 */

public class GamePresenterImpl implements GamePresenter {

    private MainView mainView;
    private PreguntaView preguntaView;
    private GameInteractor gameInteractor;
    private RecyclerViewAdapter.RepositoryViewHolder holder;
    private DashboardRestReponse dashboardRestReponse;
    private Context context;

    public static String[] gridViewImagesStarted = {
            "http://images.juegakuni.com.mx/images/bg_lv1_started.png",
            "http://images.juegakuni.com.mx/images/bg_lv2_started.png",
            "http://images.juegakuni.com.mx/images/bg_lv3_started.png",
            "http://images.juegakuni.com.mx/images/bg_lv4_started.png",
            "http://images.juegakuni.com.mx/images/bg_lv5_started.png",
            "http://images.juegakuni.com.mx/images/bg_lv6_started.png"
    };

    public static String[] gridViewImagesUnstarted = {
            "http://images.juegakuni.com.mx/images/bg_lv1_unstarted.png",
            "http://images.juegakuni.com.mx/images/bg_lv2_unstarted.png",
            "http://images.juegakuni.com.mx/images/bg_lv3_unstarted.png",
            "http://images.juegakuni.com.mx/images/bg_lv4_unstarted.png",
            "http://images.juegakuni.com.mx/images/bg_lv5_unstarted.png",
            "http://images.juegakuni.com.mx/images/bg_lv6_unstarted.png"
    };


    public GamePresenterImpl(MainView mainView, Context context) {
        this.mainView = mainView;
        this.gameInteractor = new GameInteractorImpl(this, context);
        this.context = context;

    }

    public GamePresenterImpl(PreguntaView preguntaView, Context context) {
        this.preguntaView = preguntaView;
        this.gameInteractor = new GameInteractorImpl(this, context);
        this.context = context;

    }

    public void getDashboard() {
        Log.i(TAG, "OSE| presenter get dash ");
        gameInteractor.getDashboard();
    }

    public void setDashboard() {

    }

    public void setDashboard(DashboardRestReponse dashboardResponse, String fecha) {
        Log.i(TAG, "OSE| Presenter set dash");
        this.dashboardRestReponse = dashboardResponse;
        mainView.setDashboard(fecha);
    }

    public void onBindRepositoryRowViewAtPosition(int position, RepositoryRowView rowView) {
        //Log.i(TAG, "OSE| Presenter Blind");
        if (dashboardRestReponse != null) {
            //set nivel
            rowView.setNivel(context.getString(R.string.lbl_nivel, dashboardRestReponse.getNiveles().get(position).getNivel()));

            //set estado nivel y series
            int nivel = dashboardRestReponse.getJugadorNivel().getNivel();
            int serie = dashboardRestReponse.getJugadorNivel().getSerieActual();

            if (nivel == position + 1) {
                rowView.setEstadoNivel(context.getString(R.string.lbl_estado_nivel_actual));
                if (serie == 1 && dashboardRestReponse.getNiveles().get(position).getSeriesJugador() == 0) {
                    rowView.setSeries(dashboardRestReponse.getNiveles().get(position).getSeries(), 0);
                } else {
                    rowView.setSeries(dashboardRestReponse.getNiveles().get(position).getSeries(), serie);
                }
                rowView.setImage(gridViewImagesStarted[position]);
                rowView.setTextColor(true);

            } else if (position + 1 < nivel) {
                rowView.setEstadoNivel(context.getString(R.string.lbl_estado_nivel_terminado));
                rowView.setSeries(dashboardRestReponse.getNiveles().get(position).getSeries(),
                        dashboardRestReponse.getNiveles().get(position).getSeries());
                rowView.setImage(gridViewImagesStarted[position]);
                rowView.setTextColor(true);

            } else {
                rowView.setSeries(dashboardRestReponse.getNiveles().get(position).getSeries(), 0);
                rowView.setImage(gridViewImagesUnstarted[position]);

            }
            //set imagen fondo
            //set premios ganados
            if (dashboardRestReponse.getNiveles().get(position).getTieneRecompensa()) {
                rowView.setPremiosGanados(true);
            } else {
                rowView.setPremiosGanados(false);

            }

            rowView.setPremiosDisponibles(context.getString(R.string.lbl_premios_disponibles, dashboardRestReponse.getNiveles().get(position).getRecompensasDisponibles()));
            if (position == 0) {
                gameInteractor.getSerie(dashboardRestReponse);
            }
        }
    }

    public void actualizarSerie() {

    }

    public void setSuccessSerie() {
        preguntaView.setSerieUp();

    }

    public void setSuccessPregunta(PreguntaBean pregunta, RespuestaBean respuestaBean) {
        //preguntaView.setClase(pregunta, respuestaBean);
        //preguntaView.setClase(pregunta, respuestaBean);
        getPregunta();
    }

    public void setFail() {
        //preguntaView.setClase(boolean bien);


    }

    public void setPreguntaView(int position) {
        if (position + 1 == dashboardRestReponse.getJugadorNivel().getNivel()) {
            mainView.setFragmentPregunta(position);

        }
    }

    public void getPregunta() {
        gameInteractor.getPregunta();
    }

    public void setPregunta(PreguntaBean pregunta) {
        if (preguntaView != null && pregunta != null)
            preguntaView.setPregunta(pregunta);
    }

    public void evaluatePregunta(PreguntaBean preguntaBean, int position) {

        if (preguntaBean != null && preguntaView != null) {
            gameInteractor.evaluarPregunta(preguntaBean, position);
        }
    }

    public void setClase(PreguntaBean pregunta, RespuestaBean respuestaBean, boolean nivel, boolean serie, boolean premio, String desripcionPremio){
        preguntaView.setClase(pregunta, respuestaBean, nivel, serie, premio, desripcionPremio);
    }

    @Override
    public void setChangeColorsResponse(int position, boolean correcta) {
        preguntaView.changeColorButton(position, correcta);
    }
}
