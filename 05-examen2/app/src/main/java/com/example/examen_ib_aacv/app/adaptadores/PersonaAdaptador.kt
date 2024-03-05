import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_ib_aacv.app.fragmentos.CrearPersonaFragment
import com.example.examen_ib_aacv.R
import com.example.examen_ib_aacv.app.actividades.PersonaActivity
import com.example.examen_ib_aacv.app.interfaces.OnDataChangeListener
import com.example.examen_ib_aacv.data.entidades.Persona
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class PersonaAdaptador(private var listaPersonas: List<Persona>) :
    RecyclerView.Adapter<PersonaAdaptador.PersonaViewHolder>() {

    class PersonaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        val nombre: TextView = itemView.findViewById(R.id.tv_nombre_persona)
        val id: TextView = itemView.findViewById(R.id.tv_id_persona)

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
                    val personasCollection = FirebaseFirestore.getInstance().collection("personas")
                    personasCollection.document(id.text.toString())
                        .delete()
                        .addOnSuccessListener {
                            Snackbar.make(itemView, "Persona eliminada", Snackbar.LENGTH_SHORT).show()
                            (itemView.context as? OnDataChangeListener)?.onDataChange() // notifica al adaptador que se ha eliminado un elemento
                        }
                        .addOnFailureListener { e ->
                            Snackbar.make(itemView, "Error al eliminar persona: ${e.message}", Snackbar.LENGTH_SHORT).show()
                        }
                }
                builder.setNegativeButton("Cancelar", null)
                val dialog: AlertDialog = builder.create()
                dialog.show()

            } catch (e: Exception) {
                mostrarSnackbar("Se produjo un error al eliminar la persona ${id.text}")
            }

        }

        fun mostrarSnackbar(texto: String) {
            Snackbar
                .make(
                    itemView, // Parent view
                    texto, //texto
                    Snackbar.LENGTH_INDEFINITE //tiempo
                )
                .show()
        }

        fun editarPersona() {
            val fragmento = CrearPersonaFragment()
            val activity = itemView.context as PersonaActivity
            val bundle = Bundle() // sirve para pasar datos entre fragmentos y actividades
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
    }

    override fun getItemCount() = listaPersonas.size

    fun actualizarDatos(listaPersonas: List<Persona>) {
        this.listaPersonas = listaPersonas
    }
}
