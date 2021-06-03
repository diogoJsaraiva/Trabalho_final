package com.example.projetofinalandroid

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TesteBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdvacinasOpenHelper() = BdMarcacoesOpenHelper(getAppContext())

    private fun gettabelaVacina(db: SQLiteDatabase) = TabelaVacina(db)

    private fun inserirVacina(gettabelaCategorias: TabelaVacina, categoria: Vacina) : Long {
        val id = gettabelaCategorias.insert(categoria.toContentValues())
        assertNotEquals(-1, id)
        return id
    }


    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdMarcacoesOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados(){

        val db = getBdvacinasOpenHelper().readableDatabase
        assert(db.isOpen)

        db.close()
    }

   @Test
   fun consegueInserirCategorias(){
       val db = getBdvacinasOpenHelper().writableDatabase


       val vacina = Vacina(nome="Pfizer",quantidade = 10)
       inserirVacina(gettabelaVacina(db), vacina)

       db.close()
   }


}