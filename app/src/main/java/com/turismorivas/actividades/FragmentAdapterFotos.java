package com.turismorivas.actividades;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.turismorivas.modelo.PuntoDeInteres;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author vale
 * @since 2-8-18
 * @see {@link GaleriaFragment}, {@link FotoDetailFragment}
 *
 * Es el marco que va proporcinando path_fotos al GaleriaFragement, almacennado para ello
 * la referencia a la colección de imágenes relativa al punto de interés en curso.
 * Emplea la clase FotoFragmentDeatil, para propicionar las vistas que se rendrizan en la Galeria
 */

public class FragmentAdapterFotos extends FragmentStatePagerAdapter {


    private List<String> array_fotos_actual ;

    public FragmentAdapterFotos (FragmentManager fm)
    {

        super (fm);
    }

    public void setPuntoDeInteres(PuntoDeInteres pi)
    {
        array_fotos_actual = pi.getPath_fotos();

    }
    @Override
    public Fragment getItem(int i) {
        FotoDetailFragment fragment = null;
        Bundle bundle = null;

        fragment = new FotoDetailFragment();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        /*array_fotos_actual[i].compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();*/

        bundle = new Bundle();

        //bundle.putByteArray("N_FOTO", byteArray);//al fragment le damos un tag, que valdrá a la postre para la id del la foto en curso
        fragment.setPath_foto(array_fotos_actual.get(i));
        fragment.setArguments(bundle);


        return  fragment;
    }

    @Override
    public int getCount() {
        int tamanio = 0;

        tamanio = array_fotos_actual.size();

        return tamanio;
    }
}
