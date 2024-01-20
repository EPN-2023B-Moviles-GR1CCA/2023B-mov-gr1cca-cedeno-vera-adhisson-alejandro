package com.example.examen_ib_aacv.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examen_ib_aacv.CrearTareaFragment
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.layout.MyToolbar
import com.getbase.floatingactionbutton.FloatingActionButton

class TareaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea)

        MyToolbar().show(this, "Tareas Domesticas", true)

        val fabCrearTarea: FloatingActionButton = findViewById(R.id.fab_crear_tarea)
        fabCrearTarea.setOnClickListener {
            // Abre el fragmento como ventana flotante
            val fragment = CrearTareaFragment()
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .commit()
        }

    }
}