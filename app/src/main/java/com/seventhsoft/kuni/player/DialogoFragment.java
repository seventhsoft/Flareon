package com.seventhsoft.kuni.player;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.seventhsoft.kuni.R;

public class DialogoFragment extends DialogFragment {

    private int dialogo;
    private TextView txtDialogo;
    private TextView txtTituloDialogo;
    private Button btnOk;

    public DialogoFragment() {
        // Required empty public constructor
    }


    public static DialogoFragment newInstance(int dialogo) {
        DialogoFragment fragment = new DialogoFragment();
        Bundle args = new Bundle();
        args.putInt("dialogo", dialogo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dialogo = getArguments().getInt("dialogo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialogo, container, false);

        txtDialogo = (TextView) view.findViewById(R.id.txtDialogo);
        txtTituloDialogo = (TextView) view.findViewById(R.id.txtTituloDialogo);

        btnOk = (Button) view.findViewById(R.id.btnOk);

        if (dialogo == 1) {
            txtTituloDialogo.setText(getString(R.string.lbl_titulo_dialogo_contraseña));
            txtDialogo.setText(getString(R.string.lbl_dialogo_contraseña));

        } else if (dialogo == 2){
            txtTituloDialogo.setText(getString(R.string.lbl_titulo_dialogo_cuenta));
            txtDialogo.setText(getString(R.string.lbl_dialogo_cuenta));
        }else if (dialogo ==3){
            txtTituloDialogo.setText(getString(R.string.no_conexion));
            txtDialogo.setText(getString(R.string.no_conexion_revisa));
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
