package com.locotodevteam.seprecempresas.view.tramite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.locotodevteam.seprecempresas.R
import com.locotodevteam.seprecempresas.databinding.FragmentTramiteBinding
import com.locotodevteam.seprecempresas.formatDate
import com.locotodevteam.seprecempresas.model.TramiteDetail
import com.locotodevteam.seprecempresas.retrofit.ConnectionResult
import com.locotodevteam.seprecempresas.toDate
import com.locotodevteam.seprecempresas.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TramiteFragment : Fragment() {
    lateinit var tramiteBinding: FragmentTramiteBinding
    val tramiteViewModel by viewModels<TramiteViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_tramite, container, false)
        tramiteBinding = FragmentTramiteBinding.bind(view)
        return tramiteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCardsViewGone()
        initSearchView()
        initSubscriptions()
    }

    fun initSearchView() {
        tramiteBinding.mySearchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { searchText ->
                        setCardsViewGone()
                        tramiteViewModel.getTramiteDetail(searchText)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            }
        )
    }

    fun initSubscriptions() {
        tramiteViewModel.tramiteDetailResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ConnectionResult.NetworkSuccess<*> -> {
                    val tramiteDetail = result.data as TramiteDetail
                    tramiteBinding.apply {
                        txttramiteTitle.text =
                            "Trámite " + tramiteDetail.datos?.parametrica?.id + " - " + tramiteDetail.datos?.parametrica?.nombre
                        txtMatriculaTramite.text = tramiteDetail?.datos?.empresa?.matricula
                        txtEstadoTramite.text = tramiteDetail.datos?.estado
                        val fechaIngresoDate = tramiteDetail.datos?.fechaCreacion?.toDate()
                        txtFechaIngresoTramite.text = fechaIngresoDate?.formatDate()
                        txtNombreTramite.text = tramiteDetail.datos?.empresa?.razonSocial
                        var estadosHistorial = ""
                        var fechasHistorial = ""
                        tramiteDetail.datos?.bitacoras?.forEach { currBitacora ->
                            estadosHistorial += currBitacora?.operacion + "\n\n"
                            fechasHistorial += currBitacora?.fecha?.toDate()?.formatDate() + "\n\n"
                        }
                        txtEstadosHistorial.text = estadosHistorial
                        txtFechasHistorial.text = fechasHistorial
                    }
                    // become visible
                    setCardsViewVisible()
                }

                is ConnectionResult.NetworkFailure -> {
                    Snackbar.make(requireView(), result.message, Snackbar.LENGTH_SHORT)
                        .apply {
                            setAnchorView((activity as MainActivity).mainBinding.myBottomNavView)
                            show()
                        }
                }
            }
        }
    }

    fun setCardsViewGone() {
        tramiteBinding.apply {
            firstCardView.visibility = View.GONE
            secondCardView.visibility = View.GONE
            txtHistorial.visibility = View.GONE
        }
    }

    fun setCardsViewVisible() {
        tramiteBinding.apply {
            firstCardView.visibility = View.VISIBLE
            secondCardView.visibility = View.VISIBLE
            txtHistorial.visibility = View.VISIBLE
        }
    }
}