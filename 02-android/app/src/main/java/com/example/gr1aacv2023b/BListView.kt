package com.example.gr1aacv2023b

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class BListView : AppCompatActivity() {

    //cargo mi arreglo que esta guardado en memoria
    var arregloBEntrenadores = BBaseDatosMemoria.arregloBEntrenadores
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val listView = findViewById<ListView>(R.id.lv_list_view)

        //creo un adaptador para poder mostrar mi arreglo en el list view
        val adaptador = ArrayAdapter(
            this, //contexto
            // como se va a ver (XML)
            android.R.layout.simple_list_item_1,
            //que se va a mostrar
            arregloBEntrenadores
        )
        listView.adapter = adaptador // le asigno el adaptador al list view para que se muestre
        adaptador.notifyDataSetChanged()

        fun anadirEntrenador(adaptador: ArrayAdapter<BEntrenador>) {
            arregloBEntrenadores.add(
                BEntrenador(
                    arregloBEntrenadores.size + 1,
                    "Nuevo Entrenador ${arregloBEntrenadores.size + 1}",
                    "Descripcion ${arregloBEntrenadores.size + 1}"
                )
            )
            adaptador.notifyDataSetChanged()
        }

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_list_view)
        botonAnadirListView.setOnClickListener {
            anadirEntrenador(adaptador)
        }

        registerForContextMenu(listView) //para que se muestre el menu contextual

    } //fin onCreate

    var posicionItemSeleccionado = 0
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // llena el menu contextual
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        // obtenemos el id del arraylist seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion

    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.lv_list_view), // Donde va a aparecer
                texto, // El texto que va a aparecer
                Snackbar.LENGTH_LONG // Duración
            )
            .show()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                mostrarSnackbar("${posicionItemSeleccionado}")
                return true
            }

            R.id.mi_eliminar -> {
                mostrarSnackbar("${posicionItemSeleccionado}")
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                mostrarSnackbar("Eliminar Aceptado")
            })
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val opciones = resources.getStringArray(R.array.string_array_opciones_dialogo)

        val seleccionPrevia = booleanArrayOf(
            false, // false -> no esta seleccionado
            true, // true -> esta seleccionado
            false
        )
        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia
        ) { dialog,
            which,
            isChecked ->
            mostrarSnackbar("Dio click en el item $which")
        }
        val dialogo = builder.create()
        dialogo.show()
    }

}