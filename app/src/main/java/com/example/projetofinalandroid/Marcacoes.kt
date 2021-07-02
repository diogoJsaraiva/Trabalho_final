package com.example.projetofinalandroid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Marcacoes(
    var id: Long = -1, var datadose: Date, var numero_dose: Int,
    var idVacina: Long, var nomeVacina: String? = null) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaMarcacoes.CAMPO_DATA_DOSE, datadose.date)
        valores.put(TabelaMarcacoes.CAMPO_NUMERO_DOSE, numero_dose)
        valores.put(TabelaMarcacoes.CAMPO_ID_VACINA, idVacina)


        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Marcacoes {
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val coldatadose = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_DATA_DOSE)
            val colnumerodose = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_NUMERO_DOSE)
            val colvacina = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_ID_VACINA)

            val colNomeVacina = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_EXTERNO_NOME_Vacina)



            val id = cursor.getLong(colId)
            val datadose = cursor.getLong(coldatadose)
            val numerodose = cursor.getInt(colnumerodose)
            val vacina = cursor.getLong(colvacina)
            val nomeVacina  = if (colNomeVacina != -1) cursor.getString(colNomeVacina) else null

            return Marcacoes(id, Date(datadose),numerodose,vacina,nomeVacina)
        }
    }
}