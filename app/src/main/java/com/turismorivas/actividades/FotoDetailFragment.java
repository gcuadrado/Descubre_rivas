package com.turismorivas.actividades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.turismorivas.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 *
 * @author vale
 * @since  2-8-2018
 *
 * Clase que infla cada vista (foto), para que sea usada por el FragmentAdaptarFotos
 */
public class FotoDetailFragment extends Fragment {

    private String path_foto;

    /**
     *
     * @param args contiene la clave con el id de la foto (R.drawable) y es invocado automáticamente por su Adapter
     */
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        Log.d(getClass().getCanonicalName(), "SetArgumentsInvocado");
        //byte[] byteArray = args.getByteArray("N_FOTO");
       // id_drawable= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public FotoDetailFragment() {
        // constructor por defecto, requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_foto_detail, container, false);

        try {

            ImageView imageView = rootView.findViewById(R.id.foto_actual);

            //imageView.setImageBitmap(id_drawable);
            imageView.setImageBitmap(loadImageFromStorage(path_foto));

        }catch (Exception e)
        {
            Log.e("MENSAJE", e.getMessage());
        }


        return rootView;
    }

    public void setPath_foto(String path_foto) {
        this.path_foto = path_foto;
    }

    private Bitmap loadImageFromStorage(String path)
    {
        Bitmap b=null;
        try {
            File f=new File(path);
            b = BitmapFactory.decodeStream(new FileInputStream(f));

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
return b;
    }
}
