package com.locotodevteam.seprecempresas.model

data class CompanyDetail(
    val datos: Datos?,
    val finalizado: Boolean?,
    val mensaje: String?
) {
    data class Datos(
        val actividadesEconomicas: List<List<ActividadesEconomica?>?>?,
        val codEstadoActualizacion: CodEstadoActualizacion?,
        val codTipoUnidadEconomica: CodTipoUnidadEconomica?,
        val contactos: Contactos?,
        val correos: Correos?,
        val direccion: Direccion?,
        val estado: String?,
        val id: String?,
        val matricula: String?,
        val matriculaAnterior: String?,
        val nit: String?,
        val objetos_sociales: List<ObjetosSociale?>?,
        val razonSocial: String?
    ) {
        data class ActividadesEconomica(
            val clasificador: Clasificador?,
            val codigo: String?,
            val descripcion: String?,
            val id: String?,
            val tipo: String?
        ) {
            data class Clasificador(
                val codigo: String?,
                val id: String?,
                val tipo: String?
            )
        }

        data class CodEstadoActualizacion(
            val nombre: String?
        )

        data class CodTipoUnidadEconomica(
            val nombre: String?
        )

        data class Contactos(
            val descripcion: List<Descripcion?>?,
            val id: String?,
            val tipoContacto: String?
        ) {
            data class Descripcion(
                val numero: String?,
                val tipo: String?
            )
        }

        data class Correos(
            val descripcion: List<Descripcion?>?,
            val id: String?,
            val tipoContacto: String?
        ) {
            data class Descripcion(
                val correo: String?,
                val tipo: String?
            )
        }

        data class Direccion(
            val direccionCompleta: String?,
            val domicilio: String?
        )

        data class ObjetosSociale(
            val objetoSocial: String?
        )
    }
}