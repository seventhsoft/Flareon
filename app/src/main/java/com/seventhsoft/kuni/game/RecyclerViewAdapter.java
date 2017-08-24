package com.seventhsoft.kuni.game;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        this.onCompetitionClickListener=onCompetitionClickListener;
    }


    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "OSE| Adapter create");

        return new RepositoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_grid_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Log.i(TAG, "OSE| Adapter holder");
        gamePresenter.onBindRepositoryRowViewAtPosition(position, holder);

    }

    @Override
    public int getItemCount() {

        return 5;//gamePresenter.getRepositoriesRowsCount();
    }


    public class RepositoryViewHolder extends RecyclerView.ViewHolder implements RepositoryRowView, View.OnClickListener
    {

        private TextView txtNivel;
        private TextView txtEstado;
        private TextView txtRecompensas;
        private ImageView imageView;
        private ImageView imageRecompensa;
        private TextView txtSeries;
        private CardView cardView;
        private Context context;


        public RepositoryViewHolder(View itemView) {
            super(itemView);
            txtNivel = (TextView) itemView.findViewById(R.id.txtNivel);
            txtEstado = (TextView) itemView.findViewById(R.id.txtEstado);
            txtRecompensas = (TextView) itemView.findViewById(R.id.txtRecompensas);
            txtSeries = (TextView) itemView.findViewById(R.id.txtSeries);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageRecompensa = (ImageView) itemView.findViewById(R.id.recompensa);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            int width = ((itemView.getResources().getDisplayMetrics().widthPixels) / 2) - 10;
            cardView.setMinimumWidth(width);
            context = KuniApplication.getContext();
            itemView.setOnClickListener(this);

        }

        public void setNivel(String title) {
            txtNivel.setText(title);
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

        public void setImage(int resourse) {
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


    /*private String[] titulo;
    private String[] subTitulo;
    private String[] supportText;
    private int[] Imageid;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public RecyclerViewAdapter(Context context, String[] titulo, String[] subTitulo,
                                 String[] supportText, int[] Imageid) {
        this.mInflater = LayoutInflater.from(context);
        this.Imageid = Imageid;
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.supportText = supportText;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_grid_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtNivel.setText(titulo[position]);
        holder.txtEstado.setText(subTitulo[position]);
        holder.txtRecompensas.setText(supportText[position]);
        holder.imageView.setImageResource(Imageid[position]);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return titulo.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtNivel;
        public TextView txtEstado;
        public TextView txtRecompensas;
        public ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            txtNivel = (TextView) itemView.findViewById(R.id.gridview_text_title);
            txtEstado = (TextView) itemView.findViewById(R.id.gridview_subText);
            txtRecompensas = (TextView) itemView.findViewById(R.id.gridview_support_text);
            imageView = (ImageView) itemView.findViewById(R.id.gridview_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return titulo[id];
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    */
}

