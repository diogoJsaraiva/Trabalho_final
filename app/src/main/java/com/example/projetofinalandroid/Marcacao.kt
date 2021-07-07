package com.example.projetofinalandroid


import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Marcacao (var id: Long = -1, var data: Date, var idPessoa: Long, var idVacina: Long){
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaMarcacao.CAMPO_DATA, data.time)
            put(TabelaMarcacao.CAMPO_ID_Pessoa, idPessoa)
            put(TabelaMarcacao.CAMPO_ID_VACINA, idVacina)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Marcacao {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colData = cursor.getColumnIndex(TabelaMarcacao.CAMPO_DATA)

            val colIdPessoa= cursor.getColumnIndex(TabelaMarcacao.CAMPO_ID_Pessoa)
            val colIdVacina = cursor.getColumnIndex(TabelaMarcacao.CAMPO_ID_VACINA)

            val id = cursor.getLong(colId)
            val data = cursor.getLong(colData)
            val idPessoa = cursor.getLong(colIdPessoa)
            val idVacina = cursor.getLong(colIdVacina)

            return Marcacao(id, Date(data), idPessoa, idVacina)
        }
    }
}