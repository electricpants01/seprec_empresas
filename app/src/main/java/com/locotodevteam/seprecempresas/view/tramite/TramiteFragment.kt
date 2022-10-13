package com.locotodevteam.seprecempresas.view.tramite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import com.locotodevteam.seprecempresas.R
import com.locotodevteam.seprecempresas.databinding.FragmentTramiteBinding
import com.locotodevteam.seprecempresas.formatDate
import com.locotodevteam.seprecempresas.toDate


class TramiteFragment : Fragment() {
    lateinit var tramiteBinding: FragmentTramiteBinding
    val tramiteViewModel by viewModels<TramiteViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  inflater.inflate(R.layout.fragment_tramite, container, false)
        tramiteBinding = FragmentTramiteBinding.bind(view)
        return tramiteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCardsViewGone()
        initSearchView()
        initSubscriptions()
    }

    fun initSearchView(){
        tramiteBinding.mySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                setCardsViewGone()
                tramiteViewModel.getTramiteDetail(query.toString(), requireContext())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    fun initSubscriptions(){
        tramiteViewModel.tramiteDetail.observe(viewLifecycleOwner) {tramiteDetail ->
            tramiteBinding.txttramiteTitle.text = "Trámite " + tramiteDetail?.datos?.parametrica?.id + " - " + tramiteDetail?.datos?.parametrica?.nombre
            tramiteBinding.txtMatriculaTramite.text = tramiteDetail?.datos?.empresa?.matricula
            tramiteBinding.txtEstadoTramite.text = tramiteDetail.datos?.estado
            val fechaIngresoDate = tramiteDetail.datos?.fechaCreacion?.toDate()
            tramiteBinding.txtFechaIngresoTramite.text = fechaIngresoDate?.formatDate()
            tramiteBinding.txtNombreTramite.text = tramiteDetail.datos?.empresa?.razonSocial
            var estadosHistorial = ""
            var fechasHistorial = ""
            tramiteDetail.datos?.bitacoras?.forEach { currBitacora ->
                estadosHistorial += currBitacora?.operacion + "\n\n"
                fechasHistorial += currBitacora?.fecha?.toDate()?.formatDate() + "\n\n"
            }
            tramiteBinding.txtEstadosHistorial.text = estadosHistorial
            tramiteBinding.txtFechasHistorial.text = fechasHistorial
            // become visible
            setCardsViewVisible()
        }
    }

    fun setCardsViewGone(){
        tramiteBinding.firstCardView.visibility = View.GONE
        tramiteBinding.secondCardView.visibility = View.GONE
        tramiteBinding.txtHistorial.visibility = View.GONE
    }

    fun setCardsViewVisible(){
        tramiteBinding.firstCardView.visibility = View.VISIBLE
        tramiteBinding.secondCardView.visibility = View.VISIBLE
        tramiteBinding.txtHistorial.visibility = View.VISIBLE
    }
}