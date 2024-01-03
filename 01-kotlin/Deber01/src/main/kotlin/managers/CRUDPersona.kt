package managers

import entidades.Persona
import java.io.File
import java.io.IOException

class CRUDPersona {
    private val personasFile = File("archivos/personas.txt")

    init {
        try {
            if (personasFile.createNewFile()) {
                println("Base de personas creada correctamente.")
            }
        } catch (e: IOException) {
            println("Error al intentar crear la base de personas ${e.message}")
        }
    }

    fun crearPersona(nombre: String, fechaNacimiento: String) {
        val codigo = generarCodigo()
        personasFile.appendText("${codigo};${nombre};${fechaNacimiento}\n")
    }

    fun crearPersona(persona: Persona) {
        val codigo = generarCodigo()
        personasFile.appendText("${codigo};${persona.nombre};${persona.fechaNacimiento}\n")
    }

    fun leerPersonas(): List<Persona> {
        return personasFile.readLines().map {
            val datos = it.split(";")
            Persona(datos[0].toInt(), datos[1], datos[2])
        }
    }

    fun actualizarPersona(nombre: String, nuevaFecha: String) {
        val personas = leerPersonas()
        val personasFiltradas = personas.filter { it.nombre != nombre }

        personasFile.writeText("")
        personasFiltradas.forEach { crearPersona(it) }
        crearPersona(nombre, nuevaFecha)
    }


    fun borrarPersona(nombre: String) {
        val personas = leerPersonas()
        val personasFiltradas = personas.filter { it.nombre != nombre }

        personasFile.writeText("")
        personasFiltradas.forEach { crearPersona(it) }
    }

    fun generarCodigo(): Int {
        //Lee los codigos, y crea un nuevo codigo aleatorio para la persona en orden de 1 a n
        val personas = leerPersonas()
        return personas.size + 1
    }
}