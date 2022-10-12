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
import com.locotodevteam.seprecempresas.view.MainActivity

class DetailFragment : Fragment() {

    lateinit var detailBinding: FragmentDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    val args: DetailFragmentArgs by navArgs()
    lateinit var companyId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  inflater.inflate(R.layout.fragment_detail, container, false)
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


    private fun initSubscriptions(){
        detailViewModel.companyDetail.observe(viewLifecycleOwner){companyDetail ->
            detailBinding.txtRazonSocial.text = companyDetail.datos?.razonSocial
            detailBinding.txtMatriculaComercio.text = companyDetail.datos?.matricula
            detailBinding.txtMatriculaAnterior.text = companyDetail.datos?.matriculaAnterior
            detailBinding.txtEstadoMatricula.text = companyDetail.datos?.estado
            var actividades: String = ""
            companyDetail.datos?.objetos_sociales?.forEach { currActividad ->
                actividades += "✔ " + currActividad?.objetoSocial + "\n"
            }
            detailBinding.txtActividad.text = actividades
            detailBinding.txtTipoDeSocietario.text = companyDetail.datos?.codTipoUnidadEconomica?.nombre
            detailBinding.txtDomicilio.text = companyDetail.datos?.direccion?.domicilio
            detailBinding.txtDireccion.text = companyDetail.datos?.direccion?.direccionCompleta
            var contacto: String = ""
            companyDetail.datos?.contactos?.descripcion?.forEach { currContacto ->
                contacto += "✔ " + currContacto?.tipo + ": " + currContacto?.numero + "\n"
            }
            detailBinding.txtContacto.text = contacto
            var correos: String = ""
            companyDetail.datos?.correos?.descripcion?.forEach { currCorreo ->
                correos += "✔ " + currCorreo?.tipo + ": " + currCorreo?.correo + "\n"
            }
            detailBinding.txtCorreo.text = correos
            var actividadesEconomicas: String = ""
            companyDetail.datos?.actividadesEconomicas?.forEach { currActividadEconomica ->
                currActividadEconomica?.forEach { currActividad ->
                    actividadesEconomicas += "✔ " + currActividad?.descripcion + "\n"

                }
            }
            detailBinding.txtActividadesEconomicas.text = actividadesEconomicas
            detailBinding.myContainer.visibility = View.VISIBLE
            detailBinding.myProgressbar.visibility = View.GONE
            detailBinding.lottieNotFound.visibility = View.GONE
        }

        detailViewModel.companyLoadError.observe(viewLifecycleOwner){isError ->
            if(isError){
                val snack = Snackbar.make(detailBinding.root, "No se han encontrado datos", Snackbar.LENGTH_LONG)
                snack.setAnchorView((activity as MainActivity).mainBinding.myBottomNavView)
                snack.show()
                detailBinding.myContainer.visibility = View.GONE
                detailBinding.myProgressbar.visibility = View.GONE
                detailBinding.lottieNotFound.visibility = View.VISIBLE
            }
        }
    }


}