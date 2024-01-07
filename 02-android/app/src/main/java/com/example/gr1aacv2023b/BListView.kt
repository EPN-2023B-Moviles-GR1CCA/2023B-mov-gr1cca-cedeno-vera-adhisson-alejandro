package com.example.gr1aacv2023b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

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
//        inflater.inflate(R.menu.menu, menu)
        // obtenemos el id del arraylist seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion

    }

}