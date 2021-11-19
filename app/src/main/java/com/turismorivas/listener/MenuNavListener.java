package com.turismorivas.listener;

import android.content.Context;
import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.navigation.NavigationView;
import android.util.Log;
import android.view.MenuItem;


import com.turismorivas.actividades.MapaActivity;

/**
 * @author vale
 * @since  13/08/18.
 *
 * Listener para el Menú lateral de Navegación. Se emplea para detectar cuando un punto
 * de interés es seleccionado en el menú y llamar a la MapaActivity para que focalice el punto
 * seleccionado en el mapa
 */

public class MenuNavListener implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;//guardamos una referencia a la actividad que contiene el mapa y el menú

    public MenuNavListener (Context context)
    {
        this.context = context;
    }


    /**
     * Método que captura las selecciones del menú lateral (Navigator), y gestiona
     * la petición del usuario. Puede seleccionar un punto de interés (0-15)
     * o cambiar de tipo de mapa (opciones 16-18)
     *
     *
     * @param item el menú seleccionado
     * @return el valor devuelto por el padre
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        String menu = item.getTitle().toString();



        MapaActivity activity = (MapaActivity) context;

        switch (menu){
            case "@historiaderivas":
                activity.contacto();
                break;
            case "Híbrido":
                activity.cambiarTipoMapa(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case "Normal":
                activity.cambiarTipoMapa(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case "Satélite":
                activity.cambiarTipoMapa(GoogleMap.MAP_TYPE_SATELLITE);
                break;
                default:
                    activity.visitaPuntoDeInteres(menu);
                    break;
        }



        return false;
    }
}
