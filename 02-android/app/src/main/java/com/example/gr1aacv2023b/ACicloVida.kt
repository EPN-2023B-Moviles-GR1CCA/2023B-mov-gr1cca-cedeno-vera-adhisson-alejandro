package com.example.gr1aacv2023b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {

    var textoGLobal = ""

    fun mostrarSnackbar(texto: String) {
        textoGLobal += " $texto"
        Snackbar
            .make(
                findViewById(R.id.cl_ciclo_vida), //view
                textoGLobal, //texto
                Snackbar.LENGTH_INDEFINITE //tiempo
            )
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("OnCreate")
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackbar("onStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSnackbar("onResume")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackbar("onRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackbar("onPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSnackbar("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            //guardar datos primitivos
            putString("textoGuardado", textoGLobal) //clave, valor
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // recuperar las variables primitivas
        val textoRecuperado: String? = savedInstanceState
            .getString("textoGuardado") //clave

        if (textoRecuperado != null) {
            textoGLobal = textoRecuperado
            mostrarSnackbar(textoRecuperado)
        }
    }

}