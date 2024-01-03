import managers.CRUDPersona


fun main() {
//    Crear Un menu dentro de un bucle para el usuario

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
    limpiarConsola()
    println("Menu de tareas domesticas")
}

fun menuPersonas() {
    val crudManager = CRUDPersona()
    var opcionPersonas = 0

    do {
        limpiarConsola()
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