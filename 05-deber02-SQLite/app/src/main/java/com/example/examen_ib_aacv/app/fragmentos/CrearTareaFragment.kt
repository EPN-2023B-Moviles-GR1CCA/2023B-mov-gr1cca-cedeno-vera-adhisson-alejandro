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
import com.example.examen_ib_aacv.data.bdd.BaseDeDatos
import com.google.android.material.snackbar.Snackbar


class CrearTareaFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(
                R.layout.fragment_crear_tarea,
                container,
                false
            )
        if (arguments == null) return view

        val nombreTarea = arguments?.getString("nombreTarea")
        val editTextNombre = view.findViewById<EditText>(R.id.edtxt_nombre_tarea)
        editTextNombre.setText(nombreTarea)

        val descripcionTarea = arguments?.getString("descripcionTarea")
        val editTextDescripcion = view.findViewById<EditText>(R.id.edtxt_descripcion_tarea)
        editTextDescripcion.setText(descripcionTarea)

        val idPersona = arguments?.getInt("idPersona")
        val editTextIdPersona = view.findViewById<EditText>(R.id.edtxt_id_persona_tarea)
        editTextIdPersona.setText(idPersona.toString())

        val titulo = view.findViewById<TextView>(R.id.tv_titulo_tarea)
        titulo.setText(if (nombreTarea != null) "Editar Tarea" else "Crear Tarea")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //manejo de eventos

        val btnCerrar = view.findViewById<View>(R.id.btn_salir_tarea)
        btnCerrar.setOnClickListener {
            dismiss()
        }

        val btnGuardarTarea = view.findViewById<Button>(R.id.btn_guardar_tarea)
        btnGuardarTarea.setOnClickListener {
            val nombreTarea = view.findViewById<EditText>(R.id.edtxt_nombre_tarea)
            val descripcionTarea = view.findViewById<EditText>(R.id.edtxt_descripcion_tarea)
            val idPersona = view.findViewById<EditText>(R.id.edtxt_id_persona_tarea)

            val idTarea = arguments?.getInt("idTarea")

            if (idTarea != null) {
                // si se paso un idTarea, entonces se va a editar
                val respuesta = BaseDeDatos
                    .tablasBDD!!.actualizarTarea(
                        idTarea,
                        idPersona.text.toString().toInt(),
                        nombreTarea.text.toString(),
                        descripcionTarea.text.toString(),
                    )
                if (respuesta) mostrarSnackbar("Se actualizo la tarea")
            } else {
                // si no se paso un idTarea, entonces se va a crear
                val respuesta = BaseDeDatos
                    .tablasBDD!!.crearTarea(
                        idPersona.text.toString().toInt(),
                        nombreTarea.text.toString(),
                        descripcionTarea.text.toString(),
                    )
                if (respuesta) mostrarSnackbar("Se creo la tarea")
            }
            (activity as OnDataChangeListener).onDataChange()
            dismiss()


        }

    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                activity?.findViewById(android.R.id.content) ?: return, // Parent view
                texto, //texto
                Snackbar.LENGTH_LONG //tiempo
            )
            .show()
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