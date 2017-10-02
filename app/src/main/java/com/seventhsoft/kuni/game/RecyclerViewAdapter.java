package com.seventhsoft.kuni.game;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seventhsoft.kuni.KuniApplication;
import com.seventhsoft.kuni.R;

import static android.content.ContentValues.TAG;

/**
 * Created by olibits on 16/07/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RepositoryViewHolder> {

    private final GamePresenter gamePresenter;
    private OnCompetitionClickListener onCompetitionClickListener;

    public RecyclerViewAdapter(GamePresenter gamePresenter, OnCompetitionClickListener onCompetitionClickListener) {
        this.gamePresenter = gamePresenter;
        this.onCompetitionClickListener = onCompetitionClickListener;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_grid_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        gamePresenter.onBindRepositoryRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {

        return 5;//gamePresenter.getRepositoriesRowsCount();
    }

    public class RepositoryViewHolder extends RecyclerView.ViewHolder implements RepositoryRowView, View.OnClickListener {

        private TextView txtNivel;
        private TextView txtEstado;
        private TextView txtRecompensas;
        private ImageView imageView;
        private ImageView imageRecompensa;
        private TextView txtSeries;
        private CardView cardView;
        private Context context;
        private ProgressBar progressBar;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            txtNivel = (TextView) itemView.findViewById(R.id.txtNivel);
            txtEstado = (TextView) itemView.findViewById(R.id.txtEstado);
            txtRecompensas = (TextView) itemView.findViewById(R.id.txtRecompensas);
            txtSeries = (TextView) itemView.findViewById(R.id.txtSeries);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageRecompensa = (ImageView) itemView.findViewById(R.id.recompensa);
            imageRecompensa.setVisibility(View.GONE);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            int width = ((itemView.getResources().getDisplayMetrics().widthPixels) / 2) - 10;
            cardView.setMinimumWidth(width);
            context = KuniApplication.getContext();
            itemView.setOnClickListener(this);
        }

        public void setNivel(String title) {
            txtNivel.setText(title);
            progressBar.setVisibility(View.GONE);
        }

        public void setEstadoNivel(String subTitle) {
            txtEstado.setText(subTitle);
        }

        public void setPremiosGanados(boolean premiosGanados) {
            if (premiosGanados) {
                imageRecompensa.setVisibility(View.VISIBLE);
            } else {
                imageRecompensa.setVisibility(View.GONE);
            }
        }

        public void setPremiosDisponibles(String supportText) {
            txtRecompensas.setText(supportText);
        }

        public void setSeries(int series, int serieActual) {
            txtSeries.setText(String.format(context.getString(R.string.lbl_series_progreso), serieActual, series));
        }

        public void setImage(String resourse) {
            Glide.with(context).load(resourse).into(imageView);
        }

        public void setTextColor(boolean started) {
            if (started) {
                txtNivel.setTextColor(context.getResources().getColor(R.color.white));
                txtEstado.setTextColor(context.getResources().getColor(R.color.white));
                txtRecompensas.setTextColor(context.getResources().getColor(R.color.white));
                txtSeries.setTextColor(context.getResources().getColor(R.color.white));
            }
        }

        @Override
        public void onClick(View view) {
            Log.i(TAG, "OSE| click");
            if (onCompetitionClickListener != null)
                onCompetitionClickListener.onCompetitionClidked(getAdapterPosition());
        }
    }

}



