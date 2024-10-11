package com.vicksam.ferapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class sqlLiteHelper(context: Context) : SQLiteOpenHelper(
    context, "registroEmocional.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = """
            CREATE TABLE registroEmocional (
                _id INTEGER PRIMARY KEY AUTOINCREMENT, 
                Paciente INTEGER, 
                Fecha TEXT, 
                Emociones TEXT, 
                Precision REAL, 
                Error REAL
            )
        """.trimIndent()
        db!!.execSQL(ordenCreacion)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS registroEmocional"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }

    fun addData(paciente: String, fecha: String, emocion: String, precision: Float, error: Float) {
        val datos = ContentValues().apply {
            put("Paciente", paciente)
            put("Fecha", fecha)
            put("Emociones", emocion)
            put("Precision", precision)
            put("Error", error)
        }

        val db = this.writableDatabase
        db.insert("registroEmocional", null, datos)
        db.close()
    }
}

