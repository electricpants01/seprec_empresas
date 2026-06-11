# Skill: Android Retrofit Networking

## Description
Expertise in Retrofit 2 + OkHttp networking for Android, including service interface definition, error handling with sealed classes, interceptors, and Hilt module configuration.

## Project-Specific Patterns

### Service Interface
```kotlin
interface CompanyService {
    @GET("api/empresas/buscarEmpresas")
    suspend fun getCompany(
        @Query("filtro") searchCompany: String,
        @Query("limite") limit: String = "30",
        @Query("pagina") pagina: String = "1"
    ): Response<Company>

    @GET("api/empresas/informacionDirectorioEmpresa/{companyId}/establecimiento/{companyId}")
    suspend fun getCompanyDetail(@Path("companyId") companyId: String): Response<CompanyDetail>

    @GET("api/tramites/buscarTramite")
    suspend fun getTramiteDetail(@Query("codigo") tramiteId: String): Response<TramiteDetail>
}
```

### NetworkResult Sealed Class
```kotlin
sealed class NetworkResult {
    data class NetworkSuccess<T>(val data: T) : NetworkResult()
    data class NetworkEmptyResult<T>(val data: T) : NetworkResult()
    data class NetworkFailure(@StringRes val message: Int) : NetworkResult()
}
```

### Data Source (Error Handling Pattern)
```kotlin
class CompanyServiceDataSource @Inject constructor(
    private val companyService: CompanyService
) : ICompanyService {
    override suspend fun getCompany(searchCompany: String): NetworkResult {
        return try {
            val response = companyService.getCompany(searchCompany)
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.datos?.filas?.isEmpty() == true) {
                    NetworkResult.NetworkEmptyResult(body as Company)
                } else {
                    NetworkResult.NetworkSuccess(body as Company)
                }
            } else {
                NetworkResult.NetworkFailure(R.string.company_search_not_found)
            }
        } catch (e: UnknownHostException) {
            NetworkResult.NetworkFailure(R.string.network_error)
        }
    }
}
```

### Hilt Network Module
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://servicios.seprec.gob.bo/"

    @Singleton @Provides
    fun providesRetrofitInstance(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = Level.BODY })
                .addInterceptor(ChuckerInterceptor.Builder(context).build())
                .build())
            .build()
    }

    @Singleton @Provides
    fun providesCompanyService(retrofit: Retrofit): CompanyService {
        return retrofit.create(CompanyService::class.java)
    }

    @Singleton @Provides
    fun providesCompanyServiceDataSource(companyService: CompanyService): ICompanyService {
        return CompanyServiceDataSource(companyService)
    }
}
```

### Key Points
- All Retrofit calls are `suspend` functions returning `Response<T>`
- Error handling done in DataSource layer, not in ViewModel
- `NetworkFailure` uses `@StringRes` for multilingual error messages
- Chucker interceptor added only in debug builds
- HTTP logging interceptor with `Level.BODY` for debugging
- Gson converter for JSON serialization
