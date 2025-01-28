package com.locototeam.core.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
data class CompanyEntity(
    @PrimaryKey val id: String,
    val estado: String?,
    val razonSocial: String?,
    val codEstadoActualizacionId: Int?,
    val codEstadoActualizacionNombre: String?,
    val idEstablecimiento: String?,
    val direccionCodDepartamentoId: Int?,
    val direccionCodDepartamentoNombre: String?,
)