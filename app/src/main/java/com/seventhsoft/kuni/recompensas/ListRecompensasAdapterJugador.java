package com.seventhsoft.kuni.recompensas;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.game.OnCompetitionClickListener;

import static android.content.ContentValues.TAG;

/**
 * Created by olibits on 6/09/17.
 */

public class ListRecompensasAdapterJugador extends RecyclerView.Adapter<ListRecompensasAdapterJugador.ListViewHolder> {

    private RecompensasPresenter recompensasPresenter;
    private OnCompetitionClickListener onCompetitionClickListener;

    public ListRecompensasAdapterJugador(RecompensasPresenter recompensasPresenter, OnCompetitionClickListener onCompetitionClickListener) {
        this.recompensasPresenter = recompensasPresenter;
        this.onCompetitionClickListener = onCompetitionClickListener;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        recompensasPresenter.onBindRepositoryRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return recompensasPresenter.setSizeJugador();//gamePresenter.getRepositoriesRowsCount();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements ListRowView , Button.OnClickListener{

        private TextView txtHeader;
        private TextView txtFooter;
        private Button btnCanjear;
        public int size = 0;

        public ListViewHolder(View itemView) {
            super(itemView);
            txtHeader = (TextView) itemView.findViewById(R.id.txtHeader);
            txtFooter = (TextView) itemView.findViewById(R.id.txtFooter);
            btnCanjear = (Button) itemView.findViewById(R.id.btnCanjear);
            btnCanjear.setOnClickListener(this);

        }

        @Override
        public void setHeader(String header) {
            txtHeader.setText(header);
        }

        @Override
        public void setFooter(String footer) {
            txtFooter.setText(footer);
        }

        @Override
        public void setButton(String button) {

        }

        @Override
        public void setSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        @Override
        public void onClick(View view) {
            Log.i(TAG, "OSE| click");
            if (onCompetitionClickListener != null)
                onCompetitionClickListener.onCompetitionClidked(getAdapterPosition());

        }
    }
}
