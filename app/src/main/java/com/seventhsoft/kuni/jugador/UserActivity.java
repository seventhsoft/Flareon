package com.seventhsoft.kuni.jugador;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seventhsoft.kuni.R;

public class UserActivity extends AppCompatActivity {

    Fragment newFragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (getIntent().hasExtra("bandera")) {
            seFragment(getIntent().getExtras().getInt("bandera"));
        }
    }

    private void seFragment(int bandera){
        switch (bandera) {
            case 1:
                newFragment = new SignUpFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment, "signup");
                transaction.addToBackStack("signup");
                transaction.commit();
                break;
        }
    }
}