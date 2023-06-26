package com.locotodevteam.seprecempresas.entityUtils

import com.locotodevteam.seprecempresas.model.Company
import com.locotodevteam.seprecempresas.room.entity.CompanyEntity


fun Company.toCompanyEntity(): List<CompanyEntity>? {
    return this.datos?.filas?.map { company ->
        CompanyEntity(
            id = company?.id ?: "",
            estado = company?.estado,
            razonSocial = company?.razonSocial,
            codEstadoActualizacionId = company?.codEstadoActualizacion?.id,
            codEstadoActualizacionNombre = company?.codEstadoActualizacion?.nombre,
            idEstablecimiento = company?.idEstablecimiento,
            direccionCodDepartamentoId = company?.direccion?.codDepartamento?.id,
            direccionCodDepartamentoNombre = company?.direccion?.codDepartamento?.nombre
        )
    }
}

fun List<CompanyEntity>.toCompany(): Company {
    val filas = this.map {
        Company.Datos.Fila(
            id = it.id,
            estado = it.estado,
            razonSocial = it.razonSocial,
            codEstadoActualizacion = Company.Datos.Fila.CodEstadoActualizacion(
                id = it.codEstadoActualizacionId,
                nombre = it.codEstadoActualizacionNombre
            ),
            idEstablecimiento = it.idEstablecimiento,
            direccion = Company.Datos.Fila.Direccion(
                codDepartamento = Company.Datos.Fila.Direccion.CodDepartamento(
                    id = it.direccionCodDepartamentoId,
                    nombre = it.direccionCodDepartamentoNombre
                ),
                id = null
            )
        )
    }
    return Company(
        datos = Company.Datos(filas = filas, tipoFiltro = null, total = null),
        finalizado = null,
        mensaje = null
    )
}