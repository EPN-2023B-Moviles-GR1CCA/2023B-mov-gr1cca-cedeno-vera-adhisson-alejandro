package com.example.examen_ib_aacv.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ib_aacv.CrearPersonaFragment
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.data.BaseDeDatosHelper
import com.example.examen_ib_aacv.data.PersonaAdaptador
import com.example.examen_ib_aacv.layout.MyToolbar
import com.getbase.floatingactionbutton.FloatingActionButton


class PersonaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persona)

        MyToolbar().show(this, "Personas", true)

        val fabCrearPersona: FloatingActionButton = findViewById(R.id.fab_crear_persona)
        fabCrearPersona.setOnClickListener {
            // Abre el fragmento como ventana flotante
            val fragment = CrearPersonaFragment()
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .commit()
        }

        val baseDeDatosHelper = BaseDeDatosHelper(this)
        val listaPersonas = baseDeDatosHelper.obtenerPersonas()

        val adaptador = PersonaAdaptador(listaPersonas)

        val rvPersonas: RecyclerView = findViewById(R.id.rv_personas)
        rvPersonas.layoutManager = LinearLayoutManager(this)
        rvPersonas.adapter = adaptador

    }
}