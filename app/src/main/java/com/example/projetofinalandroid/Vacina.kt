package com.example.projetofinalandroid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

class Vacina (var id: Long = -1, var nome: String, var quantidade : String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaVacina.CAMPO_NOME, nome)
        valores.put(TabelaVacina.CAMPO_QUANTIDADE, quantidade)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Vacina {
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val colNome = cursor.getColumnIndex(TabelaVacina.CAMPO_NOME)
            val colQuantidade = cursor.getColumnIndex(TabelaVacina.CAMPO_QUANTIDADE)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val quantidade = cursor.getString(colQuantidade)

            return Vacina(id, nome,quantidade)
        }
    }
}