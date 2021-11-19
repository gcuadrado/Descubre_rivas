package com.turismorivas.actividades;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.navigation.NavigationView;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.turismorivas.R;
import com.turismorivas.listener.MenuNavListener;
import com.turismorivas.modelo.PuntoDeInteres;
import com.turismorivas.servicios.DescargarFotos;
import com.turismorivas.servicios.GetPuntosInteres;
import com.turismorivas.util.Constantes;

import com.turismorivas.util.CustomInfoWindowGoogleMap;
import com.turismorivas.util.InfoWindowData;
import com.turismorivas.util.PreferencesUsuario;

/**
 * @version 1.0.1
 * @author Cuadrado
 * @since 20-7-18
 *
 * 13-8-2018 1.0.1 Vale Refactorizo, se agrega el menú lateral de navegación. Comentarios normalizados.
 * Inclusión de iconos de menú en resolución estándar (24x24)
 *
 * Clase utilizada utilizada para mostrar el mapa y dibujar los marcadores de cada punto de interés
 * Se recogen los eventos sobre el menú lateral (apertura, cierre y selección)
 */
public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {


    public static final byte NUM_PUNTOS_INTERES = 16;//TOTAL DE PUNTOS DE INTERÉS CATALOGADOS
    private final static LatLng POSICION_INICIO = new LatLng(40.351906, -3.535733);//POSICIÓN GEOGRÁFICA DE RIVAS
    private final static float NIVEL_ZOOM = 13;//NIVEL DE PROXIMIDAD DE LA CÁMARA DEL MAPA

    private GoogleMap googleMap_global;//el mapa propiamente dicho
    private static Map<String,PuntoDeInteres> lpi;//listado de puntos de interés
    private DrawerLayout drawerLayout;//el menú lateral
    private boolean menu_visible;//para gestionar si está visible o no el menú lateral

    private static Map<String,Marker> lista_marcadores;//lista que almacena los marcadores del mapa, para que desde el menú lateral, cuando se pulse un nombre, se pueda ir a ese marcador (centrar el mapa en él y mostrar su cartelito)


    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mapa_menu);

            //TODO habría que chequear si hay conexión a internet y está el api disponible :Issue abierto https://github.com/googlemaps/android-samples/issues/104

            //iniciamos el menú
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //muestra el boton de para atrás (por defecto)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_lateral);//personalizo con el del menu

            //asignamos listener del menú lateral
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            
            NavigationView navigationView = (NavigationView) findViewById(R.id.navview);
            navigationView.setNavigationItemSelectedListener(new MenuNavListener(this));


            //si es la primera vez queremos informar al usuario de la necesidad de tener conexión a internet (al menos la primera vez)
            if (primeraVez()) {
                mostrarBienvenida();
            }

            //pedimos a la librería de Google El mapa, de forma asíncrnona. onMapReady será invocado. a la vuelta
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        } catch (Throwable t) {
            Log.e("MIAPP", "Error en maps", t);
            //TODO mostrar Dialog de Cierre de aplicación
        }
    }

    /**
     * Se le informa al usuario mediante una Diálgo Alerta que debe tener conexión
     * a internet. Se actualiza que este mensaje ha sido mostrado tras su aceptación
     */
    private void mostrarBienvenida() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.bienvenido);
        builder.setMessage(R.string.aviso_pvez);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                PreferencesUsuario.guardarPrefPrimeraVez(false, getBaseContext());
            }
        });

        AlertDialog mensaje_aviso = builder.create();
        mensaje_aviso.getButton(DialogInterface.BUTTON_POSITIVE);
        //personalizo el color que por defecto es coloraccent, pero entra en conflicto con el color de valoración de las estrellas
        mensaje_aviso.show();
        //después de mostralo, hay que personalizar el color y no antes. Si no falla
        Button botonok = mensaje_aviso.getButton(DialogInterface.BUTTON_POSITIVE);
        botonok.setTextColor(ContextCompat.getColor(this, R.color.colorGris));


    }

    /**
     * Método empleado para saber si es la primera vez que el usuario entra en esta pantalla
     *
     * @return true si es la primera vez que el usuario abre esta actividad, false en caso contrario
     */
    private boolean primeraVez() {
        boolean pvez = false;

        pvez = PreferencesUsuario.getPrefPrimeraVez(this);

        return pvez;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_item = item.getItemId();
        switch (id_item) {
            case R.id.creditos:
                startActivity(new Intent(this, CreditosActivity.class));
                break;

            case R.id.ajustes:
                startActivity(new Intent(this, AjustesActivity.class));
                break;

            case android.R.id.home:
                if (menu_visible) {
                    drawerLayout.closeDrawers();
                    menu_visible = false;
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                    menu_visible = true;
                }
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Método que permite obtener un Punto de Interés por su id (posición en la lista en realidad)
     *
     * @param nombre el id del punto de interés
     * @return el punto de interés i-ésimo
     */
    public static PuntoDeInteres getPuntoDeInteres(String nombre) {
        PuntoDeInteres pi = null;

        pi = lpi.get(nombre);

        return pi;
    }


    /**
     * Método que dibuja en el mapa la ubicación física de los puntos de interés.
     * A cada punto dibujado (Marker), se le asigna un número con setTag, que identifica numéricamente
     * a cada punto de interés. De este modo, se consigue a posteriori con getTag, obtener el punto de interés
     * asociado a ese Marker
     *
     * @param lpuntoDeInteres la lista de puntos con la info de cada punto
     * @param googleMap       el mapa donde se dibuja
     */
    public  void marcarPuntos(Map<String,PuntoDeInteres> lpuntoDeInteres, GoogleMap googleMap) {

        Marker m = null;
        MarkerOptions opciones_marcador = null;
        LatLng posicion = null;
        NavigationView navView = (NavigationView) findViewById(R.id.navview);
        Menu menu = navView.getMenu();
        SubMenu subMenu=menu.addSubMenu("Puntos de interés");
       this.lista_marcadores = new TreeMap<>();//para almacenar los marersy luego poder refrenciarlos tras clickar el menú lateral
if(lpuntoDeInteres!=null){
        for (PuntoDeInteres puntoDeInteres : lpuntoDeInteres.values()) {

            opciones_marcador = new MarkerOptions();
            opciones_marcador.title(puntoDeInteres.getNombre());
            posicion = new LatLng(puntoDeInteres.getLatitud(), puntoDeInteres.getLongitud());
            opciones_marcador.position(posicion);
            int icono;
            if (puntoDeInteres.getCategoria() == PuntoDeInteres.CATEGORIA.MISTERIO) {
                icono = R.drawable.geo_icono;
            } else {
                icono = R.drawable.marker_rivas;
            }
            opciones_marcador.icon(BitmapDescriptorFactory.fromResource(icono));
            m = googleMap.addMarker(opciones_marcador);
            InfoWindowData info = new InfoWindowData();
            info.setNum_punto(puntoDeInteres.getId());
            info.setNombre_punto(puntoDeInteres.getNombre());
            m.setTag(puntoDeInteres);
            subMenu.add(puntoDeInteres.getNombre());
            //m.showInfoWindow();
            this.lista_marcadores.put(m.getTitle(), m);//Guardo la lista de marcadores para luego poder recurrir a ella cuando tenga que referirme a un marcador desde su selección en el menú lateral
        }
        }
    }

    public void añadirPuntosNav(){

    }

    /**
     * Método que centra el mapa de forma inicial
     *
     * @param googleMap
     */
    private static void centrar_mapa(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(POSICION_INICIO, NIVEL_ZOOM));


    }


    /**
     * Método que carga toda la información disponible de cada punto de interés documentado.
     *
     * @return la lista de puntos de interés que será mostrada
     */
    private List<PuntoDeInteres> obtenerListaPuntosDeInteres() {

        List<PuntoDeInteres> lpi = null;

        lpi = new ArrayList<PuntoDeInteres>(NUM_PUNTOS_INTERES);


        return lpi;
    }

    /**
     * Se produce una llamada a este método al obtener la imagen que represeta el mapa
     * del servicio de Google Maps API. Aprovechamos para iniciar el mapa con la información
     * gráfica y de control que queremos proveer
     *
     * @param googleMap la variable que represeta el mapa
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap_global = googleMap;

        centrar_mapa(googleMap);//centramos la cámara
        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);
        googleMap.setInfoWindowAdapter(customInfoWindow);
        //asginamos el listener para cuando la información mostrada por el marcador sea tocada (cartelito con el nombre)
        this.googleMap_global.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                PuntoDeInteres p = (PuntoDeInteres) marker.getTag();
                String nombre = p.getNombre();//obtenemos el número de punto de interés
                Intent i = new Intent(getBaseContext(), PuntoDeInteresActivity.class);//con un intent explícito


                i.putExtra(Constantes.CLAVE_INTENT_PI, nombre);
                startActivity(i);

            }
        })
        ;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pedirPermisoLocalizacion();
        }

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                //TODO: Any custom actions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pedirPermisoLocalizacion();
                }
                return false;
            }
        });


        descargarPuntos();
        //señalamos los puntos en el mapa


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void pedirPermisoLocalizacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permissions, Constantes.CODIGO_SOLICITUD_PERMISO_LOCALIZACIÓN);
        }else{
            googleMap_global.setMyLocationEnabled(true);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constantes.CODIGO_SOLICITUD_PERMISO_LOCALIZACIÓN) {

            boolean concecido = false;
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION) || permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        concecido = true;
                    } else {
                        concecido = false;
                    }
                }
            }

            if (concecido) {

                pedirPermisoLocalizacion();
            }
        }
    }

    private void descargarPuntos(){
        if(isNetwork(this)) {
            GetPuntosInteres getPuntosInteres = new GetPuntosInteres() {
                protected void onPostExecute(Map<String,PuntoDeInteres> puntos) {
                    lpi = puntos;

                    if(lpi!=null) {
                        if (!lpi.isEmpty()) {
                            DescargarFotos descargarFotos=new DescargarFotos(MapaActivity.this);
                            descargarFotos.execute(lpi);
                            marcarPuntos(lpi, googleMap_global);

                        }
                    }else{
                        lpi=PreferencesUsuario.getPuntosInteres(MapaActivity.this);
                        marcarPuntos(lpi, googleMap_global);
                    }

                    FrameLayout progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
                    AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    progressBarHolder.setAnimation(outAnimation);
                    progressBarHolder.setVisibility(View.GONE);

                }
            };
            //Iniciar animación de espera
            FrameLayout progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
            AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
            try {
                getPuntosInteres.execute(this);//obtenemos los puntos de interés, con sus respectiva descripción
            } catch (Exception e) {

            }
        }else {
            lpi=PreferencesUsuario.getPuntosInteres(MapaActivity.this);
            marcarPuntos(lpi, googleMap_global);
        }

    }

    /**
     * Este método es invocado cuando un punto de interés ha sido seleccinado desde el menú lateral
     * Lo que hace el método es centrar el mapa en ese punto y mostrar el letrero correspondiente a ese marcador
     *
     * @param nombre_punto el id del marcador
     */
    public void visitaPuntoDeInteres(String nombre_punto) {

        //NOTA: Posible mejora: informar al usuario con un diálogo que toque el nombre del sitio para acceder a la ficha
        //NOTA 2: Al simular nosotros que se ha tocado el marker, se enfoca la cámara y se dibuja el letrero de información,
        //https://developers.google.com/android/reference/com/google/android/gms/maps/GoogleMap.OnMarkerClickListener.html#onMarkerClick(com.google.android.gms.maps.model.Marker)
        //pero no se consigue dibujar los iconos de Google Maps como acceso directo para cómo llegar o ver en Maps
        //Debate en foro sin solución aparente https://stackoverflow.com/questions/29801193/how-to-trigger-the-onclick-event-of-a-marker-on-a-google-maps-v2-for-android#comment57351853_31156994

        drawerLayout.closeDrawers();//cierre del menú lateral
        Marker m = lista_marcadores.get(nombre_punto);

        m.showInfoWindow();
        googleMap_global.moveCamera(CameraUpdateFactory.newLatLngZoom(m.getPosition(), NIVEL_ZOOM));
        menu_visible = false;
    }


    /**
     * Método que cambia el tipo de mapa a ojos del usuario, según su selección en el menú lateral:
     * SATÉLITE, HÍBRRIDO, O NORMAL (Dibujo-Esquema)
     *
     * @param tipo_mapa identifica el tipo de mapa que el usuario quieree
     */
    public void cambiarTipoMapa(int tipo_mapa) {


        drawerLayout.closeDrawers();//cierre del menú
        googleMap_global.setMapType(tipo_mapa);

    }

    public void contacto(){
        Uri uri = Uri.parse("http://instagram.com/historiaderivas/");
        Intent insta = new Intent(Intent.ACTION_VIEW, uri);
        insta.setPackage("com.instagram.android");

        if (isIntentAvailable(this, insta)) {
            startActivity(insta);
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/historiaderivas/")));
        }
    }

    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;

    }

    public  boolean isNetwork(Context context) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}