package com.seventhsoft.kuni.game;

import android.content.Context;
import android.util.Log;

import com.seventhsoft.kuni.models.modelsrest.DashboardRestReponse;
import com.seventhsoft.kuni.player.PlayerRepositoryImpl;
import com.seventhsoft.kuni.services.RestServiceFactory;
import com.seventhsoft.kuni.services.TrackerService;

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
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by olibits on 8/08/17.
 */

public class GameInteractorImpl implements GameInteractor{

    private GamePresenter gamePresenter;
    private PlayerRepositoryImpl playerRepository;
    private String token;


    public GameInteractorImpl(GamePresenter gamePresenter, Context context) {
        this.gamePresenter = gamePresenter;
        this.playerRepository = new PlayerRepositoryImpl();
    }

    public void getDashboard(){

        token = "bearer " + playerRepository.getToken();
        TrackerService restService = RestServiceFactory.createRetrofitService(TrackerService.class,
                TrackerService.SERVICE_ENDPOINT, token);
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        restService.getDashboard()
                .subscribeOn(Schedulers.newThread())
                .observeOn(scheduler)
                .subscribe(new Subscriber<DashboardRestReponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DashboardRestReponse response) {
                        Log.i(TAG, "OSE| interactor set dash ");

                        gamePresenter.setDashboard(response,"hola gola"); //getDias(response.getConcursoRest().getFechaInicio(), response.getConcursoRest().getFechaFin()));
                    }
                });
    }

    private String getDias(long inicio, long fin) {
        // write your code here
        //long inicio = Long.parseLong(start);
        //long fin = Long.parseLong("1501545599000");

        try {
            DateFormat df =  DateFormat.getDateInstance(DateFormat.LONG, new Locale("es","MX"));

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

                Map<TimeUnit,Long> dias = computeDiff(dateInicio, dateFin);
                System.out.println(dias);

                System.out.println(dias.get(TimeUnit.DAYS));
                return String.valueOf(dias.get(TimeUnit.DAYS));
            }
            catch(ParseException pe) {
                System.out.println("ERROR: could not parse date in string ");
                return null;

            }

            //Date date = DateFormat.getDateInstance(DateFormat.DEFAULT).parse(String.valueOf(1498885201));
            //System.out.println(date);
        }catch(Exception e){
            System.out.println(e);
            return null;
        }

    }

    private static Map<TimeUnit,Long> computeDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);
        Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
        long milliesRest = diffInMillies;
        for ( TimeUnit unit : units ) {
            long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;
            result.put(unit,diff);
        }
        return result;
    }



}
