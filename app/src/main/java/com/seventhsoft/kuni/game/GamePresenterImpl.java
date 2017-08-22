package com.seventhsoft.kuni.game;

import android.content.Context;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;


/**
 * Created by olibits on 8/08/17.
 */

public class GamePresenterImpl implements GamePresenter {

    private MainView mainView;
    private GameInteractor gameInteractor;
    private RecyclerViewAdapter.RepositoryViewHolder holder;
    private DashboardRestReponse dashboardRestReponse;
    private Context context;


    public GamePresenterImpl(MainView mainView, Context context) {
        this.mainView = mainView;
        this.gameInteractor = new GameInteractorImpl(this, context);
        this.context = context;

    }

    public void getDashboard() {
        gameInteractor.getDashboard();
    }

    public void setDashboard() {

    }

    public void setDashboard(DashboardRestReponse dashboardResponse, String fecha) {
        this.dashboardRestReponse = dashboardResponse;
        mainView.setDashboard(fecha);
    }

    public void onBindRepositoryRowViewAtPosition(int position, RepositoryRowView rowView) {
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
        } else if (position + 1 < nivel) {
            rowView.setEstadoNivel(context.getString(R.string.lbl_estado_nivel_terminado));
            rowView.setSeries(dashboardRestReponse.getNiveles().get(position).getSeries(),
                    dashboardRestReponse.getNiveles().get(position).getSeries());

        } else {
            rowView.setSeries(dashboardRestReponse.getNiveles().get(position).getSeries(), 0);

        }
        //set imagen fondo
        rowView.setImage(R.drawable.moon_flat);

        //set premios ganados
        if (dashboardRestReponse.getNiveles().get(position).getTieneRecompensa()) {
            rowView.setPremiosGanados(true);
        } else {
            rowView.setPremiosGanados(false);

        }

        //set premios disponibles
        rowView.setPremiosDisponibles(context.getString(R.string.lbl_premios_disponibles, dashboardRestReponse.getNiveles().get(position).getRecompensasDisponibles()));
        //gameInteractor.getDashboard();
    }


}
