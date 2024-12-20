package com.locotoinnovations.seprecbolivia.ui.view.bottomNav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    selectedItemIndex: Int,
    onSelectedItemIndexChange: (Int) -> Unit
) {
    NavigationBar {
        bottomNavItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                icon = {
                    BadgedBox(badge = {
                        // no badge
                    }) {
                        Icon(
                            imageVector = if (selectedItemIndex == index) {
                                bottomNavItem.selectedIcon
                            } else {
                                bottomNavItem.unselectedIcon
                            },
                            contentDescription = stringResource(id = bottomNavItem.label)
                        )

                    }
                },
                onClick = {
                    onSelectedItemIndexChange(index)
                },
                alwaysShowLabel = true,
                label = {
                    Text(
                        stringResource(id = bottomNavItem.label)
                    )
                },
                selected = selectedItemIndex == index
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationRailBar(
    selectedItemIndex: Int,
    onSelectedItemIndexChange: (Int) -> Unit
) {
    NavigationRail(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .offset(x = (-1).dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Bottom)
        ) {
            bottomNavItems.forEachIndexed { index, bottomNavItem ->
                NavigationRailItem(
                    icon = {
                        BadgedBox(badge = {
                            // no badge
                        }) {
                            Icon(
                                imageVector = if (selectedItemIndex == index) {
                                    bottomNavItem.selectedIcon
                                } else {
                                    bottomNavItem.unselectedIcon
                                },
                                contentDescription = stringResource(id = bottomNavItem.label)
                            )

                        }
                    },
                    onClick = {
                        onSelectedItemIndexChange(index)
                    },
                    alwaysShowLabel = true,
                    label = {
                        Text(
                            stringResource(id = bottomNavItem.label)
                        )
                    },
                    selected = selectedItemIndex == index
                )
            }
        }
    }
}