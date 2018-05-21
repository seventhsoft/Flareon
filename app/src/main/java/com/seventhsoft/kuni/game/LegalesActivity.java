package com.seventhsoft.kuni.game;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.utils.ToolbarFragment;

public class LegalesActivity extends AppCompatActivity {

    private TextView txtBases;
    private TextView txtPrivacidad;
    private TextView txtReglas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legales);
        txtBases = (TextView) findViewById(R.id.txtBases);
        txtPrivacidad = (TextView) findViewById(R.id.txtPrivacidad);
        txtReglas = (TextView) findViewById(R.id.txtReglas);
        setToolbar();
        clickListener();
    }

    public void clickListener() {
        txtBases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://about.juegakuni.mx/bases.html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        txtPrivacidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("http://about.juegakuni.mx/privacidad.html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        txtReglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InstruccionesActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * Set the toolbar for the activity
     */
    private void setToolbar() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;
        //if (fragment == null) {
        fragment = ToolbarFragment.newInstance(2);
        fm.beginTransaction()
                .add(R.id.toolbar_fragment, fragment)
                .commit();
        //}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
