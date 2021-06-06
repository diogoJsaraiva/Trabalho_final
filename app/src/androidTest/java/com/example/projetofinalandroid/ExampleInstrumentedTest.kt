package com.example.projetofinalandroid

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
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

    private fun getTabelaVacina(db: SQLiteDatabase) = TabelaVacina(db)
    private fun getTabelaMarcacoes(db: SQLiteDatabase) = TabelaMarcacoes(db)
    private fun getTabelaPessoas(db: SQLiteDatabase) = TabelaPessoas(db)

    private fun inserirVacina(tabelaVacina: TabelaVacina, vacina: Vacina) : Long {
        val id = tabelaVacina.insert(vacina.toContentValues())
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
   fun consegueInserirVacina(){
       val db = getBdvacinasOpenHelper().writableDatabase


       val vacina = Vacina(nome="Pfizer",quantidade = 10)
       inserirVacina(getTabelaVacina(db), vacina)

       db.close()
   }

    @Test
    fun consegueAlterarVacina(){
        val db = getBdvacinasOpenHelper().writableDatabase

        val vacina = Vacina(nome="Pfizer",quantidade = 2)
        val tabelaVacinas = getTabelaVacina(db)

        inserirVacina(tabelaVacinas, vacina)
        vacina.id = inserirVacina(tabelaVacinas,vacina)
        vacina.nome = "Pfizer"
        vacina.quantidade = 2
        val registosAlterados = tabelaVacinas.update(
                vacina.toContentValues(),"${BaseColumns._ID}=?",
                arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosAlterados)


        db.close()
    }

    @Test
    fun consegueLerVacina(){
        val db = getBdvacinasOpenHelper().writableDatabase
        val gettabelaCategorias = getTabelaVacina(db)
        val vacina = Vacina(nome="Pfizer",quantidade = 2)
        vacina.id = inserirVacina(gettabelaCategorias,vacina)

        val cursor = gettabelaCategorias.query(
                TabelaVacina.TODOS_CAMPOS,
                "${BaseColumns._ID}=?",
                arrayOf(vacina.id.toString()),
                null,null,null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())


    }

    private fun getVacinaBaseDados(tabela: TabelaVacina , id:Long): Vacina {

        val cursor = tabela.query(
                TabelaVacina.TODOS_CAMPOS,
                "${BaseColumns._ID} =?",
                arrayOf(id.toString()),
                null,null,null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Vacina.fromCursor(cursor)
    }


}