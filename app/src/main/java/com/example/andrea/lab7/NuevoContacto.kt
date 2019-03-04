package com.example.andrea.lab7

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_editar_contacto.*
import kotlinx.android.synthetic.main.activity_nuevo_contacto.*

class NuevoContacto : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_contacto)
    }

fun addContact(view: View){
    val values = ContentValues()
    values.put(DataBase.NOMBRE, NAME.text.toString())


}
    fun REGRESO (view: View){
        val intent: Intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}
