package com.example.projetofinalandroid

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPessoas (db: SQLiteDatabase) : BaseColumns {
    private val db : SQLiteDatabase = db
    fun cria() {
        db?.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $CAMPO_TELEFONE TEXT NOT NULL ,$CAMPO_EMAIL TEXT, $CAMPO_MORADA TEXT NOT NULL, $CAMPO_DATA_NASCIMENTO INTEGER NOT NULL,$CAMPO_ID_MARCACAO INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ID_MARCACAO) REFERENCES ${TabelaMarcacoes.NOME_TABELA})")
    }



    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
            columns: Array<String>,
            selection: String?,
            selectionArgs: Array<String>?,
            groupBy: String?, having: String?,
            orderBy: String?
    ): Cursor? {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_TABELA = "pessoas"
        const val CAMPO_NOME = "nome"
        const val CAMPO_TELEFONE = "telefone"
        const val CAMPO_EMAIL = "email"
        const val CAMPO_MORADA = "morada"
        const val CAMPO_DATA_NASCIMENTO = "datanascimento"
        const val CAMPO_ID_MARCACAO = "id_marcacao"

        val TODOS_CAMPOS = arrayOf(BaseColumns._ID, TabelaPessoas.CAMPO_NOME,
                TabelaPessoas.CAMPO_TELEFONE,
                TabelaPessoas.CAMPO_TELEFONE,
                TabelaPessoas.CAMPO_EMAIL,
                TabelaPessoas.CAMPO_MORADA,
                TabelaPessoas.CAMPO_DATA_NASCIMENTO,
                TabelaPessoas.CAMPO_ID_MARCACAO

        )
    }

}