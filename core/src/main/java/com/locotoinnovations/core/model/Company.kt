package com.locotoinnovations.core.model

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

    companion object {
        fun empty() = Company(
            datos = null,
            finalizado = null,
            mensaje = null
        )

        fun sample() = Company(
            datos = Datos(
                filas = listOf(
                    Datos.Fila(
                        codEstadoActualizacion = Datos.Fila.CodEstadoActualizacion(
                            id = 1,
                            nombre = "Active"
                        ),
                        direccion = Datos.Fila.Direccion(
                            codDepartamento = Datos.Fila.Direccion.CodDepartamento(
                                id = 101,
                                nombre = "Santa Cruz"
                            ),
                            id = "D001"
                        ),
                        estado = "Open",
                        id = "F001",
                        idEstablecimiento = "E001",
                        razonSocial = "Sample Company A"
                    ),
                    Datos.Fila(
                        codEstadoActualizacion = Datos.Fila.CodEstadoActualizacion(
                            id = 2,
                            nombre = "Inactive"
                        ),
                        direccion = Datos.Fila.Direccion(
                            codDepartamento = Datos.Fila.Direccion.CodDepartamento(
                                id = 102,
                                nombre = "La Paz"
                            ),
                            id = "D002"
                        ),
                        estado = "Closed",
                        id = "F002",
                        idEstablecimiento = "E002",
                        razonSocial = "Sample Company B"
                    )
                ),
                tipoFiltro = "Sample Filter",
                total = 2
            ),
            finalizado = true,
            mensaje = "Sample message"
        )
    }
}
