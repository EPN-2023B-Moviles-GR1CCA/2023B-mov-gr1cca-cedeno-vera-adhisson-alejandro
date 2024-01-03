package managers

import entidades.Persona
import java.io.File

class CRUDPersona {
    private val personasFile = File("archivos/personas.txt")

    fun crearPersona(persona: Persona) {
        personasFile.appendText("${persona.nombre},${persona.fechaNacimiento}\n")
    }

    fun leerPersonas(): List<Persona> {
        return personasFile.readLines().map {
            val datos = it.split(",")
            Persona(datos[0], datos[1])
        }
    }

    fun actualizarPersona(nombre: String, nuevaFecha: String) {
        val personas = leerPersonas()
        val personasFiltradas = personas.filter { it.nombre != nombre }

        personasFile.writeText("")
        personasFiltradas.forEach { crearPersona(it) }
        crearPersona(Persona(nombre, nuevaFecha))
    }


    fun borrarPersona(nombre: String) {
        val personas = leerPersonas()
        val personasFiltradas = personas.filter { it.nombre != nombre }

        personasFile.writeText("")
        personasFiltradas.forEach { crearPersona(it) }
    }
}