package com.example.projetofinalandroid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Vacina (var id: Long = -1, var nomeVacina: String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaVacinas.CAMPO_NOME_VACINA, nomeVacina)


        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Vacina {
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val colNomeVacina = cursor.getColumnIndex(TabelaVacinas.CAMPO_NOME_VACINA)


            val id = cursor.getLong(colId)

            val nomeVacina = cursor.getString(colNomeVacina)


            return Vacina(id, nomeVacina)
        }
    }
}