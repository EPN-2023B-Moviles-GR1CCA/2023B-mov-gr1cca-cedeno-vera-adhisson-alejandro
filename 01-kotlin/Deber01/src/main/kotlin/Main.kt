import entidades.TareaDomestica
import managers.CRUDPersona
import managers.CRUDTareas


fun main() {

    var opcionWhen = 0

    do {
        limpiarConsola()
        println("===========Bienvenido al menu Principal===========")
        println("1. Tareas Domesticas")
        println("2. Personas")
        println("0. Salir")

        opcionWhen = readlnOrNull()?.toInt() ?: 0

        when (opcionWhen) {
            1 -> menuTareaDomestica()
            2 -> menuPersonas()
        }
    } while (opcionWhen != 0)

    println("======== Gracias por usar el programa ========")

}

fun menuTareaDomestica() {
    val crudTarea = CRUDTareas()
    var opcionTareas = 0
    limpiarConsola()
    do {
        println("============= Bienvenido al menu de Tareas Domesticas =============")
        println("*Para hacer modificaciones a las tareas, ingrese el codigo de la persona y el nombre exacto de la persona*")
        println("1. Crear Tarea")
        println("2. Leer todas las Tareas")
        println("3. Actualizar Tarea")
        println("4. Borrar Tarea")
        println("5. Leer tareas de una persona")
        println("0. Salir al menu anterior")

        opcionTareas = readlnOrNull()?.toInt() ?: 0

        when (opcionTareas) {
            1 -> {
                println("Ingrese el codigo de la persona")
                val codigoPersona = readlnOrNull()?.toInt() ?: 0
                println("Ingrese el nombre de la tarea")
                val nombreTarea = readlnOrNull() ?: ""
                println("Ingrese la descripcion de la tarea")
                val descripcion = readlnOrNull() ?: ""
                println("Ingrese la fecha de creacion de la tarea (dd/mm/aaaa)")
                val fechaCreacion = readlnOrNull() ?: ""

                crudTarea.crearTarea(codigoPersona, nombreTarea, descripcion, fechaCreacion, false)
            }

            2 -> {
                crudTarea.leerTareas().forEach { println(it) }
            }

            3 -> {
                println("Ingrese el codigo de la persona")
                val codigoPersona = readlnOrNull()?.toInt() ?: 0
                println("Ingrese el nombre de la tarea")
                val nombreTarea = readlnOrNull() ?: ""
                println("Ingrese la nueva descripcion de la tarea")
                val nuevaDescripcion = readlnOrNull() ?: ""
                println("Ingrese la nueva fecha de creacion de la tarea (dd/mm/aaaa)")
                val nuevaFechaCreacion = readlnOrNull() ?: ""
                println("Ingrese si la tarea esta completada (true/false)")
                val nuevaCompletada = readlnOrNull()?.toBoolean() ?: false

                if (nombreTarea == "" || nuevaDescripcion == "" || nuevaFechaCreacion == "") {
                    println("No se puede actualizar la tarea")
                } else {
                    crudTarea.actualizarTarea(
                        TareaDomestica(
                            codigoPersona,
                            nombreTarea,
                            nuevaDescripcion,
                            nuevaFechaCreacion,
                            nuevaCompletada
                        )
                    )

                }
            }

            4 -> {
                println("Ingrese el codigo de la persona")
                val codigoPersona = readlnOrNull()?.toInt() ?: 0
                println("Ingrese el nombre de la tarea")
                val nombreTarea = readlnOrNull() ?: ""
                crudTarea.borrarTarea(codigoPersona, nombreTarea)
                println("Se ha borrado la tarea con nombre $nombreTarea")
            }

            5 -> {
                println("Ingrese el codigo de la persona")
                val codigoPersona = readlnOrNull()?.toInt() ?: 0
                crudTarea.leerTareaPersona(codigoPersona).forEach { println(it) }
            }

            else -> {
                println("Opcion no valida")
            }
        }

    } while (opcionTareas != 0)
}

fun menuPersonas() {
    val crudManager = CRUDPersona()
    var opcionPersonas = 0
    limpiarConsola()
    do {
        println("============= Bienvenido al menu de Personas =============")
        println("1. Crear Persona")
        println("2. Leer todas las Personas")
        println("3. Actualizar Persona")
        println("4. Borrar Persona")
        println("0. Salir al menu anterior")

        opcionPersonas = readlnOrNull()?.toInt() ?: 0

        when (opcionPersonas) {
            1 -> {
                println("Ingrese el nombre de la persona")
                val nombre = readlnOrNull() ?: ""
                println("Ingrese la fecha de nacimiento de la persona (dd/mm/aaaa)")
                val fechaNacimiento = readlnOrNull() ?: ""
                crudManager.crearPersona(nombre, fechaNacimiento)
            }

            2 -> {
                crudManager.leerPersonas().forEach { println(it) }
            }

            3 -> {
                println("Ingrese el nombre de la persona")
                val nombre = readlnOrNull() ?: ""
                println("Ingrese la nueva fecha de nacimiento de la persona")
                val nuevaFecha = readlnOrNull() ?: ""

                if (nombre == "" || nuevaFecha == "") {
                    println("No se puede actualizar la persona")
                } else {
                    crudManager.actualizarPersona(nombre, nuevaFecha)
                }
            }

            4 -> {
                println("Ingrese el nombre de la persona")
                val nombre = readlnOrNull() ?: ""
                crudManager.borrarPersona(nombre)
                println("Se ha borrado la persona con nombre $nombre")
            }

            0 -> {
                println("Saliendo al menu anterior")
            }

            else -> {
                println("Opcion no valida")
            }

        }

    } while (opcionPersonas != 0)
}

fun limpiarConsola() {
    for (int in 1..30) {
        println()
    }
}