package com.example.gr1aacv2023b

class BBaseDatosMemoria {
    // companion object

    companion object {
        var arregloBEntrenadores = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenadores
                .add(
                    BEntrenador(1, "Adrian", "Descripcion Adrian")
                )
            arregloBEntrenadores
                .add(
                    BEntrenador(2, "Vicente", "Descripcion Vicente")
                )
            arregloBEntrenadores
                .add(
                    BEntrenador(3, "Wendy", "Descripcion Wendy")
                )

        }
    }

}