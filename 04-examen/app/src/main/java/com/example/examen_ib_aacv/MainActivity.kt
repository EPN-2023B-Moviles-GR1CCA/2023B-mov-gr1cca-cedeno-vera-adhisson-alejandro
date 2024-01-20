package com.example.examen_ib_aacv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.examen_ib_aacv.app.PersonaActivity
import com.example.examen_ib_aacv.app.TareaActivity
import com.example.examen_ib_aacv.layout.MyToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyToolbar().show(this, "Examen IB", false)

        val btn_tareas_domesticas = findViewById<Button>(R.id.btn_tareas_domesticas)
            .setOnClickListener {
                irActividad(TareaActivity::class.java)
            }

        val btn_personas = findViewById<Button>(R.id.btn_personas)
            .setOnClickListener {
                irActividad(PersonaActivity::class.java)
            }


    }

    fun irActividad( // me va a servir para ir a otra actividad
        clase: Class<*>
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
        this.startActivity(intentExplicito)
    }


}