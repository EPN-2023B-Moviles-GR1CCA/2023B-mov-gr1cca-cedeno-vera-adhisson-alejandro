package com.example.gr1aacv2023b

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ESqliteHelperEntrenador(
    contexto: Context?, // this
) : SQLiteOpenHelper( // SQLiteOpenHelper es una clase abstracta
    contexto, // contexto
    "moviles", // nombre de la base de datos
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        //si tengo que crear mas tablas lo hago aqui
        val scriptCrearTablaEntrenador =
            // si esta entre 3 comillas es un string multilinea
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptCrearTablaEntrenador)
    }

    override fun onUpgrade(
        p0: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
        //aqui se actualiza la estructura de la base de datos
    }

    fun crearEntrenador( //se usan los content values
        nombre: String,
        descripcion: String
    ): Boolean {
        val conexionEscritura = writableDatabase // se crea la base de datos en modo escritura
        val valoresAGuardar = ContentValues() // se crea un contenedor de valores a guardar
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoEscritura = conexionEscritura
            .insert( //insert devuelve -1 si no se guarda nada
                "ENTRENADOR", // nombre tabla
                null, // llave primaria
                valoresAGuardar //valores a guardar
            )
        conexionEscritura.close()
//        return if (resultadoEscritura.toInt() == -1) false else true
        return resultadoEscritura.toInt() != -1
    }

    fun eliminarEntrenadorFormulario(id: Int): Boolean { // se usan los parametros de consulta
        val conexionEscritura = writableDatabase
        // where id = ?
        val parametroConsultaDelete =
            arrayOf(id.toString()) // arreglo de parametros para la consulta
        // [1]
        // "id=?" -> "id=1"
        // [1,2,3], "id=? AND a=? AND b=?" -> "id=1 AND a=2 AND b=3
        // para evitar inyeccion de codigo
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR", // Nombre tabla
                "id=?", // Where columna = valor
                parametroConsultaDelete
            )
        conexionEscritura.close()
        return resultadoEliminacion.toInt() != -1
    }

    fun consultarEntrenadorPorID(id: Int): BEntrenador { // devuleve un entrenador exista o no
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM ENTRENADOR WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery( // nos ayuda a hacer consultas
            scriptConsultaLectura,
            parametrosConsultaLectura
        ) // devuelve la fila de la consulta sea null o el entrenador
        // logica de la busqueda

        val existeUsuario = resultadoConsultaLectura.moveToFirst() // mueve el cursor al primer elemento si no hay registro no mueve a ningun lado
        val usuarioEncontrado = BEntrenador(0, "", "")
//        val arreglo = arrayListOf<BEntrenador>() // si quiero devolver varios entrenadores
        if (existeUsuario) { // si existe el usuario
            do {
                // asegurase del tipo de dato que nos devuleve la consulta
                val id = resultadoConsultaLectura.getInt(0) //indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val descripcion = resultadoConsultaLectura.getString(2)
                if (id != null) {
                    // si es arreglo
//                    val usuarioEncontrado = BEntrenador(0, "", "")

                    // llenar el arreglo con un nuevo BEntrenador
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion

//                    arreglo.add(usuarioEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun actualizarEntrenadorFormulario(
        nombre: String,
        descripcion: String,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        // where id = ?
        val parametroConsultaUpdate =
            arrayOf(idActualizar.toString()) // arreglo de parametros para la consulta
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR", // Nombre Tabla
                valoresAActualizar, // Valores a actualizar
                "id=?", // Where id = ?
                parametroConsultaUpdate, // Parametros consulta
//                arrayOf( // esto es lo mismo de arriba
//                    // parametrosWhere
//                    idActualizar.toString()
//                ) // parametrosWhere
            )
        conexionEscritura.close()
        return resultadoActualizacion.toInt() != -1
    }


}