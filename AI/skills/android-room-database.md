# Skill: Android Room Database

## Description
Expertise in working with Android Room for local data persistence, including entity definition, DAOs, database configuration, migrations, and integration with the repository pattern.

## Project-Specific Patterns

### Entity Definition
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

### DAO with Base Interface
```kotlin
@Dao
abstract class CompanyDao : UtilDao<CompanyEntity> {
    @Query("SELECT * FROM company")
    abstract fun getAll(): List<CompanyEntity>

    @Query("SELECT * FROM company WHERE razonSocial LIKE '%' || :companyName || '%'")
    abstract fun searchCompany(companyName: String): List<CompanyEntity>
}
```

### Generic UtilDao
The `UtilDao<Entity>` interface provides generic CRUD operations:
- `insert(entity)`, `insertOrReplace(entity)`, `insertOrIgnore(entity)`
- `updateOrAbort(entity)`, `updateOrReplace(entity)`, `updateOrIgnore(entity)`
- `delete(entity)`
- Batch versions of all above

### Database Class
```kotlin
@Database(
    entities = [CompanyEntity::class, ApiTimestampEntity::class],
    version = 2,
    exportSchema = true
)
abstract class SeprecDatabase : RoomDatabase() {
    companion object { const val DATABASE_NAME = "seprec_database" }
    abstract fun companyDao(): CompanyDao
    abstract fun apiTimestampDao(): ApiTimestampDao

    object MIGRATION_1_2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""create table ApiTimestampEntity (...)""")
        }
    }
}
```

### Database Creation (Hilt Module)
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(context: Context): SeprecDatabase {
        return Room.databaseBuilder(context, SeprecDatabase::class.java, SeprecDatabase.DATABASE_NAME)
            .addMigrations(SeprecDatabase.MIGRATION_1_2)
            .build()
    }
    
    @Singleton @Provides
    fun providesCompanyDao(db: SeprecDatabase): CompanyDao = db.companyDao()
    
    @Singleton @Provides
    fun providesApiTimestampDao(db: SeprecDatabase): ApiTimestampDao = db.apiTimestampDao()
}
```

### Key Points
- Entities are in `core/room/entity/`
- DAOs are in `core/room/dao/` and extend `UtilDao<T>`
- Database class is `SeprecDatabase` in `core/room/`
- Schema exported to `core/schemas/`
- All Room operations are `suspend` functions
- KSP is used for Room annotation processing
