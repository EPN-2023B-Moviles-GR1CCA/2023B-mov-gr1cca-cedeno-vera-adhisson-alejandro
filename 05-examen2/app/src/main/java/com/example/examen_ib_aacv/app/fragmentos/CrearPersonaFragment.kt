package com.example.examen_ib_aacv.app.fragmentos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.app.interfaces.OnDataChangeListener
import com.example.examen_ib_aacv.data.entidades.Persona
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class CrearPersonaFragment : DialogFragment() {

    private val db = FirebaseFirestore.getInstance()
    private val personasCollection = db.collection("personas")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crear_persona, container, false)
        if (arguments == null) return view

        val nombrePersona = arguments?.getString("nombrePersona")
        val editTextNombre = view.findViewById<EditText>(R.id.edtxt_nombre_persona)
        editTextNombre.setText(nombrePersona)
        val titulo = view.findViewById<TextView>(R.id.tv_titulo_persona)
        titulo.text = if (nombrePersona != null) "Editar Persona" else "Crear Persona"

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nombrePersona = arguments?.getString("nombrePersona")
        val editTextNombre = view.findViewById<EditText>(R.id.edtxt_nombre_persona)
        editTextNombre.setText(nombrePersona)
        val titulo = view.findViewById<TextView>(R.id.tv_titulo_persona)
        titulo.text = if (nombrePersona != null) "Editar Persona" else "Crear Persona"

        val btnCerrar = view.findViewById<View>(R.id.btn_salir)
        btnCerrar.setOnClickListener {
            dismiss()
        }

        val btnGuardarPersona = view.findViewById<View>(R.id.btn_guardar_persona)
        btnGuardarPersona.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val idPersona = arguments?.getString("id")
            if (idPersona != null) {
                // Si se pasó un ID de persona, actualiza la persona existente
                val personaRef = personasCollection.document(idPersona)
                personaRef.update("nombre", nombre)
                    .addOnSuccessListener {
                        mostrarSnackbar("Se actualizó la persona")
                        (activity as? OnDataChangeListener)?.onDataChange()
                        dismiss()
                    }
                    .addOnFailureListener { e ->
                        mostrarSnackbar("Error al actualizar la persona: ${e.message}")
                    }
            } else {
                val nuevaPersona = Persona(
                    id = null, // Firestore generará un ID automáticamente
                    nombre = nombre
                )
                personasCollection.add(nuevaPersona)
                    .addOnSuccessListener {
                        mostrarSnackbar("Se creó la persona")
                        (activity as? OnDataChangeListener)?.onDataChange()
                        dismiss()
                    }
                    .addOnFailureListener { e ->
                        mostrarSnackbar("Error al crear la persona: ${e.message}")
                    }
            }
        }
    }


    private fun mostrarSnackbar(texto: String) {
        Snackbar.make(requireView(), texto, Snackbar.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        // Hace que el diálogo del fragmento ocupe el ancho completo de la pantalla
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
