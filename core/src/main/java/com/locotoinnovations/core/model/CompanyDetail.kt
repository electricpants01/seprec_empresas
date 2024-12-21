package com.locotoinnovations.core.model

data class CompanyDetail(
    val datos: Datos?,
    val finalizado: Boolean?,
    val mensaje: String?
) {

    companion object {
        fun empty() = CompanyDetail(null, false, "")

        fun sample() = CompanyDetail(
            datos = Datos(
                actividadesEconomicas = listOf(
                    listOf(
                        Datos.ActividadesEconomica(
                            clasificador = Datos.ActividadesEconomica.Clasificador(
                                codigo = "123",
                                id = "1",
                                tipo = "Primary"
                            ),
                            codigo = "IT001",
                            descripcion = "Information Technology Services",
                            id = "1001",
                            tipo = "Service"
                        )
                    )
                ),
                codEstadoActualizacion = Datos.CodEstadoActualizacion(
                    nombre = "Updated"
                ),
                codTipoUnidadEconomica = Datos.CodTipoUnidadEconomica(
                    nombre = "Private"
                ),
                contactos = Datos.Contactos(
                    descripcion = listOf(
                        Datos.Contactos.Descripcion(
                            numero = "123-456-789",
                            tipo = "Phone"
                        )
                    ),
                    id = "1",
                    tipoContacto = "Primary"
                ),
                correos = Datos.Correos(
                    descripcion = listOf(
                        Datos.Correos.Descripcion(
                            correo = "info@example.com",
                            tipo = "Work"
                        )
                    ),
                    id = "1",
                    tipoContacto = "Primary"
                ),
                direccion = Datos.Direccion(
                    direccionCompleta = "123 Main Street, City, Country",
                    domicilio = "123 Main Street"
                ),
                estado = "Active",
                id = "1234",
                matricula = "56789",
                matriculaAnterior = "98765",
                nit = "100200300",
                objetos_sociales = listOf(
                    Datos.ObjetosSociale(
                        objetoSocial = "Software Development"
                    )
                ),
                razonSocial = "Sample Company"
            ),
            finalizado = true,
            mensaje = "Data retrieved successfully"
        )
    }

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