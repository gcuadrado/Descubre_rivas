package com.turismorivas.servicios;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;
import com.turismorivas.modelo.PuntoDeInteres;
import com.turismorivas.util.PreferencesUsuario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class DescargarFotos extends AsyncTask<Map<String,PuntoDeInteres>,Void,Void> {

    private WeakReference<Context> weakReference;
    private Map<String,PuntoDeInteres> puntoDeInteresMap;
    public DescargarFotos(Context context){
        this.weakReference=new WeakReference<>(context);
    }

    @Override
    protected Void doInBackground(Map<String,PuntoDeInteres>... puntosDeInteres) {
        Context context=weakReference.get();
        if (context!=null){
            if(puntosDeInteres[0]!=null) {
                puntoDeInteresMap=puntosDeInteres[0];
                for (PuntoDeInteres p : puntoDeInteresMap.values()) {
                    for (String foto : p.getUrl_fotos()) {
                        guardarFotos(foto, p, p.getId(), p.getUrl_fotos().indexOf(foto));
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(puntoDeInteresMap!=null && weakReference.get()!=null) {
            PreferencesUsuario.guardarPuntosDeInteres(puntoDeInteresMap, this.weakReference.get());
        }
    }

    private void guardarFotos(String foto_actual, PuntoDeInteres puntoDeInteres, int id_punto, int id_foto){
        try {
            Bitmap bitmap = drawable_from_url(foto_actual);



            String path = saveToInternalStorage(bitmap, id_punto+"", id_foto+"");
            puntoDeInteres.addPathFoto(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap drawable_from_url(String url) throws java.net.MalformedURLException, java.io.IOException {
        Bitmap x;

       /* HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
        connection.setRequestProperty("User-agent","Mozilla/4.0");

        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);*/
        url=url.trim();

        x= Picasso.get().load(url).get();

        return x;
    }
    private String saveToInternalStorage(Bitmap bitmapImage,String id_punto,String id_foto){
        ContextWrapper cw = new ContextWrapper(this.weakReference.get());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("Imagenes_POI", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,id_punto+"_"+id_foto+".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String absolutePath=mypath.getAbsolutePath();
        return absolutePath;
    }
}
