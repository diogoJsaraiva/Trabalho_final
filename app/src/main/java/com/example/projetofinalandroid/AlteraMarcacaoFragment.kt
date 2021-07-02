package com.example.projetofinalandroid

import android.database.Cursor
import android.net.Uri
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

class AlteraMarcacaoFragment: Fragment(), LoaderManager.LoaderCallbacks<Cursor>  {

    /**
     * A simple [Fragment] subclass as the second destination in the navigation.
     */



        private  lateinit var  calendarData: Calendar
        private  lateinit var  editTextDose: EditText
        private  lateinit var  spinnerVacina: Spinner


        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?,
        ): View? {
            // Inflate the layout for this fragment
            DadosApp.fragment = this
            (activity as MainActivity).menuAtual = R.menu.menu_alterar_marcacao

            return inflater.inflate(R.layout.fragment_altera_marcacao, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            calendarData = view.findViewById(R.id.calendarData)
            editTextDose = view.findViewById<EditText>(R.id.editTextDose)
            spinnerVacina = view.findViewById<Spinner>(R.id.spinnerVacina)

            LoaderManager.getInstance(this)
                    .initLoader(ID_LOADER_MANAGER_MARCACAO, null, this)

            editTextDose.setText(DadosApp.marcacoesSelecionado!!.numero_dose)



            val data = DadosApp.marcacoesSelecionado!!.datadose as DatePicker
            val day = data.dayOfMonth
            val month = data.month + 1
            val year = data.year
            calendarData.set(year,month,day)


            //view.findViewById<Button>(R.id.button_second).setOnClickListener {
            //   findNavController().navigate(R.id.NovaVacinaFragment_to_ListaVacinaFragment)
            // }
        }

    fun navegaListaMarcacao() {
        findNavController().navigate(R.id.action_alteraMarcacaoFragment_to_Lista_MarcacoesFragment)
    }

        fun guarda(){

            val dose = editTextDose.text.toString()

            if(dose.isEmpty()){
                editTextDose.setError("Coloque o numero da dose")
                editTextDose.requestFocus()
                return
            }

            val data = calendarData as DatePicker

            val day = data.dayOfMonth
            val month = data.month + 1
            val year = data.year

            val vacina =  spinnerVacina.selectedItemId

            val marcacoes = Marcacoes(datadose = Date(year,month,day), numero_dose = dose.toInt(), idVacina = vacina)

            val uriLivro = Uri.withAppendedPath(
                    ContentProviderMarcacoes.ENDERECO_MARCACOES,
                    marcacoes.id.toString()
            )

            val registos = activity?.contentResolver?.update(
                    uriLivro,
                    marcacoes.toContentValues(),
                    null,
                    null
            )

            if (registos != 1) {
                Toast.makeText(
                        requireContext(),
                        "Erro alterar marcacao",
                        Toast.LENGTH_LONG
                ).show()
                return
            }

            Toast.makeText(
                    requireContext(),
                    "Alterado com sucesso",
                    Toast.LENGTH_LONG
            ).show()
            navegaListaMarcacao()
        }


    fun processaOpcaoMenu(item: MenuItem):Boolean{
        when(item.itemId) {
            R.id.action_guardar_marcacao_alterar -> guarda()
            R.id.action_cancelar_marcacao_alterar -> cancelar()
            else -> return false

        }
        return true
    }




    fun cancelar(){
            findNavController().navigate(R.id.action_marcacoes_novo_to_action_lista_MarcacoesFragment)
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
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            return CursorLoader(
                    requireContext(),
                    ContentProviderMarcacoes.ENDERECO_Pessoas,
                    TabelaPessoas.TODOS_CAMPOS,
                    null, null,
                    TabelaPessoas.CAMPO_NOME


            )
        }


        /**
         * Called when a previously created loader has finished its load.  Note
         * that normally an application is *not* allowed to commit fragment
         * transactions while in this call, since it can happen after an
         * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
         *
         *
         * This function is guaranteed to be called prior to the release of
         * the last data that was supplied for this Loader.  At this point
         * you should remove all use of the old data (since it will be released
         * soon), but should not do your own release of the data since its Loader
         * owns it and will take care of that.  The Loader will take care of
         * management of its data so you don't have to.  In particular:
         *
         *
         *  *
         *
         *The Loader will monitor for changes to the data, and report
         * them to you through new calls here.  You should not monitor the
         * data yourself.  For example, if the data is a [android.database.Cursor]
         * and you place it in a [android.widget.CursorAdapter], use
         * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
         * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
         * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
         * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
         * from doing its own observing of the Cursor, which is not needed since
         * when a change happens you will get a new Cursor throw another call
         * here.
         *  *  The Loader will release the data once it knows the application
         * is no longer using it.  For example, if the data is
         * a [android.database.Cursor] from a [android.content.CursorLoader],
         * you should not call close() on it yourself.  If the Cursor is being placed in a
         * [android.widget.CursorAdapter], you should use the
         * [android.widget.CursorAdapter.swapCursor]
         * method so that the old Cursor is not closed.
         *
         *
         *
         * This will always be called from the process's main thread.
         *
         * @param loader The Loader that has finished.
         * @param data The data generated by the Loader.
         */
        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            atualizaSpinnerPessoas(data)
            atualizaCategoriaSelecionada()
        }




        /**
         * Called when a previously created loader is being reset, and thus
         * making its data unavailable.  The application should at this point
         * remove any references it has to the Loader's data.
         *
         *
         * This will always be called from the process's main thread.
         *
         * @param loader The Loader that is being reset.
         */
        override fun onLoaderReset(loader: Loader<Cursor>) {
            atualizaSpinnerPessoas(null)

        }

        private fun atualizaSpinnerPessoas(data: Cursor?) {
            spinnerVacina.adapter = SimpleCursorAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    data,
                    arrayOf(TabelaVacina.CAMPO_NOME),
                    intArrayOf(android.R.id.text1),
                    0


            )

        }


    private fun atualizaCategoriaSelecionada() {
        val idCategoria = DadosApp.marcacoesSelecionado!!.idVacina

        val ultimaCategoria = spinnerVacina.count - 1
        for (i in 0..ultimaCategoria) {
            if (idCategoria == spinnerVacina.getItemIdAtPosition(i)) {
                spinnerVacina.setSelection(i)
                return
            }
        }
    }


        companion object{
            const val ID_LOADER_MANAGER_MARCACAO= 0

        }
    }
