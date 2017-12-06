package com.tac.marcos.minhamerenda;

import android.location.Location;
import android.util.Log;

import com.example.marcos.okhttptest.Escola;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Created by Davi on 05/12/17.
 */

public class LocationHelper {



    public ArrayList<Escola> transforma(ArrayList<Escola> e, Double latitudeAtual, Double longitudeAtual){


        Location atual = new Location("");
        atual.setLatitude(latitudeAtual);
        atual.setLongitude(longitudeAtual);

        ArrayList<Escola> local = new ArrayList<Escola>();
        local = e;
        double distancia;
        int i;
        for(i = 0; i<e.size();i++){
            Location l = new Location("");
            l.setLatitude(Double.valueOf(e.get(i).getLatitude()));
            l.setLongitude(Double.valueOf(e.get(i).getLongitude()));
            distancia  = l.distanceTo(atual);
            e.get(i).setDistancia(distancia);


        }

        return local;
    }
/*
    public ArrayList<Location> ordenar(ArrayList<Location> loc, String lati, String longi) {
        Location l = new Location("");
        l.setLatitude(Double.valueOf(lati));
        l.setLongitude(Double.valueOf(longi));
        ArrayList<Location> local = new ArrayList<Location>();

        int i, j, k;
        Location[] array = new Location[loc.size()];
        for(i = 0; i<loc.size(); i++) {
            array[i] = loc.get(i);
            Log.i("mensagem", ""+loc.get(i));

        }


        for ( i = array.length; i >= 1; i--) {
            for ( j = 1; j < i; j++) {
                if (array[j - 1].distanceTo(l) > array[j].distanceTo(l)) {
                    Location aux = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = aux;
                }
            }
        }

        for(i =0; i< array.length; i++){

            local.add(array[i]);
        }







        return local;
    }
*/


    public ArrayList<Escola> ordenar(ArrayList<Escola> escola) {

        ArrayList<Escola> e = escola;
        ArrayList<Escola> escolaAux = new  ArrayList<Escola>();


        int i, j, k;
        Escola[] array = new Escola[e.size()];
        for(i = 0; i<e.size(); i++) {
            array[i] = e.get(i);
            Log.i("mensagem", ""+e.get(i));

        }


        for ( i = array.length; i >= 1; i--) {
            for ( j = 1; j < i; j++) {

                if (array[j - 1].getDistancia() > array[j].getDistancia() ){
                    Escola aux = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = aux;
                }
            }
        }

        for(i =0; i< array.length; i++){

            escolaAux.add(array[i]);
        }







        return escolaAux;
    }



}
