package com.example.gr1aacv2023b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)
        mostrarSnackbar("${nombre} ${apellido} ${edad}")
        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)
        boton.setOnClickListener {
            devolverRespuesta()
        }
    }

    fun devolverRespuesta() {
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nombreModificado", "Alejandro")
        intentDevolverParametros.putExtra("edadModificada", 22)
        setResult(
            RESULT_OK,
            intentDevolverParametros // Le envio los parametros del intent
        ) // Aqui se cierra la actividad
        finish() // Aqui se cierra la actividad
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.id_layout_main), //view
                texto, //texto
                Snackbar.LENGTH_INDEFINITE //tiempo
            )
            .show()
    }
}

