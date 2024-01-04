package entidades

data class TareaDomestica(
    val codigoPersona: Int,
    val nombreTarea: String,
    val descripcion: String,
    val fechaCreacion: String,
    val completada: Boolean
)
