# Skill: Android Navigation Compose

## Description
Expertise in Jetpack Navigation Compose, including NavHost setup, argument passing, bottom navigation integration, and deep linking.

## Project-Specific Patterns

### Route Definitions
Routes are defined as constants in `MainActivity` companion object:
```kotlin
companion object {
    private const val ARG_NAVIGATION_SEARCH = "search"
    private const val ARG_NAVIGATION_TRAMITE = "tramite"
    private const val ARG_NAVIGATION_MAP = "map"
    private const val ARG_NAVIGATION_ME = "me"
    const val ARG_NAVIGATION_SEARCH_DETAIL = "search_detail"
    const val ARG_NAVIGATION_SEARCH_ID = "id"
}
```

### NavHost Setup
```kotlin
val navController = rememberNavController()

NavHost(
    navController = navController,
    startDestination = ARG_NAVIGATION_SEARCH,
    modifier = Modifier.fillMaxSize().padding(...)
) {
    composable(ARG_NAVIGATION_SEARCH) {
        HomeListScreen(navController = navController, isTablet = isTablet)
    }
    composable(ARG_NAVIGATION_TRAMITE) {
        TramiteScreen()
    }
    composable(ARG_NAVIGATION_MAP) {
        SeprecMapScreen()
    }
    composable(ARG_NAVIGATION_ME) {
        InfoScreen()
    }
    composable(
        route = "${ARG_NAVIGATION_SEARCH_DETAIL}/{${ARG_NAVIGATION_SEARCH_ID}}",
        arguments = listOf(
            navArgument(ARG_NAVIGATION_SEARCH_ID) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        HomeListDetail(onBackClick = { navController.navigateUp() })
    }
}
```

### Navigation with Arguments
```kotlin
navController.navigate("${MainActivity.ARG_NAVIGATION_SEARCH_DETAIL}/${it.id.orEmpty()}")
```

### Reading Arguments in ViewModel
```kotlin
@HiltViewModel
class HomeListDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val companyRepository: CompanyRepository,
) : ViewModel() {
    private val companyId: String = savedStateHandle.get<String>(
        MainActivity.ARG_NAVIGATION_SEARCH_ID
    )!!
}
```

### Bottom Navigation
- `BottomNavItem` sealed class with `route`, icons, and `@StringRes label`
- 4 tabs: Home (search), Trámite (tramite), Map (map), Info (me)
- Navigation triggered via: `navController.navigate(bottomNavItems[it].route)`
- State preserved with `rememberSaveable { mutableIntStateOf(0) }`

### Key Points
- Single `NavController`, no nested navigation graphs
- Arguments passed via route strings with `{param}` syntax
- `SavedStateHandle` used to access navigation arguments in ViewModels
- Back navigation via `navController.navigateUp()`
