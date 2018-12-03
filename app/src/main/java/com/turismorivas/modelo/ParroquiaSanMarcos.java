package com.turismorivas.modelo;

import com.turismorivas.R;

public class ParroquiaSanMarcos extends PuntoDeInteres {


        public ParroquiaSanMarcos () {

            this.accesibilidad = true;
            this.nombre = "Parroquia San Marcos";
            this.enlace_web = "http://www.parroquiasanmarcos.com/iframeFondo2.php?file=Horarios.html";
            this.fecha_inicio = "La iglesia de San Marcos es uno de los edificios icónicos del Casco Antiguo de Rivas-Vaciamadrid.";

            this.latitud =40.327602f;
            this.longitud =-3.518747f;
            this.coste = -1;
            this.horario = "De lunes a domingo, desde las 9:00 a las 8:00 de la tarde noche";
            this.info_detallada = "La iglesia de San Marcos fue fundada, junto al resto del pueblo, el 23 de julio de 1959. A pesar de ser relativamente reciente, en su interior alberga una pila bautismal del siglo XVII.\n" +
                    "\n" +
                    "Dicha pila bautismal procede de una primitiva parroquia de San Marcos fundada en 1632 en el antiguo Vaciamadrid que no ha llegado hasta nuestros días.";
            this.info_basica = "La iglesia de San Marcos es uno de los edificios icónicos del Casco Antiguo de Rivas-Vaciamadrid";
            this.categorias = new CATEGORIA[]{CATEGORIA.RELIGIOSO, CATEGORIA.CULTURAL};
            this.fotos = new int[]{ R.drawable.parroquia_smarcos_4,R.drawable.parroquia_smarcos_2,R.drawable.parroquia_smarcos_3 };
            this.area = -1;
            this.info_contacto = null;
            this.direccion="Calle del Ayuntamiento, 3, 28521 Rivas-Vaciamadrid, Madrid";
            this.puntuacion = -1;
        }
}

