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
 * Created by olibits on 9/09/17.
 */

public class ListRecompensasAdapterConcurso extends RecyclerView.Adapter<ListRecompensasAdapterConcurso.ListViewHolderConcurso> {

    private RecompensasPresenter recompensasPresenter;


    public ListRecompensasAdapterConcurso(RecompensasPresenter recompensasPresenter) {
        this.recompensasPresenter = recompensasPresenter;

    }

    @Override
    public ListRecompensasAdapterConcurso.ListViewHolderConcurso onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "OSE| Adapter create concurso");
        return new ListRecompensasAdapterConcurso.ListViewHolderConcurso(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ListRecompensasAdapterConcurso.ListViewHolderConcurso holder, int position) {
        Log.i(TAG, "OSE| Adapter holder concurso");
        recompensasPresenter.onBindRepositoryRowViewConcurso(position, holder);
    }

    @Override
    public int getItemCount() {
        return recompensasPresenter.setSizeConcurso();//gamePresenter.getRepositoriesRowsCount();
    }

    public class ListViewHolderConcurso extends RecyclerView.ViewHolder implements ListRowView {

        private TextView txtHeader;
        private TextView txtFooter;
        private Button btnCanjear;
        public int size = 0;

        public ListViewHolderConcurso(View itemView) {
            super(itemView);
            txtHeader = (TextView) itemView.findViewById(R.id.txtHeader);
            txtFooter = (TextView) itemView.findViewById(R.id.txtFooter);
            btnCanjear = (Button) itemView.findViewById(R.id.btnCanjear);
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
            btnCanjear.setText(button);
        }

        @Override
        public void setSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }


        public void onClickListener(){
            btnCanjear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
