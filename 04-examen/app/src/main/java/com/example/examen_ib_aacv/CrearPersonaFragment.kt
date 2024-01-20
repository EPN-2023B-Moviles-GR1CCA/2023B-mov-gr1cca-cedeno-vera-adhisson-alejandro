package com.example.examen_ib_aacv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.examen_ib_aacv.data.BaseDeDatos
import com.google.android.material.snackbar.Snackbar

class CrearPersonaFragment : DialogFragment {

    constructor() : super()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crear_persona, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //manejo de eventos

        val btnCerrar = view.findViewById<View>(R.id.btn_salir)
        btnCerrar.setOnClickListener {
            dismiss()
        }

        val btnGuardarPersona = view.findViewById<View>(R.id.btn_guardar_persona)
        btnGuardarPersona.setOnClickListener {
            val nombre = view.findViewById<EditText>(R.id.edtxt_nombre_persona)
            val respuesta = BaseDeDatos
                .tablasBDD!!.crearPersona(
                    nombre.text.toString()

                )
            if (respuesta) mostrarSnackbar("Se creo la persona")
        }

    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                requireView(), // Parent view
                texto, //texto
                Snackbar.LENGTH_INDEFINITE //tiempo
            )
            .show()
    }


}