package com.example.gr1aacv2023b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions


class GGoogleMapsActivity : AppCompatActivity() {

    private lateinit var mapa: GoogleMap
    var permisos = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
        solicitarPermisos()
        iniciarLogicaMapa()
    }

    fun iniciarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            // whit(x) => if(X != null)
            with(googleMap) {
                mapa = googleMap
                establecerConfiguracionMapa()
                moverQuicentro()
                aniadirPolilinea()
                aniadirPoligono()
                escucharListeners()
            }
        }
    }

    fun escucharListeners(){
        mapa.setOnPolygonClickListener {
            Log.i("mapa", "setOnPolygonClickListener ${it}")
            it.tag //identificador
        }
        mapa.setOnPolylineClickListener {
            Log.i("mapa", "setOnPolylineClickListener ${it}")
            it.tag //identificador
        }
        mapa.setOnMarkerClickListener {
            Log.i("mapa", "setOnMarkerClickListener ${it}")
            it.showInfoWindow()
            return@setOnMarkerClickListener true
        }
        mapa.setOnCameraMoveListener {
            Log.i("mapa", "setOnCameraMoveListener")
        }
        mapa.setOnCameraMoveStartedListener {
            Log.i("mapa", "setOnCameraMoveStartedListener")
        }
        mapa.setOnCameraIdleListener {
            Log.i("mapa", "setOnCameraIdleListener")
        }
    }
    fun aniadirPoligono() {
        with(mapa) {
            //poligono
            val poligonoUno = mapa
                .addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .add(
                            LatLng(
                                -0.1759187040647396,
                                -78.48506472421384
                            ),
                            LatLng(
                                -0.17632468492901104,
                                -78.48265589308046
                            ),
                            LatLng(
                                -0.17746143130181483,
                                -78.4770533307815
                            )
                        )
                )
            poligonoUno.fillColor = -0xc771c4
            poligonoUno.tag = "poligono-1" // identificador
        }
    }

    fun aniadirPolilinea() {
        with(mapa) {
            val polilineaUno = mapa
                .addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .add(
                            LatLng(
                                -0.1759187040647396,
                                -78.48506472421384
                            ),
                            LatLng(
                                -0.17632468492901104,
                                -78.48265589308046
                            ),
                            LatLng(
                                -0.17746143130181483,
                                -78.4770533307815
                            )
                        )
                )
            polilineaUno.color = -0xc771c4
            polilineaUno.tag = "polilinea-1" // identificador
        }
    }

    fun moverQuicentro() {
        val quicentro = LatLng(-0.176823, -78.480979)
        val zoom = 17f
        val titulo = "Quicentro"
        val markQuicentro = aniadirMarcador(quicentro, titulo)
        moverCamaraConZoom(quicentro, zoom)

    }

    fun aniadirMarcador(latLng: LatLng, title: String): Marker {
        return mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )!! // pilas esos !! son para que no sea nulo y sirva
    }

    fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f) {
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )
    }

    fun establecerConfiguracionMapa() {
        val contexto = this.applicationContext
        with(mapa) {
            val permisoFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos =
                permisoFineLocation == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (tienePermisos) {
                mapa.isMyLocationEnabled = true // tenermos permisos
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true // Defecto
        }
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