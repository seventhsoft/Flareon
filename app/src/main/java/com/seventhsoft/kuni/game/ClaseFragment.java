package com.seventhsoft.kuni.game;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.utils.ToolbarFragment;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;


public class ClaseFragment extends Fragment {

    private static final String CLASE = "clase";
    private static final String RESPUESTA = "respuesta";
    private static final String CORRECTA = "correcta";
    private static final String NIVEL = "nivel";
    private static final String SERIE = "serie";
    private static final String PREMIO = "premio";
    private static final String DESCRIPCIONPREMIO = "descripcionpremio";
    private static final String IMAGEN = "imagen";

    private String clase;
    private String respuesta;
    private boolean respuestaCorrecta;
    private boolean nivel;
    private boolean serie;
    private boolean premio;
    private String imagen;
    private String descripcionPremio;
    private Button btnSiguiente;
    private TextView txtClase;
    private TextView txtRespuestaCorrecta;
    private ImageView imagenClase;

    private OnFragmentClaseInteractionListener mListener;

    public ClaseFragment() {
        // Required empty public constructor
    }

    public static ClaseFragment newInstance(String clase, String respuesta, String imagen, boolean bien,
                                            boolean nivel, boolean serie, boolean premio,
                                            String descripcionPremio) {
        ClaseFragment fragment = new ClaseFragment();
        Bundle args = new Bundle();
        args.putString(CLASE, clase);
        args.putString(RESPUESTA, respuesta);
        args.putString(IMAGEN, imagen);
        args.putBoolean(CORRECTA, bien);
        args.putBoolean(NIVEL, nivel);
        args.putBoolean(SERIE, serie);
        args.putBoolean(PREMIO, premio);
        args.putString(DESCRIPCIONPREMIO, descripcionPremio);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clase = getArguments().getString(CLASE);
            respuesta = getArguments().getString(RESPUESTA);
            respuestaCorrecta = getArguments().getBoolean(CORRECTA);
            nivel = getArguments().getBoolean(NIVEL);
            imagen = getArguments().getString(IMAGEN);
            serie = getArguments().getBoolean(SERIE);
            premio = getArguments().getBoolean(PREMIO);
            descripcionPremio = getArguments().getString(DESCRIPCIONPREMIO);
        }
    }

    @Override

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        //fondo = (ImageView) getActivity().findViewById(R.id.imageView);
        //setToolbar();
        btnSiguiente = (Button) getActivity().findViewById(R.id.btnSiguientePregunta);
        txtClase = (TextView) getActivity().findViewById(R.id.txtClase);
        txtRespuestaCorrecta = (TextView) getActivity().findViewById(R.id.txtRespuesta);
        imagenClase = (ImageView) getActivity().findViewById(R.id.imageViewClase);
        txtRespuestaCorrecta.setText(respuesta);
        txtClase.setText(clase);
        Log.i(TAG, "OSE| imagen: "+ imagen);

        Picasso.with(getActivity()).load(imagen)
                .error(R.drawable.noclass)//.placeholder(R.drawable.noclass)
                .into(imagenClase, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "OSE| Si se carho la imagen");

                    }

                    @Override
                    public void onError() {
                        Log.e(TAG, "OSE| Error al cargar la imagen");

                    }
                });
        onButtonPressed(respuestaCorrecta, nivel, serie, premio, descripcionPremio);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clase, container, false);
    }

    public void onButtonPressed(final boolean correcta, final boolean nivel, final boolean serie,
                                final boolean premio, final String descripcionPremio) {
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onFragmentClaseInteraction(correcta, nivel, serie, premio, descripcionPremio);
                }
            }
        });

    }
    /**
     * Set the toolbar for the activity
     */
    private void setToolbar() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment;
        //if (fragment == null) {
        fragment = ToolbarFragment.newInstance(1);
        fm.beginTransaction()
                .add(R.id.toolbar_fragment, fragment)
                .commitAllowingStateLoss();
        //}
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentClaseInteractionListener) {
            mListener = (OnFragmentClaseInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentClaseInteractionListener {
        void onFragmentClaseInteraction(boolean correcta, boolean nivel, boolean serie, boolean premio,
                                        String descripcionPremio);
    }
}
