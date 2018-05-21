package com.seventhsoft.kuni.game;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
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
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.seventhsoft.kuni.KuniApplication;
import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.PreguntaBean;
import com.seventhsoft.kuni.player.DialogoFragment;
import com.seventhsoft.kuni.player.Login;
import com.seventhsoft.kuni.player.PlayerPresenter;
import com.seventhsoft.kuni.player.PlayerPresenterImpl;
import com.seventhsoft.kuni.player.SesionPreference;
import com.seventhsoft.kuni.player.UserActivity;
import com.seventhsoft.kuni.utils.ConexionInternetPreference;
import com.seventhsoft.kuni.utils.ProgressFragment;
import com.seventhsoft.kuni.utils.ToolbarFragment;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements MainView, OnCompetitionClickListener {


    private DrawerLayout drawerLayout;

    private static final String GRAPH_PATH = "me/permissions";
    private static final String SUCCESS = "success";

    private GridView gridView;
    private Context context;
    private ArrayList arrayList;
    private TextView txtConcurso;
    private FrameLayout fondo;
    private FragmentTransaction transaction;
    private Fragment fragmentProgress;

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
        fondo = (FrameLayout) findViewById(R.id.fragment_progress);

        int height = this.getResources().getDisplayMetrics().heightPixels;
        int alturaRecycle = ((int) convertPixelsToDp(height, context));
        recyclerView.getLayoutParams().height = alturaRecycle;

        Log.i(TAG, "OSE| height recycle " + alturaRecycle + " " + height);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                2, //number of grid columns
                GridLayoutManager.VERTICAL,
                false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10, context), true));

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

    public void setDashboard(final long fecha, final long inicio) {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Log.i(TAG, "OSE| Run ");
                //txtConcurso = (TextView) findViewById(R.id.txtConcurso);

                //txtConcurso.setText(getString(R.string.dias_restantes, getDias(inicio, fecha)));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private boolean isConexionInternet() {
        Boolean conexionInternet;
        ConexionInternetPreference conexionInternetPreference = ConexionInternetPreference.
                getInstance(context);
        if (conexionInternetPreference.getData("estatusInternet")) {
            conexionInternet = true;
        } else {
            conexionInternet = false;
        }
        return conexionInternet;
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

    private String getDias(long inicio, long fin) {
        // write your code here
        //long inicio = Long.parseLong(start);
        //long fin = Long.parseLong("1501545599000");

        try {
            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, new Locale("es", "MX"));

            Date date = new Date();

            String fechaInicio = String.valueOf(df.format(date));
            String fechaFin = String.valueOf(df.format(fin));
            System.out.println(fechaInicio);
            System.out.println(fechaFin);
            try {
                Date dateInicio = df.parse(fechaInicio);
                System.out.println("Parsed date: " + dateInicio.toString());
                Date dateFin = df.parse(fechaFin);
                System.out.println("Parsed date: " + dateFin.toString());

                Map<TimeUnit, Long> dias = computeDiff(dateInicio, dateFin);
                System.out.println(dias);

                System.out.println(dias.get(TimeUnit.DAYS));
                return String.valueOf(dias.get(TimeUnit.DAYS));
            } catch (ParseException pe) {
                System.out.println("ERROR: could not parse date in string ");
                return null;

            }

            //Date date = DateFormat.getDateInstance(DateFormat.DEFAULT).parse(String.valueOf(1498885201));
            //System.out.println(date);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    private static Map<TimeUnit, Long> computeDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);
        Map<TimeUnit, Long> result = new LinkedHashMap<TimeUnit, Long>();
        long milliesRest = diffInMillies;
        for (TimeUnit unit : units) {
            long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;
            result.put(unit, diff);
        }
        return result;
    }

    private void setBottomNavigation() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;
        fragment = BottomNavigationFragment.newInstance(1);
        fm.beginTransaction()
                .add(R.id.fragment_container, fragment, "bottom_navegation").commit();
    }

    public void onCompetitionClidked(int position) {
        Log.i(TAG, "OSE| onCompetitionClidked " + position);
        gamePresenter.setPreguntaView(position);
    }

    public void setFragmentPregunta(int position) {
        if(isConexionInternet()) {
            Intent intent = new Intent(MainActivity.this, PreguntaActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }else {
            onNoConnection();
        }
    }


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
            case "Legales":
                Intent intent = new Intent(getApplicationContext(), LegalesActivity.class);
                startActivity(intent);
                break;
        }
        if (("Salir").equals(title)) {
            showProgress();
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
                        /*Toast.makeText(
                                MainActivity.this,
                                R.string.error_exception,
                                Toast.LENGTH_LONG).show();*/
                    } else if (response.getJSONObject().getBoolean(SUCCESS)) {
                        LoginManager.getInstance().logOut();
                        playerPresenter.closeSesion();
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
        hideProgress();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Converting dp to pixel
     */
    private static int dpToPx(int dp, Context context) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            return (px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_XXXHIGH))-300;
        }
        if (density >= 3.5) {
            return (px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_560))-500;
        }
        if (density >= 3.0) {
            return (px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_XXHIGH))-397;
        }
        if (density >= 2.0) {
            return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_XHIGH)-267;
        }
        if (density >= 1.5) {
            return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_HIGH)- 196;//dpToPx(56, context);//207;
        }
        if (density >= 1.0) {
            return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_MEDIUM)-130;
        }
        if(density<1){
            return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_LOW)-100;
        }
        return 0;
    }

    @Override
    public void onNoConnection() {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                DialogFragment newFragment =  DialogoFragment.newInstance(3);
                newFragment.show(getSupportFragmentManager(), "dialogo");
                //Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.no_conexion), Toast.LENGTH_LONG);
                //toast.show();
            }
        });
    }
    public void hideProgress() {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                fondo.setVisibility(View.GONE);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(fragmentProgress);
                transaction.addToBackStack("progress");
                transaction.commitAllowingStateLoss();
            }
        });
    }

    //view

    public void showProgress() {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                fondo.setVisibility(View.VISIBLE);

                fragmentProgress = ProgressFragment.newInstance();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_progress, fragmentProgress, "progress");
                transaction.addToBackStack("progress");
                transaction.commitAllowingStateLoss();
            }
        });
    }
}
