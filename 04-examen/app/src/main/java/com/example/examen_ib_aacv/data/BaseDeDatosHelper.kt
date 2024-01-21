package com.example.examen_ib_aacv.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.examen_ib_aacv.data.entidades.Persona
import com.example.examen_ib_aacv.data.entidades.Tarea

class BaseDeDatosHelper(
    contexto: Context?, // this
) : SQLiteOpenHelper( // SQLiteOpenHelper es una clase abstracta
    contexto, // contexto
    "tareas-personas", // nombre de la base de datos
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaPersona = """
            CREATE TABLE PERSONA(
                id_persona INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_persona VARCHAR(50)
            );
        """.trimIndent()
        val crearTablaTarea = """
            CREATE TABLE TAREA(
                id_tarea INTEGER PRIMARY KEY AUTOINCREMENT,
                id_Persona INTEGER,
                nombre_tarea VARCHAR(50),
                descripcion_tarea TEXT,
                FOREIGN KEY(id_Persona) REFERENCES PERSONA(id_persona)
            );
        """.trimIndent()
        db?.execSQL(crearTablaPersona)
        db?.execSQL(crearTablaTarea)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearPersona(nombre: String): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre_persona", nombre)
        val resultadoEscritura = conexionEscritura
            .insert(
                "PERSONA",
                null,
                valoresAGuardar
            )
        conexionEscritura.close()
        return resultadoEscritura.toInt() != -1
    }

    fun eliminarPersona(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEscritura = conexionEscritura
            .delete(
                "PERSONA",
                "id_persona=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return resultadoEscritura.toInt() != -1
    }

    fun obtenerPersonas(): List<Persona> {
        val listaPersonas = mutableListOf<Persona>()
        val conexionLectura = readableDatabase
        val resultadoLectura = conexionLectura.rawQuery("SELECT * FROM PERSONA", null)
        if (resultadoLectura.moveToFirst()) {
            do {
                val id = resultadoLectura.getInt(0) // Columna 0 -> ID
                val nombre = resultadoLectura.getString(1) // Columna 1 -> NOMBRE
                val persona = Persona(id, nombre)
                listaPersonas.add(persona)
            } while (resultadoLectura.moveToNext())
        }
        resultadoLectura.close()
        conexionLectura.close()
        return listaPersonas
    }

    fun actualizarNombrePersona(id: Int, nuevoNombre: String): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre_persona", nuevoNombre)
        val resultadoActualizacion = conexionEscritura
            .update(
                "PERSONA",
                valoresAActualizar,
                "id_persona=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return resultadoActualizacion.toInt() != -1
    }

    fun crearTarea(idPersona: Int, nombre: String, descripcion: String): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("id_Persona", idPersona)
        valoresAGuardar.put("nombre_tarea", nombre)
        valoresAGuardar.put("descripcion_tarea", descripcion)
        val resultadoEscritura = conexionEscritura
            .insert(
                "TAREA",
                null,
                valoresAGuardar
            )
        conexionEscritura.close()
        return resultadoEscritura.toInt() != -1
    }

    fun eliminarTarea(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEscritura = conexionEscritura
            .delete(
                "TAREA",
                "id_tarea=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return resultadoEscritura.toInt() != -1
    }

    fun obtenerTareas(): List<Tarea> {
        val listaTareas = mutableListOf<Tarea>()
        val conexionLectura = readableDatabase
        val resultadoLectura =
            conexionLectura.rawQuery("SELECT * FROM TAREA", null)
        if (resultadoLectura.moveToFirst()) {
            do {
                val id = resultadoLectura.getInt(0) // Columna 0 -> ID
                val idPersona = resultadoLectura.getInt(1) // Columna 1 -> ID_PERSONA
                val nombre = resultadoLectura.getString(2) // Columna 2 -> NOMBRE
                val descripcion = resultadoLectura.getString(3) // Columna 3 -> DESCRIPCION
                val tarea = Tarea(id, idPersona, nombre, descripcion)
                listaTareas.add(tarea)
            } while (resultadoLectura.moveToNext())
        }
        resultadoLectura.close()
        conexionLectura.close()
        return listaTareas
    }

    fun actualizarTarea(idTarea: Int, idPersona: Int, nuevoNombre: String, nuevaDescripcion: String): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre_tarea", nuevoNombre)
        valoresAActualizar.put("id_Persona", idPersona)
        valoresAActualizar.put("descripcion_tarea", nuevaDescripcion)
        val resultadoActualizacion = conexionEscritura
            .update(
                "TAREA",
                valoresAActualizar,
                "id_tarea=?",
                arrayOf(
                    idTarea.toString()
                )
            )
        conexionEscritura.close()
        return resultadoActualizacion.toInt() != -1
    }


}