package com.example.projetofinalandroid

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class EliminaPessoaFragment  : Fragment(){
    private lateinit var textViewnome: TextView
    private lateinit var textViewTelefone: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewMorada: TextView
    private  lateinit var textViewData: TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_pessoas

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elemina_pessoa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewnome = view.findViewById<TextView>(R.id.textViewNome)
        textViewMorada = view.findViewById(R.id.textViewMorada)
        textViewData = view.findViewById(R.id.textViewData_Nascimento)
        textViewEmail = view.findViewById(R.id.textViewEmail)
        textViewTelefone = view.findViewById(R.id.textViewTelefone)

        val pessoa = DadosApp.pessoasSelecionado!!
        textViewnome.setText(pessoa.nome)
        textViewMorada.setText(pessoa.morada)
        textViewEmail.setText(pessoa.email)
        textViewData.setText(pessoa.dataNascimento.toString())
        textViewTelefone.setText(pessoa.telefone)
    }

    fun navegaListaPessoas() {
        findNavController().navigate(R.id.action_eliminaPessoaFragment_to_Lista_MarcacoesFragment)
    }

    fun elimina() {
        val uriLivro = Uri.withAppendedPath(
            ContentProviderPessoas.ENDERECO_Pessoas,
            DadosApp.pessoasSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriLivro,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                "não foi possivel eliminar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            "foi possivel eliminar",
            Toast.LENGTH_LONG
        ).show()
        navegaListaPessoas()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_pessoa -> elimina()
            R.id.action_cancelar_eliminar_pessoa -> navegaListaPessoas()
            else -> return false
        }

        return true
    }
}
