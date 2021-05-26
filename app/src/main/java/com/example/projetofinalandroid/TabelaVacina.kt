package com.example.projetofinalandroid

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaVacina(db: SQLiteDatabase) : BaseColumns  {
    private val db: SQLiteDatabase = db
    fun cria() = db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,$CAMPO_NOME TEXT NOT NULL,$CAMPO_QUANTIDADE NUMBER NOT NULL)")

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA,null,values)
    }

    fun update(values: ContentValues, whereArgs: Array<String>, whereClause: String): Int {
        return db.update(NOME_TABELA,values,whereClause,whereArgs)


    }

    fun delete(whereClause: String, WhereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA,whereClause,WhereArgs)
    }


    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(NOME_TABELA,columns,selection,selectionArgs,groupBy,having,orderBy)
    }

    //CRUD




    companion object{
        const val NOME_TABELA = "Vacina"
        const val CAMPO_NOME = "Vacina"
        const val CAMPO_QUANTIDADE = "Stock"
    }
}