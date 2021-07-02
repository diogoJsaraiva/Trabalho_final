package com.example.projetofinalandroid

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TesteBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdMarcacoesOpenHelper() = BdMarcacoesOpenHelper(getAppContext())

    private fun getTabelaVacina(db: SQLiteDatabase) = TabelaVacina(db)
    private fun getTabelaMarcacoes(db: SQLiteDatabase) = TabelaMarcacoes(db)
    private fun getTabelaPessoas(db: SQLiteDatabase) = TabelaPessoas(db)

    private fun inserirVacina(tabelaVacina: TabelaVacina, vacina: Vacina) : Long {
        val id = tabelaVacina.insert(vacina.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun inserirPessoas(tabelaPessoas: TabelaPessoas,pessoas: Pessoas) : Long {
        val id = tabelaPessoas.insert(pessoas.toContentValues())
        assertNotEquals(-1, id)
        return id
    }


    private fun inserirMarcacoes(tabelaMarcacoes: TabelaMarcacoes,marcacoes: Marcacoes) : Long {
        val id = tabelaMarcacoes.insert(marcacoes.toContentValues())
        assertNotEquals(-1, id)
        return id
    }


    private fun getVacinaBaseDados(tabelaVacina:TabelaVacina , id:Long): Vacina {

        val cursor = tabelaVacina.query(
                TabelaVacina.TODOS_CAMPOS,
                "${BaseColumns._ID} =?",
                arrayOf(id.toString()),
                null,null,null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Vacina.fromCursor(cursor)
    }

    private fun getPessoasBaseDados(tabelaPessoas: TabelaPessoas , id:Long): Pessoas {

        val cursor = tabelaPessoas.query(
                TabelaPessoas.TODOS_CAMPOS,
                "${BaseColumns._ID} =?",
                arrayOf(id.toString()),
                null,null,null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Pessoas.fromCursor(cursor)
    }


    private fun getMarcacoesBaseDados(tabelaMarcacoes: TabelaMarcacoes , id:Long): Marcacoes {

        val cursor = tabelaMarcacoes.query(
                TabelaMarcacoes.TODOS_CAMPOS,
                "${BaseColumns._ID} =?",
                arrayOf(id.toString()),
                null,null,null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Marcacoes.fromCursor(cursor)
    }




    @Before
    fun apagaBaseDados() {
       getAppContext().deleteDatabase(BdMarcacoesOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados(){

        val db = getBdMarcacoesOpenHelper().readableDatabase
        assert(db.isOpen)

        db.close()
    }

   @Test
   fun consegueInserirVacina(){
       val db = getBdMarcacoesOpenHelper().writableDatabase
       val tabelaVacina = getTabelaVacina(db)
       val vacina = Vacina(nome="Pfizer")

       vacina.id = inserirVacina(tabelaVacina,vacina)

       val vacinaBD = getVacinaBaseDados(tabelaVacina,vacina.id)

       assertEquals(vacina, vacinaBD)

       db.close()
   }



    @Test
    fun consegueAlterarVacina(){
        val db = getBdMarcacoesOpenHelper().writableDatabase
        val tabelaVacina = getTabelaVacina(db)
        val vacina = Vacina(nome="?")

        vacina.id = inserirVacina(tabelaVacina,vacina)


        vacina.nome = "Pfizer"

        val registosAlterados = tabelaVacina.update(
                vacina.toContentValues(),"${BaseColumns._ID}=?",
                arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosAlterados)
        val vacinaBD = getVacinaBaseDados(tabelaVacina, vacina.id)
        assertEquals(vacina, vacinaBD)

        db.close()
    }

    @Test
    fun consegueApagarVacinas(){
        val db = getBdMarcacoesOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacina(db)
        val vacina = Vacina(nome = "?")

        vacina.id = inserirVacina(tabelaVacinas, vacina)

        val registosEliminados = tabelaVacinas.delete(
                "${BaseColumns._ID}=?",
                arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerVacina(){
        val db = getBdMarcacoesOpenHelper().writableDatabase
        val tabelaVacina = getTabelaVacina(db)
        val vacina = Vacina(nome="Moderna")

        vacina.id = inserirVacina(tabelaVacina,vacina)

        val vacinaBD = getVacinaBaseDados(tabelaVacina, vacina.id)
        assertEquals(vacina, vacinaBD)

        db.close()


    }
    @Test
    fun consegueInserirMarcacoes(){
        val db = getBdMarcacoesOpenHelper().writableDatabase
        val tabelaVacina = getTabelaVacina(db)
        val tabelaMarcacoes = getTabelaMarcacoes(db)



        val vacina = Vacina(nome="Moderna")
        vacina.id = inserirVacina(tabelaVacina,vacina)

        val  marcacoes = Marcacoes(
            datadose =  Date(1989 + 1900,2 ,31),
            numero_dose = 1,
            idVacina = vacina.id ,

        )
        marcacoes.nomeVacina = vacina.nome
        marcacoes.id = inserirMarcacoes(tabelaMarcacoes,marcacoes)


        assertEquals(marcacoes, getMarcacoesBaseDados(tabelaMarcacoes,marcacoes.id))

        db.close()
    }

    @Test
    fun consegueAlterarMarcacoes(){
        val db = getBdMarcacoesOpenHelper().writableDatabase


        val tabelaVacinas = getTabelaVacina(db)
        val vacina = Vacina(nome = "AsZeneca")

        vacina.id = inserirVacina(tabelaVacinas, vacina)


        val tabelaMarcacoes = getTabelaMarcacoes(db)
         val  marcacoes = Marcacoes(
            datadose = Date(2021-1900,7,10),
            numero_dose = 2,
            idVacina = vacina.id,
                 nomeVacina = vacina.nome
        )

        marcacoes.id = inserirMarcacoes(tabelaMarcacoes, marcacoes)
        marcacoes.datadose= Date(2021-1900,7,10)
        marcacoes.numero_dose= 2
        marcacoes.idVacina = vacina.id
        marcacoes.nomeVacina = vacina.nome



        val registosAlterados = tabelaMarcacoes.update(
            marcacoes.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(marcacoes.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueApagarMarcacoes(){
        val db = getBdMarcacoesOpenHelper().writableDatabase



        val tabelaVacinas = getTabelaVacina(db)
        val vacina = Vacina(nome = "AstrZeneca")

        vacina.id = inserirVacina(tabelaVacinas, vacina)


        val tabelaMarcacoes = getTabelaMarcacoes(db)
        val marcacoes = Marcacoes(
            datadose = Date(2021-1988,7,10),
            numero_dose = 0,
            idVacina = vacina.id ,
                nomeVacina = vacina.nome
        )
        marcacoes.id = inserirMarcacoes(tabelaMarcacoes, marcacoes)



        val registosEliminados =tabelaMarcacoes.delete(
            "${BaseColumns._ID}=?",
            arrayOf(marcacoes.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }


    @Test
    fun consegueLerMarcacoes(){
        val db = getBdMarcacoesOpenHelper().writableDatabase

        val tabelaVacinas = getTabelaVacina(db)
        val vacina = Vacina(nome = "AstrZenca")

        vacina.id = inserirVacina(tabelaVacinas, vacina)


        val tabelaMarcacoes = getTabelaMarcacoes(db)
        val marcacoes = Marcacoes(
            datadose = Date(2021-1988,7,10),
            numero_dose = 2,
            idVacina = vacina.id,
                nomeVacina = vacina.nome

        )

        marcacoes.id = inserirMarcacoes(tabelaMarcacoes, marcacoes)


        val dosesBD = getMarcacoesBaseDados(tabelaMarcacoes, marcacoes.id)
        assertEquals(marcacoes, dosesBD)
        db.close()
    }

    @Test
    fun consegueInserirPessoas(){
        val db = getBdMarcacoesOpenHelper().writableDatabase
        val tabelaPessoas = getTabelaPessoas(db)
        val pessoas = Pessoas(nome ="Aníbal Almeida", telefone = "+355 932978568", email = "almeida@gmail.com", morada = "Rua Vila de Trancoso, Guarda", dataNascimento = Date(1999-1900, 5,26))

        pessoas.id = inserirPessoas(tabelaPessoas, pessoas)
        val utenteBD = getPessoasBaseDados(tabelaPessoas, pessoas.id)
        assertEquals(pessoas, utenteBD)
        db.close()
    }

    @Test
    fun consegueAlterarPessoas(){
        val db = getBdMarcacoesOpenHelper().writableDatabase
        val tabelaPessoas = getTabelaPessoas(db)
        val pessoas = Pessoas(nome ="?", telefone = "?", email = "?", morada = "?", dataNascimento = Date())

        pessoas.id = inserirPessoas(tabelaPessoas, pessoas)
        pessoas.nome="Amilcar Sousa"
        pessoas.telefone="+355 965734894"
        pessoas.email="asousa@hotmai.com"
        pessoas.morada="Rua da Fonte, Viseu"
        pessoas.dataNascimento=  Date(1999-1900, 8,2)

        val registosAlterados = tabelaPessoas.update(
            pessoas.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(pessoas.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueApagarPessoas(){
        val db = getBdMarcacoesOpenHelper().writableDatabase
        val tabelaPessoas = getTabelaPessoas(db)
        val pessoas = Pessoas(nome ="?", telefone = "?", email = "?", morada = "?", dataNascimento = Date())

        pessoas.id = inserirPessoas(tabelaPessoas, pessoas)

        val registosEliminados =tabelaPessoas.delete(
            "${BaseColumns._ID}=?",
            arrayOf(pessoas.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerPessoas(){
        val db = getBdMarcacoesOpenHelper().writableDatabase
        val tabelaUtentes = getTabelaPessoas(db)
        val pessoas = Pessoas(nome ="Gonçalves", telefone = "914369458", email = "g@hotmail.com", morada = "Braga",dataNascimento = Date(1999-1900, 5,26))

        pessoas.id = inserirPessoas(tabelaUtentes, pessoas)

        val utenteBD = getPessoasBaseDados(tabelaUtentes, pessoas.id)
        assertEquals(pessoas, utenteBD)

        db.close()
    }

}