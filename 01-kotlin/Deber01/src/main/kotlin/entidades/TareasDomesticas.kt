package entidades

data class TareasDomesticas(
    val nombreTarea: String,
    val descripcion: String,
    val fechaCreacion: String,
    val duracionEstimada: Int,
    val completada: Boolean
)
