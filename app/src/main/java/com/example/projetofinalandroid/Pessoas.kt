package com.example.projetofinalandroid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

 data class Pessoas (var id: Long = -1, var nome: String, var telefone : String, var email:String,var morada: String, var dataNascimento: Date) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaPessoas.CAMPO_NOME, nome)
        valores.put(TabelaPessoas.CAMPO_TELEFONE, telefone)
        valores.put(TabelaPessoas.CAMPO_EMAIL, email)
        valores.put(TabelaPessoas.CAMPO_MORADA, morada)
        valores.put(TabelaPessoas.CAMPO_DATA_NASCIMENTO, dataNascimento.time)


        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Pessoas {
            val colId = cursor.getColumnIndex(BaseColumns._ID)

            val colNome = cursor.getColumnIndex(TabelaPessoas.CAMPO_NOME)
            val colTelefone = cursor.getColumnIndex(TabelaPessoas.CAMPO_TELEFONE)
            val colEmail = cursor.getColumnIndex(TabelaPessoas.CAMPO_EMAIL)
            val colMorada = cursor.getColumnIndex(TabelaPessoas.CAMPO_MORADA)
            val colNascimento = cursor.getColumnIndex(TabelaPessoas.CAMPO_DATA_NASCIMENTO)


            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val telefone = cursor.getString(colTelefone)
            val email = cursor.getString(colEmail)
            val morada = cursor.getString(colMorada)
            val nascimento = cursor.getLong(colNascimento)


            return Pessoas(id, nome,telefone,email,morada, Date(nascimento))
        }
    }
}