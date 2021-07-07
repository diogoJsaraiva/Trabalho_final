package com.example.projetofinalandroid


import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderPessoas   : ContentProvider(){
    private var bdMarcacoesOpenHelper : BdMarcacoesOpenHelper? = null

    override fun onCreate(): Boolean {
        bdMarcacoesOpenHelper = BdMarcacoesOpenHelper(context)


        return true
    }

    override fun query(
            uri: Uri,
            projection: Array<out String>?,
            selection: String?,
            selectionArgs: Array<out String>?,
            sortOrder: String?
    ): Cursor? {
        val bd = bdMarcacoesOpenHelper!!.readableDatabase


        return when(getUriMatcher().match(uri)){
            URI_VACINA -> TabelaVacinas(bd).query(
                    projection as Array<String>,
                    selection,
                    selectionArgs as Array<String>,
                    null,
                    null,
                    sortOrder
            )
            URI_VACINA_ESPECIFICO -> TabelaVacinas(bd).query(
                    projection as Array<String>,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!), // id
                    null,
                    null,
                    null
            )
            URI_PESSOAS -> TabelaPessoas(bd).query(
                    projection as Array<String>,
                    selection,
                    selectionArgs as Array<String>,
                    null,
                    null,
                    sortOrder
            )
            URI_PESSOAS_ESPECIFICO ->TabelaPessoas(bd).query(
                    projection as Array<String>,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!), // id
                    null,
                    null,
                    null
            )
            URI_MARCACOES -> TabelaMarcacao(bd).query(
                    projection as Array<String>,
                    selection,
                    selectionArgs as Array<String>,
                    null,
                    null,
                    sortOrder
            )
            URI_MARCACOES_ESPECIFICO->TabelaMarcacao(bd).query(
                    projection as Array<String>,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!), // id
                    null,
                    null,
                    null
            )
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return when(getUriMatcher().match(uri)){
            URI_VACINA -> "$MULTIPLOS_ITEMS/$VACINA"
            URI_VACINA_ESPECIFICO -> "$UNICO_ITEM/$VACINA"
            URI_PESSOAS-> "$MULTIPLOS_ITEMS/$PESSOAS"
            URI_PESSOAS_ESPECIFICO -> "$UNICO_ITEM/$PESSOAS"
            URI_MARCACOES -> "$MULTIPLOS_ITEMS/$MARCACAO"
            URI_MARCACOES_ESPECIFICO -> "$UNICO_ITEM/$MARCACAO"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdMarcacoesOpenHelper!!.writableDatabase

        val id = when(getUriMatcher().match(uri)){
            URI_VACINA -> TabelaVacinas(bd).insert(values!!)
            URI_PESSOAS -> TabelaPessoas(bd).insert(values!!)
            URI_MARCACOES-> TabelaMarcacao(bd).insert(values!!)
            else -> -1
        }

        if (id == -1L) return null
        return Uri.withAppendedPath(uri, id.toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdMarcacoesOpenHelper!!.writableDatabase

        return when(getUriMatcher().match(uri)){
            URI_VACINA_ESPECIFICO -> TabelaVacinas(bd).delete(
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            URI_PESSOAS_ESPECIFICO -> TabelaPessoas(bd).delete(
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            URI_MARCACOES_ESPECIFICO ->TabelaMarcacao(bd).delete(
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            else -> 0
        }
    }

    override fun update(
            uri: Uri,
            values: ContentValues?,
            selection: String?,
            selectionArgs: Array<out String>?
    ): Int {
        val bd = bdMarcacoesOpenHelper!!.writableDatabase

        return when(getUriMatcher().match(uri)){
            URI_VACINA_ESPECIFICO -> TabelaVacinas(bd).update(
                    values!!,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            URI_PESSOAS_ESPECIFICO ->TabelaPessoas(bd).update(
                    values!!,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            URI_MARCACOES_ESPECIFICO ->TabelaMarcacao(bd).update(
                    values!!,
                    "${BaseColumns._ID}=?",
                    arrayOf(uri.lastPathSegment!!) // id
            )
            else -> 0
        }
    }

    companion object{
        private const val AUTHORITY = "com.example.projetofinalandroid"
        private const val VACINA = "vacina"
        private const val PESSOAS = "pessoas"
        private const val MARCACAO = "marcacao"

        private const val URI_VACINA = 100
        private const val URI_VACINA_ESPECIFICO = 101
        private const val URI_PESSOAS = 200
        private const val URI_PESSOAS_ESPECIFICO = 201
        private const val URI_MARCACOES = 300
        private const val URI_MARCACOES_ESPECIFICO = 301

        private const val MULTIPLOS_ITEMS = "vnd,android.cursor.dir"
        private const val UNICO_ITEM = "vnd,android.cursor.item"

        private val ENDERECO_BASE = Uri.parse("content://$AUTHORITY")
        public val ENDERECO_VACINAS = Uri.withAppendedPath(ENDERECO_BASE, VACINA)
        public val ENDERECO_Pessoas = Uri.withAppendedPath(ENDERECO_BASE, PESSOAS)
        public val ENDERECO_MARCACOES = Uri.withAppendedPath(ENDERECO_BASE, MARCACAO)

        private fun getUriMatcher() : UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, VACINA, URI_VACINA)
            uriMatcher.addURI(AUTHORITY, "$VACINA/#", URI_VACINA_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, PESSOAS, URI_PESSOAS)
            uriMatcher.addURI(AUTHORITY, "$PESSOAS/#", URI_PESSOAS_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, MARCACAO, URI_MARCACOES)
            uriMatcher.addURI(AUTHORITY, "$MARCACAO/#", URI_MARCACOES_ESPECIFICO)

            return uriMatcher
        }
    }

}