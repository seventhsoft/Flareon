package com.seventhsoft.kuni.game;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.player.UserActivity;
import com.seventhsoft.kuni.recompensas.RecompensasActivity;


public class BottomNavigationFragment extends Fragment {


    //private OnFragmentInteractionListener mListener;

    public BottomNavigationFragment() {
        // Required empty public constructor
    }


    public static BottomNavigationFragment newInstance(int menu) {
        BottomNavigationFragment fragment = new BottomNavigationFragment();
        Bundle args = new Bundle();
        args.putInt("menu", menu);

        fragment.setArguments(args);
        return fragment;
    }

    private int menu() {
        return getArguments().getInt("menu");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                getActivity().findViewById(R.id.bottom_navigation);
        switch (menu()) {
            case 1:
                bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                break;

            case 2:
                bottomNavigationView.getMenu().findItem(R.id.action_premios).setChecked(true);
                break;

            case 3:
                bottomNavigationView.getMenu().findItem(R.id.action_perfil).setChecked(true);
                break;
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                setMainActivity();
                                break;

                            case R.id.action_premios:
                                setRecompensas();
                                break;

                            case R.id.action_perfil:
                                setCuenta();
                                break;

                        }
                        return true;
                    }
                });
    }

    private void setCuenta() {
        Intent intent = new Intent(getContext(), UserActivity.class);
        intent.putExtra("bandera", 2);
        startActivity(intent);
    }

    private void setRecompensas() {
        Intent intent = new Intent(getContext(), RecompensasActivity.class);
        startActivity(intent);
    }

    private void setMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
