package com.turismorivas.modelo;

import com.turismorivas.R;

public class TrincherasLagunaCampillo extends PuntoDeInteres {

        public TrincherasLagunaCampillo(){
            nombre = "Trincheras y campamento del ejército republicano";
            fecha_inicio = "1938";
            area = 3000;
            direccion = "Calle Piscina Maspalomas s/n";
            latitud = 40.324841f;
            longitud = -3.499226f;
            accesibilidad = false;
            info_contacto = "null";

            enlace_web = "https://www.diarioderivas.es/intervencion-arqueologica-rivas-batalla-jarama/";

            info_basica = "Trincheras excavadas por el ejército republicano durante la Guerra" +
                    "Civil española";

            info_detallada =  "En estas trincheras pasó el ejército republicano varios meses durante" +
                    "la Guerra Civil. Hasta hace pocos años, se convervaba una " +
                    "pequeña parte de las mismas a la vista pero" +
                    "no fueron descubiertas en su totalidad hasta que llevó acabo una excavación arqueológica" +
                    "en el verano de 2018." +
                    "\n\n" +
                    "Estas intervenciones permitieron aflorar el trazado completo y descubrir elementos curiosos" +
                    "como un soporte para una cama, un pequeño horno excavado en la roca o diversos utensilios" +
                    "del día a día pertenecientes a los soldado como botes de tinta, crucifijos o laxantes para " +
                    "completar la dieta de baja calidad que mantenían." +
                    "\n\n" +
                    "Según los propios arqueólogos, estas trincheras se construyeron una vez terminada la Batalla del Jarama," +
                    "cuando el frente estaba estabilizado, seguramente en una fecha cercana a 1938. Se trataba de una " +
                    "fortificación de segunda línea, desde la que se tenía visión directa al Puente de Arganda." +
                    "\n\n" +
                    "En el pasillo que se introduce hacia el interior del valle, se ubicaba un campamento, donde los soldados" +
                    "pasaban la noche y que contaba, al menos, con una bombilla, algo poco común en la época." +
                    "\n\n" +
                    "Por el buen estado de conservación durante su descubrimiento, se cree que al finalizar la " +
                    "Guerra Civil se decidió volver a enterrar toda esta obra con el objetivo de evitar futuros accidentes." +
                    "\n\n" +
                    "Actualmente el espacio se encuentra abierto permanentemente y se pide encarecidamente que cualquier" +
                    "visita se lleve a cabo sin dañar ni ensuciar el patrimonio.";

            puntuacion = 0;
            horario = "Accesible permanentemente";
            coste = -1;
            this.categorias = new CATEGORIA[]{CATEGORIA.CULTURAL, CATEGORIA.HISTORICO};
            this.fotos = new int[]{R.drawable.trincheracampamento_1,R.drawable.trincheracampamento_2,R.drawable.trincheracampamento_3};

        }



    }


