package com.turismorivas.actividades;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.turismorivas.R;
import com.turismorivas.modelo.PuntoDeInteres;
import com.turismorivas.util.Constantes;

import me.relex.circleindicator.CircleIndicator;

/**
 * @author vale
 * @since 2-8-18
 * @see  PuntoDeInteresActivity
 *
 * Fragmento que representa la galeria de imágenes asociada al punto de interés.
 * Consta ademaś de una descripción texual en su Layout, donde se ecribe superpuesto
 * el nombre del citado punto
 *
 *
 */

public class GaleriaFragment extends Fragment {


    private ViewPager viewPager;
    private FragmentAdapterFotos pagerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = null;

        vista = inflater.inflate(R.layout.fragment_galeria, container, false);

        viewPager = (ViewPager) vista.findViewById(R.id.vp_fotos);

        pagerAdapter = new FragmentAdapterFotos(getChildFragmentManager());
        FragmentActivity fa = getActivity();

        pagerAdapter.setPuntoDeInteres(PuntoDeInteresActivity.getPuntoDeInteresActual());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setClipToPadding(false);
        //viewPager.setPadding(50,0,50,0);
        //viewPager.setPageMargin(10);
        CircleIndicator indicator = (CircleIndicator) vista.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        pagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        TextView tv = vista.findViewById(R.id.nombre_sitio);
        tv.setText(PuntoDeInteresActivity.getPuntoDeInteresActual().getNombre());


        Log.d(Constantes.TAG_APP, "Pager Adapter Asociado");



        return vista;

    }
}
