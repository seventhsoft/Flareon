package com.seventhsoft.kuni.recompensas;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.game.MainActivity;
import com.seventhsoft.kuni.game.OnCompetitionClickListener;
import com.seventhsoft.kuni.models.modelsrest.RecompensasJugadorRestResponse;
import com.seventhsoft.kuni.utils.ToolbarFragment;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class RecompensasJugadorFragment extends Fragment implements RecompensasView, OnCompetitionClickListener {

    private RecyclerView recyclerView;
    private ListRecompensasAdapterJugador adapter;
    private RecompensasPresenter recompensasPresenter;
    private FrameLayout fragmetCodigo;
    private TextView txtRecompensas;

    public RecompensasJugadorFragment() {
        // Required empty public constructor
    }


    public static RecompensasJugadorFragment newInstance(String param1, String param2) {
        RecompensasJugadorFragment fragment = new RecompensasJugadorFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recompensas_jugador, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        recompensasPresenter = new RecompensasPresenterImpl(this);
        recompensasPresenter.getRecompensasJugador();
        //Log.i(TAG, "OSE| height recycle "+ recyclerView.getHeight());
        fragmetCodigo = (FrameLayout) getActivity().findViewById(R.id.fragment_container_codigo);
        fragmetCodigo.setVisibility(View.GONE);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.list_recycle_view);
        txtRecompensas = (TextView) getActivity().findViewById(R.id.txtRecompensas);
        txtRecompensas.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                1, //number of grid columns
                GridLayoutManager.VERTICAL,
                false));
        //recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));

        if (adapter == null) {
            Log.i(TAG, "OSE| Adapter null ");

            adapter = new ListRecompensasAdapterJugador(recompensasPresenter, this);
        }
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onRecompensaSuccess(RecompensasJugadorRestResponse recompensa) {
        DialogFragment newFragment = CodigoBarrasFragment.newInstance(recompensa, 0);
        newFragment.show(getActivity().getSupportFragmentManager(), "codigo_barras");
    }

    @Override
    public void onCompetitionClidked(int position) {
        Log.i(TAG, "OSE| onCompetitionClidked");
        recompensasPresenter.getRecompensa(position);

    }

    @Override
    public void onRecompensasEmpty() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                txtRecompensas.setVisibility(View.VISIBLE);
                txtRecompensas.setText(getString(R.string.lbl_recompensas_vacias));
            }
        });

    }

    @Override
    public void onRecompensasJugadorSuccess() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onRecompensasConcursoSuccess() {

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
