package com.example.examen_ib_aacv.data.entidades.bdd

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FirestoreManager {

    private val db = FirebaseFirestore.getInstance()
    private val personasCollection = db.collection("personas")
    private val tareasCollection = db.collection("tareas")

    fun crearPersona(nombre: String) {
        val persona = hashMapOf(
            "nombre" to nombre
        )
        personasCollection.add(persona)
    }

    fun eliminarPersona(personaId: String) {
        personasCollection.document(personaId).delete()
    }

    fun actualizarPersona(personaId: String, nuevoNombre: String) {
        personasCollection.document(personaId).update("nombre", nuevoNombre)
    }

    fun obtenerPersonas(callback: (List<Map<String, Any>>) -> Unit) {
        personasCollection.get().addOnSuccessListener { result ->
            val listaPersonas = mutableListOf<Map<String, Any>>()
            for (document in result) {
                listaPersonas.add(document.data)
            }
            callback(listaPersonas)
        }
    }

    // Repite el proceso similar para las tareas
}