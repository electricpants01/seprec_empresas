package com.locotoinnovations.seprecbolivia.ui.view.bottomNav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.locotoinnovations.seprecbolivia.R

internal sealed class BottomNavItem(
    val route: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    @StringRes val label: Int
) {
    object Home : BottomNavItem("search", Icons.Outlined.Search, Icons.Filled.Search, R.string.home)
    object Search : BottomNavItem(
        "tramite",
        Icons.Outlined.AccountBox,
        Icons.Filled.AccountBox,
        R.string.tramite
    )

    object SeprecMap :
        BottomNavItem("map", Icons.Outlined.LocationOn, Icons.Filled.LocationOn, R.string.map)

    object Me :
        BottomNavItem("me", Icons.Outlined.AccountCircle, Icons.Filled.AccountCircle, R.string.info)
}

internal val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Search,
    BottomNavItem.SeprecMap,
    BottomNavItem.Me
)