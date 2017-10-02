package com.seventhsoft.kuni.game;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.seventhsoft.kuni.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClaseFragment extends Fragment {

    private ImageView fondo;
    private OnFragmentInteractionListener mListener;
    private RelativeLayout frameLayout;
    private Button btnSiguiente;
    private Button btnTablero;
    private boolean nivel;
    private boolean recompensa;


    public ClaseFragment() {
        // Required empty public constructor
    }

    public static ClaseFragment newInstance(boolean nivel, boolean mal, boolean premio, String descripcionPremio) {
        ClaseFragment fragment = new ClaseFragment();
        Bundle args = new Bundle();
        args.putBoolean("mal", mal);
        args.putBoolean("nivel", nivel);
        fragment.setArguments(args);
        return fragment;
    }

    private boolean isNivel() {
        return getArguments().getBoolean("nivel");
    }

    private boolean isMal() {
        return getArguments().getBoolean("mal");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clase, container, false);
    }

    @Override

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        //fondo = (ImageView) getActivity().findViewById(R.id.imageView);
        btnSiguiente = (Button) getActivity().findViewById(R.id.btnSiguientePregunta);
        btnTablero = (Button) getActivity().findViewById(R.id.btnTablero);
        frameLayout = (RelativeLayout) getActivity().findViewById(R.id.frameLayoutImage);
        onButtonPressed();
        if (isNivel()) {
            frameLayout.setBackground(getActivity().getDrawable(R.drawable.bg_level_complete));
            //fondo.setImageDrawable(getActivity().getDrawable(R.drawable.bg_level_complete));
        } else if (!isMal()) {
            frameLayout.setBackground(getActivity().getDrawable(R.drawable.serie_incompleta));

        } else if (isMal()) {
            frameLayout.setBackground(getActivity().getDrawable(R.drawable.bg_serie_complete));
        }


    }

    public void onButtonPressed() {
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onFragmentInteraction();
                }
            }
        });
        btnTablero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
