package com.example.examen_ib_aacv.app.adaptadores

import android.app.AlertDialog
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ib_aacv.app.fragmentos.CrearTareaFragment
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.app.actividades.TareaActivity
import com.example.examen_ib_aacv.data.entidades.Tarea
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.CollectionReference


class TareaAdaptador(private var listaTareas: List<Tarea>) :
    RecyclerView.Adapter<TareaAdaptador.TareaViewHolder>() {

    private val db = FirebaseFirestore.getInstance()
    private val tareasCollection = db.collection("tareas") // Colección de tareas en Firestore

    class TareaViewHolder(itemView: View, private val tareasCollection: CollectionReference) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        val nombre: TextView = itemView.findViewById(R.id.tv_nombre_tarea)
        val descripcion: TextView = itemView.findViewById(R.id.tv_descripcion_tarea)
        val idPersona: TextView = itemView.findViewById(R.id.tv_id_persona_tarea)
        val idTarea: TextView = itemView.findViewById(R.id.tv_id_tarea)

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
                editarTarea()
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
                builder.setTitle("Eliminar Tarea")
                builder.setMessage("¿Está seguro de eliminar la tarea?")
                builder.setPositiveButton("Si") { dialog, which ->
                    eliminarTarea()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            } catch (e: Exception) {
                mostrarSnackbar("Se produjo un error al eliminar la tarea ${idTarea.text}")
            }
        }

        private fun eliminarTarea() {
            tareasCollection.document(idTarea.text.toString())
                .delete()
                .addOnSuccessListener {
                    Snackbar.make(itemView, "Tarea eliminada", Snackbar.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Snackbar.make(
                        itemView,
                        "Error al eliminar la tarea: ${e.message}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
        }

        fun editarTarea() {
            try {
                val fragmento = CrearTareaFragment()
                val activity = itemView.context as TareaActivity
                val bundle = Bundle()
                bundle.putString("idTarea", idTarea.text.toString())
                bundle.putString("idPersona", idPersona.text.toString())
                bundle.putString("nombreTarea", nombre.text.toString())
                bundle.putString("descripcionTarea", descripcion.text.toString())
                fragmento.arguments = bundle
                fragmento.show(activity.supportFragmentManager, "Editar Tarea")
            } catch (e: Exception) {
                mostrarSnackbar("Se produjo un error al editar la tarea ${idTarea.text}")
            }
        }

        fun mostrarSnackbar(texto: String) {
            Snackbar
                .make(itemView, texto, Snackbar.LENGTH_LONG)
                .show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tarea_item, parent, false)
        return TareaViewHolder(view, tareasCollection)
    }

    override fun getItemCount() = listaTareas.size

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = listaTareas[position]
        holder.nombre.text = tarea.nombre
        holder.descripcion.text = tarea.descripcion
        holder.idPersona.text = tarea.idPersona.toString()
        holder.idTarea.text = tarea.id.toString()
    }

    fun actualizarDatos(listaTareas: List<Tarea>) {
        this.listaTareas = listaTareas
        notifyDataSetChanged()
    }
}
