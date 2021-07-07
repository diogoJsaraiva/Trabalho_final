package com.example.projetofinalandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

import java.util.*

class EditaPessoaFragment: Fragment(){



    private lateinit var editTextNome: EditText
    private lateinit var editTextTelefone: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextMorada: EditText
    private  lateinit var  viewCalendar: CalendarView

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_altera_pessoas
        return inflater.inflate(R.layout.fragment_altera_pessoas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextNome)
        editTextTelefone = view.findViewById(R.id.editTextTelefone)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextMorada = view.findViewById(R.id.editTextMorada)
        viewCalendar = view.findViewById(R.id.calendarView)

       val date = DadosApp.pessoasSelecionado!!.dataNascimento.toString()

        editTextNome.setText(DadosApp.pessoasSelecionado!!.nome)
        editTextEmail.setText(DadosApp.pessoasSelecionado!!.email)
        editTextTelefone.setText(DadosApp.pessoasSelecionado!!.telefone)
        editTextMorada.setText(DadosApp.pessoasSelecionado!!.morada)
        viewCalendar.setDate(date.toLong())

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    fun navegaListaPessoas() {
        findNavController().navigate(R.id.action_editaPessoaFragment_to_Lista_MarcacoesFragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_nome))
            editTextNome.requestFocus()
            return
        }

        val telefone = editTextTelefone.text.toString()
        if (telefone.isEmpty()) {
            editTextTelefone.setError(getString(R.string.preencha_telefone))
            editTextTelefone.requestFocus()
            return
        }

        val email = editTextEmail.text.toString()
        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.preencha_email))
            editTextEmail.requestFocus()
            return
        }

        val morada = editTextTelefone.text.toString()
        if (morada.isEmpty()) {
            editTextMorada.setError(getString(R.string.preencha_morada))
            editTextMorada.requestFocus()
            return
        }



        val data =  viewCalendar as Calendar
        val  dia =  data.get(Calendar.DAY_OF_WEEK)
        val  mes =  data.get(Calendar.MONTH)
        val  ano =  data.get(Calendar.YEAR)

        val utente = Pessoas(nome = nome,
            telefone = telefone,
            email = email,
            morada = morada,
            dataNascimento = Date(ano -1900,  mes-1, dia),
            dose = 0)

        val uri = activity?.contentResolver?.insert(
            ContentProviderPessoas.ENDERECO_Pessoas,
            utente.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                getString(R.string.erro_inserir_pessoa),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.utente_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaPessoas()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_marcacao -> guardar()
            R.id.action_cancelar_novo_marcacao -> navegaListaPessoas()
            else -> return false
        }

        return true
    }


}
