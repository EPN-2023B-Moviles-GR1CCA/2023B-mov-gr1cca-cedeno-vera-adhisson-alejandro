package com.example.gr1aacv2023b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap

class GGoogleMapsActivity : AppCompatActivity() {

    private lateinit var mapa: GoogleMap
    var permisos = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
        solicitarPermisos()
    }

    fun solicitarPermisos() {
        val contexto = this.applicationContext
        val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
        val permisoCoarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                // permisos que se van a verificar
                permisoFineLocation
            )
        val tienePermisos =
            permisosFineLocation == android.content.pm.PackageManager.PERMISSION_GRANTED
        if (tienePermisos) {
            permisos = true
        } else {
            // solicitar permisos
            ActivityCompat.requestPermissions(
                this, // contexto
                arrayOf(
                    permisoFineLocation,
                    permisoCoarseLocation
                ),
                1 // Codigo que se va a verificar
            )
        }
    }


}