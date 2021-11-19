package com.turismorivas.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.turismorivas.R;
import com.turismorivas.modelo.PuntoDeInteres;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

        PuntoDeInteres p=(PuntoDeInteres)marker.getTag();
        TextView market_title = view.findViewById(R.id.tituloMarker);
        market_title.setText(p.getNombre());

        //Se crea un Bitmap con la primera imagen de ese punto de interés
        if(!p.getPath_fotos().isEmpty()) {
            String path_foto = p.getPath_fotos().get(0);
            Bitmap foto = loadImageFromStorage(path_foto);
            //Se redimensiona esa imagen para no tener problemas de memoria
            Bitmap miniatura = resize(foto, 128, 128);
            //Se coge el Imageview
            ImageView imagen = view.findViewById(R.id.imagenMarker);
            //Se inserta la imagen redimensionada en el ImageView
            imagen.setImageBitmap(miniatura);
        }



        return view;
    }

    private Bitmap loadImageFromStorage(String path) {
        Bitmap b = null;
        try {
            File f = new File(path);
            b = BitmapFactory.decodeStream(new FileInputStream(f));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    private Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }
}

