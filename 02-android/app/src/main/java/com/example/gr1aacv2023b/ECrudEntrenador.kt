package com.example.gr1aacv2023b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class ECrudEntrenador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_entrenador)

        //logica para buscar entrenador
        val botonBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscarBDD
            .setOnClickListener {
                val id = findViewById<EditText>(R.id.input_id)
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val descripcion = findViewById<EditText>(R.id.input_descripcion)

                //buscar en la base de datos
                val entrenador = EBaseDeDatos.tablaEntrenador!!
                    .consultarEntrenadorPorID(id.text.toString().toInt())
                //setear los valores en los inputs
                if (entrenador.id == 0) {
                    mostrarSnackbar("No se encontro el entrenador")
                }
                id.setText(entrenador.id.toString())
                nombre.setText(entrenador.nombre)
                descripcion.setText(entrenador.descripcion)
                mostrarSnackbar("Se encontró el entrenador")

            }

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val descripcion = findViewById<EditText>(R.id.input_descripcion)
                val respuesta = EBaseDeDatos
                    .tablaEntrenador!!.crearEntrenador(
                        nombre.text.toString(),
                        descripcion.text.toString()
                    )
                if (respuesta) mostrarSnackbar("Se creo el entrenador")
            }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD
            .setOnClickListener {
                val id = findViewById<EditText>(R.id.input_id)
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val descripcion = findViewById<EditText>(R.id.input_descripcion)
                val respuesta = EBaseDeDatos
                    .tablaEntrenador!!.actualizarEntrenadorFormulario(
                        nombre.text.toString(),
                        descripcion.text.toString(),
                        id.text.toString().toInt()
                    )
                if (respuesta) mostrarSnackbar("Se actualizo el entrenador")

            }

        val botonEliminarBDD = findViewById<Button>(R.id.btn_eliminar_bdd)
        botonEliminarBDD
            .setOnClickListener {
                val id = findViewById<EditText>(R.id.input_id)
                val respuesta = EBaseDeDatos
                    .tablaEntrenador!!.eliminarEntrenadorFormulario(
                        id.text.toString().toInt()
                    )
                if (respuesta) mostrarSnackbar("Se elimino el entrenador")
            }


    } // fin on create

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.cl_sqlite), //view
                texto, //texto
                Snackbar.LENGTH_INDEFINITE //tiempo
            )
            .show()
    }

}