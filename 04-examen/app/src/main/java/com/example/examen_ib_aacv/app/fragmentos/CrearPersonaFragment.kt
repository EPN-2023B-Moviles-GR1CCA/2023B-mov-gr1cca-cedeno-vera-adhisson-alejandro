package com.example.examen_ib_aacv.app.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.data.bdd.BaseDeDatos
import com.google.android.material.snackbar.Snackbar

class CrearPersonaFragment : DialogFragment {

    constructor() : super()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(
                R.layout.fragment_crear_persona,
                container,
                false
            )
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

        //manejo de eventos

        val btnCerrar = view.findViewById<View>(R.id.btn_salir)
        btnCerrar.setOnClickListener {
            dismiss()
        }

//        val btnGuardarPersona = view.findViewById<View>(R.id.btn_guardar_persona)
//        btnGuardarPersona.setOnClickListener {
//            val nombre = view.findViewById<EditText>(R.id.edtxt_nombre_persona)
//            val respuesta = BaseDeDatos
//                .tablasBDD!!.crearPersona(
//                    nombre.text.toString()
//
//                )
//            if (respuesta) mostrarSnackbar("Se creo la persona")
//        }

        val btnGuardarPersona = view.findViewById<View>(R.id.btn_guardar_persona)
        btnGuardarPersona.setOnClickListener {
            val nombre = view.findViewById<EditText>(R.id.edtxt_nombre_persona)
            val idPersona = arguments?.getInt("id")
            if (idPersona != null) {
                // Si se pasó un ID de persona, actualiza la persona existente
                val respuesta = BaseDeDatos
                    .tablasBDD!!.actualizarNombrePersona(idPersona, nombre.text.toString())
                if (respuesta) mostrarSnackbar("Se actualizó la persona")
            } else {
                // Si no se pasó un ID de persona, crea una nueva persona
                val respuesta = BaseDeDatos
                    .tablasBDD!!.crearPersona(nombre.text.toString())
                if (respuesta) mostrarSnackbar("Se creó la persona")
            }
        }

    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                requireView(), // Parent view
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