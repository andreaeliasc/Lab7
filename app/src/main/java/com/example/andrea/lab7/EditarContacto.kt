package com.example.andrea.lab7

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_editar_contacto.*

class EditarContacto : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_contacto)
    }

    fun edit(view: View){
        val values= ContentValues()
        values.put(DataBase.NOMBRE, NAME.text.toString())
        values.put(DataBase.CEL, Phone.text.toString())
        values.put(DataBase.EMAIL, Mail.text.toString())
        MAIN.Cn.set(intent.getIntExtra("item",0), Cn(Phone.text.toString(),NAME.text.toString(),Mail.text.toString()))
        Toast.makeText(applicationContext,"Se ha editado el contacto de manera exitosa", Toast.LENGTH_SHORT).show()
        NAME.setText("")
        Phone.setText("")
        Mail.setText("")
    }
}


