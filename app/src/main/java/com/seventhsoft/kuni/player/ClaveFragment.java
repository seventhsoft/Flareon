package com.seventhsoft.kuni.player;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.UserBean;
import com.seventhsoft.kuni.utils.ToolbarFragment;

public class ClaveFragment extends Fragment implements PlayerView {

    private FragmentManager fm;
    private EditText txtContraseña;
    private AutoCompleteTextView txtContraseñaAutocomplete;
    private EditText txtContraseñaNueva;
    private EditText txtContraseñaRepetida;
    private String contraseñaActual;
    private Boolean contraseñaValida;
    private MenuItem guardarMenuItem;

    //private TextView lblContraseñaRepetida;
    private TextView lblContraseñaActual;
    private TextView lblContraseñaNueva;
    private PlayerPresenter playerPresenter;

    public ClaveFragment() {
        // Required empty public constructor
    }

    public static ClaveFragment newInstance(String param1, String param2) {
        ClaveFragment fragment = new ClaveFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setToolbar();
        //contraseñaActual = getArguments().getString("contraseña");

        return inflater.inflate(R.layout.fragment_clave, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        getActivity().setTitle("Editar contraseña");

        txtContraseña = (EditText) getActivity().findViewById(R.id.txtContraseñaActual);
        txtContraseñaNueva = (EditText) getActivity().findViewById(R.id.txtContraseñaNueva);
        txtContraseñaRepetida = (EditText) getActivity().findViewById(R.id.txtContraseñaRepetida);
        lblContraseñaActual = (TextView) getActivity().findViewById(R.id.lblContraseñaActual);
        lblContraseñaNueva = (TextView) getActivity().findViewById(R.id.lblContraseñaRepetida);
        playerPresenter = new PlayerPresenterImpl(this, getContext());
        listenerDatos();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_guardar, menu);
        guardarMenuItem = menu.findItem(R.id.action_guardar);
        //guardarMenuItem.setVisible(true); // hide play button
        guardarMenuItem.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_guardar:
                    getActivity().invalidateOptionsMenu();
                    playerPresenter.validatePasswordUpdate(txtContraseña.getText().toString(),
                            txtContraseñaNueva.getText().toString(),
                            txtContraseñaRepetida.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void listenerDatos() {

        txtContraseñaRepetida.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (guardarMenuItem != null)
                    if (txtContraseñaRepetida.isFocused()) {
                        guardarMenuItem.setVisible(true);
                        txtContraseñaRepetida.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                //userBean.setNombreEditado(true);
                            }
                        });
                    } else {
                        guardarMenuItem.setVisible(false);
                    }
            }
        });
    }
    public void setContraseñaValida(Boolean valida) {
        contraseñaValida = valida;
        if (!valida) {
            txtContraseña.setTextColor(getResources().getColor(R.color.monsoon));
            txtContraseña.setEnabled(false);
            txtContraseñaRepetida.setVisibility(View.VISIBLE);
            lblContraseñaNueva.setVisibility(View.VISIBLE);
            txtContraseñaNueva.setVisibility(View.VISIBLE);
            txtContraseñaNueva.requestFocus();
        } else {
            guardarMenuItem.setVisible(true);
            txtContraseñaRepetida.setTextColor(getResources().getColor(R.color.monsoon));
            txtContraseñaRepetida.setEnabled(false);
        }
    }

    /**
     * Overides PlayerView
     */

    public void setEmailEmptyError() {
        //txtEmail.setError(getString(R.string.error_correo));

    }

    public void setEmailError() {
       // txtEmail.setError(getString(R.string.error_correo_formato));

    }

    public void setPasswordError() {
        //txtPassword.setError(getString(R.string.error_contraseña));
    }

    public void setPasswordFormatError() {
        //txtPassword.setError(getString(R.string.error_contraseña_formato));
    }

    public void setPasswordSuccess() {
        //correctPassword = true;

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

        //txtName.setError(getString(R.string.error_nombre));
    }

    public void setFirstNameError() {
        //txtFirstName.setError(getString(R.string.error_apellido_paterno));
    }

    public void setPasswordRepeatError() {
        //txtPasswordRepeat.setError(getString(R.string.error_contraseña_repetida));
    }

    public void setLoginSuccess() {

    }

    public void setRecoverPasswordSuccess() {

    }

    public void setSignUpSuccesss() {


    }

    public void setPlayer(final UserBean usuario) {
    }

    /**
     *
     */

    public void ocultarTeclado() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Set the toolbar for the activity
     */
    private void setToolbar() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment;
        //if (fragment == null) {
        fragment = ToolbarFragment.newInstance(1);
        fm.beginTransaction()
                .add(R.id.toolbar_fragment, fragment)
                .commit();
        //}
    }


}
