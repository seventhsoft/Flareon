package com.seventhsoft.kuni.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.player.Login;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class PresentacionActivity extends AppCompatActivity {

    CarouselView carouselView;
    private TextView btnComenzar;
    int[] sampleImages = {R.drawable.onboarding_uno, R.drawable.onboarding_dos};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);
        btnComenzar = (TextView) findViewById(R.id.btnComenzar);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);
        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
}
