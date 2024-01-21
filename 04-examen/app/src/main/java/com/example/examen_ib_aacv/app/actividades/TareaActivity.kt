package com.example.examen_ib_aacv.app.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ib_aacv.app.fragmentos.CrearTareaFragment
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.data.bdd.BaseDeDatosHelper
import com.example.examen_ib_aacv.app.adaptadores.TareaAdaptador
import com.example.examen_ib_aacv.layout.MyToolbar
import com.getbase.floatingactionbutton.FloatingActionButton

class TareaActivity : AppCompatActivity() {

    lateinit var adaptador: TareaAdaptador
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
            actualizarRecyclerView()

        }

        val baseDeDatosHelper = BaseDeDatosHelper(this)
        val listaTareas = baseDeDatosHelper.obtenerTareas()
        adaptador = TareaAdaptador(listaTareas)

        val rvTareas: RecyclerView = findViewById(R.id.rv_tareas)
        rvTareas.layoutManager = LinearLayoutManager(this)
        rvTareas.adapter = adaptador

    }

    fun actualizarRecyclerView() {
        val baseDeDatosHelper = BaseDeDatosHelper(this)
        val listaTareas = baseDeDatosHelper.obtenerTareas()
        adaptador = TareaAdaptador(listaTareas)
        adaptador.notifyDataSetChanged()
    }
}