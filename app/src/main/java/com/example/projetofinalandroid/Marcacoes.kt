package com.example.projetofinalandroid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

class Marcacoes(var id: Long = -1, var datadose: Int, var numero_dose: String,var idVacina: Long, var idPessoa : Long) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaMarcacoes.CAMPO_DATA_DOSE, datadose)
        valores.put(TabelaMarcacoes.CAMPO_NUMERO_DOSE, numero_dose)
        valores.put(TabelaMarcacoes.CAMPO_ID_VACINA, idVacina)
        valores.put(TabelaMarcacoes.CAMPO_ID_VACINA, idPessoa)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Marcacoes {
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val coldatadose = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_DATA_DOSE)
            val colnumerodose = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_NUMERO_DOSE)
            val colvacina = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_ID_VACINA)
            val colpessoa = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_ID_PESSOA)


            val id = cursor.getLong(colId)
            val datadose = cursor.getInt(coldatadose)
            val numerodose = cursor.getString(colnumerodose)
            val vacina = cursor.getLong(colvacina)
            val pessoa = cursor.getLong(colpessoa)

            return Marcacoes(id, datadose,numerodose,vacina,pessoa)
        }
    }
}