package com.turismorivas.actividades;

import android.media.MediaPlayer;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.turismorivas.R;
import com.turismorivas.util.Constantes;

/**
 * @author vale
 * @since 1-8-2018
 *
 * Clase de créditos donde aparecen los pariticpantes. El que sepa leer código, tiene
 * premio para descubir el huevo de pascua ;)
 */

public class CreditosActivity extends AppCompatActivity {


    private final static String URL_AYTO_RIVAS = "http://www.rivasciudad.es";
    private final static String URL_IDEL_FORMACION = "http://www.formacionidelsl.com/";
    private static String RESPUESTA_BUENA = "quiero un mojito";
    private static String URL_VIDEO_YOUTUBE = "https://youtu.be/Yxd5qU_XvKc";
    private static String PREGUNTA = "¿Qué quieres reina?";
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.atras);

        ImageButton boton_mojito = findViewById(R.id.boton_mojito);

        boton_mojito.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(Constantes.TAG_APP, "Aquí irá el huevo de PASCUA");


                if (!SpeechRecognizer.isRecognitionAvailable(getBaseContext())) {
                    Log.d(Constantes.TAG_APP, "Aquí irá el huevo de PASCUA");


                } else {
                    Log.d("MIAPP", "Sí hay pa voz SE PUEDE MOSTRAR");


                    pregunta();

                }
                return true;
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id_item = item.getItemId();
        switch (id_item) {
            case android.R.id.home:
                Log.d(Constantes.TAG_APP, "Tocada opción salir");
                if (mediaPlayer != null) {
                    try {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                    } catch (IllegalStateException e) {

                    }
                }
                super.onBackPressed();


                break;

            default:
                Log.d(Constantes.TAG_APP, "Tocado opcion desconocida");

        }
        return super.onOptionsItemSelected(item);
    }


    private void pregunta() {


        mediaPlayer = MediaPlayer.create(this, R.raw.himno_urss);
        mediaPlayer.setLooping(false);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();
        ImageView img=findViewById(R.id.boton_mojito);
        img.setImageResource(R.drawable.pedro);
        Toast.makeText(this, "¿La URSS? ¿Pero no se habían separado?", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Pulsa atrás para detener el audio", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            } catch (IllegalStateException e) {

            }
        }
            super.onBackPressed();


    }
}

