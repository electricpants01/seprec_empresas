package com.locotodevteam.seprecempresas.model

data class Company(
    val datos: Datos?,
    val finalizado: Boolean?,
    val mensaje: String?
) {
    data class Datos(
        val filas: List<Fila?>?,
        val tipoFiltro: String?,
        val total: Int?
    ) {
        data class Fila(
            val codEstadoActualizacion: CodEstadoActualizacion?,
            val direccion: Direccion?,
            val estado: String?,
            val id: String?,
            val idEstablecimiento: String?,
            val razonSocial: String?
        ) {
            data class CodEstadoActualizacion(
                val id: Int?,
                val nombre: String?
            )

            data class Direccion(
                val codDepartamento: CodDepartamento?,
                val id: String?
            ) {
                data class CodDepartamento(
                    val id: Int?,
                    val nombre: String?
                )
            }
        }
    }
}