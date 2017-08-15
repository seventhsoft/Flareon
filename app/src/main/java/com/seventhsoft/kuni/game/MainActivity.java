package com.seventhsoft.kuni.game;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.modelsrest.Concurso;
import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;
import com.seventhsoft.kuni.player.Login;
import com.seventhsoft.kuni.player.PlayerPresenter;
import com.seventhsoft.kuni.player.PlayerPresenterImpl;
import com.seventhsoft.kuni.player.SesionPreference;
import com.seventhsoft.kuni.player.UserActivity;
import com.seventhsoft.kuni.utils.ToolbarFragment;

import org.json.JSONException;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MainView {


    private DrawerLayout drawerLayout;

    private static final String GRAPH_PATH = "me/permissions";
    private static final String SUCCESS = "success";

    private GridView gridView;
    private Context context;
    private ArrayList arrayList;
    private TextView txtConcurso;

    RecyclerViewAdapter adapter;

    private SesionPreference sesionPreference;

    private PlayerPresenter playerPresenter;

    private GamePresenter gamePresenter;

    public static String[] niveles = {
            "Nivel 1",
            "Nivel 2",
            "Nivel 3",
            "Nivel 4",
            "Nivel 5",
    };

    public static String[] series = {
            "0/10 series",
            "10/25 series",
            "50/50 series",
            "80/80 series",
            "120/120 series",
    };

    public static String[] premios = {
            "Premios agotados",
            "Restan 2 premios",
            "Restan 30 premios",
            "Restan 20 premios",
            "Restan 5 premios",
    };

    public static int[] gridViewImages = {
            R.drawable.ic_account_black_24dp,
            R.drawable.ic_calendario_azul_24dp,
            R.drawable.ic_comparable_agregar_black_24dp,
            R.drawable.ic_comparable_filtro_azul_24dp,
            R.drawable.ic_comparable_renta_azul_24dp,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerPresenter = new PlayerPresenterImpl(this, getApplicationContext());
        gamePresenter = new GamePresenterImpl(this, getApplicationContext());
        gamePresenter.getDashboard();
        setDrawer();
        setToolbar();
        //gridView = (GridView) findViewById(R.id.gridView);

        sesionPreference = SesionPreference.getInstance(context);

        //gridView.setAdapter(new CustomAndroidGridViewAdapter(this, niveles, series, premios, gridViewImages));

        setBottomNavigation();
    }

    public void setDashboard(String fecha) {
        txtConcurso = (TextView) findViewById(R.id.txtConcurso);
        txtConcurso.setText(getString(R.string.dias_restantes, fecha));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gridView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RecyclerViewAdapter(gamePresenter);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void setBottomNavigation() {
        Fragment fragment = new BottomNavigationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, "bottom_navegation");
        transaction.addToBackStack("cuenta");
        transaction.commit();

    }

    // @Override
    public void onItemClick(View view, int position) {
        /*Log.i("OSE", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        QuestionFragment questionFragment;
        Question question = new Question();
        questionFragment = QuestionFragment.newInstance(question);


        // Inflate transitions to apply
        Transition changeTransform = TransitionInflater.from(this).inflateTransition(R.transition.change_image_transform);

        Transition explodeTransform = TransitionInflater.from(this).inflateTransition(android.R.transition.explode);

        //Setup exit transition on first fragment
        //heroesListFragment.setSharedElementReturnTransition(changeTransform);
        //heroesListFragment.setExitTransition(explodeTransform);

        // Setup enter transition on second fragment
        questionFragment.setSharedElementEnterTransition(changeTransform);
        questionFragment.setEnterTransition(explodeTransform);
        view.animate().translationX(-view.findViewById(R.id.)).setDuration(600);
        transaction.addSharedElement(.getIvAvatar(), getString(R.string.transition_avatar));
        JSONObject object = new JSONObject();
        object.

        transaction.addToBackStack(null);2

        transaction.replace(R.id.main_container, heroeDetailFragment);

        transaction.commit();*/
    }

    /*private void listenerGrid() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }

        });
    }

    public void goToHeroeDetail(Question question, HeroesAdapter.Holder holder) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        QuestionFragment questionFragment;
        questionFragment = QuestionFragment.newInstance(question);


        // Inflate transitions to apply
        Transition changeTransform = TransitionInflater.from(this).inflateTransition(R.transition.change_image_transform);

        Transition explodeTransform = TransitionInflater.from(this).inflateTransition(android.R.transition.explode);

        //Setup exit transition on first fragment
        heroesListFragment.setSharedElementReturnTransition(changeTransform);
        heroesListFragment.setExitTransition(explodeTransform);

        // Setup enter transition on second fragment
        questionFragment.setSharedElementEnterTransition(changeTransform);
        questionFragment.setEnterTransition(explodeTransform);

        transaction.addSharedElement(holder.getIvAvatar(), getString(R.string.transition_avatar));


        transaction.addToBackStack(null);

        transaction.replace(R.id.main_container, heroeDetailFragment);

        transaction.commit();
    }*/

    /**
     * Set the toolbar for the activity
     */
    private void setToolbar() {
        FragmentManager fm = MainActivity.this.getSupportFragmentManager();
        Fragment fragment;
        //if (fragment == null) {
        fragment = ToolbarFragment.newInstance(0);
        fm.beginTransaction()
                .add(R.id.toolbar_fragment, fragment)
                .commit();
        //}
    }

    private void setDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {

            //View header = navigationView.getHeaderView(0);
            //txtCuenta = (TextView) header.findViewById(R.id.txtCuenta);
            //txtCorreo = (TextView) header.findViewById(R.id.txtCorreo);
            //usuarioPresenter.getCuenta();
        }
        String drawerTitle = getResources().getString(R.string.lbl_inicio);
        setupDrawerContent(navigationView);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        //menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();
                        selectItem(title);
                        return false;
                    }
                }
        );

    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);

    }

    private void selectItem(String title) {
        switch (title) {
            case "Mi cuenta":
                setCuenta();
                break;
        }
        if (("Salir").equals(title)) {
            cerrarSesion();
        }
        drawerLayout.closeDrawers(); // Cerrar drawer
        drawerLayout.clearFocus();
        setTitle(title); // Setear título actual

    }

    private void setDialog() {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.cerrar_sesion);
        builder.setMessage(R.string.confirmacion_cerrar_sesion);
        builder.setPositiveButton(R.string.btn_aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                cerrarSesion();
            }
        }).setNegativeButton(R.string.btn_cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();*/
    }


    private void cerrarSesion() {
        GraphRequest.Callback callback = new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                try {
                    if (response.getError() != null) {
                        playerPresenter.closeSesion();
                        setLogin();
                        /*Toast.makeText(
                                MainActivity.this,
                                R.string.error_exception,
                                Toast.LENGTH_LONG).show();*/
                    } else if (response.getJSONObject().getBoolean(SUCCESS)) {
                        LoginManager.getInstance().logOut();
                        setLogin();
                        // updateUI();?
                    }
                } catch (JSONException ex) { /* no op */ }
            }
        };
        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                GRAPH_PATH, new Bundle(), HttpMethod.DELETE, callback);
        request.executeAsync();

    }

    private void setLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void setCuenta() {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("bandera", 2);
        startActivity(intent);
    }

    public void onSesionClosed() {
        setLogin();
    }


}
