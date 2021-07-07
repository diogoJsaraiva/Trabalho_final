package com.example.projetofinalandroid

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class  NovaPessoaFragment : Fragment(){



    private  lateinit var  viewCalendar: CalendarView
    private lateinit var editTextNome: EditText
    private lateinit var editTextTelefone: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextMorada: EditText



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_pessoas

        return inflater.inflate(R.layout.fragment_novo_pessoas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewCalendar = view.findViewById(R.id.calendarViewData)
        editTextNome = view.findViewById(R.id.editTextNome)
        editTextTelefone = view.findViewById(R.id.editTextTelefone)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextMorada = view.findViewById(R.id.editTextMorada)




    }



    fun guardar(){

        val nome = editTextNome.text.toString()

        if(nome.isEmpty()){
            editTextNome.setError("Coloque o nome ")
            editTextNome.requestFocus()
            return
        }



        val telefone = editTextTelefone.text.toString()

        if(telefone.isEmpty()){
            editTextTelefone.setError("Coloque o telefone ")
            editTextTelefone.requestFocus()
            return
        }

        val morada = editTextMorada.text.toString()

        if(morada.isEmpty()){
            editTextMorada.setError("Coloque a morada ")
            editTextMorada.requestFocus()
            return
        }

        val email = editTextEmail.text.toString()

        if(email.isEmpty()){
            editTextEmail.setError("Coloque o nome ")
            editTextEmail.requestFocus()
            return
        }






        val data =  viewCalendar as Calendar
       val  dia =  data.get(Calendar.DAY_OF_WEEK)
        val  mes =  data.get(Calendar.MONTH)
        val  ano =  data.get(Calendar.YEAR)







        val pessoas = Pessoas(
                nome = nome,
            dataNascimento = Date(ano - 1900,mes +1 ,dia),
            dose = 0,
            morada = morada ,
                email = email ,
                telefone =telefone
        )

        val uri = activity?.contentResolver?.insert(
            ContentProviderPessoas.ENDERECO_Pessoas,
            pessoas.toContentValues()

        )

        if (uri == null) {
            Snackbar.make(
                    editTextNome,
                    "Erro ao inserir nome",
                    Snackbar.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
                requireContext(),
                "Pessoa altrada com sucesso ",
                Toast.LENGTH_LONG
        ).show()
        navegaListaPessoas()
    }


    fun navegaListaPessoas() {
        findNavController().navigate(R.id.action_novaPessoaFragment_to_Lista_MarcacoesFragment)
    }




    fun cancelar(){
        findNavController().navigate(R.id.action_novaPessoaFragment_to_Lista_MarcacoesFragment)
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */



    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_marcacao -> guardar()
            R.id.action_cancelar_novo_marcacao -> cancelar()
            else -> return false
        }

        return true
    }


    companion object{
        const val ID_LOADER_MANAGER_MARCACAO= 0

    }
}