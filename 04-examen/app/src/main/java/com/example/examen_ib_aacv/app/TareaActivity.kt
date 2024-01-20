package com.example.examen_ib_aacv.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.layout.MyToolbar

class TareaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea)

        MyToolbar().show(this, "Tareas Domesticas", true)

    }
}