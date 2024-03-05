package com.example.examen_ib_aacv.data.entidades

data class Tarea(
    var id: Int?,
    val idPersona: Int,
    val nombre: String,
    val descripcion: String
) {
    constructor() : this(null, 0, "", "")
}
