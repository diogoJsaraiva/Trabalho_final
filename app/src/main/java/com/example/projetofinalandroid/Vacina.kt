package com.example.projetofinalandroid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns


data class Vacina (var id: Long = -1, var nome: String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaVacina.CAMPO_NOME, nome)


        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Vacina {
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val colNome = cursor.getColumnIndex(TabelaVacina.CAMPO_NOME)


            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)


            return Vacina(id, nome)
        }
    }
}