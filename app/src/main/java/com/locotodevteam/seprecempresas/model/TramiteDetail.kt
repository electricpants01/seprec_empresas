package com.locotodevteam.seprecempresas.model

data class TramiteDetail(
    val datos: Datos?,
    val finalizado: Boolean?,
    val mensaje: String?
) {
    data class Datos(
        val bitacoras: List<Bitacora?>?,
        val empresa: Empresa?,
        val estado: String?,
        val fechaCreacion: String?,
        val id: Int?,
        val nombre: Nombre?,
        val parametrica: Parametrica?,
        val sucursal: Sucursal?
    ) {
        data class Bitacora(
            val fecha: String?,
            val fechaMiliSegundos: Long?,
            val id: Int?,
            val operacion: String?
        )

        data class Empresa(
            val id: String?,
            val matricula: String?,
            val razonSocial: String?
        )

        data class Nombre(
            val id: Int?,
            val nombre: String?
        )

        data class Parametrica(
            val id: Int?,
            val nombre: String?
        )

        data class Sucursal(
            val descripcion: String?
        )
    }
}