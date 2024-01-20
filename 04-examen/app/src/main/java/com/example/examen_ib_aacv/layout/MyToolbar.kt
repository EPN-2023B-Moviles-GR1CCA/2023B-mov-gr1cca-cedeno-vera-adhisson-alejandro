package com.example.examen_ib_aacv.layout

import androidx.appcompat.app.AppCompatActivity
import com.example.examen_ib_aacv.R

class MyToolbar {
    fun show(activity: AppCompatActivity, title: String, upButton: Boolean) {
        val toolbar: androidx.appcompat.widget.Toolbar = activity.findViewById(R.id.myToolBar)
        activity.setSupportActionBar(toolbar) // es para que se muestre la toolbar en la actividad actual
        activity.supportActionBar?.title = title
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(upButton)
    }
}