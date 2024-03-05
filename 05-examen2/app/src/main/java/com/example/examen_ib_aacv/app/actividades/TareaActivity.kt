package com.example.examen_ib_aacv.app.actividades

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.app.adaptadores.TareaAdaptador
import com.example.examen_ib_aacv.app.fragmentos.CrearTareaFragment
import com.example.examen_ib_aacv.app.interfaces.OnDataChangeListener
import com.example.examen_ib_aacv.data.entidades.Tarea
import com.example.examen_ib_aacv.layout.MyToolbar
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class TareaActivity : AppCompatActivity(), OnDataChangeListener {

    private lateinit var adaptador: TareaAdaptador
    private val db = FirebaseFirestore.getInstance()
    private val tareasCollection = db.collection("tareas")

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

        adaptador = TareaAdaptador(emptyList())

        val rvTareas: RecyclerView = findViewById(R.id.rv_tareas)
        rvTareas.layoutManager = LinearLayoutManager(this)
        rvTareas.adapter = adaptador

        cargarTareasDesdeFirestore()
    }

    private fun cargarTareasDesdeFirestore() {
        tareasCollection.get()
            .addOnSuccessListener { result ->
                val listaTareas = mutableListOf<Tarea>()
                var id = 1 // ID inicial
                for (document in result) {
                    try {
                        val tarea = document.toObject(Tarea::class.java)
                        tarea.id = id++ // Asignamos IDs consecutivos
                        listaTareas.add(tarea)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error al obtener tarea: ${e.message}")
                    }
                }
                adaptador.actualizarDatos(listaTareas)
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error obteniendo documentos.", exception)
            }
    }


    override fun onDataChange() {
        cargarTareasDesdeFirestore()
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            texto,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
