package com.seventhsoft.kuni.recompensas;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.modelsrest.RecompensasJugadorRestResponse;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class RecompensasConcursoFragment extends Fragment implements RecompensasView {

    private RecyclerView recyclerView;
    private ListRecompensasAdapterConcurso adapter;
    private RecompensasPresenter recompensasPresenter;
    private TextView txtRecompensas;

    public RecompensasConcursoFragment() {
        // Required empty public constructor
    }

    public static RecompensasConcursoFragment newInstance(String param1, String param2) {
        RecompensasConcursoFragment fragment = new RecompensasConcursoFragment();
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
        return inflater.inflate(R.layout.fragment_recompensas_concurso, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        recompensasPresenter = new RecompensasPresenterImpl(this);
        recompensasPresenter.getRecompensasConcurso();
        //Log.i(TAG, "OSE| height recycle "+ recyclerView.getHeight());
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.list_recycle_view_concurso);
        txtRecompensas = (TextView) getActivity().findViewById(R.id.txtRecompensas);
        txtRecompensas.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                1, //number of grid columns
                GridLayoutManager.VERTICAL,
                false));
        //recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));

        if (adapter == null) {
            Log.i(TAG, "OSE| Adapter concurso null ");

            adapter = new ListRecompensasAdapterConcurso(recompensasPresenter);
        }
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onRecompensasJugadorSuccess() {

    }

    @Override
    public void onRecompensasConcursoSuccess() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onRecompensaSuccess(RecompensasJugadorRestResponse recompensa) {
        DialogFragment newFragment = CodigoBarrasFragment.newInstance(recompensa, 0);
        newFragment.show(getActivity().getSupportFragmentManager(), "codigo_barras");
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
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
