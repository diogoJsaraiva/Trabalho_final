package com.example.projetofinalandroid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

class Marcacoes (var id: Long = -1, var dose1: String, var checkdose1 : String, var dose2:String,var checkdose2: String, var idVacina: Long) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaMarcacoes.CAMPO_DATA_DOSE_1, dose1)
        valores.put(TabelaMarcacoes.CAMPO_CHECK_DOSE_1, checkdose1)
        valores.put(TabelaMarcacoes.CAMPO_DATA_DOSE_2, dose2)
        valores.put(TabelaMarcacoes.CAMPO_CHECK_DOSE_2, checkdose2)
        valores.put(TabelaMarcacoes.CAMPO_ID_VACINA, idVacina)


        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Marcacoes {
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val coldose1 = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_DATA_DOSE_1)
            val colcheckdose1 = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_CHECK_DOSE_1)
            val coldose2 = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_DATA_DOSE_2)
            val colcheckdose2 = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_CHECK_DOSE_2)
            val colvacina = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_ID_VACINA)


            val id = cursor.getLong(colId)
            val dose1 = cursor.getString(coldose1)
            val check1 = cursor.getString(colcheckdose1)
            val dose2 = cursor.getString(coldose2)
            val check2 = cursor.getString(colcheckdose2)
            val vacina = cursor.getLong(colvacina)


            return Marcacoes(id, dose1,check1,dose2,check2,vacina)
        }
    }
}