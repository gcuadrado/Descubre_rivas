package com.turismorivas.modelo;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Val (github-valexx55)
 * @since 30-6-2018
 * @version 1.0
 *
 * Clase que representa el modelo de los Puntos de interés.
 * Contiene todos los atributos necesarios para representar
 * un punto de interés
 *
 * Se decide por motivos didácticos (no se estudia en el temario BD)
 * y organizativos (para que cada alumno documente algún punto)
 * este modelado
 */
public class PuntoDeInteres {


    public enum CATEGORIA {HISTORICO, CULTURAL, OCIO, MONUMENTAL, MISTERIO, RELIGIOSO, TURISTICO, OTRA}

    protected int id;
    protected String nombre;
    protected String fecha_inicio;//fundación, construcción
    protected String direccion;
    protected float latitud;
    protected float longitud;
    protected boolean accesibilidad;//true si puede discapacidad
    protected List<String> path_fotos;
    private List<String> url_fotos;
    protected String info_contacto;
    protected String enlace_web;
    protected String info_basica;
    protected String info_detallada;
    protected float puntuacion;
    protected String horario;
    protected float coste;
    protected CATEGORIA categoria;


    public PuntoDeInteres(int id, String nombre, String fecha_inicio, String direccion, float latitud, float longitud, boolean accesibilidad, List<String> url_fotos, String info_contacto, String enlace_web, String info_basica, String info_detallada, float puntuacion, String horario, float coste, CATEGORIA categoria) {
        this.id=id;
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.accesibilidad = accesibilidad;
        this.path_fotos = new ArrayList<>();
        this.url_fotos=url_fotos;
        this.info_contacto = info_contacto;
        this.enlace_web = enlace_web;
        this.info_basica = info_basica;
        this.info_detallada = info_detallada;
        this.puntuacion = puntuacion;
        this.horario = horario;
        this.coste = coste;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public boolean isAccesibilidad() {
        return accesibilidad;
    }

    public void setAccesibilidad(boolean accesibilidad) {
        this.accesibilidad = accesibilidad;
    }

    public List<String> getPath_fotos() {
        return path_fotos;
    }

    public void setPath_fotos(List<String> path_fotos) {
        this.path_fotos = path_fotos;
    }

    public String getInfo_contacto() {
        return info_contacto;
    }

    public void setInfo_contacto(String info_contacto) {
        this.info_contacto = info_contacto;
    }

    public String getEnlace_web() {
        return enlace_web;
    }

    public void setEnlace_web(String enlace_web) {
        this.enlace_web = enlace_web;
    }

    public String getInfo_basica() {
        return info_basica;
    }

    public void setInfo_basica(String info_basica) {
        this.info_basica = info_basica;
    }

    public String getInfo_detallada() {
        return info_detallada;
    }

    public void setInfo_detallada(String info_detallada) {
        this.info_detallada = info_detallada;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public float getCoste() {
        return coste;
    }

    public void setCoste(float coste) {
        this.coste = coste;
    }

    public CATEGORIA getCategoria() {
        return categoria;
    }

    public int getId() {
        return id;
    }

    public List<String> getUrl_fotos() {
        return url_fotos;
    }

    public void addPathFoto(String path_foto){
        this.path_fotos.add(path_foto);
    }

    public void setCategoria(CATEGORIA categoria) {
        this.categoria = categoria;
    }
}
