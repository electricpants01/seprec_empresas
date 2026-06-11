# Skill: Android Jetpack Compose UI

## Description
Expertise in building Android UIs using Jetpack Compose with Material 3, including responsive layouts for phones and tablets, custom composables, theming, and previews.

## Project-Specific Patterns

### Composable Structure
```kotlin
@Composable
fun FeatureScreen(
    viewModel: FeatureViewModel = hiltViewModel(),
    // navigation/configuration params
) {
    val state by viewModel.someState.collectAsState()
    
    Scaffold(topBar = { ... }) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            // UI content
        }
    }
}
```

### State Collection
```kotlin
val companyList by homeListViewModel.companyList.collectAsState(initial = Company.empty())
val searchText by homeListViewModel.searchText.collectAsState()
```

### Tablet/Phone Responsive Layout
```kotlin
val windowClass = calculateWindowSizeClass(activity = this)
val isTablet = windowClass.widthSizeClass != WindowWidthSizeClass.Compact

// Use NavigationRail for tablet, NavigationBar for phone
// Adjust padding: start = 80.dp on tablet, 0.dp on phone
```

### Previews with Mock Data
```kotlin
@PreviewLightDark
@Composable
fun PreviewFeatureScreen() {
    val mockNavController = rememberNavController()
    val mockService = object : ICompanyService {
        override suspend fun getCompany(searchCompany: String): NetworkResult {
            return NetworkResult.NetworkSuccess(Company.sample())
        }
        // ...
    }
    val mockRepository = CompanyRepository(mockService)
    val mockViewModel = FeatureViewModel(mockRepository)
    
    FeatureScreen(
        viewModel = mockViewModel,
        navController = mockNavController,
        isTablet = false,
    )
}
```

### Reusable Card Components
- `CompanyCard`: Displays company info with title/content rows
- `CompanyDetailCard`: Detailed company information
- `TramiteCard`: Trámite/procedure details
- `TramiteTrackCard`: Trámite tracking history
- `ModelBottomSheet`: Bottom sheet for location selection

### Theme
- Defined in `ui/theme/Color.kt`, `Theme.kt`, `Type.kt`
- Supports dynamic color on Android 12+
- Uses `SeprecBoliviaTheme` composable wrapper

## Key Dependencies
- `androidx.compose.material3.*`
- `androidx.compose.material3.windowsizeclass.*`
- `androidx.compose.material.icons.*`
- `androidx.hilt.navigation.compose.hiltViewModel()`
- `androidx.navigation.compose.*`
