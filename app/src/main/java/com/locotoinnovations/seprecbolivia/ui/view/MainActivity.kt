package com.locotoinnovations.seprecbolivia.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.locotoinnovations.seprecbolivia.R
import com.locotoinnovations.seprecbolivia.ui.theme.SeprecBoliviaTheme
import com.locotoinnovations.seprecbolivia.ui.view.bottomNav.BottomNavigationBar
import com.locotoinnovations.seprecbolivia.ui.view.bottomNav.BottomNavigationRailBar
import com.locotoinnovations.seprecbolivia.ui.view.bottomNav.bottomNavItems
import com.locotoinnovations.seprecbolivia.ui.view.screen.Contact.InfoScreen
import com.locotoinnovations.seprecbolivia.ui.view.screen.Tramite.TramiteScreen
import com.locotoinnovations.seprecbolivia.ui.view.screen.Tramite.TramiteViewModel
import com.locotoinnovations.seprecbolivia.ui.view.screen.homeList.HomeListScreen
import com.locotoinnovations.seprecbolivia.ui.view.screen.homeList.HomeListViewModel
import com.locotoinnovations.seprecbolivia.ui.view.screen.map.SeprecMapScreen
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeprecBoliviaTheme {
            }
            var selectedItemIndex by rememberSaveable {
                mutableIntStateOf(0)
            }
            var navController = rememberNavController()
            val homeListViewModel: HomeListViewModel by viewModels()
            val tramiteViewModel: TramiteViewModel by viewModels()
            //
            val windowClass = calculateWindowSizeClass(activity = this)
            val isTablet = windowClass.widthSizeClass != WindowWidthSizeClass.Compact
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(stringResource(id = R.string.app_name))
                        },
                        modifier = Modifier.padding(
                            start = if (isTablet) 80.dp else 0.dp
                        )
                    )
                },
                bottomBar = {
                    if (isTablet) {
                        BottomNavigationRailBar(
                            selectedItemIndex = selectedItemIndex,
                            onSelectedItemIndexChange = {
                                selectedItemIndex = it
                                navController.navigate(bottomNavItems[it].route)
                            }
                        )
                    } else {
                        BottomNavigationBar(
                            selectedItemIndex = selectedItemIndex
                        ) {
                            selectedItemIndex = it
                            navController.navigate(bottomNavItems[it].route)
                        }
                    }
                }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "search",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = if(!isTablet) it else PaddingValues(0.dp, 0.dp, 0.dp, 56.dp))
                        .padding(
                            start = if (isTablet) 80.dp else 0.dp,
                            top = if (isTablet) 72.dp else 0.dp
                        )
                ) {
                    composable("search") {
                        HomeListScreen(homeListViewModel = homeListViewModel)
                    }
                    composable("tramite") {
                        TramiteScreen(tramiteViewModel = tramiteViewModel)
                    }
                    composable("map") {
                        SeprecMapScreen()
                    }
                    composable("me") {
                        InfoScreen()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SeprecBoliviaTheme {
    }
}
