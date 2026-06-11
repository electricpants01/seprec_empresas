# Skill: Android Hilt Dependency Injection

## Description
Expertise in Dagger Hilt for Android dependency injection, including module definition, component scoping, ViewModel injection, and the library module pattern.

## Project-Specific Patterns

### Application Class
```kotlin
@HiltAndroidApp
class SeprecBoliviaApplication : Application()
```

### Activity Entry Point
```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity()
```

### ViewModel Injection
```kotlin
@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val companyRepository: CompanyRepository,
) : ViewModel()

// Usage in Composable:
fun HomeListScreen(
    homeListViewModel: HomeListViewModel = hiltViewModel(),
    // ...
)
```

### Module Pattern
- All modules use `@Module` + `@InstallIn(SingletonComponent::class)`
- Use `object` modules (not `class`) with `@Provides` functions
- All provides are `@Singleton` scoped

### Network Module (`core/modules/NetworkModule.kt`)
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton @Provides
    fun providesRetrofitInstance(context: Context): Retrofit { ... }
    
    @Singleton @Provides
    fun providesCompanyService(retrofit: Retrofit): CompanyService { ... }
    
    @Singleton @Provides
    fun providesCompanyServiceDataSource(companyService: CompanyService): ICompanyService { ... }
}
```

### Database Module (`core/modules/DatabaseModule.kt`)
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton
    fun provideContext(application: Application): Context { ... }
    
    @Singleton @Provides
    fun providesRoomDatabase(context: Context): SeprecDatabase { ... }
    
    @Singleton @Provides
    fun providesCompanyDao(db: SeprecDatabase): CompanyDao = db.companyDao()
    
    @Singleton @Provides
    fun providesApiTimestampDao(db: SeprecDatabase): ApiTimestampDao = db.apiTimestampDao()
}
```

### Repository Injection
```kotlin
class CompanyRepository @Inject constructor(
    private val companyServiceDataSource: ICompanyService,
) : ICompanyService by companyServiceDataSource
```

### Key Points
- All components scoped as `@Singleton`
- Modules in `core` module are available to `app` module via Hilt
- `hiltViewModel()` is used in composables for ViewModel injection
- `SavedStateHandle` injectable in ViewModels via constructor
- KSP is used (not KAPT) for Hilt annotation processing
