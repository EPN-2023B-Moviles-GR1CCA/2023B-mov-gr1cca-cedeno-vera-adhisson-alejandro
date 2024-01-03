package entidades

import enums.TareasDomesticasEnum

data class TareaDomestica(
    val nombreTarea: TareasDomesticasEnum,
    val descripcion: String,
    val fechaCreacion: String,
    val duracionEstimada: Int,
    val completada: Boolean
)
