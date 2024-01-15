package com.example.gr1aacv2023b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerView : AppCompatActivity() {

    var totalLikes = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frecycler_view)
        inicializarRecyclerView()
    }

    fun inicializarRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_entrenadores)
        val adaptador = FRecyclerViewAdaptadorNombreDescripcion(
            this, // contexto
            BBaseDatosMemoria.arregloBEntrenadores, //Arreglo datos
            recyclerView, //RecyclerView
        )
        recyclerView.adapter = adaptador  // setear el adaptador al recyclerView
        recyclerView.itemAnimator =
            androidx.recyclerview.widget.DefaultItemAnimator() // animacion por defecto
        recyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this) // tipo de layout
        adaptador.notifyDataSetChanged()  // notificar al adaptador que los datos han cambiado

    }

    fun aumentarTotalLikes() {
        totalLikes = totalLikes + 1
        val textView = findViewById<TextView>(R.id.tv_total_likes)
        textView.text = totalLikes.toString()
    }


}