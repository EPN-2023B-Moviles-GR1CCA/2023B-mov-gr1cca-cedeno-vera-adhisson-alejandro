package com.example.examen_ib_aacv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import com.example.examen_ib_aacv.app.actividades.PersonaActivity
import com.example.examen_ib_aacv.app.actividades.TareaActivity
import com.example.examen_ib_aacv.layout.MyToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyToolbar().show(this, "Examen IIB", false)

        val btnTareasDomesticas = findViewById<Button>(R.id.btn_tareas_domesticas)
        btnTareasDomesticas.setOnClickListener {
            startActivity(Intent(this, TareaActivity::class.java))
        }

        val btnPersonas = findViewById<Button>(R.id.btn_personas)
        btnPersonas.setOnClickListener {
            startActivity(Intent(this, PersonaActivity::class.java))
        }
    }
}
