package com.example.examen_ib_aacv.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.data.entidades.Persona

class PersonaAdaptador(private val listaPersonas: List<Persona>) :
    RecyclerView.Adapter<PersonaAdaptador.PersonaViewHolder>() {

    class PersonaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tv_nombre)
        val id: TextView = itemView.findViewById(R.id.tv_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.persona_item, parent, false)
        return PersonaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int) {
        val persona = listaPersonas[position]
        holder.nombre.text = persona.nombre
        holder.id.text = persona.id.toString()
//        holder.nombre.text = "Nombre: " + persona.nombre
//        holder.id.text = "ID: " + persona.id.toString()
    }

    override fun getItemCount() = listaPersonas.size
}