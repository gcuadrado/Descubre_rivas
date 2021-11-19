package com.turismorivas.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.turismorivas.modelo.PuntoDeInteres;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @version 1.0.0
 * @autor Victor Tribaldos
 *
 * Clase utilizada para guardar las preferencias del usuario
 * Guarda las preferencias para hacer visibles las actividades de inicio y animación,
 * así como el control de si es la primera vez que se usa la app
 */
public class PreferencesUsuario {


    public static final String NOMBRE_FICHERO = "usuario_preferences";
    public static final String CLAVE_SALTAR_INTRO = "saltar_intro";
    public static final String CLAVE_SALTAR_ANIM = "saltar_anim";
    public static final String CLAVE_PRIMERA_VEZ = "primera_vez";
    public static final String PUNTOS_DE_INTERES = "puntos de interes";
    public static final String JSON_LAST_TIME = "json_last_time";


    //metodo que guarda las preferencias de la checkbox
    public static void guardarPrefSaltarInicio(boolean activo , Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_FICHERO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CLAVE_SALTAR_INTRO, activo);
        editor.commit();
    }

    //metodo que recupera las preferencias de la checkbox
    public static boolean getPrefSaltarInicio(Context context){

        boolean valor = false;

            SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_FICHERO, Context.MODE_PRIVATE);
            valor = sharedPreferences.getBoolean(CLAVE_SALTAR_INTRO,false);

        return valor;
    }

    public static void guardarPrefSaltarAnim(boolean activo ,Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_FICHERO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CLAVE_SALTAR_ANIM, activo);
        editor.commit();
    }

    //metodo que recupera las preferencias de la checkbox
    public static boolean getPrefSaltarAnim(Context context){

        boolean valor = false;

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_FICHERO, Context.MODE_PRIVATE);
        valor = sharedPreferences.getBoolean(CLAVE_SALTAR_ANIM,false);

        return valor;
    }


    public static void guardarPrefPrimeraVez(boolean activo , Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_FICHERO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CLAVE_PRIMERA_VEZ, activo);
        editor.commit();
    }

    public static void guardarJSONLastTime(String json, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_FICHERO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(JSON_LAST_TIME, json);
        editor.commit();
    }
    public static String getJsonLastTime(Context context){

        String json = "";

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_FICHERO, Context.MODE_PRIVATE);
        json=sharedPreferences.getString(JSON_LAST_TIME,null);


        return json;
    }

    public static void guardarPuntosDeInteres(Map<String,PuntoDeInteres> puntoDeInteres, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_FICHERO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson=new Gson();

        editor.putString(PUNTOS_DE_INTERES, gson.toJson(puntoDeInteres));
        editor.commit();
    }

    public static Map<String,PuntoDeInteres> getPuntosInteres(Context context){
        Map<String,PuntoDeInteres> puntoDeInteres=null;
        String json = "";
        Gson gson=new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_FICHERO, Context.MODE_PRIVATE);
        json=sharedPreferences.getString(PUNTOS_DE_INTERES,null);
        puntoDeInteres=gson.fromJson(json,new TypeToken<Map<String,PuntoDeInteres>>(){}.getType());

        return puntoDeInteres;
    }

    //metodo que recupera las preferencias de la checkbox
    public static boolean getPrefPrimeraVez(Context context){

        boolean valor = false;

        SharedPreferences sharedPreferences = context.getSharedPreferences(NOMBRE_FICHERO, Context.MODE_PRIVATE);
        valor = sharedPreferences.getBoolean(CLAVE_PRIMERA_VEZ,true);

        return valor;
    }

}
