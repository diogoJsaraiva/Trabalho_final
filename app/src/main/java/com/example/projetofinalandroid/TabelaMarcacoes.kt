package com.example.projetofinalandroid

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaMarcacoes(db: SQLiteDatabase) : BaseColumns  {
    private val db: SQLiteDatabase = db
    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_DATA_DOSE INTEGER NOT NULL,$CAMPO_ID_VACINA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ID_VACINA) REFERENCES ${TabelaVacina.NOME_TABELA} )")

    }

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA,null,values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
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
        val ultimaColuna = columns.size - 1

        var posColNomeVacina = -1 // -1 indica que a coluna não foi pedida
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_Vacina) {
                posColNomeVacina = i
                break
            }
        }

        if (posColNomeVacina == -1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomeVacina) {
                "${TabelaVacina.NOME_TABELA}.${TabelaVacina.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_Vacina"
            } else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "$NOME_TABELA INNER JOIN ${TabelaVacina.NOME_TABELA} ON ${TabelaVacina.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_Vacina"

        var sql = "SELECT $colunas FROM $tabelas"

        if (selection != null) sql += " WHERE $selection"

        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }

        if (orderBy != null) sql += " ORDER BY $orderBy"

        return db.rawQuery(sql, selectionArgs)
    }

    //CRUD




    companion object{
        const val NOME_TABELA = "Marcação"
        const val CAMPO_DATA_DOSE = "datadose"
        const val CAMPO_NUMERO_DOSE = "numero_dose"
        const val CAMPO_ID_VACINA = "id_vacina"

        
        const val CAMPO_ID_Vacina = "id_Vacina"
        const val CAMPO_EXTERNO_NOME_Vacina = "nome_Vacina"


        val TODOS_CAMPOS = arrayOf(BaseColumns._ID, TabelaMarcacoes.CAMPO_DATA_DOSE,
                TabelaMarcacoes.CAMPO_NUMERO_DOSE,
                TabelaMarcacoes.CAMPO_ID_VACINA

        )
    }




}