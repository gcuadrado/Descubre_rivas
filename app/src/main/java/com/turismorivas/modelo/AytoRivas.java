package com.turismorivas.modelo;

import com.turismorivas.R;

/**
 * @author Hector (XiNoRiVaS en Github)
 *
 * Esta clase representa la Casa Consistorial del Ayto de Rivas
 * con todos los atributos mencionados en la clase PuntoDeInteres
 */

public class AytoRivas extends PuntoDeInteres {

    public AytoRivas () {
        this.nombre = "Casa Consistorial del Ayuntamiento de Rivas";
        this.fecha_inicio = "1954";
        this.area = -1;
        this.direccion = "Plaza 19 de Abril s/n (28521 - Rivas Vaciamadrid)";
        this.latitud = 40.327446f;
        this.longitud = -3.518410f;
        this.horario = "De lunes a viernes de 8:00 a 20:00";
        this.info_contacto = "Teléfono: 91 660 27 10" +
                "Email: alcaldia@rivasciudad.es";
        this.info_basica = "La Casa Consistorial es el edificio histórico del Ayuntamiento de Rivas Vaciamadrid. " +
                "Se encuentra en el Casco Antiguo y forma parte del núcleo urbano diseñado por Regiones Devastadas tras la Guerra Civil." +
                "En la Casa Consistorial se encuentra la Concejalía de Salud, la Concejalía de Barrio Este, el Registro Auxiliar y Atención a la Ciudadanía.";
        this.info_detallada ="La Casa Consistorial fue el Ayuntamiento efectivo de Rivas-Vaciamadrid desde 1959 hasta principios del siglo XXI. Actualmente su uso se mantiene para diversas funciones de servicio público a los vecinos del Casco Antiguo.\n" +
                "\n" +
                "Como curiosidad, el escudo que encontramos presidiendo el lado derecho de su fachada fue encontrado en una de las fincas históricas de Rivas-Vaciamadrid, por lo que es de una antigüedad notable.";
        this.enlace_web = "http://www.rivasciudad.es/portal/contenedor_ficha.jsp?seccion=s_floc_d4_v1.jsp&contenido=1082&tipo=1&nivel=1400&layout=contenedor_ficha.jsp&codResi=1&language=es";
        this.coste = 0;
        this.accesibilidad = true;
        this.puntuacion = -1;
        this.categorias = new CATEGORIA[]{CATEGORIA.HISTORICO};
        this.fotos = new int[]{R.drawable.casaconsistorial_2, R.drawable.casaconsistorial_3, R.drawable.casaconsistorial_4 };


    }
}
