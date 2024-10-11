package com.vicksam.ferapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class sqlLiteHelper(context: Context) : SQLiteOpenHelper(
    context, "registroEmocional.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = "CREATE TABLE registroEmocional " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Paciente INTEGER, Fecha TEXT, Emociones TEXT)"
        db!!.execSQL(ordenCreacion)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS registroEmocional"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }

    fun addData(paciente: String, fecha: String, emocion: String) {
        val datos = ContentValues()
        datos.put("Paciente", paciente)
        datos.put("Fecha", fecha)
        datos.put("Emociones", emocion)

        val db = this.writableDatabase
        db.insert("registroEmocional", null, datos)
        db.close()
    }
}

