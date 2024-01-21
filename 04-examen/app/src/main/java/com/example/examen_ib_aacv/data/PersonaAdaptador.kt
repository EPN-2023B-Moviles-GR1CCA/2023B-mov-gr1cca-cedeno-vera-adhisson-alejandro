package com.example.examen_ib_aacv.data

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ib_aacv.CrearPersonaFragment
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.app.PersonaActivity
import com.example.examen_ib_aacv.data.entidades.Persona
import com.getbase.floatingactionbutton.FloatingActionButton


class PersonaAdaptador(private val listaPersonas: List<Persona>) :
    RecyclerView.Adapter<PersonaAdaptador.PersonaViewHolder>() {

    // En tu adaptador
    class PersonaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        val nombre: TextView = itemView.findViewById(R.id.tv_nombre)
        val id: TextView = itemView.findViewById(R.id.tv_id)

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {

            val editarItem = menu!!.add(
                this.adapterPosition,
                121,
                0,
                "Editar"
            )
            editarItem.setOnMenuItemClickListener {
                editarPersona()
                true
            }

            val eliminarItem = menu.add(
                this.adapterPosition,
                122,
                1,
                "Eliminar"
            )
            eliminarItem.setOnMenuItemClickListener {
                abrirDialogoEliminar()
                true
            }
        }

        fun abrirDialogoEliminar() {
            try {
                val builder = AlertDialog.Builder(itemView.context)
                builder.setTitle("Eliminar")
                builder.setMessage("¿Está seguro que desea eliminar a ${nombre.text}?")
                builder.setPositiveButton("Aceptar") { dialog, which ->
                    BaseDeDatos.tablasBDD!!.eliminarPersona(id.text.toString().toInt())
//                    (itemView.context as PersonaActivity).actualizarRecyclerView()
                }
                builder.setNegativeButton("Cancelar", null)
                val dialog: AlertDialog = builder.create()
                dialog.show()

            } catch (e: Exception) {
                mostrarSnackbar("Se produjo un error al eliminar la persona ${id.text}")
            }

        }

        fun mostrarSnackbar(texto: String) {
            com.google.android.material.snackbar.Snackbar
                .make(
                    itemView, // Parent view
                    texto, //texto
                    com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE //tiempo
                )
                .show()
        }


        fun editarPersona() {
            val fragmento = CrearPersonaFragment()
            val activity = itemView.context as PersonaActivity
            val bundle = Bundle()
            bundle.putInt("id", id.text.toString().toInt())
            bundle.putString("nombrePersona", nombre.text.toString())
            fragmento.arguments = bundle
            fragmento.show(activity.supportFragmentManager, "Editar Persona")
        }
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