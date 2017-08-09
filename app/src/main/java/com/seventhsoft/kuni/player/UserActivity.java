package com.seventhsoft.kuni.player;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.game.MainActivity;

public class UserActivity extends AppCompatActivity {

    Fragment newFragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (getIntent().hasExtra("bandera")) {
            setFragment(getIntent().getExtras().getInt("bandera"));
        }
    }

    private void setFragment(int bandera) {
        switch (bandera) {
            case 1:
                newFragment = new ForgotPasswordFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment, "forgot_password");
                transaction.addToBackStack("forgot_password");
                transaction.commit();
                break;
            case 2:
                setCuentaFragment();
                break;

        }
    }

    private void setCuentaFragment(){
        newFragment = new CuentaFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment, "cuenta");
        transaction.addToBackStack("cuenta");
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        //int count;
        //count = getSupportFragmentManager().getBackStackEntryCount();
        Fragment fragmentCuenta = getSupportFragmentManager().findFragmentByTag("cuenta");
        if (fragmentCuenta != null && fragmentCuenta.isVisible()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        /*Fragment fragmentContraseña = getSupportFragmentManager().findFragmentByTag("contraseña");
        if (fragmentContraseña != null && fragmentContraseña.isVisible()) {
            cambiarFragment(3);
        }*/
        Fragment fragmenteForgotPassword = getSupportFragmentManager().findFragmentByTag("forgot_password");
        if (fragmenteForgotPassword != null && fragmenteForgotPassword.isVisible()) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }

        Fragment fragmentClave = getSupportFragmentManager().findFragmentByTag("clave");
        if (fragmentClave != null && fragmentClave.isVisible()) {
           setCuentaFragment();
        }


    }

}