package com.turismorivas.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.turismorivas.R;
import com.turismorivas.actividades.MapaActivity;
import com.turismorivas.actividades.PuntoDeInteresActivity;
import com.turismorivas.modelo.PuntoDeInteres;

public class CustomInfoWindowAdapter extends Activity implements GoogleMap.InfoWindowAdapter {
    private final View mWindow;
    private final Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        this.mContext = context;
        mWindow= LayoutInflater.from(context).inflate(R.layout.custom_info_window,null);
    }

    private void rellenarInfoWindow(Marker marker, View view){

        //Bundle bundle=getIntent().getExtras();
        //int npi = bundle.getInt(Constantes.CLAVE_INTENT_PI, -1);
        //PuntoDeInteres punto=MapaActivity.getPuntoDeInteres(npi);
        PuntoDeInteresActivity.getPuntoDeInteresActual();
        //String titulo=marker.getTitle();
        //String descripcion=punto.getInfo_basica();
        ImageView imagenMarker=findViewById(R.id.imagenMarker);
        TextView tituloMarker=findViewById(R.id.tituloMarker);
        TextView descripcionMarker=findViewById(R.id.descripcionMarker);
        tituloMarker.setText("Hola");
        descripcionMarker.setText("descripcion");
        //int [] array_fotos=punto.getFotos();
        //Drawable imagen=getResources().getDrawable(array_fotos[0]);
        //imagenMarker.setImageDrawable(imagen);
        imagenMarker.setImageResource(R.drawable.elporcal_5);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rellenarInfoWindow(marker,mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rellenarInfoWindow(marker,mWindow);
        return mWindow;
    }
}
