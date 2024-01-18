package com.example.gr1aacv2023b

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //base de datos
        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)


        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida
            .setOnClickListener {
                irActividad(ACicloVida::class.java)
            }

        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView
            .setOnClickListener {
                irActividad(BListView::class.java)
            }

        val botonIntentImplicito = findViewById<Button>(
            R.id.btn_ir_intent_implicito
        )
        botonIntentImplicito
            .setOnClickListener {
                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK, // este es el tipo de accion que quiero hacer
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                callbackIntentImplicitoTelefono.launch(intentConRespuesta)
            }

        val botonIntentExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonIntentExplicito
            .setOnClickListener {
                abrirActividadConParametros(
                    CIntentExplicitoParametros::class.java
                )
            }

        val botonSqllite = findViewById<Button>(R.id.btn_sqlite)
        botonSqllite
            .setOnClickListener {
                irActividad(ECrudEntrenador::class.java)
            }

        val botonRView = findViewById<Button>(R.id.btn_recycler_view)
        botonRView
            .setOnClickListener {
                irActividad(FRecyclerView::class.java)
            }

        val botonGMaps = findViewById<Button>(R.id.btn_google_maps)
        botonGMaps
            .setOnClickListener {
                irActividad(GGoogleMapsActivity::class.java)
            }

    } // Fin onCreate

    fun abrirActividadConParametros(
        clase: Class<*>
    ) {
        val intentExplicito = Intent(this, clase)
        // Enviar parametros (solamente variables primitivas)
        intentExplicito.putExtra("nombre", "Adhisson")
        intentExplicito.putExtra("apellido", "Cedeño")
        intentExplicito.putExtra("edad", 23)
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

    fun irActividad( // me va a servir para ir a otra actividad
        clase: Class<*>
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
        this.startActivity(intentExplicito)
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult( // este es el callback que se ejecuta cuando se cierra la actividad
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    // Aqui se recibe la lógica del negocio
                    val data: Intent? = result.data
                    mostrarSnackBar(
                        "${data?.getStringExtra("nombreModificado")}"
                    )
                }
            }
        }

    fun mostrarSnackBar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.id_layout_main), //view
                texto, //texto
                Snackbar.LENGTH_INDEFINITE //tiempo
            )
            .show()
    }

    val callbackIntentImplicitoTelefono =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    // Aqui se recibe la lógica del negocio
                    val uri: Uri = result.data!!.data!!
                    val cursor = contentResolver.query(
                        uri, null, null, null, null, null
                    )
                    cursor?.moveToFirst()
                    val indiceTelefono = cursor?.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                    val telefono = cursor?.getString(
                        indiceTelefono!!
                    )
                    cursor?.close()
                    mostrarSnackBar(
                        "Telefono: ${telefono}"
                    )
                }
            }
        }


}