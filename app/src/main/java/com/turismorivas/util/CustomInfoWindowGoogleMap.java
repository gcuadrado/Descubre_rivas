package com.turismorivas.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.turismorivas.R;
import com.turismorivas.actividades.MapaActivity;
import com.turismorivas.modelo.PuntoDeInteres;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custom_info_window, null);

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
        TextView market_title = view.findViewById(R.id.tituloMarker);
        market_title.setText(infoWindowData.getNombre_punto());
        PuntoDeInteres p=MapaActivity.getPuntoDeInteres(infoWindowData.getNum_punto());
        ImageView imagen=view.findViewById(R.id.imagenMarker);
        imagen.setImageResource(p.getFotos()[0]);



        return view;
    }
}

