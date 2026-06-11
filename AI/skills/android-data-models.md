# Skill: Android Data Models

## Description
Expertise in defining Kotlin data classes for API models (JSON deserialization via Gson) and Room entities, including nested data structures, companion object factories, and mapping patterns.

## Project-Specific Patterns

### API Response Model Pattern
All API response models follow this pattern:
```kotlin
data class ModelName(
    val datos: Datos?,
    val finalizado: Boolean?,
    val mensaje: String?
) {
    data class Datos(...)

    companion object {
        fun empty() = ModelName(datos = null, finalizado = null, mensaje = null)
        fun sample() = ModelName(
            datos = Datos(...),
            finalizado = true,
            mensaje = "Sample message"
        )
    }
}
```

### Existing Models
1. **Company** (`core/model/Company.kt`) - Company search results
   - `Company.Datos.Fila` - Individual company row
   - Nested: `CodEstadoActualizacion(id, nombre)`, `Direccion` with `CodDepartamento(id, nombre)`
   - `Fila` fields: codEstadoActualizacion, direccion, estado, id, idEstablecimiento, razonSocial

2. **CompanyDetail** (`core/model/CompanyDetail.kt`) - Detailed company info
   - `Datos` fields: actividadesEconomicas, codEstadoActualizacion, codTipoUnidadEconomica, contactos, correos, direccion, estado, id, matricula, matriculaAnterior, nit, objetos_sociales, razonSocial
   - Complex nested structures with multiple levels

3. **TramiteDetail** (`core/model/TramiteDetail.kt`) - Procedure tracking
   - `Datos` fields: bitacoras, empresa, estado, fechaCreacion, id, nombre, parametrica, sucursal
   - `Bitacora` has: fecha, fechaMiliSegundos, id, operacion

### Room Entity Pattern
```kotlin
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
```

### Timestamp Tracking Entity
```kotlin
@Entity(tableName = ApiTimestampEntity.TABLE_NAME)
data class ApiTimestampEntity(
    @ColumnInfo(name = Column.API_KEY) val apiKey: String,
    @ColumnInfo(name = Column.LAST_SYNCED_TIMESTAMP) val lastSyncedTimestamp: String,
    @PrimaryKey @ColumnInfo(name = Column.ID) val id: String = UUID.randomUUID().toString(),
)
```

### Key Points
- All model fields are nullable (`?`) to handle partial/missing API responses
- Nested data classes defined inside parent class for namespacing
- `empty()` factory provides default empty state for UI initial values
- `sample()` factory provides mock data for previews and testing
- Room entities flatten nested API model structures
- Column names defined via `Column` companion object for type safety
