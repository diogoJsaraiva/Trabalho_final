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
    private fun getBDMarcacoesOpenHelper() = BdMarcacoesOpenHelper(getAppContext())

    private fun getTabelaPessoas(db: SQLiteDatabase) = TabelaPessoas(db)
    private fun getTabelaVacinas(db: SQLiteDatabase) = TabelaVacinas(db)
    private fun getTabelaMarcacoes(db: SQLiteDatabase) = TabelaMarcacao(db)

    private fun inserePessoas(tabelaPessoas: TabelaPessoas, utente: Pessoas): Long {
        val id = tabelaPessoas.insert(utente.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insereVacina(tabelaVacinas: TabelaVacinas, vacina: Vacina): Long {
        val id = tabelaVacinas.insert(vacina.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insereMarcacoes(tabelaMarcacoes: TabelaMarcacao, doses: Marcacao): Long {
        val id = tabelaMarcacoes.insert(doses.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun getPessoasBD(
        tabelaUtentes: TabelaPessoas, id: Long    ): Pessoas {
        val cursor = tabelaUtentes.query(
            TabelaPessoas.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Pessoas.fromCursor(cursor)
    }

    private fun getVacinaBD(
        tabelaVacinas: TabelaVacinas, id: Long    ): Vacina {
        val cursor = tabelaVacinas.query(
            TabelaVacinas.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Vacina.fromCursor(cursor)
    }

    private fun getMarcacaoBD(
        tabelaDoses: TabelaMarcacao, id: Long  ): Marcacao {
        val cursor = tabelaDoses.query(
            TabelaMarcacao.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Marcacao.fromCursor(cursor)
    }

    @Before
    fun apagaBaseDados() {
        //getAppContext().deleteDatabase(BdMarcacoesOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados(){

        val db = getBDMarcacoesOpenHelper().readableDatabase
        assert(db.isOpen)

        db.close()
    }

    @Test
    fun consegueInserirVacinas(){
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(nomeVacina = "AstraZeneca")

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val vacinaBD = getVacinaBD(tabelaVacinas, vacina.id)
        assertEquals(vacina, vacinaBD)
        db.close()
    }

    @Test
    fun consegueAlterarVacinas(){
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(nomeVacina ="?")

        vacina.id = insereVacina(tabelaVacinas, vacina)


        vacina.nomeVacina = "Pfizer"


        val registosAlterados = tabelaVacinas.update(
            vacina.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosAlterados)
        val livroBD = getVacinaBD(tabelaVacinas, vacina.id)
        assertEquals(vacina, livroBD)

        db.close()
    }

    @Test
    fun consegueApagarVacinas(){
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(nomeVacina ="?" )

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val registosEliminados = tabelaVacinas.delete(
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerVacinas(){
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(nomeVacina ="Moderna")

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val vacinaBD = getVacinaBD(tabelaVacinas, vacina.id)
        assertEquals(vacina, vacinaBD)

        db.close()
    }

    @Test
    fun consegueInserirPessoas(){
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaPessoas = getTabelaPessoas(db)
        val pessoas = Pessoas(nome="João Pascoa", telefone = "+355 932654368",email = "almeida@gmail.com", morada = "Rua Vila de Trancoso, Guarda", dataNascimento = Date(1980-1900,10,13), dose = 2)

        pessoas.id = inserePessoas(tabelaPessoas, pessoas)
        val pessoasBD = getPessoasBD(tabelaPessoas, pessoas.id)
        assertEquals(pessoas, pessoasBD)
        db.close()
    }

    @Test
    fun consegueAlterarPessoas(){
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaPessoas = getTabelaPessoas(db)
        val pessoas = Pessoas(nome="?", telefone = "?",email = "?", morada = "?", dataNascimento = Date(), dose = 0)

        pessoas.id = inserePessoas(tabelaPessoas, pessoas)
        pessoas.nome="Arminda"
        pessoas.telefone="+355 969773894"
        pessoas.email="arminda@hotmai.com"
        pessoas.morada="Rua da Arminda, Aveiro"
        pessoas.dataNascimento=Date(1997-1900, 5,25)
        pessoas.dose=1
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
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaPessoas = getTabelaPessoas(db)
        val pessoas = Pessoas(nome="?", telefone = "?",email = "?", morada = "?", dataNascimento = Date(), dose = 0)

        pessoas.id = inserePessoas(tabelaPessoas, pessoas)

        val registosEliminados =tabelaPessoas.delete(
            "${BaseColumns._ID}=?",
            arrayOf(pessoas.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerPessoas(){
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaPessoas = getTabelaPessoas(db)
        val pessoas = Pessoas(nome="Arnaldo", telefone = "+355 914347958",email = "arnarld@outlook.com", morada = "Braga", dataNascimento = Date(1953-1900,1,31), dose = 2)

        pessoas.id = inserePessoas(tabelaPessoas, pessoas)

        val utenteBD = getPessoasBD(tabelaPessoas, pessoas.id)
        assertEquals(pessoas, utenteBD)

        db.close()
    }

    @Test
    fun consegueInserirMarcacao(){
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaPessoas = getTabelaPessoas(db)
        val pessoas = Pessoas(nome="Jorge", telefone = "+355 962346800",email = "jorge@gmail.com", morada = "Guarda", dataNascimento = Date(1987-1900,2,1), dose = 3)

        pessoas.id = inserePessoas(tabelaPessoas, pessoas)

        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(nomeVacina = "AstrZeneca")

        vacina.id = insereVacina(tabelaVacinas, vacina)


        val tabelaDoses = getTabelaMarcacoes(db)
        val doses = Marcacao(data = Date(1978-1900), idPessoa = pessoas.id, idVacina = vacina.id)

        doses.id = insereMarcacoes(tabelaDoses, doses)


        val dosesBD = getMarcacaoBD(tabelaDoses, doses.id)
        assertEquals(doses, dosesBD)
        db.close()
    }

    @Test
    fun consegueAlterarDoses(){
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaUtente = getTabelaPessoas(db)
        val utente = Pessoas(nome="Diogo", telefone = "+355 962345577",email = "dio@gmail.com", morada = "Guarda", dataNascimento = Date(1932-1900), dose = 2)

        utente.id = inserePessoas(tabelaUtente, utente)

        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina( nomeVacina = "AsZeneca", )

        vacina.id = insereVacina(tabelaVacinas, vacina)


        val tabelaMarcacao = getTabelaMarcacoes(db)
        val marcacao = Marcacao(data = Date(),idPessoa = utente.id, idVacina = vacina.id)

        marcacao.id = insereMarcacoes(tabelaMarcacao, marcacao)

        val registosAlterados = tabelaMarcacao.update(
            marcacao.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(marcacao.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueApagarMarcacao(){
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaPessoas = getTabelaPessoas(db)
        val pessoas = Pessoas(nome="Ana", telefone = "+355 962967862",email = "anas@gmail.com", morada = "Lisboa", dataNascimento = Date(1983,5,13), dose = 3)

        pessoas.id = inserePessoas(tabelaPessoas, pessoas)

        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(nomeVacina = "AstrZeneca")

        vacina.id = insereVacina(tabelaVacinas, vacina)


        val tabelaMarcacao= getTabelaMarcacoes(db)
        val marcacao = Marcacao(data = Date(), idPessoa = pessoas.id, idVacina = vacina.id)

        marcacao.id = insereMarcacoes(tabelaMarcacao, marcacao)

        marcacao.data = Date(1999-1900,10,12)


        val registosEliminados =tabelaMarcacao.delete(
            "${BaseColumns._ID}=?",
            arrayOf(marcacao.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerMarcacao(){
        val db = getBDMarcacoesOpenHelper().writableDatabase
        val tabelaUtente = getTabelaPessoas(db)
        val utente = Pessoas(nome="Telmo", telefone = "+355 962973210",email = "telmo@gmail.com", morada = "Chaves", dataNascimento = Date(1999-1900,12,12), dose = 1)

        utente.id = inserePessoas(tabelaUtente, utente)

        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(nomeVacina = "AstrZenca")

        vacina.id = insereVacina(tabelaVacinas, vacina)


        val tabelaMarcacao= getTabelaMarcacoes(db)
        val marcacao = Marcacao(data = Date(2000-1900,10,10), idPessoa = utente.id, idVacina = vacina.id)

        marcacao.id = insereMarcacoes(tabelaMarcacao, marcacao)


        val dosesBD = getMarcacaoBD(tabelaMarcacao, marcacao.id)
        assertEquals(marcacao, dosesBD)
        db.close()
    }

}