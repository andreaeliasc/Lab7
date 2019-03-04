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
    fun REGRESO(view: View) {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun AGREGAR(view: View) {
        val values = ContentValues()
        values.put(DataBase.NOMBRE, NewName.text.toString())
       values.put(DataBase.CEL, Celular.text.toString())
        values.put(DataBase.EMAIL, Correo.text.toString())


        if (NewName.text.toString() !="" && Celular.text.toString()!=""&& Correo.text.toString()!=""){
            val uri = contentResolver.insert(DataBase.db_URI,values)
            MAIN.Cn.add(Cn(Celular.text.toString(),NewName.text.toString(),Correo.text.toString()))

            Toast.makeText(applicationContext,"LOGRADO",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(applicationContext,"FALLA",Toast.LENGTH_SHORT).show()
        }

        NewName.setText("")
        Celular.setText("")
        Correo.setText("")
    }




}
