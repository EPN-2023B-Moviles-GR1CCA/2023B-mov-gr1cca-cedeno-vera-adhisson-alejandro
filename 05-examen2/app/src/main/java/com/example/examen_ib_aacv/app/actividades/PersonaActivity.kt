package com.example.examen_ib_aacv.app.actividades

import PersonaAdaptador
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ib_aacv.app.fragmentos.CrearPersonaFragment
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.app.interfaces.OnDataChangeListener
import com.example.examen_ib_aacv.data.entidades.Persona
import com.example.examen_ib_aacv.layout.MyToolbar
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class PersonaActivity : AppCompatActivity(), OnDataChangeListener {

    lateinit var adaptador: PersonaAdaptador
    private val db = FirebaseFirestore.getInstance()
    private val personasCollection = db.collection("personas")

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

        adaptador = PersonaAdaptador(emptyList())

        val rvPersonas: RecyclerView = findViewById(R.id.rv_personas)
        rvPersonas.layoutManager = LinearLayoutManager(this)
        rvPersonas.adapter = adaptador

        cargarPersonasDesdeFirestore()
    }

    private fun cargarPersonasDesdeFirestore() {
        personasCollection.get()
            .addOnSuccessListener { result ->
                val listaPersonas = mutableListOf<Persona>()
                var id = 1 // ID inicial
                for (document in result) {
                    try {
                        val persona = Persona(
                            id = id++, // Asignamos IDs consecutivos
                            nombre = document.getString("nombre") ?: ""
                        )
                        listaPersonas.add(persona)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error al obtener persona: ${e.message}")
                    }
                }
                adaptador.actualizarDatos(listaPersonas)
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error obteniendo documentos.", exception)
            }
    }


    override fun onDataChange() {
        cargarPersonasDesdeFirestore()
    }
}
