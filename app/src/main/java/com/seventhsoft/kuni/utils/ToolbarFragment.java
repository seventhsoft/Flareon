package com.seventhsoft.kuni.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seventhsoft.kuni.game.MainActivity;
import com.seventhsoft.kuni.R;


public class ToolbarFragment extends Fragment {

    public ToolbarFragment() {
        // Required empty public constructor
    }

    public static ToolbarFragment newInstance(int bandera) {
        Bundle args = new Bundle();
        args.putInt("bandera", bandera);

        final ToolbarFragment fragment = new ToolbarFragment();

        fragment.setArguments(args);
        return fragment;
    }

    private int getBandera() {
        return getArguments().getInt("bandera");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toolbar, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //set toolbar appearance
        //for crate home button
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(getActivity().getTitle());
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);

        int bandera = getBandera();
        switch (bandera) {
            case 0:
                activity.getSupportActionBar().setHomeAsUpIndicator((activity.getDrawable(R.drawable.menu)));
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).openDrawer();
                        Log.i("cek", "home selected");
                    }
                });
                break;
            case 1:
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }
                });
                break;
            case 2:
                view = inflater.inflate(R.layout.toolbar_prelogin, container, false);
                toolbar = (Toolbar) view.findViewById(R.id.toolbar);
                //set toolbar appearance
                //for crate home button
                activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.toolbar_prelogin)));

                activity.setSupportActionBar(toolbar);
                activity.getSupportActionBar().setTitle(getActivity().getTitle());
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setHomeButtonEnabled(true);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }
                });
                break;
        }
        /*if (bandera == 0) {
            activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.rojo)));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        } else if (bandera == 1) {
            activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.verde)));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("cek", "home selected");

                    getActivity().onBackPressed();
                }
            });
        } else if (bandera == 3) {
            activity.getSupportActionBar().setHomeAsUpIndicator((activity.getDrawable(R.drawable.ic_menu_white_24dp)));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MapaActivity) getActivity()).openDrawer();
                    Log.i("cek", "home selected");
                }
            });
        } else {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
            Log.i("cek", "home selected");

        }
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });*/
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
