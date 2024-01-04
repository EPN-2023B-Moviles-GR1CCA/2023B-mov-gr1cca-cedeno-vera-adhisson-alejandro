package managers

import entidades.TareaDomestica
import java.io.File
import java.io.IOException

class CRUDTareas {

    private val tareasFile = File("archivos/tareas.txt")

    init {
        try {
            if (tareasFile.createNewFile()) {
                println("Base de tareas creada correctamente.")
            }
        } catch (e: IOException) {
            println("Error al intentar crear la base de tareas ${e.message}")
        }
    }

    fun crearTarea(tarea: TareaDomestica) {
        tareasFile.appendText("${tarea.codigoPersona};${tarea.nombreTarea};${tarea.descripcion};${tarea.fechaCreacion};${tarea.completada}\n")
    }

    fun crearTarea(
        codigoPersona: Int,
        nombreTarea: String,
        descripcion: String,
        fechaCreacion: String,
        completada: Boolean
    ) {
        tareasFile.appendText("${codigoPersona};${nombreTarea};${descripcion};${fechaCreacion};${completada}\n")
    }

    fun leerTareas(): List<TareaDomestica> {
        return tareasFile.readLines().map {
            val datos = it.split(";")
            TareaDomestica(datos[0].toInt(), datos[1], datos[2], datos[3], datos[4].toBoolean())
        }
    }

    fun actualizarTarea(nuevaTarea: TareaDomestica) {
//      Se filtrara por codigo de la persona y código de la tarea
        val tareas = leerTareas()
        val tareaFiltrada =
            tareas.filter { it.codigoPersona != nuevaTarea.codigoPersona && it.nombreTarea != nuevaTarea.nombreTarea }

        tareasFile.writeText("")
        tareaFiltrada.forEach { crearTarea(it) }
        crearTarea(nuevaTarea)
    }

    fun borrarTarea(codigoPersona: Int, nombreTarea: String) {
        val tareas = leerTareas()
        val tareasFiltradas = tareas.filter { it.codigoPersona != codigoPersona && it.nombreTarea != nombreTarea }
        tareasFile.writeText("")
        tareasFiltradas.forEach { crearTarea(it) }
    }

    fun leerTareaPersona(codigoPersona: Int): List<TareaDomestica> {
        val tareas = leerTareas()
        val tareasFiltradas = tareas.filter { it.codigoPersona == codigoPersona }
        return tareasFiltradas
    }


}