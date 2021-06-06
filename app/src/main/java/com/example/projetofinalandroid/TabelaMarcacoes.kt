package com.example.projetofinalandroid

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaMarcacoes(db: SQLiteDatabase) : BaseColumns  {
    private val db: SQLiteDatabase = db
    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_DATA_DOSE_1 TEXT NOT NULL, $CAMPO_CHECK_DOSE_1 TEXT NOT NULL, $CAMPO_DATA_DOSE_2 TEXT NOT NULL, $CAMPO_CHECK_DOSE_2 TEXT NOT NULL,$CAMPO_ID_VACINA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ID_VACINA) REFERENCES ${TabelaVacina.NOME_TABELA}, FOREIGN KEY ($CAMPO_ID_PESSOA) REFERENCES ${TabelaPessoas.NOME_TABELA} )")

    }

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
        selection: String,
        selectionArgs: Array<String>,
        groupBy: String,
        having: String,
        orderBy: String
    ): Cursor? {
        return db.query(NOME_TABELA,columns,selection,selectionArgs,groupBy,having,orderBy)
    }

    //CRUD




    companion object{
        const val NOME_TABELA = "Marcação"
        const val CAMPO_DATA_DOSE_1 = "datadose1"
        const val CAMPO_CHECK_DOSE_1 = "levoudose"
        const val CAMPO_DATA_DOSE_2 = "datadose2"
        const val CAMPO_CHECK_DOSE_2 = "levoudose"
        const val CAMPO_ID_VACINA = "id_vacina"
        const val CAMPO_ID_PESSOA = "id_pessoa"

    }




}