package com.locotodevteam.seprecempresas.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.locotodevteam.seprecempresas.R
import com.locotodevteam.seprecempresas.databinding.FragmentDetailBinding
import com.locotodevteam.seprecempresas.model.CompanyDetail
import com.locotodevteam.seprecempresas.retrofit.ConnectionResult
import com.locotodevteam.seprecempresas.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var detailBinding: FragmentDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    val args: DetailFragmentArgs by navArgs()
    lateinit var companyId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        detailBinding = FragmentDetailBinding.bind(view)
        // get arguments
        companyId = args.companyId
        return detailBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.getCompanyDetail(companyId)
        // navigate up
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initSubscriptions()
    }

    // navigate back when back action bar button is pressed


    private fun initSubscriptions() {
        detailViewModel.companyDetailResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ConnectionResult.NetworkSuccess<*> -> {
                    detailBinding.apply {
                        val companyDetail = result.data as CompanyDetail
                        txtRazonSocial.text = companyDetail.datos?.razonSocial
                        txtMatriculaComercio.text = companyDetail.datos?.matricula
                        txtMatriculaAnterior.text = companyDetail.datos?.matriculaAnterior
                        txtEstadoMatricula.text = companyDetail.datos?.estado
                        var actividades: String = ""
                        companyDetail.datos?.objetos_sociales?.forEach { currActividad ->
                            actividades += "✔ " + currActividad?.objetoSocial + "\n"
                        }
                        txtActividad.text = actividades
                        txtTipoDeSocietario.text =
                            companyDetail.datos?.codTipoUnidadEconomica?.nombre
                        txtDomicilio.text = companyDetail.datos?.direccion?.domicilio
                        txtDireccion.text = companyDetail.datos?.direccion?.direccionCompleta
                        var contacto: String = ""
                        companyDetail.datos?.contactos?.descripcion?.forEach { currContacto ->
                            contacto += "✔ " + currContacto?.tipo + ": " + currContacto?.numero + "\n"
                        }
                        txtContacto.text = contacto
                        var correos: String = ""
                        companyDetail.datos?.correos?.descripcion?.forEach { currCorreo ->
                            correos += "✔ " + currCorreo?.tipo + ": " + currCorreo?.correo + "\n"
                        }
                        txtCorreo.text = correos
                        var actividadesEconomicas: String = ""
                        companyDetail.datos?.actividadesEconomicas?.forEach { currActividadEconomica ->
                            currActividadEconomica?.forEach { currActividad ->
                                actividadesEconomicas += "✔ " + currActividad?.descripcion + "\n"

                            }
                        }
                        txtActividadesEconomicas.text = actividadesEconomicas
                        myContainer.visibility = View.VISIBLE
                        myProgressbar.visibility = View.GONE
                        lottieNotFound.visibility = View.GONE
                    }
                }

                is ConnectionResult.NetworkFailure -> {
                    Snackbar.make(
                        detailBinding.root,
                        result.message,
                        Snackbar.LENGTH_LONG
                    ).apply {
                        setAnchorView((activity as MainActivity).mainBinding.myBottomNavView)
                        show()
                    }
                    detailBinding.apply {
                        myContainer.visibility = View.GONE
                        myProgressbar.visibility = View.GONE
                        lottieNotFound.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


}