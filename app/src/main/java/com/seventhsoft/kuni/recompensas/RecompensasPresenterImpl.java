package com.seventhsoft.kuni.recompensas;

import com.seventhsoft.kuni.KuniApplication;
import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.modelsrest.RecompensasJugadorRestResponse;
import java.util.List;

/**
 *
 * Created by olibits on 6/09/17.
 *
 */

public class RecompensasPresenterImpl implements RecompensasPresenter {

    private List<RecompensasJugadorRestResponse> recompensasJugador;
    private List<RecompensasJugadorRestResponse> recompensasConcurso;
    private RecompensasInteractor recompensasInteractor;
    private RecompensasView recompensasView;

    public RecompensasPresenterImpl(RecompensasView recompensasView) {
        this.recompensasInteractor = new RecompensasInteractorImpl(this);
        this.recompensasView = recompensasView;
    }

    @Override
    public void onBindRepositoryRowViewAtPosition(int position, ListRowView listRowView) {
        if (recompensasJugador != null) {
            listRowView.setHeader(recompensasJugador.get(position).getDescripcion());
            listRowView.setFooter(String.valueOf(recompensasJugador.get(position).getVigencia()));
        }
    }

    @Override
    public void onBindRepositoryRowViewConcurso(int position, ListRowView listRowView) {
        if (recompensasConcurso != null) {
            listRowView.setHeader(KuniApplication.getContext().getString(R.string.lbl_nivel, position+1));
            listRowView.setFooter(recompensasConcurso.get(position).getDescripcion());
            listRowView.setButton(String.valueOf(recompensasConcurso.get(position).getCantidad()));
        }
    }

    @Override
    public int setSizeJugador() {
        if (recompensasJugador != null)
            return recompensasJugador.size();
        else
            return 0;
    }

    @Override
    public int setSizeConcurso() {
        if (recompensasConcurso != null) {
            return recompensasConcurso.size();
        } else {
            return 0;
        }
    }

    public void getRecompensasJugador() {
        recompensasInteractor.getRecompensasJugador();
    }

    @Override
    public void setRecompensasJugador(List<RecompensasJugadorRestResponse> recompensasJugador) {
        this.recompensasJugador = recompensasJugador;
        recompensasView.onRecompensasJugadorSuccess();
    }

    @Override
    public void getRecompensasConcurso() {
        recompensasInteractor.getRecompensasConcurso();
    }

    @Override
    public void setRecompensasConcurso(List<RecompensasJugadorRestResponse> recompensasConcurso) {
        this.recompensasConcurso = recompensasConcurso;
        recompensasView.onRecompensasConcursoSuccess();
    }

    @Override
    public void getRecompensa(int position) {
        recompensasView.onRecompensaSuccess(recompensasJugador.get(position));
    }
    public void setRecompensasEmpty(){
        recompensasView.onRecompensasEmpty();
    }

}
