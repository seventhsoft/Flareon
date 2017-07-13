package com.seventhsoft.kuni.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seventhsoft.kuni.R;

/**
 * Created by olibits on 12/07/17.
 */

public class CustomAndroidGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private final String[] titulo;
    private final String[] subTitulo;
    private final String[] supportText;
    private final int[] Imageid;

    public CustomAndroidGridViewAdapter(Context c, String[] titulo, String[] subTitulo, String[] supportText, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.supportText = supportText;

    }

    @Override
    public int getCount() {
        return titulo.length;
    }

    @Override
    public Object getItem(int p) {
        return null;
    }

    @Override
    public long getItemId(int p) {
        return 0;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.layout_grid_view, null);
            TextView txtTitulo = (TextView) grid.findViewById(R.id.gridview_text_title);
            TextView txtSubTitulo = (TextView) grid.findViewById(R.id.gridview_subText);
            TextView txtSupportText = (TextView) grid.findViewById(R.id.gridview_support_text);

            ImageView imageView = (ImageView) grid.findViewById(R.id.gridview_image);
            txtTitulo.setText(titulo[p]);
            txtSubTitulo.setText(subTitulo[p]);
            txtSupportText.setText(supportText[p]);
            imageView.setImageResource(Imageid[p]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
