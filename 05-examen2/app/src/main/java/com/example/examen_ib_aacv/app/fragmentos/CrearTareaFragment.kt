package com.example.examen_ib_aacv.app.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.app.interfaces.OnDataChangeListener
import com.example.examen_ib_aacv.data.entidades.Tarea
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class CrearTareaFragment : DialogFragment() {

    private val db = FirebaseFirestore.getInstance()
    private val tareasCollection = db.collection("tareas")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crear_tarea, container, false)
        if (arguments == null) return view

        val nombreTarea = arguments?.getString("nombreTarea")
        val editTextNombre = view.findViewById<EditText>(R.id.edtxt_nombre_tarea)
        editTextNombre.setText(nombreTarea)

        val descripcionTarea = arguments?.getString("descripcionTarea")
        val editTextDescripcion = view.findViewById<EditText>(R.id.edtxt_descripcion_tarea)
        editTextDescripcion.setText(descripcionTarea)

        val idPersona = arguments?.getInt("idPersona")
        val editTextIdPersona = view.findViewById<EditText>(R.id.edtxt_id_persona_tarea)
        idPersona?.let { editTextIdPersona.setText(it.toString()) }

        val titulo = view.findViewById<TextView>(R.id.tv_titulo_tarea)
        titulo.text = if (nombreTarea != null) "Editar Tarea" else "Crear Tarea"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCerrar = view.findViewById<View>(R.id.btn_salir_tarea)
        btnCerrar.setOnClickListener {
            dismiss()
        }

        val btnGuardarTarea = view.findViewById<Button>(R.id.btn_guardar_tarea)
        btnGuardarTarea.setOnClickListener {
            val nombreTarea = view.findViewById<EditText>(R.id.edtxt_nombre_tarea).text.toString()
            val descripcionTarea =
                view.findViewById<EditText>(R.id.edtxt_descripcion_tarea).text.toString()
            val idPersonaText =
                view.findViewById<EditText>(R.id.edtxt_id_persona_tarea).text.toString()
            val idPersona = idPersonaText.toIntOrNull() ?: 0

            val tarea = Tarea(
                id = null, // Firestore generará un ID automáticamente
                idPersona = idPersona,
                nombre = nombreTarea,
                descripcion = descripcionTarea
            )

            tareasCollection.add(tarea)
                .addOnSuccessListener {
                    mostrarSnackbar("Se creó la tarea")
                    (activity as? OnDataChangeListener)?.onDataChange()
                    dismiss()
                }
                .addOnFailureListener { e ->
                    mostrarSnackbar("Error al crear la tarea: ${e.message}")
                }
        }
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar.make(
            requireView(), // Parent view
            texto, // Texto
            Snackbar.LENGTH_LONG // Duración
        ).show()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
