package com.locotodevteam.seprecempresas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.locotodevteam.seprecempresas.R
import com.locotodevteam.seprecempresas.databinding.ItemCompanyBinding
import com.locotodevteam.seprecempresas.model.Company

class CompanyAdapter(private var companies: List<Company.Datos.Fila>, private var listener: CompanyAdapter.OnItemClickListener): RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(companyId: String)
    }

    fun updateList(newList: List<Company.Datos.Fila>?) {
        companies = newList ?: emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_company, parent, false)
        return CompanyViewHolder(view)
    }

    override fun getItemCount(): Int = companies.size

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val item = companies[position]
        holder.render(item, listener)
    }

    class CompanyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemCompanyBinding = ItemCompanyBinding.bind(itemView)

        fun render(item: Company.Datos.Fila, listener: OnItemClickListener) {
            itemCompanyBinding.txtCompanyId.text = item.id
            itemCompanyBinding.txtDireccion.text = item.direccion?.codDepartamento?.nombre
            itemCompanyBinding.txtEstado.text = item.estado
            itemCompanyBinding.txtMatricula.text = item.codEstadoActualizacion?.nombre
            itemCompanyBinding.txtRazonSocial.text = item.razonSocial?.trimStart()
            itemCompanyBinding.myCardView.setOnClickListener{
                listener.onItemClick(item.id ?: "")
            }
        }
    }
}