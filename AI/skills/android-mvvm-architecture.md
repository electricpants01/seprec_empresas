# Skill: Android MVVM Architecture

## Description
Expertise in Android MVVM architecture with Clean Architecture principles, including ViewModel patterns, repository pattern, state management with StateFlow, and event handling.

## Project-Specific Patterns

### Architecture Layers
```
View (Composable) → ViewModel → Repository → DataSource → Service (Retrofit)
                                                 ↘ DAO (Room)
```

### ViewModel Pattern
```kotlin
@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val repository: CompanyRepository,
) : ViewModel() {

    // State
    private val _data = MutableStateFlow(InitialState)
    val data = _data.asStateFlow()

    // Events (one-shot)
    private val _event = MutableStateFlow<FeatureEvent?>(null)
    val event = _event.asStateFlow()

    init { loadData() }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getData()
            withContext(Dispatchers.Main) {
                when (result) {
                    is NetworkResult.NetworkSuccess<*> -> _data.value = result.data as Type
                    is NetworkResult.NetworkEmptyResult<*> -> _event.emit(FeatureEvent.Empty)
                    is NetworkResult.NetworkFailure -> _event.emit(FeatureEvent.Error)
                }
            }
        }
    }

    sealed class FeatureEvent {
        data class ShowError(val message: String) : FeatureEvent()
        object Empty : FeatureEvent()
    }
}
```

### Repository Pattern (Delegation)
```kotlin
interface ICompanyService {
    suspend fun getCompany(searchCompany: String): NetworkResult
    suspend fun getCompanyDetail(companyId: String): NetworkResult
    suspend fun getTramiteDetail(tramiteId: String): NetworkResult
}

class CompanyRepository @Inject constructor(
    private val companyServiceDataSource: ICompanyService,
) : ICompanyService by companyServiceDataSource
```

### State Collection in Composable
```kotlin
val state by viewModel.data.collectAsState(initial = InitialState)

// Events (one-shot) collected via LaunchedEffect
LaunchedEffect(viewModel.event) {
    viewModel.event.collectLatest { event ->
        when (event) {
            is FeatureEvent.ShowError -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            null -> Unit
        }
    }
}
```

### Key Points
- Data flows one direction: View ← ViewModel ← Repository ← DataSource
- `MutableStateFlow` for mutable state, `.asStateFlow()` for public exposure
- Events use nullable `StateFlow<Event?>` with `collectLatest` for one-shot events
- `viewModelScope` for coroutine scoping (auto-cancelled when ViewModel cleared)
- `Dispatchers.IO` for data operations, `Dispatchers.Main` for state updates
- Sealed classes for typed events within ViewModels
- Repository delegates to interface via `by` keyword
