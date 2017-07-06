package com.seventhsoft.kuni;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.seventhsoft.kuni.player.Login;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private static final String GRAPH_PATH = "me/permissions";
    private static final String SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDrawer();
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

    private void selectItem(String title) {
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
                        Toast.makeText(
                                MainActivity.this,
                                R.string.error_exception,
                                Toast.LENGTH_LONG).show();
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

        /*usuarioRepository.borrarUsuarios();
        sesionPreference = SesionPreference.getInstance(this);
        sesionPreference.saveData("statusSesion", false);
        pantallaUsuario(1);*/
    }

    private void setLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void setCuenta(String cuenta, String correo) {
        //txtCuenta.setText(cuenta);
        //txtCorreo.setText(correo);
    }


}
