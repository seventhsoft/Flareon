package com.seventhsoft.kuni.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.player.Login;
import com.seventhsoft.kuni.player.SesionPreference;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class InicioActivity extends AppCompatActivity {

    private Subscription subscription = null;
    private SesionPreference sesionPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        sesionPreference = SesionPreference.getInstance(this);
        splashScreen();
    }


    public void splashScreen() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            return;
        }
        subscription = Observable
                .timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onNext(Long t) {
                        if (sesionPreference.getData("statusSesion")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), PresentacionActivity.class);
                            startActivity(intent);

                        }
                        //Intent intent = new Intent(getApplicationContext(), Login.class);
                        //startActivity(intent);

                        subscription.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "OSE| " + "Error Splash" + e.getMessage());

                    }

                    @Override
                    public void onCompleted() {
                    }
                });
    }
}
