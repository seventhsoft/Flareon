package com.seventhsoft.kuni.game;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seventhsoft.kuni.R;

/**
 * Created by olibits on 16/07/17.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private String[] titulo;
    private String[] subTitulo;
    private String[] supportText;
    private int[] Imageid;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, String[] titulo, String[] subTitulo,
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
        holder.txtTitulo.setText(titulo[position]);
        holder.txtSubTitulo.setText(subTitulo[position]);
        holder.txtSupportText.setText(supportText[position]);
        holder.imageView.setImageResource(Imageid[position]);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return titulo.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtTitulo;
        public TextView txtSubTitulo;
        public TextView txtSupportText;
        public ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            txtTitulo = (TextView) itemView.findViewById(R.id.gridview_text_title);
            txtSubTitulo = (TextView) itemView.findViewById(R.id.gridview_subText);
            txtSupportText = (TextView) itemView.findViewById(R.id.gridview_support_text);
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
}

