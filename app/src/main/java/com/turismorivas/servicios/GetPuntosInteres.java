package com.turismorivas.servicios;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.turismorivas.modelo.PuntoDeInteres;
import com.turismorivas.util.PreferencesUsuario;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetPuntosInteres extends AsyncTask<Context,Void, Map<String,PuntoDeInteres>> {
    private Map<String,PuntoDeInteres> puntos_interes;
    private Context context;

    @Override
    protected Map<String,PuntoDeInteres> doInBackground(Context... contexts) {
            Log.d("stop", "doinbackground");

            String json_puntos=getJsonPuntos();
            this.puntos_interes=new TreeMap<>();
            String json_last_time=PreferencesUsuario.getJsonLastTime(contexts[0]);
            if(!json_puntos.equals(json_last_time)) {
                    PreferencesUsuario.guardarJSONLastTime(json_puntos,contexts[0]);
                this.context = contexts[0];
                this.puntos_interes = contruirPuntosDeInteres(json_puntos,contexts[0]);

            }else{
                //Comprobamos que cuando todos los puntos de interés guardados en las Preferences han descargado completamente sus fotos
                Map<String,PuntoDeInteres> puntosGuardados=PreferencesUsuario.getPuntosInteres(contexts[0]);
                if(puntosGuardados!=null){
                    boolean ok=true;
                    for(PuntoDeInteres p: puntosGuardados.values()){
                        if(p.getPath_fotos().size()!=p.getUrl_fotos().size()){
                            ok=false;
                            break;
                        }
                    }
                    if(!ok){
                        PreferencesUsuario.guardarJSONLastTime(json_puntos,contexts[0]);
                        this.context = contexts[0];
                        this.puntos_interes = contruirPuntosDeInteres(json_puntos,contexts[0]);
                    }else{
                        puntos_interes=null;
                    }

                }else{
                    PreferencesUsuario.guardarJSONLastTime(json_puntos,contexts[0]);
                    this.context = contexts[0];
                    this.puntos_interes = contruirPuntosDeInteres(json_puntos,contexts[0]);
                }

            }


            return puntos_interes;

    }



    private String getJsonPuntos(){
       OkHttpClient client = new OkHttpClient.Builder().build();

       String json_puntos = "";

       Request hacerGet = new Request.Builder()
               .url("https://www.descubrerivas.es/get_fotos_punto.php")
               .get()
               .build();
       try (Response response = client.newCall(hacerGet).execute()) {
           json_puntos = response.body().string();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return json_puntos;
    }

   private Map<String,PuntoDeInteres> contruirPuntosDeInteres(String info_punto,Context context){

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(info_punto);
        JsonArray jsonArray = element.getAsJsonArray();
        //Vaciamos el directorio de las path_fotos para no acumular las que se borren del servidor
       vaciarDirectorioFotos();

        for(int i=0;i<jsonArray.size();i++){
            JsonObject punto_actual=jsonArray.get(i).getAsJsonObject();
            int id= punto_actual.get("id").getAsInt();
            String nombre= punto_actual.get("nombre").getAsString();
            String info_basica=punto_actual.get("info_basica").getAsString();
            String info_detallada=punto_actual.get("info_detallada").getAsString();
            String fecha_inicio=punto_actual.get("fecha_inicio").getAsString();
            String direccion=punto_actual.get("direccion").getAsString();
            float latitud=punto_actual.get("latitud").getAsFloat();
            float longitud=punto_actual.get("longitud").getAsFloat();
            boolean accesibilidad=punto_actual.get("accesibilidad").getAsBoolean();
            String horario=punto_actual.get("horario").getAsString();
            String contacto=punto_actual.get("contacto").getAsString();
            String enlace_info=punto_actual.get("enlace_info").getAsString();
            String url_fotos="";
            try {
                 url_fotos = punto_actual.get("url_fotos").getAsString();
            }catch (Exception e){
                url_fotos="NULL";
            }
            List<String> fotos=getFotosPunto(i+1,context,url_fotos);
            float coste=punto_actual.get("coste").getAsFloat();
            PuntoDeInteres.CATEGORIA categorias=PuntoDeInteres.CATEGORIA.valueOf(punto_actual.get("categoria").getAsString());





            PuntoDeInteres p=new PuntoDeInteres(id,nombre,fecha_inicio,direccion,latitud,longitud,accesibilidad,fotos,contacto,enlace_info,info_basica,info_detallada,4f,horario,coste,categorias);
            puntos_interes.put(p.getNombre(),p);

        }
        return puntos_interes;
    }

    private List<String> getFotosPunto(int id,Context context, String urls){
        List<String> url_fotos=new ArrayList<>();
        if(!urls.equals("NULL")) {
            OkHttpClient client = new OkHttpClient.Builder().build();
             url_fotos = Arrays.asList(urls.split(","));






              /*  for (int i = 0; i < url_fotos.size(); i++) {
                    String foto_actual = url_fotos.get(i);
                    guardarFotos(foto_actual, fotos,id,i+1);

                }*/


        }

        return url_fotos;
    }


    private void vaciarDirectorioFotos(){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("Imagenes_POI", Context.MODE_PRIVATE);

        //BORRAMOS TODAS LAS IMAGENES ANTES DE DESCARGAR, PARA NO ALMACENAR LAS QUE YA NO ESTÉN EN EL SERVIDOR
        String[] files;
        files = directory.list();
        for (int i=0; i<files.length; i++) {
            File myFile = new File(directory, files[i]);
            myFile.delete();
        }
    }
}
