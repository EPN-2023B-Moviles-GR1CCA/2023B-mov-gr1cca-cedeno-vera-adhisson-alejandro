package com.example.examen_ib_aacv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

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

    }


}