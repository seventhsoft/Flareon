package com.seventhsoft.kuni.game;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.seventhsoft.kuni.KuniApplication;
import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.player.Login;
import com.seventhsoft.kuni.player.PlayerPresenter;
import com.seventhsoft.kuni.player.PlayerPresenterImpl;
import com.seventhsoft.kuni.player.SesionPreference;
import com.seventhsoft.kuni.player.UserActivity;
import com.seventhsoft.kuni.utils.ToolbarFragment;

import org.json.JSONException;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements MainView, OnCompetitionClickListener {


    private DrawerLayout drawerLayout;

    private static final String GRAPH_PATH = "me/permissions";
    private static final String SUCCESS = "success";

    private GridView gridView;
    private Context context;
    private ArrayList arrayList;
    private TextView txtConcurso;

    private RecyclerViewAdapter adapter;

    private SesionPreference sesionPreference;

    private PlayerPresenter playerPresenter;

    private GamePresenter gamePresenter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = KuniApplication.getContext();
        playerPresenter = new PlayerPresenterImpl(this, getApplicationContext());
        gamePresenter = new GamePresenterImpl(this, getApplicationContext());
        gamePresenter.getDashboard();
        setDrawer();
        setToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.gridView);
        int height = this.getResources().getDisplayMetrics().heightPixels;
        int alturaRecycle = height - 494;
        recyclerView.getLayoutParams().height = alturaRecycle;//(int) convertPixelsToDp(alturaRecycle, this);
        Log.i(TAG, "OSE| height recycle "+ recyclerView.getHeight());

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                2, //number of grid columns
                GridLayoutManager.VERTICAL,
                false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        if (adapter == null) {
            Log.i(TAG, "OSE| Adapter null ");

            adapter = new RecyclerViewAdapter(gamePresenter, this);
        }
        recyclerView.setAdapter(adapter);
        //gridView = (GridView) findViewById(R.id.gridView);

        sesionPreference = SesionPreference.getInstance(context);

        //gridView.setAdapter(new CustomAndroidGridViewAdapter(this, niveles, series, premios, gridViewImages));

        setBottomNavigation();
    }

    public void setDashboard(final String fecha) {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Log.i(TAG, "OSE| Run ");
                txtConcurso = (TextView) findViewById(R.id.txtConcurso);

                txtConcurso.setText(getString(R.string.dias_restantes, fecha));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    /**
     * Set the progress time
     */
    private void setProgressTime() {
        FragmentManager fm = MainActivity.this.getSupportFragmentManager();
        Fragment fragment;
        //if (fragment == null) {
        fragment = new ProgressTimeFragment();
        fm.beginTransaction()
                .add(R.id.toolbar_fragment, fragment)
                .commit();
        //}
    }

    private void setBottomNavigation() {
        Fragment fragment = new BottomNavigationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, "bottom_navegation");
        transaction.addToBackStack("bottom_navegation");
        transaction.commit();

    }

    public void onCompetitionClidked(int position) {
        Log.i(TAG, "OSE| onCompetitionClidked " + position);
        gamePresenter.setPreguntaView(position);
    }

    public void setFragmentPregunta(int position) {
        Intent intent = new Intent(MainActivity.this, PreguntaActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
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
        builder.setNivel(R.string.cerrar_sesion);
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

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / metrics.DENSITY_DEFAULT);
        return dp;
    }
}
