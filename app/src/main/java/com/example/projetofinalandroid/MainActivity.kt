package com.example.projetofinalandroid

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    private lateinit var menu: Menu
    var menuAtual = R.menu.menu_lista_marcacoes
        set(value){

            field = value

            invalidateOptionsMenu()
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        DadosApp.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(menuAtual, menu)
        this.menu = menu
        if(menuAtual == R.menu.menu_lista_marcacoes) {
            atualizaMenuListaLivros(false)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val processado = when (item.itemId) {
            R.id.action_settings ->{
                Toast.makeText(this, "Marcacoes v.1.0", Toast.LENGTH_LONG).show()
                true
            }
            else -> when(menuAtual){
                R.menu.menu_lista_marcacoes -> (DadosApp.fragment as ListaMarcacoesFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_livro ->  (DadosApp.fragment as NovoLivroFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_livro ->  (DadosApp.fragment as EditLivroFragment).processaOpcaoMenu(item)
                R.menu.menu_elimina_livro ->  (DadosApp.fragment as Elimina_LivroFragment).processaOpcaoMenu(item)
                else -> false
            }



        }
        return if (processado) true else super.onOptionsItemSelected(item)
    }


    public fun atualizaMenuListaLivros(permiteAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_marcacao).setVisible(permiteAlterarEliminar)
        menu.findItem(R.id.action_eliminar_marcacao).setVisible(permiteAlterarEliminar)
        //invalidateOptionsMenu()
    }


}