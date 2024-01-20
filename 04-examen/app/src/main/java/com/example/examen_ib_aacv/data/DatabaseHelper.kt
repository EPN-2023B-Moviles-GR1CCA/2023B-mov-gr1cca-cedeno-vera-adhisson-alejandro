package com.example.examen_ib_aacv.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(
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
                nombre_persona TEXT
            );
        """.trimIndent()
        val crearTablaTarea = """
            CREATE TABLE TAREA(
                id_tarea INTEGER PRIMARY KEY AUTOINCREMENT,
                id_Persona INTEGER,
                nombre_tarea TEXT,
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


}