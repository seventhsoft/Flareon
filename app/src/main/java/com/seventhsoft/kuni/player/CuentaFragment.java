package com.seventhsoft.kuni.player;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.UserBean;
import com.seventhsoft.kuni.utils.ToolbarFragment;

public class CuentaFragment extends Fragment implements PlayerView {

    private FragmentManager fm;
    private EditText txtContraseña;
    private Boolean contraseñaValida;
    private EditText txtNombre;
    private EditText txtApellidos;
    private EditText txtCorreoElectronico;
    private LinearLayout layout;
    private MenuItem guardarMenuItem;
    private Button btnGuardar;
    private PlayerPresenter playerPresenter;
    private Boolean editar;
    private UserBean userBean;


    public CuentaFragment() {
        // Required empty public constructor
    }

    public static CuentaFragment newInstance(String param1, String param2) {
        CuentaFragment fragment = new CuentaFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        setToolbar();
        userBean = new UserBean();
        userBean.setApellidoEditado(false);
        editar = false;
        layout = (LinearLayout) getActivity().findViewById(R.id.mainLayout);
        contraseñaValida = true;
        txtContraseña = (EditText) getActivity().findViewById(R.id.txtContraseñaActual);
        txtNombre = (EditText) getActivity().findViewById(R.id.txtNombre);
        txtApellidos = (EditText) getActivity().findViewById(R.id.txtApellidos);
        txtCorreoElectronico = (EditText) getActivity().findViewById(R.id.txtCorreo);
        txtCorreoElectronico.setEnabled(false);
        playerPresenter = new PlayerPresenterImpl(this, getContext());
        playerPresenter.getPlayer();
        listenerDatos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_cuenta, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_guardar, menu);
        guardarMenuItem = menu.findItem(R.id.action_guardar);
        //guardarMenuItem.setVisible(true); // hide play button

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_guardar:
                editar = true;
                getActivity().invalidateOptionsMenu();
                playerPresenter.updatePlayerNames(userBean,
                        txtApellidos.getText().toString(),
                        txtNombre.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (editar) {
            guardarMenuItem.setVisible(true); // show the pause button
        } else {
            guardarMenuItem.setVisible(false); // hide the pause button
        }
    }


    /**
     * Escucha cuando se selecciona un edittext
     */

    public void listenerDatos() {

        txtNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editar = true;
                if (guardarMenuItem != null)
                    if (txtNombre.isFocused()) {
                        guardarMenuItem.setVisible(true);

                    } else {
                        guardarMenuItem.setVisible(false);
                    }
            }
        });
        txtApellidos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editar = true;
                if (guardarMenuItem != null)
                    if (txtApellidos.isFocused()) {
                        guardarMenuItem.setVisible(true);

                    } else {
                        guardarMenuItem.setVisible(false);
                    }
            }
        });

        txtContraseña.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editar = true;
                if (guardarMenuItem != null)
                    if (txtContraseña.isFocused()) {
                        setClaveFragment();
                    } else {
                        guardarMenuItem.setVisible(false);
                    }
            }
        });

    }


    /**
     * Overrides playerview
     */

    public void setEmailEmptyError() {
    }

    public void setEmailError() {
    }

    public void setPasswordError() {
    }

    public void setPasswordFormatError() {
    }

    public void setPasswordRepeatError() {
    }

    public void setLoginError() {
    }

    public void setSignUpError() {
    }

    public void setRecoverPassword() {
    }

    public void setError() {
    }

    public void setNameError() {
    }

    public void setFirstNameError() {
    }

    public void setPasswordSuccess() {
    }

    public void setLoginSuccess() {
    }

    public void setRecoverPasswordSuccess() {
    }

    public void setSignUpSuccesss() {
    }

    public void setPlayer(final UserBean usuario) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                userBean.setFirstName(usuario.getFirstName());
                userBean.setName(usuario.getName());
                txtContraseña.setText(usuario.getPassword());
                txtNombre.setText(usuario.getName());
                txtApellidos.setText(usuario.getFirstName());
                txtCorreoElectronico.setText(usuario.getEmail());

            }
        });
    }

    private void setClaveFragment(){
        fm = getActivity().getSupportFragmentManager();
        Fragment newFragment;
        FragmentTransaction transaction;
        newFragment = new ClaveFragment();
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment, "clave");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Set the toolbar for the activity
     */
    private void setToolbar() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment;
        //if (fragment == null) {
        fragment = ToolbarFragment.newInstance(2);
        fm.beginTransaction()
                .add(R.id.toolbar_fragment, fragment)
                .commit();
        //}
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
